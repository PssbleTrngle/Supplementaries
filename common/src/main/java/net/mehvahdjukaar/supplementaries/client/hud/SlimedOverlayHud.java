package net.mehvahdjukaar.supplementaries.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.mehvahdjukaar.supplementaries.common.entities.ISlimeable;
import net.mehvahdjukaar.supplementaries.reg.ModTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public abstract class SlimedOverlayHud {
    protected final Minecraft mc;

    protected SlimedOverlayHud(Minecraft minecraft) {
        this.mc = minecraft;
    }

    protected void render(GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {

        float alpha = ISlimeable.getAlpha(mc.player, partialTicks);
        if (alpha > 0) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
            int slide = (int) (screenHeight / 3f * (1 - alpha));
            blit(graphics, ModTextures.SLIME_GUI_OVERLAY, 0, 0, -90, 0.0F, 0.0F,
                    screenWidth, screenHeight + slide, screenWidth, screenHeight + slide);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    //just needed for float texture size
    public void blit(GuiGraphics gui, ResourceLocation atlasLocation, int x, int y, int blitOffset, float uOffset, float vOffset,
                     float uWidth, float vHeight, int textureWidth, int textureHeight) {
        this.blit(gui, atlasLocation, x, x + uWidth, y, y + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public void blit(GuiGraphics gui, ResourceLocation atlasLocation, float x1, float x2, float y1, float y2, int blitOffset,
                     float uWidth, float vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(gui, atlasLocation, x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }

    public void innerBlit(GuiGraphics gui, ResourceLocation atlasLocation, float x1, float x2, float y1, float y2,
                          float blitOffset, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = gui.pose().last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x1, y1, blitOffset).uv(minU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, x1, y2, blitOffset).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, x2, y2, blitOffset).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, x2, y1, blitOffset).uv(maxU, minV).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}
