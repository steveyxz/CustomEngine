/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.gui.components.buttons;

import engine.core.input.MouseInputMethods;
import engine.core.objects.gui.components.GuiComponent;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.tools.maths.vectors.Vector2f;

public abstract class Button extends GuiComponent {

    protected GuiTexture buttonPassive;
    protected GuiTexture buttonDown;
    protected GuiTexture buttonHover;
    protected ButtonState state = ButtonState.PASSIVE;
    protected int timer = 0;
    private int delay;

    public Button(String textureHover, String textureClick, String texturePassive, int delay) {
        super(new GuiTexture(Loader.loadTexture(texturePassive)));
        this.buttonPassive = new GuiTexture(Loader.loadTexture(texturePassive));
        this.buttonHover = new GuiTexture(Loader.loadTexture(textureHover));
        this.buttonDown = new GuiTexture(Loader.loadTexture(textureClick));
        this.delay = delay;
    }


    public GuiTexture getButtonPassive() {
        return buttonPassive;
    }

    public void setButtonPassive(GuiTexture buttonPassive) {
        this.buttonPassive = buttonPassive;
    }

    public GuiTexture getButtonDown() {
        return buttonDown;
    }

    public void setButtonDown(GuiTexture buttonDown) {
        this.buttonDown = buttonDown;
    }

    public GuiTexture getButtonHover() {
        return buttonHover;
    }

    public void setButtonHover(GuiTexture buttonHover) {
        this.buttonHover = buttonHover;
    }

    public ButtonState getState() {
        return state;
    }

    public void setState(ButtonState state) {
        this.state = state;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isShown()) {
            if (timer < 0) {
                Vector2f pos = getTexture().getPos();
                Vector2f scale = getTexture().getScale();
                if (MouseInputMethods.isMouseClickWithin((int) ((pos.x - (scale.x) + 1) / 2 * GLFWDisplayManager.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * GLFWDisplayManager.getHeight()), (int) (scale.x * GLFWDisplayManager.getWidth()), (int) (scale.y * GLFWDisplayManager.getHeight()))) {
                    System.out.println("test");
                    setTexture(buttonDown);
                    setState(ButtonState.CLICK_DOWN);
                } else if (MouseInputMethods.checkBounds(MouseInputMethods.getMouseX(), MouseInputMethods.getMouseY(), (int) ((pos.x - (scale.x) + 1) / 2 * GLFWDisplayManager.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * GLFWDisplayManager.getHeight()), (int) (scale.x * GLFWDisplayManager.getWidth()), (int) (scale.y * GLFWDisplayManager.getHeight()))) {
                    hover();
                    setTexture(buttonHover);
                    if (getState() == ButtonState.CLICK_DOWN) {
                        click();
                        setTexture(buttonPassive);
                        setState(ButtonState.PASSIVE);
                    }
                } else {
                    setTexture(buttonPassive);
                }
                timer = delay;
            }
            timer--;
        }
    }

    public abstract void click();

    public abstract void hover();
}