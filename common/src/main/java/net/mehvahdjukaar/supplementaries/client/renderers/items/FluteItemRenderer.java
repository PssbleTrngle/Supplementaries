package net.mehvahdjukaar.supplementaries.client.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.moonlight.api.client.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class FluteItemRenderer extends ItemStackRenderer {

    public FluteItemRenderer() {
        super();
    }

    //TODO: re add

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack matrixStack,
                             MultiBufferSource buffer, int light, int overlay) {
        if (!stack.isEmpty()) {

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            matrixStack.pushPose();
            boolean gui = transform == ItemTransforms.TransformType.GUI || transform == ItemTransforms.TransformType.GROUND || transform == ItemTransforms.TransformType.FIXED;
                /*
            BakedModel model;
            if (gui) {
                model = itemRenderer.getItemModelShaper().getModelManager().getModel(ClientRegistry.FLUTE_2D_MODEL);
            }
            else{
                model = itemRenderer.getItemModelShaper().getModelManager().getModel(ClientRegistry.FLUTE_3D_MODEL);
            }


            if (model.isLayered()) {
                ForgeHooksClient.drawItemLayered(itemRenderer, model, stack, matrixStack, buffer, light, overlay, true); }
            else {
                RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, true);
                VertexConsumer vertexconsumer;
                vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, stack.hasFoil());

                itemRenderer.renderModelLists(model, stack, light, overlay, matrixStack, vertexconsumer);
            }

             */
            matrixStack.popPose();
        }

    }


}
