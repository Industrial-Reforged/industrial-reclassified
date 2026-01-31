package com.portingdeadmods.examplemod.impl.networks;

import com.mojang.serialization.Codec;
import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.thepigcat.transportlib.api.TransportNetwork;
import com.thepigcat.transportlib.api.Transporting;
import com.thepigcat.transportlib.api.TransportingHandler;
import com.thepigcat.transportlib.impl.TransportingImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyTransportHandler implements TransportingHandler<Integer> {
    public static final EnergyTransportHandler INSTANCE = new EnergyTransportHandler();

    private EnergyTransportHandler() {
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }

    @Override
    public boolean validTransportValue(Integer value) {
        return value >= 0;
    }

    @Override
    public List<Integer> split(Integer value, int amount) {
        // TODO: Only split for interactors we can actually interact with
        return splitNumberEvenly(value, amount);
    }

    @Override
    public @Nullable Integer join(Integer value0, Integer value1) {
        if (value1 > 0 && value0 > Integer.MAX_VALUE - value1) {
            return null;
        } else if (value1 < 0 && value0 < Integer.MIN_VALUE - value1) {
            return null;
        }
        return value0 + (int) value1;
    }

    @Override
    public Integer remove(Integer value, Integer toRemove) {
        return Math.max(value - toRemove, this.defaultValue());
    }

    @Override
    public Codec<Integer> valueCodec() {
        return Codec.INT;
    }

    @Override
    public Integer receive(ServerLevel level, BlockPos interactorPos, Direction direction, Integer value) {
        BlockEntity blockEntity = level.getBlockEntity(interactorPos);

        if (blockEntity != null) {
            EnergyHandler energyHandler = level.getCapability(IRCapabilities.ENERGY_BLOCK, interactorPos, blockEntity.getBlockState(), blockEntity, direction);
            if (energyHandler != null) {
                int filled = energyHandler.fillEnergy(value, false);
                return this.remove(value, filled);
            }
        }
        return value;
    }

    @Override
    public Transporting<Integer> createTransporting(TransportNetwork<Integer> network) {
        return new TransportingImpl<>(network);
    }

    private static List<Integer> splitNumberEvenly(int number, int parts) {
        if (parts <= 0) {
            throw new IllegalArgumentException("Number of parts must be greater than 0");
        }

        List<Integer> result = NonNullList.withSize(parts, 0);
        int remainder = number % parts;
        int quotient = number / parts;

        for (int i = 0; i < remainder; i++) {
            result.set(i, quotient + 1);
        }

        for (int i = remainder; i < parts; i++) {
            result.set(i, quotient);
        }

        return result;
    }
}
