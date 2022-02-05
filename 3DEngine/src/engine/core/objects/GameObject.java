/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects;

import engine.core.objects.components.Component;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {

    //Texturing
    public final TexturedModel model;
    private final List<Component> components = new ArrayList<>();
    //Positioning (per tick)
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f velocity;
    public float scale;
    //Scene Info
    public String sceneId = "";
    //Lighting
    private float shineDamper = 1;
    private float reflectivity = 0;

    public GameObject(Vector3f position, Vector3f rotation, float scale, TexturedModel model) {
        this.position = position;
        this.model = model;
        this.scale = scale;
        this.rotation = rotation;
        this.velocity = new Vector3f(0, 0, 0);
    }

    public void move(float x, float y, float z) {
        position.translate(x, y, z);
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void tick() {
        position.translate(velocity.x, velocity.y, velocity.z);
        for (Component c : components) {
            c.tick();
        }
    }

    public void frame() {
        for (Component c : components) {
            c.frame();
        }
    }

    public void addComponent(Component c) {
        if (c.parent() != this) {
            return;
        }
        this.components.add(c);
    }

    public void removeComponent(Component c) {
        this.components.remove(c);
    }

}
