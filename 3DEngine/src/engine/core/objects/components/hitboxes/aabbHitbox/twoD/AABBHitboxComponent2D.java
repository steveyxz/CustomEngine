/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.hitboxes.aabbHitbox.twoD;

import engine.core.objects.components.Component;
import engine.core.objects.shapes.twoD.TwoDObject;
import engine.core.tools.maths.vectors.Vector2f;

public class AABBHitboxComponent2D extends Component {

    private final AABBHitbox2D hitbox;
    private final Vector2f parentPosition = new Vector2f(0, 0);

    public AABBHitboxComponent2D(TwoDObject parent, Vector2f size) {
        super(parent);
        this.hitbox = new AABBHitbox2D(new Vector2f(parent.getPosition().x, parent.getPosition().y), size);
    }

    @Override
    public String getId() {
        return "aabbhitbox2d";
    }

    @Override
    public void tick() {
    }

    @Override
    public void frame() {
        parentPosition.set(parent().position.x, parent().position.y);
        this.hitbox.setPosition(parentPosition);
    }

    public AABBHitbox2D hitbox() {
        return hitbox;
    }
}
