package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.impl.networks.EnergyNetwork;
import com.portingdeadmods.examplemod.impl.networks.EnergyTransportHandler;
import com.thepigcat.transportlib.TransportLib;
import com.thepigcat.transportlib.api.TransferSpeed;
import com.thepigcat.transportlib.api.TransportNetwork;
import com.thepigcat.transportlib.impl.TransportNetworkImpl;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class IRNetworks {
    public static final DeferredRegister<TransportNetwork<?>> NETWORKS = DeferredRegister.create(TransportLib.NETWORK_REGISTRY, TransportLib.MODID);

    public static final Supplier<EnergyNetwork> ENERGY = NETWORKS.register("energy", () -> EnergyNetwork.build(TransportNetworkImpl.builder(EnergyTransportHandler.INSTANCE)
            //.synced(ByteBufCodecs.INT)
            .interactorCheck((level, pos, dir) -> {
                BlockEntity be = level.getBlockEntity(pos.relative(dir));
                if (be != null) {
                    return level.getCapability(IRCapabilities.ENERGY_BLOCK, be.getBlockPos(), be.getBlockState(), be, dir) != null;
                }
                return false;
            })
            .transferSpeed(TransferSpeed::instant))
    );

}
