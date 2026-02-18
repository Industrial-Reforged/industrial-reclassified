package com.portingdeadmods.examplemod.api.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.capabilities.StorageChangedListener;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.content.menus.ChargingSlot;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeInput;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.ItemOutputComponentFlag;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
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

public class MachineBlockEntity extends ContainerBlockEntity implements RedstoneBlockEntity, WrenchListenerBlockEntity {
    private final List<ChargingSlot> chargingSlots;
    private final List<BlockCapabilityCache<EnergyHandler, Direction>> caches;
    protected final IRMachine machine;
    private EnergyHandler euStorage;
    private RedstoneSignalType redstoneSignalType = RedstoneSignalType.IGNORED;
    private int redstoneSignalStrength;
    protected MachineRecipe cachedRecipe;
    protected float progress;
    protected float progressIncrement;
    protected boolean burnt;
    private boolean removedByWrench;

    public MachineBlockEntity(IRMachine machine, BlockPos blockPos, BlockState blockState) {
        super(machine.getBlockEntityType(), blockPos, blockState);
        this.machine = machine;
        this.caches = new ArrayList<>();
        this.chargingSlots = new ArrayList<>();
        this.progressIncrement = 1F;
    }

    public MachineRecipeLayout<?> getRecipeLayout() {
        return this.machine.getRecipeLayout();
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

    public boolean isBurnt() {
        return burnt;
    }

    public void setBurnt(boolean burnt) {
        this.burnt = burnt;
        this.updateData();
    }

    protected int getResultSlot() {
        return 1;
    }

    protected int getResultTank() {
        return 0;
    }

    protected @NotNull MachineRecipeInput createRecipeInput() {
        return new MachineRecipeInput(List.of(this.getItemHandler().getStackInSlot(0)));
    }

    protected void onItemsChanged(int slot) {
        this.updateData();

        this.refreshCachedRecipe();
    }

    protected void onFluidsChanged(int tank) {
        this.updateData();

        this.refreshCachedRecipe();
    }

    protected void onEuChanged(int oldAmount) {
        this.updateData();

        this.refreshCachedRecipe();
    }

    protected void tickRecipe() {
        if (this.cachedRecipe != null) {
            if (this.getProgress() >= this.getMaxProgress()) {
                this.progress = 0;
                RegistryAccess provider = this.level.registryAccess();
                ItemStack resultItem = this.cachedRecipe.assemble(this.createRecipeInput(), provider);
                ItemOutputComponentFlag output = this.cachedRecipe.getComponentByFlag(IRRecipeComponentFlags.ITEM_OUTPUT);
                boolean hasResultEnergy = this.cachedRecipe.hasResultEnergy(provider);
                boolean hasResultItem = this.cachedRecipe.hasResultItem(provider);
                if (hasResultEnergy) {
                    this.getEuStorage().forceFillEnergy(this.cachedRecipe.assembleEnergy(this.createRecipeInput(), provider), false);
                }
                if (hasResultItem && output.isOutputted(this.level.random, 0)) {
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

    @Override
    public void dropItems(IItemHandler handler) {
        if (!this.removedByWrench && handler != null) {
            super.dropItems(handler);
        } else {
            this.removedByWrench = false;
        }
    }

    public int getRedstoneSignalStrength() {
        return redstoneSignalStrength;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);

        this.burnt = tag.getBoolean("burnt");
        this.redstoneSignalStrength = tag.getInt("signal_strength");
        this.redstoneSignalType = RedstoneSignalType.CODEC.decode(NbtOps.INSTANCE, tag.get("redstone_signal")).result().orElse(Pair.of(RedstoneSignalType.IGNORED, new CompoundTag())).getFirst();
        this.progress = tag.getFloat("progress");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);

        tag.putBoolean("burnt", this.burnt);
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

    @Override
    public void beforeRemoveByWrench(Player player) {
        this.removedByWrench = true;
    }

    private void refreshCachedRecipe() {
        MachineRecipeInput recipeInput = this.createRecipeInput();
        MachineRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.getRecipeLayout().getRecipeType(), recipeInput, this.level)
                .map(RecipeHolder::value)
                .orElse(null);
        RegistryAccess provider = this.level.registryAccess();
        if (recipe != null) {
            if (recipe.hasResultItem(provider)) {
                if (!forceInsertItem((IItemHandlerModifiable) this.getItemHandler(), this.getResultSlot(), recipe.assemble(recipeInput, provider).copy(), true, i -> {
                }).isEmpty()) {
                    this.cachedRecipe = null;
                    return;
                }
            }
            if (recipe.hasResultFluid(provider)) {
                FluidStack resultFluid = recipe.assembleFluid(recipeInput, provider);
                if (forceFillTank(this.getFluidHandler(), resultFluid.copy(), IFluidHandler.FluidAction.SIMULATE, i -> {
                }) != resultFluid.getAmount()) {
                    this.cachedRecipe = null;
                    return;
                }
            }
            if (recipe.hasResultEnergy(provider)) {
                int resultEnergy = recipe.assembleEnergy(recipeInput, provider);
                if (this.getEuStorage().forceFillEnergy(resultEnergy, true) != resultEnergy) {
                    this.cachedRecipe = null;
                    return;
                }
            }
        }
        this.cachedRecipe = recipe;
    }

    public int forceFillTank(IFluidHandler fluidHandler, FluidStack resource, IFluidHandler.FluidAction action, Consumer<Integer> onChange) {
        if (resource.isEmpty()) {
            return 0;
        }

        FluidStack fluid = fluidHandler.getFluidInTank(0);
        int capacity = fluidHandler.getTankCapacity(0);

        if (action.simulate()) {
            if (fluid.isEmpty()) {
                return Math.min(capacity, resource.getAmount());
            }
            if (!FluidStack.isSameFluidSameComponents(fluid, resource)) {
                return 0;
            }
            return Math.min(capacity - fluid.getAmount(), resource.getAmount());
        }
        if (fluid.isEmpty()) {
            fluid = resource.copyWithAmount(Math.min(capacity, resource.getAmount()));
            onChange.accept(0);
            return fluid.getAmount();
        }
        if (!FluidStack.isSameFluidSameComponents(fluid, resource)) {
            return 0;
        }
        int filled = capacity - fluid.getAmount();

        if (resource.getAmount() < filled) {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        } else {
            fluid.setAmount(capacity);
        }
        if (filled > 0)
            onChange.accept(0);
        return filled;
    }

}
