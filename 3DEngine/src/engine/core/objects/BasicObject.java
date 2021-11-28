/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects;

import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector3f;

public class BasicObject extends GameObject {
    public BasicObject(Vector3f position, Vector3f rotation, float scale, TexturedModel model) {
        super(position, rotation, scale, model);
    }
}
