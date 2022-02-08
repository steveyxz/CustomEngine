/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.renderers;

import engine.core.objects.GameObject;
import engine.core.objects.Scene;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.renderEngine.camera.Camera;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.RawModel;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.renderEngine.shaders.object.ObjectShader;
import engine.core.tools.maths.FastTrig;
import engine.core.tools.maths.TransformationMaths;
import engine.core.tools.maths.vectors.Matrix4f;
import engine.core.tools.maths.vectors.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class GameRenderer {

    public static final float FOV = 100;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 50;
    private final ObjectShader shader = new ObjectShader();
    private Matrix4f projectionMatrix;

    public GameRenderer() {
        reloadProjections();
    }

    public void reloadProjections() {
        createProjectionMatrix();
        shader.start();
        shader.changeProjections(projectionMatrix);
        shader.stop();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    /**
     * Prepares the renderer for another render by clearing the display.
     */
    public void prepare(Scene scene) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Vector3f bgColor = scene.backgroundColor();
        GL11.glClearColor(bgColor.x, bgColor.y, bgColor.z, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        shader.start();
        scene.sortLights();
        shader.loadPointLights(scene.getPointLights());
        shader.loadSpotLights(scene.getSpotLights());
        shader.changeView(MasterRenderer.camera);
    }

    public void render(Scene scene) {
        Map<TexturedModel, List<GameObject>> entities = scene.getObjects();
        Set<TexturedModel> models = entities.keySet();
        List<TexturedModel> realModels = sort(models);
        Camera camera = MasterRenderer.camera;
        Vector3f cameraPos = camera.getPositions().get(scene.getSceneId());
        Vector3f X = new Vector3f(cameraPos.x, cameraPos.y, cameraPos.z);
        float x = FastTrig.cos(Math.toRadians(camera.getYaw(scene.getSceneId()))) * FastTrig.cos(Math.toRadians(camera.getPitch(scene.getSceneId())));
        float y = FastTrig.sin(Math.toRadians(camera.getYaw(scene.getSceneId()))) * FastTrig.cos(Math.toRadians(camera.getPitch(scene.getSceneId())));
        float z = FastTrig.sin(Math.toRadians(camera.getPitch(scene.getSceneId())));
        Vector3f V = new Vector3f(x, y, z);
        shader.loadAmbientLighting(scene.getAmbientLight());
        for (TexturedModel model : realModels) {
            prepareTexturedModel(model);
            List<GameObject> batch = entities.get(model);
            for (GameObject e : batch) {
                if (e.getSceneId().equals(scene.getSceneId())) {
                    e.frame();
                    Vector3f Y = new Vector3f(e.getPosition().x, e.getPosition().y, e.getPosition().z);
                    Vector3f YMinusX = Y.negate(X);//new Vector3f(Y.x - X.x, Y.y - X.y, Y.z - X.z);
                    float dot = Vector3f.dot(YMinusX, V);
                    if (!(dot > 0)) {
                        prepareInstance(e);
                        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                    }
                }
            }
            unbindTexturedModels();
        }
    }

    public void cleanUp() {
        shader.stop();
    }


    private List<TexturedModel> sort(Set<TexturedModel> entities) {
        List<TexturedModel> returned = new ArrayList<>(entities);
        returned.sort(Comparator.comparing(TexturedModel::getGlobalZIndex));
        return returned;
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        if (texture.getSpecularMapId() != -1) {
            shader.loadHasSpecularMap(true);
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMapId());
        } else {
            shader.loadHasSpecularMap(false);
        }
    }

    private void unbindTexturedModels() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(GameObject entity) {
        Matrix4f transformationVertex = TransformationMaths.createTransformationMatrix(entity.getPosition(),
                entity.rotation.x, entity.rotation.y, entity.rotation.z, entity.getScale());
        shader.changeTransformations(transformationVertex);
        shader.loadShine(entity.getShineDamper(), entity.getReflectivity());
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) GLFWDisplayManager.getWidth() / (float) GLFWDisplayManager.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}
