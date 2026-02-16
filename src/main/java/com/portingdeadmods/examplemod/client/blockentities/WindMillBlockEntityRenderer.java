package com.portingdeadmods.examplemod.client.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.examplemod.IndustrialReclassified;
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

public class WindMillBlockEntityRenderer implements BlockEntityRenderer<WindMillBlockEntity> {
    private final BakedModel windMillBladeModel;
    private final ModelBlockRenderer modelRenderer;

    public WindMillBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelRenderer = context.getBlockRenderDispatcher().getModelRenderer();
        this.windMillBladeModel = Minecraft.getInstance().getModelManager().getModel(IndustrialReclassified.WINDMILL_BLADE_MODEL);
    }

    @Override
    public void render(WindMillBlockEntity windMillBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Direction direction = windMillBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        int light = LevelRenderer.getLightColor(windMillBlockEntity.getLevel(), windMillBlockEntity.getBlockPos().relative(direction));
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot())); // face correct direction
            poseStack.mulPose(Axis.XP.rotationDegrees(90)); // orient blades upright

            // --- Apply rotation (spin blades) ---
            int y = windMillBlockEntity.getBlockPos().getY() - windMillBlockEntity.getLevel().getMinBuildHeight();
            int height = windMillBlockEntity.getLevel().getMaxBuildHeight() - windMillBlockEntity.getLevel().getMinBuildHeight();
            float speed = ((float) y / height) * 50.0f;
            float rotation = (windMillBlockEntity.getLevel().getGameTime() + partialTicks) * speed; // speed factor
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation == 0 ? 45 : rotation));

            // --- Center model for rendering ---
            poseStack.translate(-0.5, -0.5, -0.5);

            poseStack.translate(0, 1, 0);
            this.modelRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.SOLID), null, this.windMillBladeModel, 255, 255, 255, light, packedOverlay);
        }
        poseStack.popPose();
    }
}
