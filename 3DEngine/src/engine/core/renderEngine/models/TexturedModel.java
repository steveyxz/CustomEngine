/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.models;

public class TexturedModel {

    private final RawModel rawModel;
    private ModelTexture texture;
    private int globalZIndex = 0;

    /**
     * Represents a textured model
     *
     * @param model   The raw model which this textured model is based on.
     * @param texture The texture which will then be applied to the raw model.
     */
    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.texture = texture;
    }

    public int getGlobalZIndex() {
        return globalZIndex;
    }

    public void setGlobalZIndex(int globalZIndex) {
        this.globalZIndex = globalZIndex;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }
}
