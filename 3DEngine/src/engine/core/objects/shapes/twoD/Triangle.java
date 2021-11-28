/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.shapes.twoD;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector2f;

import static engine.core.global.Global.*;

public class Triangle extends TwoDObject {
    public Triangle(Vector2f position, Vector2f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(triangleVertices, triangleTextureCoords, triangleIndices, triangleNormals), texture));
    }
}
