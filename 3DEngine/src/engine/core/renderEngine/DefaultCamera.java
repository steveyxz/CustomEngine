package engine.core.renderEngine;

import engine.core.global.Global;
import engine.core.objects.Scene;
import engine.core.objects.SceneManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.currentScene;

public class DefaultCamera extends Camera {
    @Override
    public void tick() {

    }

    @Override
    public void frame() {
        Vector3f currentPos = getPositions().get(currentScene.getSceneId());
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            Vector3f movement = degreeToDirection(360 - getYaw());
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(movement.x, movement.y, movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Vector3f movement = degreeToDirection(360 - getYaw());
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            Vector3f movement = degreeToDirection(360 - getYaw() - 90);
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Vector3f movement = degreeToDirection(360 - getYaw() + 90);
            movement.scale(DisplayManager.getFrameTimeSeconds() * Global.movementSpeed);
            currentPos.translate(-movement.x, -movement.y, -movement.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            setYaw(getYaw() - DisplayManager.getFrameTimeSeconds() * 30);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            setYaw(getYaw() + DisplayManager.getFrameTimeSeconds() * 30);
        }
    }
}
