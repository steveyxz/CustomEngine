/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.lighting;

import engine.core.tools.maths.vectors.Vector3f;

public class SpotLight extends Light {

    private Vector3f direction;
    private float cutOff;
    private float outerCutOff;

    public SpotLight(Vector3f lightPos, Vector3f color) {
        super(lightPos, color);
    }

    public SpotLight(Vector3f lightPos, Vector3f color, Vector3f attenuation) {
        super(lightPos, color, attenuation);
    }

    public SpotLight(Vector3f lightPos, Vector3f color, Vector3f attenuation, Vector3f direction, float cutOff, float outerCutOff) {
        super(lightPos, color, attenuation);
        this.direction = direction;
        this.cutOff = cutOff;
        this.outerCutOff = outerCutOff;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }

    public float getOuterCutOff() {
        return outerCutOff;
    }

    public void setOuterCutOff(float outerCutOff) {
        this.outerCutOff = outerCutOff;
    }
}
