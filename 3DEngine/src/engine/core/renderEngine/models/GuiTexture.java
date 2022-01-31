/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.models;

import engine.core.tools.maths.vectors.Vector2f;

public class GuiTexture {

    private final int texture;
    private int globalZIndex = 0;

    public GuiTexture(int texture) {
        this.texture = texture;
    }

    public GuiTexture(int texture, int globalZIndex) {
        this.texture = texture;
        this.globalZIndex = globalZIndex;
    }

    public GuiTexture(GuiTexture original) {
        this(original.getTexture(), original.globalZIndex());
    }

    public int getTexture() {
        return texture;
    }

    public int globalZIndex() {
        return globalZIndex;
    }

    public void setGlobalZIndex(int globalZIndex) {
        this.globalZIndex = globalZIndex;
    }

}
