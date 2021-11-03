package engine.core.objects.shapes.twoD;

import engine.core.global.Global;
import engine.core.objects.GameObject;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class TwoDObject extends GameObject {

    private Vector2f gamePosition = new Vector2f();

    public TwoDObject(Vector2f position, Vector2f rotation, float scale, TexturedModel model) {
        super(new Vector3f(position.x, position.y, -0.2f), new Vector3f(rotation.x, rotation.y, -0.2f), scale, model);
    }

    public TwoDObject(Vector2f position, Vector2f rotation, Vector2f gamePosition, float scale, TexturedModel model) {
        super(new Vector3f(position.x, position.y, -0.2f), new Vector3f(rotation.x, rotation.y, -0.2f), scale, model);
        this.gamePosition = gamePosition;
    }

    @Override
    public void move(float x, float y, float z) {
        move(x, y);
    }

    public void move(float x, float y) {
        super.move(x, y, 0);
        //Remember! Positive y will move upwards lol
        this.gamePosition.translate(x / Global.gameTileWidth, y / Global.gameTileHeight);
    }

    public float dist(TwoDObject other) {
        Vector2f gamePosition = getGamePosition();
        return (float) Math.sqrt((other.gamePosition.x - gamePosition.x) * (other.gamePosition.x - gamePosition.x) + (other.gamePosition.y - gamePosition.y) * (other.gamePosition.y - gamePosition.y));
    }

    public Vector2f getGamePosition() {
        return gamePosition;
    }

    public void setGamePosition(Vector2f gamePosition) {
        this.gamePosition = gamePosition;
    }
}
