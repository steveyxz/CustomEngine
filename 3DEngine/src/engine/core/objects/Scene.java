/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects;

import engine.core.global.Global;
import engine.core.objects.gui.components.GuiComponent;
import engine.core.objects.lighting.PointLight;
import engine.core.objects.lighting.SpotLight;
import engine.core.renderEngine.camera.Camera;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.renderEngine.text.fontMeshCreator.Text;
import engine.core.renderEngine.text.fontRendering.TextMaster;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.*;

import static engine.core.renderEngine.renderers.MasterRenderer.camera;

public class Scene {

    public static SceneManager sceneManager = new SceneManager();

    private final Map<TexturedModel, List<GameObject>> objects = new HashMap<>();
    private final Map<GuiTexture, List<GuiComponent>> guis = new HashMap<>();
    private final List<Text> texts = new ArrayList<>();
    private final List<PointLight> pointLights = new ArrayList<>();
    private final List<SpotLight> spotLights = new ArrayList<>();
    private final String sceneId;
    private Vector3f cameraPos = new Vector3f();
    private Vector3f backgroundColor = new Vector3f(0, 0, 0);
    private Vector3f ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);

    public Scene(String sceneId) {
        this.sceneId = sceneId;
        sceneManager.addScene(this);
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

    public void addSpotLight(SpotLight light) {
        spotLights.add(light);
    }

    public void removeSpotLight(SpotLight light) {
        spotLights.remove(light);
    }

    public void clearSpotLights() {
        spotLights.clear();
    }

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void addPointLight(PointLight light) {
        pointLights.add(light);
    }

    public void removePointLight(PointLight light) {
        pointLights.remove(light);
    }

    public void clearPointLights() {
        pointLights.clear();
    }

    public List<PointLight> getPointLights() {
        return pointLights;
    }

    public void addObject(GameObject entity) {
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

    public void addObjects(GameObject... entities) {
        for (GameObject g : entities) {
            addObject(g);
        }
    }

    public void addGui(GuiComponent entity) {
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

    public void addGuis(GuiComponent... entities) {
        for (GuiComponent g : entities) {
            addGui(g);
        }
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

    @SuppressWarnings("all")
    public void removeObject(GameObject object) {
        for (int i = 0; i < objects.size(); i++) {
            Object key = objects.keySet().toArray()[i];
            objects.get(key).remove(object);
            if (objects.get(key).size() < 1) {
                objects.remove(key);
            }
        }
    }

    @SuppressWarnings("all")
    public void removeGui(GuiComponent object) {
        for (int i = 0; i < guis.size(); i++) {
            Object key = guis.keySet().toArray()[i];
            guis.get(key).remove(object);
            if (guis.get(key).size() < 1) {
                guis.remove(key);
            }
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
        if (camera.enabled()) {
            camera.tick();
            camera.updateCameraVectors();
        }

    }

    public void sortLights() {
        Camera c = camera;
        Vector3f cPos = c.getPositions().get(Global.currentScene.getSceneId());
        spotLights.sort((o1, o2) -> {
            Vector3f o1Pos = o1.getLightPos();
            Vector3f o2Pos = o2.getLightPos();
            o1Pos.translate(cPos.x, cPos.y, cPos.z);
            o2Pos.translate(cPos.x, cPos.y, cPos.z);
            o1Pos.set(Math.abs(o1Pos.x), Math.abs(o1Pos.y), Math.abs(o1Pos.z));
            o2Pos.set(Math.abs(o2Pos.x), Math.abs(o2Pos.y), Math.abs(o2Pos.z));
            return 0;
        });
        pointLights.sort((o1, o2) -> {
            Vector3f o1Pos = o1.getLightPos();
            Vector3f o2Pos = o2.getLightPos();
            o1Pos.translate(cPos.x, cPos.y, cPos.z);
            o2Pos.translate(cPos.x, cPos.y, cPos.z);
            o1Pos.set(Math.abs(o1Pos.x), Math.abs(o1Pos.y), Math.abs(o1Pos.z));
            o2Pos.set(Math.abs(o2Pos.x), Math.abs(o2Pos.y), Math.abs(o2Pos.z));
            return 0;
        });
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

    public Vector3f backgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector3f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }
}
