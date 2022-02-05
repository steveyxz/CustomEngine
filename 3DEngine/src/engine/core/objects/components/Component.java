/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components;

import engine.core.objects.GameObject;

public abstract class Component {

    private final GameObject parent;

    public Component(GameObject parent) {
        this.parent = parent;
    }

    public abstract String getId();

    public abstract void tick();

    public abstract void frame();

    public GameObject parent() {
        return parent;
    }
}
