/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.hitboxes.aabbHitbox.twoD;

import engine.core.tools.maths.vectors.Vector2f;

public class AABBHitbox2D {

    //vec3(width, height, depth)
    private final Vector2f size;
    private final Vector2f position;

    public AABBHitbox2D(Vector2f size, Vector2f position) {
        this.size = size;
        this.position = position;
    }

    public void setX(float x) {
        position.setX(x);
    }

    public void setY(float y) {
        position.setY(y);
    }

    public void setWidth(float width) {
        size.setX(width);
    }

    public void setHeight(float height) {
        size.setY(height);
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
    }

    public void setSize(float width, float height) {
        this.size.set(width, height);
    }

    public Vector2f size() {
        return size;
    }

    public Vector2f position() {
        return position;
    }

    public boolean doesIntersectWith(AABBHitbox2D other) {
        return (this.position.x <= other.position.x && this.position.x + this.size.x >= other.position.x + other.size.x) &&
                (this.position.y <= other.position.y && this.position.y + this.size.y >= other.position.y + other.size.y);
    }

    public boolean doesIntersectWith(Vector2f point) {
        return (point.x >= this.position.x && point.x <= this.position.x + this.size.x) &&
                (point.y >= this.position.y && point.y <= this.position.y + this.size.y);
    }
}
