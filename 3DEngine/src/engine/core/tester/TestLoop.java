package engine.core.tester;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.BasicObject;
import engine.core.objects.GameObject;
import engine.core.objects.Scene;
import engine.core.objects.lighting.Light;
import engine.core.objects.shapes.twoD.Square;
import engine.core.renderEngine.DefaultCamera;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.ObjParser;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.text.FontGlobal;
import engine.core.text.fontMeshCreator.Text;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class TestLoop extends Game {
    public static void main(String[] args) {
        new TestLoop();
    }

    @Override
    protected void preLoop() {
        MasterRenderer.camera = new DefaultCamera();
        Scene scene = new Scene("pog");
        scene.addLight(new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));
        scene.processObject(new Square(new Vector2f(0, 0), new Vector2f(0, 0), 1, new ModelTexture(Loader.loadTexture("icons/tank"))));
        Scene.sceneManager.changeScene("pog");
    }
}
