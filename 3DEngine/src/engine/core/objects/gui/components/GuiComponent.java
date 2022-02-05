/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.components;

import engine.core.objects.gui.components.buttons.Button;
import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import engine.core.objects.gui.constraints.types.ValueConstraint;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.tools.maths.vectors.Vector2f;

import java.util.UUID;

import static engine.core.global.Global.currentScene;

public abstract class GuiComponent {

    private final UUID uniqueID = UUID.randomUUID();
    public GuiConstraint xPos = new ValueConstraint(0);
    public GuiConstraint yPos = new ValueConstraint(0);
    public GuiConstraint width = new ValueConstraint(0);
    public GuiConstraint height = new ValueConstraint(0);
    private String sceneID = "";
    private GuiTexture texture;
    private boolean shown = true;
    private boolean stationary = true;

    public GuiComponent(GuiTexture texture) {
        this.texture = texture;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public String getSceneID() {
        return sceneID;
    }

    public void setSceneID(String sceneID) {
        this.sceneID = sceneID;
    }

    public void tick() {
    }

    public void transform() {
        if (xPos != null)
            xPos.transform();
        if (yPos != null)
            yPos.transform();
        if (width != null)
            width.transform();
        if (height != null)
            height.transform();
    }

    public GuiConstraint getXPos() {
        return xPos;
    }

    public void setXPos(GuiConstraint xPos) {
        xPos.setParent(this);
        xPos.setLevel(ConstraintLevel.X);
        this.xPos = xPos;
    }

    public GuiConstraint getYPos() {
        return yPos;
    }

    public void setYPos(GuiConstraint yPos) {
        yPos.setParent(this);
        yPos.setLevel(ConstraintLevel.Y);
        this.yPos = yPos;
    }

    public GuiConstraint getWidth() {
        return width;
    }

    public void setWidth(GuiConstraint width) {
        width.setParent(this);
        width.setLevel(ConstraintLevel.WIDTH);
        this.width = width;
    }

    public GuiConstraint getHeight() {
        return height;
    }

    public void setHeight(GuiConstraint height) {
        height.setParent(this);
        height.setLevel(ConstraintLevel.HEIGHT);
        this.height = height;
    }

    public GuiTexture getTexture() {
        return texture;
    }

    public void setTexture(GuiTexture texture) {
        if (!this.texture.equals(texture)) {
            if (this instanceof Button) {
                if (!((Button) this).active()) {
                    return;
                }
            }
            currentScene.removeGui(this);
            this.texture = texture;
            currentScene.addGui(this);
        }
    }

    public Vector2f getFinalDimensions() {
        return new Vector2f(width.getValueShift(), height.getValueShift());
    }

    public Vector2f getFinalPosition() {
        return new Vector2f(xPos.getValueShift(), yPos.getValueShift());
    }

    public UUID uniqueID() {
        return uniqueID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GuiComponent) {
            return ((GuiComponent) obj).uniqueID.equals(uniqueID);
        }
        return false;
    }

    @Override
    public String toString() {
        return "GuiComponent[scale=" + getFinalDimensions() + ",pos=" + getFinalPosition() + ",uniqueID=" + uniqueID + "]";
    }
}
