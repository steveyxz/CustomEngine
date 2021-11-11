/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.constraints;

import engine.core.objects.gui.components.GuiComponent;

public abstract class GuiConstraint {

    private GuiComponent parent;
    private ConstraintLevel level;
    private float valueShift = 0;

    public GuiConstraint() {
    }

    public GuiConstraint(GuiComponent parent) {
        this.parent = parent;
    }

    public GuiComponent getParent() {
        return parent;
    }

    public void setParent(GuiComponent parent) {
        this.parent = parent;
    }

    public ConstraintLevel getLevel() {
        return level;
    }

    public void setLevel(ConstraintLevel level) {
        this.level = level;
    }

    public float getValueShift() {
        return valueShift;
    }

    public void setValueShift(float valueShift) {
        this.valueShift = valueShift;
    }

    public abstract void transform();
}
