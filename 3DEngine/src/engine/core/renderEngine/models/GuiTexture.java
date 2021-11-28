/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.models;

import engine.core.tools.maths.vectors.Vector2f;

public class GuiTexture {

    private final int texture;
    private Vector2f pos = new Vector2f(0, 0);
    private Vector2f scale = new Vector2f(0, 0);

    public GuiTexture(int texture) {
        this.texture = texture;
    }

    public GuiTexture(int texture, Vector2f pos, Vector2f scale) {
        this.texture = texture;
        this.pos = pos;
        this.scale = scale;
    }

    public GuiTexture(GuiTexture original) {
        this(original.getTexture());
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }
}
