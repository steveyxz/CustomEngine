/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.flappybird;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.lighting.Light;
import engine.core.objects.shapes.threeD.ThreeDObject;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.OBJFileLoader;
import engine.core.renderEngine.camera.BasicCamera;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.tools.maths.vectors.Vector3f;

import java.io.FileNotFoundException;

public class FlappyBird extends Game {

    public FlappyBird() {
        super(800, 600, true, "Lighting test");
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    @Override
    protected void preLoop() {
        MasterRenderer.camera = new BasicCamera();
        Scene s = new Scene("main");
        s.setAmbientLight(0.2f);
        s.addLight(new Light(new Vector3f(3, 3, 3), new Vector3f(1, 0.5f, 0.5f)));
        s.setBackgroundColor(new Vector3f(0.5f, 0.5f, 1f));
        try {
            ThreeDObject box = new ThreeDObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1, new TexturedModel(OBJFileLoader.loadOBJToRawModel("textures/dragon"), new ModelTexture(Loader.loadTexture("textures/green"), false)));
            box.setReflectivity(1);
            box.setShineDamper(32);
            s.addObject(box);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scene.sceneManager.changeScene("main");
    }
}
