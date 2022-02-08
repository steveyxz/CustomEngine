/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.models;

import engine.core.renderEngine.Loader;

public class ModelTexture {

    private int textureID;
    private int specularMapId = -1;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public ModelTexture(int textureID, int specularMapId) {
        this.textureID = textureID;
        this.specularMapId = specularMapId;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public int getSpecularMapId() {
        return specularMapId;
    }
}
