/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.renderers;

import engine.core.objects.Scene;
import engine.core.objects.gui.components.GuiComponent;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.models.RawModel;
import engine.core.renderEngine.shaders.gui.GuiShader;
import engine.core.tools.maths.TransformationMaths;
import engine.core.tools.maths.vectors.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.*;

public class GuiRenderer {

    private final RawModel quad;
    private final GuiShader shader;

    public GuiRenderer() {
        this.shader = new GuiShader();
        float[] position = new float[]{-1, 1, -1, -1, 1, 1, 1, -1};
        quad = Loader.loadToVAO(position);
    }

    private List<GuiTexture> sort(Set<GuiTexture> entities) {
        List<GuiTexture> returned = new ArrayList<>(entities);
        returned.sort(Comparator.comparing(GuiTexture::globalZIndex));
        return returned;
    }

    public void render(Scene scene) {
        Map<GuiTexture, List<GuiComponent>> guis = scene.getGuis();
        Set<GuiTexture> t = guis.keySet();
        List<GuiTexture> sortedKeys = sort(t);
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (GuiTexture texture : sortedKeys) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
            for (GuiComponent comp : guis.get(texture)) {
                Matrix4f matrix = TransformationMaths.createTransformationMatrix(comp.getFinalPosition(), comp.getFinalDimensions());
                shader.loadTransformation(matrix);
                GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            }
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void prepare() {
        shader.start();
    }

    public void cleanUp() {
        shader.stop();
    }
}
