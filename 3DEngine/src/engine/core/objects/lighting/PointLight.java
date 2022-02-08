/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.lighting;

import engine.core.tools.maths.vectors.Vector3f;

public class PointLight extends Light {
    public PointLight(Vector3f lightPos, Vector3f color) {
        super(lightPos, color);
    }

    public PointLight(Vector3f lightPos, Vector3f color, Vector3f attenuation) {
        super(lightPos, color, attenuation);
    }
}
