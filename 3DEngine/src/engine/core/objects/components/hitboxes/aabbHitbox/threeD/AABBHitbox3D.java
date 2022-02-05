/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.hitboxes.aabbHitbox.threeD;

import engine.core.tools.maths.vectors.Vector3f;

public class AABBHitbox3D {

    //vec3(width, height, depth)
    private final Vector3f size;
    private final Vector3f position;

    public AABBHitbox3D(Vector3f size, Vector3f position) {
        this.size = size;
        this.position = position;
    }

    public void setX(float x) {
        position.setX(x);
    }

    public void setY(float y) {
        position.setY(y);
    }

    public void setZ(float z) {
        position.setZ(z);
    }

    public void setWidth(float width) {
        size.setX(width);
    }

    public void setHeight(float height) {
        size.setY(height);
    }

    public void setDepth(float depth) {
        size.setZ(depth);
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setSize(Vector3f size) {
        this.size.set(size);
    }

    public void setSize(float width, float height, float depth) {
        this.size.set(width, height, depth);
    }

    public Vector3f size() {
        return size;
    }

    public Vector3f position() {
        return position;
    }

    public boolean doesIntersectWith(AABBHitbox3D other) {
        return (this.position.x <= other.position.x && this.position.x + this.size.x >= other.position.x + other.size.x) &&
                (this.position.y <= other.position.y && this.position.y + this.size.y >= other.position.y + other.size.y) &&
                (this.position.z <= other.position.z && this.position.z + this.size.z >= other.position.z + other.size.z);
    }

    public boolean doesIntersectWith(Vector3f point) {
        return (point.x >= this.position.x && point.x <= this.position.x + this.size.x) &&
                (point.y >= this.position.y && point.y <= this.position.y + this.size.y) &&
                (point.z >= this.position.z && point.z <= this.position.z + this.size.z);
    }
}
