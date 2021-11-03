package engine.core.objects.shapes.twoD;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;

import static engine.core.global.Global.*;

public class Square extends TwoDObject {
    public Square(Vector2f position, Vector2f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(squareVertices, squareTextureCoords, squareIndices, squareNormals), texture));
    }
}
