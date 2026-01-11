package com.portingdeadmods.examplemod.api.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.capabilities.StorageChangedListener;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.content.menus.ChargingSlot;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MachineBlockEntity extends ContainerBlockEntity implements RedstoneBlockEntity {
    private final List<ChargingSlot> chargingSlots;
    private final List<BlockCapabilityCache<EnergyHandler, Direction>> caches;
    private EnergyHandler euStorage;
    private RedstoneSignalType redstoneSignalType = RedstoneSignalType.IGNORED;
    private int redstoneSignalStrength;

    public MachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.caches = new ArrayList<>();
        this.chargingSlots = new ArrayList<>();
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

    public int getRedstoneSignalStrength() {
        return redstoneSignalStrength;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);

        this.redstoneSignalStrength = tag.getInt("signal_strength");
        this.redstoneSignalType = RedstoneSignalType.CODEC.decode(NbtOps.INSTANCE, tag.get("redstone_signal")).result().orElse(Pair.of(RedstoneSignalType.IGNORED, new CompoundTag())).getFirst();
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);

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
