/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects;

import engine.core.global.Global;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.HashMap;

import static engine.core.renderEngine.renderers.MasterRenderer.camera;

public class SceneManager {

    public HashMap<String, Scene> scenes = new HashMap<>();

    public void addScene(String sceneID, Scene scene) {
        scenes.put(sceneID, scene);
        if (!camera.getPositions().containsKey(sceneID)) {
            camera.getPositions().put(sceneID, new Vector3f(0, 0, 0));
        }
    }

    public void removeScene(String sceneID) {
        scenes.remove(sceneID);
    }

    public Scene getScene(String sceneID) {
        return scenes.get(sceneID);
    }

    public void changeScene(String sceneID) {
        Scene scene = scenes.get(sceneID);
        if (scene != null) {
            scene.reloadTexts();
            Global.currentScene = scene;
        }
    }

}
