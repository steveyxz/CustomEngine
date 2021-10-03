package engine.core.objects;

import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class BasicObject extends GameObject {
    public BasicObject(Vector3f position, Vector3f rotation, float scale, TexturedModel model) {
        super(position, rotation, scale, model);
    }
}
