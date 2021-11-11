/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.text.fontRendering;

import engine.core.renderEngine.shaders.ShaderProgram;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/textVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/textFragment.shader";

    private int location_color;
    private int location_translation;

    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_color = super.getUniformVariable("colour");
        location_translation = super.getUniformVariable("translation");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
    }

    protected void loadColor(Vector3f color) {
        super.loadVector3f(location_color, color);
    }


    protected void loadInfo(Vector2f transformation) {
        super.loadVector2f(location_translation, transformation);
    }


}
