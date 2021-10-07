package engine.core.tester;

import engine.addons.gameLoopManager.Game;
import engine.core.global.Global;
import engine.core.objects.BasicObject;
import engine.core.objects.GameObject;
import engine.core.objects.Scene;
import engine.core.objects.gui.constraints.types.OppRelativeConstraint;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.objects.gui.constraints.types.ValueConstraint;
import engine.core.objects.lighting.Light;
import engine.core.objects.shapes.twoD.Square;
import engine.core.particles.ParticleSystem;
import engine.core.particles.ParticleTexture;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.ObjParser;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.text.FontGlobal;
import engine.core.text.fontMeshCreator.Text;
import engine.core.text.fontRendering.TextMaster;
import org.lwjgl.input.Keyboard;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

public class TestLoop extends Game {

    public static void main(String[] args) {
        new TestLoop();
    }

    @Override
    protected void preLoop() {

        MasterRenderer.camera = new TesterCamera();

        //Create Scenes
        Scene scene = new Scene("start");
        Scene scene2 = new Scene("main");

        Text t = new Text("bruh", 10, FontGlobal.timesNewRoman, new Vector2f(0, 0), 10, false, 2);
        t.setColour(1, 1, 1);
        scene.addText(t);

        GameObject testObj2 = new BasicObject(new Vector3f(0, 0, -10), new Vector3f(0, 0, 0), 10, new TexturedModel(ObjParser.load("textures/dragon"), new ModelTexture(Loader.loadTexture("textures/white"))));
        testObj2.setReflectivity(0.1f);
        testObj2.setShineDamper(0.2f);
        scene2.processObject(testObj2);
        scene2.addLight(new Light(new Vector3f(0, 0, 0), new Vector3f(0.5f, 0, 0.9f)));
        scene2.addLight(new Light(new Vector3f(0, 0, -40), new Vector3f(1, 1, 0.2f)));

        Scene.sceneManager.changeScene("start");
    }

    @Override
    protected void preTick() {
        if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            Scene.sceneManager.changeScene("start");
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            Scene.sceneManager.changeScene("main");
        }
    }
}
