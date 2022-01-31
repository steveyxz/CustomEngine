/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.constraints.types;

import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import engine.core.tools.maths.vectors.Vector2f;

public class RelativeConstraint extends GuiConstraint {

    private final boolean shiftCentre;
    private float value;

    public RelativeConstraint(float value) {
        this(value, true);
    }

    public RelativeConstraint(float value, boolean shiftCentre) {
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
            float x = (value * 2) - 1;
            setValueShift(x);
            if (shiftCentre) {
                float add = getParent().getWidth().getValueShift();
                setValueShift(getValueShift() + add);
            }
        }

        if (getLevel() == ConstraintLevel.Y) {
            float y = (2 - value * 2) - 1;
            setValueShift(y);
            if (shiftCentre) {
                float add = getParent().getHeight().getValueShift();
                setValueShift(getValueShift() - add);
            }
        }

        if (getLevel() == ConstraintLevel.WIDTH) {
            setValueShift(value);
        }

        if (getLevel() == ConstraintLevel.HEIGHT) {
            setValueShift(value);
        }

    }
}
