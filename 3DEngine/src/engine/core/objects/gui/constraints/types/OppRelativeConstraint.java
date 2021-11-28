/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.constraints.types;

import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.tools.maths.vectors.Vector2f;

public class OppRelativeConstraint extends GuiConstraint {

    private float value;

    public OppRelativeConstraint(float value) {
        this.value = value;
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public void transform() {
        Vector2f texturePos = getParent().getTexture().getPos();
        Vector2f textureScale = getParent().getTexture().getScale();
        if (getLevel() == ConstraintLevel.X) {
            float pos = getParent().getYPos().getValueShift();
            setValueShift(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
            texturePos.setX(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
        }
        if (getLevel() == ConstraintLevel.Y) {
            float pos = getParent().getXPos().getValueShift();
            setValueShift(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
            texturePos.setY(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
        }
        if (getLevel() == ConstraintLevel.WIDTH) {
            float pos = getParent().getHeight().getValueShift();
            setValueShift(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
            textureScale.setX(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
        }
        if (getLevel() == ConstraintLevel.HEIGHT) {
            float pos = getParent().getWidth().getValueShift();
            setValueShift(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
            textureScale.setY(value * pos * (GLFWDisplayManager.getWidth() / (GLFWDisplayManager.getHeight() * 1f)));
        }
    }
}
