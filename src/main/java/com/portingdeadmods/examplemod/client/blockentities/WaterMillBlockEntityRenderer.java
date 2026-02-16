package com.portingdeadmods.examplemod.client.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.blockentities.WaterMillBlockEntity;
import com.portingdeadmods.examplemod.content.blockentities.WindMillBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WaterMillBlockEntityRenderer implements BlockEntityRenderer<WaterMillBlockEntity> {
    private final BakedModel waterMillBladeModel;
    private final ModelBlockRenderer modelRenderer;

    public WaterMillBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelRenderer = context.getBlockRenderDispatcher().getModelRenderer();
        this.waterMillBladeModel = Minecraft.getInstance().getModelManager().getModel(IndustrialReclassified.WATERMILL_BLADE_MODEL);
    }

    @Override
    public void render(WaterMillBlockEntity windMillBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Direction direction = windMillBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        int light = LevelRenderer.getLightColor(windMillBlockEntity.getLevel(), windMillBlockEntity.getBlockPos().relative(direction));
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot())); // face correct direction
            poseStack.mulPose(Axis.XP.rotationDegrees(90)); // orient blades upright

            // --- Apply rotation (spin blades) ---
            float speed = 15.0f;
            float rotation = (windMillBlockEntity.getLevel().getGameTime() + partialTicks) * speed; // speed factor
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation == 0 ? 45 : rotation));

            // --- Center model for rendering ---
            poseStack.translate(-0.5, -0.5, -0.5);

            poseStack.translate(0, 1, 0);
            this.modelRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.SOLID), null, this.waterMillBladeModel, 255, 255, 255, light, packedOverlay);
        }
        poseStack.popPose();
    }
}
