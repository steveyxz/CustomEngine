/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.shapes.twoD;

import engine.core.global.Global;
import engine.core.objects.GameObject;
import engine.core.renderEngine.models.TexturedModel;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;

public abstract class TwoDObject extends GameObject {

    private Vector2f gamePosition = new Vector2f();

    public TwoDObject(Vector2f position, Vector2f rotation, float scale, TexturedModel model) {
        super(new Vector3f(position.x, position.y, -0.21f), new Vector3f(rotation.x, rotation.y, -0.21f), scale, model);
        this.gamePosition.set(position.x / Global.gameTileWidth, position.y / Global.gameTileHeight);
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
