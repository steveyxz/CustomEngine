/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.flappybird;

import engine.addons.gameLoopManager.Game;
import engine.core.global.Global;
import engine.core.objects.Scene;
import engine.core.objects.lighting.PointLight;
import engine.core.objects.lighting.SpotLight;
import engine.core.objects.shapes.threeD.ThreeDObject;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.ObjectFileLoader;
import engine.core.renderEngine.camera.BasicCamera;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector3f;

import java.io.FileNotFoundException;
import java.util.Random;

import static engine.core.renderEngine.renderers.MasterRenderer.camera;

public class FlappyBird extends Game {

    private SpotLight playerLight;

    public FlappyBird() {
        super(800, 600, true, "Lighting test");
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    @Override
    protected void preLoop() {
        camera = new BasicCamera();
        Scene s = new Scene("main");
        SpotLight light = new SpotLight(new Vector3f(3, 3, 3), new Vector3f(1, 0.5f, 0.5f), new Vector3f(1, 0.02f, 0.02f));
        PointLight light2 = new PointLight(new Vector3f(3, 3, 3), new Vector3f(1, 0.5f, 0.5f), new Vector3f(1, 0.2f, 0.2f));
        this.playerLight = light;
        s.addSpotLight(light);
        //s.addPointLight(light2);
        s.setBackgroundColor(new Vector3f(1, 0.5f, 0.5f));
        try {
            Random r = new Random();
            for (int i = 0; i < 100; i++) {
                ThreeDObject box = new ThreeDObject(new Vector3f(r.nextInt(10), r.nextInt(10), r.nextInt(10)), new Vector3f(r.nextInt(360), r.nextInt(360), r.nextInt(360)), 1.5f, new TexturedModel(ObjectFileLoader.loadOBJToRawModel("textures/cube"), new ModelTexture(Loader.loadTexture("textures/green"))));
                box.setReflectivity(1);
                box.setShineDamper(32);
                s.addObject(box);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scene.sceneManager.changeScene("main");
    }

    @Override
    protected void preTick() {
        playerLight.setCutOff(12);
        playerLight.setOuterCutOff(15);
        playerLight.setDirection(camera.getVectorDirection(Global.currentScene.getSceneId()));
        playerLight.setLightPos(camera.getPosition(Global.currentScene.getSceneId()));
    }
}
