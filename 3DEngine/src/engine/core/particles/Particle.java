/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.particles;


import engine.core.global.Global;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;
import engine.core.tools.maths.vectors.Vector4f;

import java.util.Random;

import static engine.core.global.Global.currentScene;
import static engine.core.renderEngine.renderers.MasterRenderer.camera;

public class Particle {

    private final Vector3f position;
    private final Vector3f speed;
    private final float gravityEffect;
    private final float lifeLength;
    private final float rotation;
    private final float scale;
    private final Vector4f color;
    private final ParticleTexture texture;
    private final Vector3f velocity = new Vector3f();
    private final Vector2f texOffset1 = new Vector2f();
    private final Vector2f texOffset2 = new Vector2f();
    private float elapsedTime = 0;
    private float distance;
    private float blend;

    public Particle(ParticleTexture texture, Vector3f position, Vector3f speed, float gravityEffect, float lifeLength, float offset, float rotation, float scale) {
        this.position = position;
        this.speed = speed;
        this.gravityEffect = gravityEffect;
        Random random = new Random();
        this.lifeLength = (float) ((lifeLength - offset) + random.nextDouble() * ((lifeLength + offset) - (lifeLength - offset)));
        this.rotation = rotation;
        this.scale = scale;
        this.texture = texture;
        this.color = new Vector4f(0, 0, 0, 0);
        ParticleMaster.addParticle(this);
    }

    public Particle(Vector4f color, Vector3f position, Vector3f speed, float gravityEffect, float lifeLength, float offset, float rotation, float scale) {
        this.position = position;
        this.speed = speed;
        this.gravityEffect = gravityEffect;
        Random random = new Random();
        this.lifeLength = (float) ((lifeLength - offset) + random.nextDouble() * ((lifeLength + offset) - (lifeLength - offset)));
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;
        this.texture = new ParticleTexture(Integer.MAX_VALUE, 0);
        ParticleMaster.addParticle(this);
    }

    public Vector2f getTexOffset1() {
        return texOffset1;
    }

    public float getDistance() {
        return distance;
    }

    public Vector2f getTexOffset2() {
        return texOffset2;
    }

    public float getBlend() {
        return blend;
    }

    public Vector4f getColor() {
        return color;
    }

    public ParticleTexture getTexture() {
        return texture;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    protected boolean update() {
        speed.y += Global.PARTICLE_GRAVITY * gravityEffect * GLFWDisplayManager.getFrameTimeSeconds();
        velocity.set(speed);
        velocity.scale(GLFWDisplayManager.getFrameTimeSeconds());
        Vector3f.add(velocity, position, position);
        distance = Vector3f.sub(camera.getPositions().get(currentScene.getSceneId()), position, null).lengthSquared();
        if (color.w == 0) {
            updateTextureCoordInfo();
        }
        elapsedTime += GLFWDisplayManager.getFrameTimeSeconds();
        return elapsedTime < lifeLength;
    }

    private void updateTextureCoordInfo() {
        float lifeFactor = elapsedTime / lifeLength;
        int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
        float atlasProgress = lifeFactor * stageCount;
        int index1 = (int) Math.floor(atlasProgress);
        int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
        this.blend = atlasProgress % 1;
        setTextureOffset(texOffset1, index1);
        setTextureOffset(texOffset2, index2);
    }

    private void setTextureOffset(Vector2f offset, int index) {
        int column = index % texture.getNumberOfRows();
        int row = index / texture.getNumberOfRows();
        offset.x = column / (texture.getNumberOfRows() * 1f);
        offset.y = row / (texture.getNumberOfRows() * 1f);
    }
}
