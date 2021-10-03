package engine.core.renderEngine.shaders.gui;

import engine.core.renderEngine.shaders.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/guiVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/guiFragment.shader";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }


    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformVariable("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }


}
