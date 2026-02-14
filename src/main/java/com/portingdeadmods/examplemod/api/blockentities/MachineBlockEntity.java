package com.portingdeadmods.examplemod.api.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.capabilities.StorageChangedListener;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponentFlag;
import com.portingdeadmods.examplemod.content.menus.ChargingSlot;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeInput;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import com.portingdeadmods.examplemod.registries.IRRecipeLayouts;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MachineBlockEntity extends ContainerBlockEntity implements RedstoneBlockEntity {
    protected final MachineRecipeLayout<?> recipeLayout;
    private final List<ChargingSlot> chargingSlots;
    private final List<BlockCapabilityCache<EnergyHandler, Direction>> caches;
    private EnergyHandler euStorage;
    private RedstoneSignalType redstoneSignalType = RedstoneSignalType.IGNORED;
    private int redstoneSignalStrength;
    protected MachineRecipe cachedRecipe;
    protected float progress;
    protected float progressIncrement;

    public MachineBlockEntity(IRMachine machine, BlockPos blockPos, BlockState blockState) {
        super(machine.getBlockEntityType(), blockPos, blockState);
        this.recipeLayout = machine.getRecipeLayout();
        this.caches = new ArrayList<>();
        this.chargingSlots = new ArrayList<>();
        this.progressIncrement = 1F;
    }

    public MachineRecipe getCachedRecipe() {
        return cachedRecipe;
    }

    @Override
    public void tick() {
        super.tick();

        for (ChargingSlot chargingSlot : this.chargingSlots) {
            if (chargingSlot.getItem().isEmpty()) continue;

            this.tickChargingSlot(chargingSlot);
        }

        if (this.shouldSpreadEnergy() && !level.isClientSide()) {
            int amountPerBlock = this.getAmountPerBlock();

            for (BlockCapabilityCache<EnergyHandler, Direction> cache : this.caches) {
                EnergyHandler energyStorage = cache.getCapability();
                if (energyStorage != null) {
                    int filled = energyStorage.fillEnergy(amountPerBlock, false);
                    getEuStorage().drainEnergy(filled, false);
                }
            }
        }

        this.tickRecipe();

    }

    protected int getResultSlot() {
        return 1;
    }

    protected @NotNull MachineRecipeInput createRecipeInput() {
        return new MachineRecipeInput(List.of(this.getItemHandler().getStackInSlot(0)));
    }

    protected void onItemsChanged(int slot) {
        this.updateData();

        MachineRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeLayout.getRecipeType(), this.createRecipeInput(), this.level)
                .map(RecipeHolder::value)
                .orElse(null);
        if (recipe != null && forceInsertItem((IItemHandlerModifiable) this.getItemHandler(), this.getResultSlot(), recipe.getResultItem(this.level.registryAccess()).copy(), true, i -> {
        }).isEmpty()) {
            this.cachedRecipe = recipe;
        } else {
            this.cachedRecipe = null;
        }
    }

    protected void tickRecipe() {
        if (this.cachedRecipe != null) {
            if (this.getProgress() >= this.getMaxProgress()) {
                this.progress = 0;
                ItemStack resultItem = this.cachedRecipe.getResultItem(this.level.registryAccess());
                OutputComponentFlag output = this.cachedRecipe.getComponentByFlag(IRRecipeComponentFlags.OUTPUT);
                if (output.isOutputted(this.level.random, 0)) {
                    this.forceInsertItem((IItemHandlerModifiable) this.getItemHandler(), this.getResultSlot(), resultItem.copy(), false, this::onItemsChanged);
                }
                this.getItemHandler().extractItem(0, 1, false);
            } else {
                this.progress += this.progressIncrement;
            }
        }
    }

    protected int getAmountPerBlock() {
        int blocks = 0;
        for (BlockCapabilityCache<EnergyHandler, Direction> cache : this.caches) {
            if (cache.getCapability() != null) {
                blocks++;
            }
        }
        int amountPerBlock;
        if (getEuStorage().getEnergyStored() >= getEuStorage().getMaxOutput() * blocks) {
            amountPerBlock = getEuStorage().getMaxOutput();
        } else {
            amountPerBlock = getEuStorage().getEnergyStored() / blocks;
        }
        return amountPerBlock;
    }

    public void addChargingSlot(ChargingSlot chargingSlot) {
        this.chargingSlots.add(chargingSlot);
    }

    protected void tickChargingSlot(ChargingSlot slot) {
        ItemStack itemStack = slot.getItem();
        EnergyHandler energyStorage = this.getEuStorage();
        EnergyHandler itemEnergyStorage = itemStack.getCapability(IRCapabilities.ENERGY_ITEM);
        if (itemEnergyStorage != null && !level.isClientSide()) {
            if (slot.getMode() == ChargingSlot.ChargeMode.CHARGE) {
                int filled = itemEnergyStorage.fillEnergy(Math.min(itemEnergyStorage.getMaxInput(), energyStorage.getMaxOutput()), true);
                int drained = energyStorage.forceDrainEnergy(filled, true);
                int newFilled = itemEnergyStorage.fillEnergy(drained, false);
                energyStorage.forceDrainEnergy(newFilled, false);
            } else {
                int drained = itemEnergyStorage.drainEnergy(Math.min(itemEnergyStorage.getMaxOutput(), energyStorage.getMaxInput()), true);
                int filled = energyStorage.fillEnergy(drained, true);
                int newDrained = itemEnergyStorage.drainEnergy(filled, false);
                energyStorage.fillEnergy(newDrained, false);
            }
        }
    }

    public int getProgress() {
        return (int) this.progress;
    }

    public int getMaxProgress() {
        return this.cachedRecipe != null ? this.cachedRecipe.getComponent(TimeComponent.TYPE).time() : 0;
    }

    public boolean shouldSpreadEnergy() {
        return false;
    }

    protected <H extends EnergyHandler> H addEuStorage(H handler) {
        return this.addHandler(IRCapabilities.ENERGY_BLOCK.name(), handler);
    }

    protected final <T extends EnergyHandler & StorageChangedListener> void addEuStorage(Function<Supplier<? extends EnergyTier>, T> energyHandlerConstructor, Supplier<? extends EnergyTier> energyTier, int energyCapacity, Consumer<Integer> onChangedFunction) {
        T storage = energyHandlerConstructor.apply(energyTier);
        storage.setOnChangedFunction(onChangedFunction);
        storage.setEnergyCapacity(energyCapacity);
        this.addEuStorage(storage);
    }

    public EnergyHandler getEuStorage() {
        return this.getHandler(IRCapabilities.ENERGY_BLOCK);
    }

    @Override
    public void dropItems(IItemHandler handler) {
        if (handler != null) {
            super.dropItems(handler);
        }
    }

    @Override
    public int emitRedstoneLevel() {
        return 0;
    }

    @Override
    public void setRedstoneSignalType(RedstoneSignalType redstoneSignalType) {
        this.redstoneSignalType = redstoneSignalType;
        this.updateData();
    }

    @Override
    public RedstoneSignalType getRedstoneSignalType() {
        return redstoneSignalType;
    }

    public void setRedstoneSignalStrength(int redstoneSignalStrength) {
        this.redstoneSignalStrength = redstoneSignalStrength;
    }

    public void initCapCache() {
        if (level instanceof ServerLevel serverLevel) {
            for (Direction direction : Direction.values()) {
                this.caches.add(BlockCapabilityCache.create(IRCapabilities.ENERGY_BLOCK, serverLevel, worldPosition.relative(direction), direction));
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.initCapCache();
    }

    public int getRedstoneSignalStrength() {
        return redstoneSignalStrength;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);

        this.redstoneSignalStrength = tag.getInt("signal_strength");
        this.redstoneSignalType = RedstoneSignalType.CODEC.decode(NbtOps.INSTANCE, tag.get("redstone_signal")).result().orElse(Pair.of(RedstoneSignalType.IGNORED, new CompoundTag())).getFirst();
        this.progress = tag.getFloat("progress");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);

        tag.putFloat("progress", this.progress);
        tag.putInt("signal_strength", this.redstoneSignalStrength);
        Optional<Tag> tag1 = RedstoneSignalType.CODEC.encodeStart(NbtOps.INSTANCE, this.redstoneSignalType).result();
        tag1.ifPresent(value -> {
            tag.put("redstone_signal", value);
        });
    }

    public @Nullable EnergyHandler getEuHandlerOnSide(@Nullable Direction direction) {
        return this.getEuStorage();
    }
}
