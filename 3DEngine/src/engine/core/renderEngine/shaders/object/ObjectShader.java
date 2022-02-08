/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.shaders.object;

import engine.core.objects.lighting.PointLight;
import engine.core.objects.lighting.SpotLight;
import engine.core.renderEngine.camera.Camera;
import engine.core.renderEngine.shaders.ShaderProgram;
import engine.core.tools.maths.FastTrig;
import engine.core.tools.maths.TransformationMaths;
import engine.core.tools.maths.vectors.Matrix4f;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.List;

public class ObjectShader extends ShaderProgram {

    //This needs to match the shaders
    private static final int MAX_LIGHTS = 4;

    private static final String VERTEX_FILE = "shaders/imageVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/imageFragment.shader";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPosition_point;
    private int[] location_lightColour_point;
    private int[] location_attenuation_point;
    private int[] location_lightPosition_spot;
    private int[] location_lightColour_spot;
    private int[] location_attenuation_spot;
    private int[] location_lightDirection_spot;
    private int[] location_lightCutOffs_spot;
    private int[] location_lightOuterCutOffs_spot;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_ambient;
    private int location_hasSpecularMap;


    public ObjectShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformVariable("model");
        location_projectionMatrix = getUniformVariable("projection");
        location_viewMatrix = getUniformVariable("view");
        location_shineDamper = getUniformVariable("material.shininess");
        location_reflectivity = getUniformVariable("material.reflectivity");
        location_ambient = getUniformVariable("ambientLightColor");
        location_hasSpecularMap = getUniformVariable("useSpecularLighting");

        location_lightPosition_point = new int[MAX_LIGHTS];
        location_lightColour_point = new int[MAX_LIGHTS];
        location_attenuation_point = new int[MAX_LIGHTS];
        location_lightPosition_spot = new int[MAX_LIGHTS];
        location_lightColour_spot = new int[MAX_LIGHTS];
        location_attenuation_spot = new int[MAX_LIGHTS];
        location_lightCutOffs_spot = new int[MAX_LIGHTS];
        location_lightDirection_spot = new int[MAX_LIGHTS];
        location_lightOuterCutOffs_spot = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition_point[i] = super.getUniformVariable("pointLights[" + i + "].position");
            location_lightColour_point[i] = super.getUniformVariable("pointLights[" + i + "].color");
            location_attenuation_point[i] = super.getUniformVariable("pointLights[" + i + "].attenuation");
            location_lightPosition_spot[i] = super.getUniformVariable("spotLights[" + i + "].position");
            location_lightColour_spot[i] = super.getUniformVariable("spotLights[" + i + "].color");
            location_attenuation_spot[i] = super.getUniformVariable("spotLights[" + i + "].attenuation");
            location_lightCutOffs_spot[i] = super.getUniformVariable("spotLights[" + i + "].cutOff");
            location_lightDirection_spot[i] = super.getUniformVariable("spotLights[" + i + "].direction");
            location_lightOuterCutOffs_spot[i] = super.getUniformVariable("spotLights[" + i + "].outerCutOff");
        }

    }

    public void loadHasSpecularMap(boolean hasSpecularMap) {
        super.loadBoolean(location_hasSpecularMap, hasSpecularMap);
    }

    public void changeTransformations(Matrix4f value) {
        super.loadMatrix(location_transformationMatrix, value);
    }

    public void changeProjections(Matrix4f value) {
        super.loadMatrix(location_projectionMatrix, value);
    }

    public void changeView(Camera value) {
        Matrix4f view = TransformationMaths.createViewMatrix(value);
        super.loadMatrix(location_viewMatrix, view);
    }

    public void loadAmbientLighting(Vector3f value) {
        super.loadVector3f(location_ambient, value);
    }

    public void loadShine(float damper, float reflectivity) {
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(location_shineDamper, damper);
    }

    public void loadPointLights(List<PointLight> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.loadVector3f(location_lightPosition_point[i], lights.get(i).getLightPos());
                super.loadVector3f(location_lightColour_point[i], lights.get(i).getColor());
                super.loadVector3f(location_attenuation_point[i], lights.get(i).getAttenuation());
            } else {
                super.loadVector3f(location_lightPosition_point[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_lightColour_point[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_attenuation_point[i], new Vector3f(0, 0, 0));
            }
        }
    }

    public void loadSpotLights(List<SpotLight> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.loadVector3f(location_lightPosition_spot[i], lights.get(i).getLightPos());
                super.loadVector3f(location_lightColour_spot[i], lights.get(i).getColor());
                super.loadVector3f(location_attenuation_spot[i], lights.get(i).getAttenuation());
                super.loadFloat(location_lightCutOffs_spot[i], lights.get(i).getCutOff());
                super.loadVector3f(location_lightDirection_spot[i], lights.get(i).getDirection());
                super.loadFloat(location_lightOuterCutOffs_spot[i], lights.get(i).getOuterCutOff());
            } else {
                super.loadVector3f(location_lightPosition_spot[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_lightColour_spot[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_attenuation_spot[i], new Vector3f(0, 0, 0));
                super.loadFloat(location_lightCutOffs_spot[i], 0);
                super.loadVector3f(location_lightDirection_spot[i], new Vector3f(0, 0, 0));
                super.loadFloat(location_lightOuterCutOffs_spot[i], 0);
            }
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "aPos");
        super.bindAttribute(1, "aTexCoords");
        super.bindAttribute(2, "aNormal");
    }
}
