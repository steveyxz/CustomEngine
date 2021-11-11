/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.particles;

public class ParticleTexture {

    private final int textureID;
    private final int numberOfRows;

    public ParticleTexture(int textureID, int numberOfRows) {
        this.textureID = textureID;
        this.numberOfRows = numberOfRows;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
