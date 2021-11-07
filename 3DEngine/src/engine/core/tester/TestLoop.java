package engine.core.tester;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.lighting.Light;
import engine.core.objects.shapes.twoD.Square;
import engine.core.renderEngine.DefaultCamera;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.renderers.MasterRenderer;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

public class TestLoop extends Game {
    public static void main(String[] args) {
        new TestLoop();
    }

    @Override
    protected void preLoop() {
        MasterRenderer.camera = new DefaultCamera();
        Scene scene = new Scene("pog");
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            scene.processObject(new Square(new Vector2f(rand.nextInt(100), rand.nextInt(100)), new Vector2f(0, 0), 1, new ModelTexture(Loader.loadTexture("icons/tank"))));
        }
        scene.addLight(new Light(new Vector3f(0, 0, 10), new Vector3f(1, 1, 1)));
        Scene.sceneManager.changeScene("pog");
    }
}
