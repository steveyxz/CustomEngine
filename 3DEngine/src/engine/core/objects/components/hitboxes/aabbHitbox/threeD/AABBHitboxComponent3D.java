/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.hitboxes.aabbHitbox.threeD;

import engine.core.objects.GameObject;
import engine.core.objects.components.Component;
import engine.core.tools.maths.vectors.Vector3f;

public class AABBHitboxComponent3D extends Component {

    private final AABBHitbox3D hitbox;

    public AABBHitboxComponent3D(GameObject parent, Vector3f size) {
        super(parent);
        this.hitbox = new AABBHitbox3D(parent.position, size);
    }

    @Override
    public String getId() {
        return "aabbhitbox3d";
    }

    @Override
    public void tick() {
    }

    @Override
    public void frame() {
        this.hitbox.setPosition(parent().position);
    }

    public AABBHitbox3D hitbox() {
        return hitbox;
    }
}
