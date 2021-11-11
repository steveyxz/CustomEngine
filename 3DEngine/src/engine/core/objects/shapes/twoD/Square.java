/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.shapes.twoD;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;

import static engine.core.global.Global.*;

public class Square extends TwoDObject {
    public Square(Vector2f position, Vector2f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(squareVertices, squareTextureCoords, squareIndices, squareNormals), texture));
    }
}
