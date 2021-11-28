/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.constraints.types;

import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.tools.maths.vectors.Vector2f;

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
        Vector2f texturePos = getParent().getTexture().getPos();
        Vector2f textureScale = getParent().getTexture().getScale();
        if (getLevel() == ConstraintLevel.X) {
            float x = ((value / GLFWDisplayManager.getWidth() * 2) - 1);
            texturePos.setX(x);
            setValueShift(x);
            if (shiftCentre) {
                texturePos.setX(texturePos.getX() + textureScale.getX());
            }
        }
        if (getLevel() == ConstraintLevel.Y) {
            float y = ((2 - (value / GLFWDisplayManager.getHeight() * 2)) - 1);
            texturePos.setY(y);
            setValueShift(y);
            if (shiftCentre) {
                texturePos.setY(texturePos.getY() - textureScale.getY());
            }
        }
        if (getLevel() == ConstraintLevel.WIDTH) {
            float width = value / GLFWDisplayManager.getWidth();
            textureScale.setX(width);
            setValueShift(width);
        }
        if (getLevel() == ConstraintLevel.HEIGHT) {
            float height = value / GLFWDisplayManager.getHeight();
            textureScale.setY(height);
            setValueShift(height);
        }
    }
}
