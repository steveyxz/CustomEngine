/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine;

import engine.core.global.Global;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.currentScene;

public class DefaultCamera extends Camera {

    private Vector3f downMovement = new Vector3f(0, -Global.movementSpeed, 0);
    private Vector3f upMovement = new Vector3f(0, Global.movementSpeed, 0);

    @Override
    public void tick() {

    }

    @Override
    public void frame() {
        String sceneId = currentScene.getSceneId();
        Vector3f currentPos = getPositions().get(sceneId);
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId));
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(movement.x, movement.y, movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId));
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId) - 90);
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId) + 90);
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            downMovement.set(0, -Global.movementSpeed, 0);
            downMovement.scale(DisplayManager.getFrameTimeSeconds());
            currentPos.translate(downMovement.x, downMovement.y, downMovement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            upMovement.set(0, Global.movementSpeed, 0);
            upMovement.scale(DisplayManager.getFrameTimeSeconds());
            currentPos.translate(upMovement.x, upMovement.y, upMovement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            setYaw(getYaw(sceneId) - DisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            setYaw(getYaw(sceneId) + DisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
    }
}
