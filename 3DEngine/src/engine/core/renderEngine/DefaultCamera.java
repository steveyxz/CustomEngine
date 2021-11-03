package engine.core.renderEngine;

import engine.core.global.Global;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.currentScene;

public class DefaultCamera extends Camera {
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
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            setYaw(getYaw(sceneId) - DisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            setYaw(getYaw(sceneId) + DisplayManager.getFrameTimeSeconds() * 30, sceneId);
        }
    }
}
