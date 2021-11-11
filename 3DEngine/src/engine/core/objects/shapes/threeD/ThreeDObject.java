/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.shapes.threeD;

import engine.core.global.Global;
import engine.core.objects.GameObject;
import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class ThreeDObject extends GameObject {

    private Vector3f gamePosition;

    public ThreeDObject(Vector3f position, Vector3f rotation, Vector3f gamePosition, float scale, TexturedModel model) {
        super(position, rotation, scale, model);
        this.gamePosition = gamePosition;
    }

    public float dist(ThreeDObject other) {
        Vector3f gamePosition = getGamePosition();
        return (float) Math.sqrt((other.gamePosition.x - gamePosition.x) * (other.gamePosition.x - gamePosition.x) + (other.gamePosition.y - gamePosition.y) * (other.gamePosition.y - gamePosition.y) + (other.gamePosition.z - gamePosition.z) * (other.gamePosition.z - gamePosition.z));
    }

    @Override
    public void move(float x, float y, float z) {
        super.move(x, y, z);
        //Remember! Positive y will move upwards lol
        this.gamePosition.translate(x / Global.gameTileWidth, y / Global.gameTileHeight, y / Global.gameTileDepth);
    }

    public Vector3f getGamePosition() {
        return gamePosition;
    }

    public void setGamePosition(Vector3f gamePosition) {
        this.gamePosition = gamePosition;
    }
}
