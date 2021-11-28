/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.lighting;


import engine.core.tools.maths.vectors.Vector3f;

public class Light {

    private Vector3f lightPos;
    private Vector3f color;

    private Vector3f attenuation = new Vector3f(1, 0, 0);

    public Light(Vector3f lightPos, Vector3f color) {
        this.lightPos = lightPos;
        this.color = color;
    }

    public Light(Vector3f lightPos, Vector3f color, Vector3f attenuation) {
        this.lightPos = lightPos;
        this.color = color;
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getLightPos() {
        return lightPos;
    }

    public void setLightPos(Vector3f lightPos) {
        this.lightPos = lightPos;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
