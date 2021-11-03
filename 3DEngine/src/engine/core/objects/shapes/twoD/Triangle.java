package engine.core.objects.shapes.twoD;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;

import static engine.core.global.Global.*;

public class Triangle extends TwoDObject {
    public Triangle(Vector2f position, Vector2f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(triangleVertices, triangleTextureCoords, triangleIndices, triangleNormals), texture));
    }
}
