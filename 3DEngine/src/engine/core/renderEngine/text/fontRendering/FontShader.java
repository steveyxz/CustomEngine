package engine.core.renderEngine.text.fontRendering;

import engine.core.renderEngine.shaders.ShaderProgram;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;

public class FontShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/textVertex.shader";
    private static final String FRAGMENT_FILE = "shaders/textFragment.shader";

    private int location_colour;
    private int location_translation;

    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_colour = super.getUniformVariable("colour");
        location_translation = super.getUniformVariable("translation");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    protected void loadColour(Vector3f colour) {
        super.loadVector3f(location_colour, colour);
    }

    protected void loadTranslation(Vector2f translation) {
        super.loadVector2f(location_translation, translation);
    }


}
