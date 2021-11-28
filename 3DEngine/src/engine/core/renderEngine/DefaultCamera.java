/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine;

import engine.core.global.Global;
import engine.core.input.KeyboardInputMethods;
import engine.core.tools.maths.vectors.Vector3f;
import org.lwjgl.glfw.GLFW;

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
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_W)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId));
            movement.scale(GLFWDisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(movement.x, movement.y, movement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_S)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId));
            movement.scale(GLFWDisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_A)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId) - 90);
            movement.scale(GLFWDisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_D)) {
            Vector3f movement = degreeToDirection(360 - getYaw(sceneId) + 90);
            movement.scale(GLFWDisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            downMovement.set(0, -Global.movementSpeed, 0);
            downMovement.scale(GLFWDisplayManager.getFrameTimeSeconds());
            currentPos.translate(downMovement.x, downMovement.y, downMovement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            upMovement.set(0, Global.movementSpeed, 0);
            upMovement.scale(GLFWDisplayManager.getFrameTimeSeconds());
            currentPos.translate(upMovement.x, upMovement.y, upMovement.z);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            setYaw(getYaw(sceneId) - GLFWDisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            setYaw(getYaw(sceneId) + GLFWDisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
    }
}
