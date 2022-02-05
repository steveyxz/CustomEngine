/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.hitboxes.aabbHitbox;

import engine.core.objects.GameObject;
import engine.core.objects.components.Component;
import engine.core.tools.maths.vectors.Vector3f;

public class AABBHitboxComponent extends Component {

    private final AABBHitbox hitbox;

    protected AABBHitboxComponent(GameObject parent, Vector3f size) {
        super(parent);
        this.hitbox = new AABBHitbox(parent.position, size);
    }

    @Override
    public String getId() {
        return "aabbhitbox";
    }

    @Override
    public void tick() {
    }

    @Override
    public void frame() {
    }

    public AABBHitbox hitbox() {
        return hitbox;
    }
}
