/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.particles;

import engine.core.renderEngine.shaders.ShaderProgram;
import engine.core.tools.maths.vectors.Matrix4f;

public class ParticleShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/particleVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/particleFragment.shader";

    private int location_numberOfRows;
    private int location_projectionMatrix;

    public ParticleShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_numberOfRows = super.getUniformVariable("numberOfRows");
        location_projectionMatrix = super.getUniformVariable("projectionMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "modelViewMatrix");
        super.bindAttribute(5, "texOffsets");
        super.bindAttribute(6, "blendFactor");
    }

    protected void loadNumberOfRows(float number) {
        super.loadFloat(location_numberOfRows, number);
    }

    protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
        super.loadMatrix(location_projectionMatrix, projectionMatrix);
    }

}
