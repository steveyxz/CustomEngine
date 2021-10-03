package engine.core.objects.shapes.twoD;

import engine.core.objects.GameObject;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.*;

public class Square extends GameObject {
    public Square(Vector3f position, Vector3f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(squareVertices, squareTextureCoords, squareIndices, squareNormals), texture));
    }
}
