package engine.core.objects.shapes.twoD;

import engine.core.objects.GameObject;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.*;

public class Triangle extends GameObject {
    public Triangle(Vector3f position, Vector3f rotation, float scale, ModelTexture texture) {
        super(position, rotation, scale, new TexturedModel(Loader.loadToVAO(triangleVertices, triangleTextureCoords, triangleIndices, triangleNormals), texture));
    }
}
