package engine.core.tester;

import engine.core.renderEngine.DefaultCamera;
import engine.core.renderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.currentScene;
import static engine.core.global.Global.movementSpeed;

public class TesterCamera extends DefaultCamera {
    @Override
    public void tick() {
    }

    @Override
    public void frame() {
        super.frame();
        Vector3f currentPos = getPositions().get(currentScene.getSceneId());
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            currentPos.y -= movementSpeed * DisplayManager.getFrameTimeSeconds();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            currentPos.y += movementSpeed * DisplayManager.getFrameTimeSeconds();
        }
    }
}
