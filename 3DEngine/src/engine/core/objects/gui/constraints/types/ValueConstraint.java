/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.constraints.types;

import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import engine.core.renderEngine.GLFWDisplayManager;

public class ValueConstraint extends GuiConstraint {

    private final boolean shiftCentre;
    private float value;

    public ValueConstraint(float value) {
        this(value, true);
    }

    public ValueConstraint(float value, boolean shiftCentre) {
        this.value = value;
        this.shiftCentre = shiftCentre;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isShiftCentre() {
        return shiftCentre;
    }

    @Override
    public void transform() {
        if (getLevel() == ConstraintLevel.X) {
            float x = ((value / GLFWDisplayManager.getWidth() * 2) - 1);
            setValueShift(x);
            if (shiftCentre) {
                setValueShift(getValueShift() + getParent().getWidth().getValueShift());
            }
        }
        if (getLevel() == ConstraintLevel.Y) {
            float y = (((value / GLFWDisplayManager.getHeight() * 2)) - 1);
            setValueShift(y);
            if (shiftCentre) {
                setValueShift(getValueShift() + getParent().getHeight().getValueShift());
            }
        }
        if (getLevel() == ConstraintLevel.WIDTH) {
            float width = value / GLFWDisplayManager.getWidth();
            setValueShift(width);
        }
        if (getLevel() == ConstraintLevel.HEIGHT) {
            float height = value / GLFWDisplayManager.getHeight();
            setValueShift(height);
        }
    }
}
