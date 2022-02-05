/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.shaders.object;

import engine.core.objects.lighting.Light;
import engine.core.renderEngine.camera.Camera;
import engine.core.renderEngine.shaders.ShaderProgram;
import engine.core.tools.maths.TransformationMaths;
import engine.core.tools.maths.vectors.Matrix4f;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.List;

public class ObjectShader extends ShaderProgram {

    //This needs to match the shaders
    private static final int MAX_LIGHTS = 12;

    private static final String VERTEX_FILE = "shaders/imageVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/imageFragment.shader";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPosition;
    private int[] location_lightColour;
    private int[] location_attenuation;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;


    public ObjectShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformVariable("transformationMatrix");
        location_projectionMatrix = getUniformVariable("projectionMatrix");
        location_viewMatrix = getUniformVariable("viewMatrix");
        location_shineDamper = getUniformVariable("shineDamper");
        location_reflectivity = getUniformVariable("reflectivity");
        location_useFakeLighting = getUniformVariable("useFakeLighting");
        location_useFakeLighting = getUniformVariable("skyColour");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformVariable("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformVariable("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformVariable("attenuation[" + i + "]");
        }

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

    public void changeSkyColour(float r, float g, float b) {
        super.loadVector3f(location_skyColour, new Vector3f(r, g, b));
    }

    public void changeSkyColour(Vector3f colour) {
        super.loadVector3f(location_skyColour, colour);
    }

    public void useFakeLighting(boolean value) {
        super.loadFloat(location_useFakeLighting, value ? 1 : 0);
    }

    public void loadShine(float damper, float reflectivity) {
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(location_shineDamper, damper);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.loadVector3f(location_lightPosition[i], lights.get(i).getLightPos());
                super.loadVector3f(location_lightColour[i], lights.get(i).getColor());
                super.loadVector3f(location_attenuation[i], lights.get(i).getAttenuation());
            } else {
                super.loadVector3f(location_lightPosition[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_lightColour[i], new Vector3f(0, 0, 0));
                super.loadVector3f(location_attenuation[i], new Vector3f(1, 0, 0));
            }
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
}
