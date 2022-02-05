/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.flappybird.objects;

import engine.core.objects.components.animation.FrameAnimationComponent;
import engine.core.objects.shapes.twoD.TwoDObject;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector2f;

import static engine.core.global.Global.*;

public class Bird extends TwoDObject {
    public Bird(Vector2f position, Vector2f rotation) {
        super(position, rotation, 1, new TexturedModel(Loader.loadToVAO(squareVertices, squareTextureCoords, squareIndices, squareNormals), new ModelTexture(Loader.loadTexture("textures/bird/bird0"))));
        this.addComponent(new FrameAnimationComponent(this, 5, "textures/bird/bird0", "textures/bird/bird1", "textures/bird/bird2", "textures/bird/bird3"));
    }
}
