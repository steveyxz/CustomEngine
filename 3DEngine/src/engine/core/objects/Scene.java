/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects;

import engine.core.objects.gui.components.GuiComponent;
import engine.core.objects.lighting.Light;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.text.fontMeshCreator.Text;
import engine.core.text.fontRendering.TextMaster;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    public static SceneManager sceneManager = new SceneManager();

    private final Map<TexturedModel, List<GameObject>> objects = new HashMap<>();
    private final Map<GuiTexture, List<GuiComponent>> guis = new HashMap<>();
    private final List<Text> texts = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private final String sceneId;
    private Vector3f cameraPos = new Vector3f();

    public Scene(String sceneId) {
        this.sceneId = sceneId;
        sceneManager.addScene(sceneId, this);
    }

    public Vector3f getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(Vector3f cameraPos) {
        this.cameraPos = cameraPos;
    }

    public void addText(Text text) {
        texts.add(text);
        TextMaster.loadText(text);
        reloadTexts();
    }

    public void removeText(Text text) {
        texts.remove(text);
        TextMaster.removeText(text);
        reloadTexts();
    }

    public void clearTexts() {
        texts.clear();
        reloadTexts();
    }

    public void reloadTexts() {
        TextMaster.clearTexts();
        for (Text t : texts) {
            TextMaster.loadText(t);
        }
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void removeLight(Light light) {
        lights.remove(light);
    }

    public void clearLights() {
        lights.clear();
    }

    public List<Light> getLights() {
        return lights;
    }

    public void processObject(GameObject entity) {
        TexturedModel model = entity.getModel();
        List<GameObject> batch = objects.get(model);
        if (batch != null) {
            batch.add(entity);
            objects.put(model, batch);
        } else {
            List<GameObject> newBatch = new ArrayList<>();
            newBatch.add(entity);
            objects.put(model, newBatch);
        }
        entity.setSceneId(getSceneId());
    }

    public void processGui(GuiComponent entity) {
        GuiTexture model = entity.getTexture();
        List<GuiComponent> batch = guis.get(model);
        if (batch != null) {
            batch.add(entity);
            guis.put(model, batch);
        } else {
            List<GuiComponent> newBatch = new ArrayList<>();
            newBatch.add(entity);
            guis.put(model, newBatch);
        }
        entity.setSceneID(getSceneId());
    }

    public Map<TexturedModel, List<GameObject>> getObjects() {
        return objects;
    }

    public Map<GuiTexture, List<GuiComponent>> getGuis() {
        return guis;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void removeObject(GameObject object) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(objects.keySet().toArray()[i]).remove(object);
        }
    }

    public void removeGui(GuiComponent object) {
        for (int i = 0; i < guis.size(); i++) {
            guis.get(guis.keySet().toArray()[i]).remove(object);
        }
    }

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            List<GameObject> gameObjects = objects.get(objects.keySet().toArray()[i]);
            for (int j = 0; j < gameObjects.size(); j++) {
                gameObjects.get(j).tick();
            }
        }
        for (int i = 0; i < guis.size(); i++) {
            List<GuiComponent> guiComponents = guis.get(guis.keySet().toArray()[i]);
            for (int j = 0; j < guiComponents.size(); j++) {
                guiComponents.get(j).tick();
            }
        }
        MasterRenderer.camera.tick();
    }

    public void transformGuis() {
        for (int i = 0; i < guis.size(); i++) {
            List<GuiComponent> guiComponents = guis.get(guis.keySet().toArray()[i]);
            for (int j = 0; j < guiComponents.size(); j++) {
                guiComponents.get(j).transform();
            }
        }
    }

    public void render() {
        transformGuis();
        MasterRenderer.render();
    }
}
