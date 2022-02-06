/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.models;

import engine.core.renderEngine.Loader;

public class ModelTexture {

    private int textureID;
    private int specularMapId = -1;
    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public ModelTexture(int textureID, boolean hasTransparency) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
    }

    public ModelTexture(int textureID, int specularMapId, boolean hasTransparency) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
        this.specularMapId = specularMapId;
    }

    public ModelTexture(int textureID, boolean hasTransparency, boolean useFakelighting) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
        this.useFakeLighting = useFakelighting;
    }

    public ModelTexture(int textureID, int specularMapId, boolean hasTransparency, boolean useFakelighting) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
        this.useFakeLighting = useFakelighting;
        this.specularMapId = specularMapId;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public int getSpecularMapId() {
        return specularMapId;
    }
}
