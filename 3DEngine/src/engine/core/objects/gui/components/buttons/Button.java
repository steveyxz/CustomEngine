package engine.core.objects.gui.components.buttons;

import engine.core.global.MouseMethods;
import engine.core.objects.gui.components.GuiComponent;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

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
                if (MouseMethods.isMouseClickWithin((int) ((pos.x - (scale.x) + 1) / 2 * Display.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * Display.getHeight()), (int) (scale.x * Display.getWidth()), (int) (scale.y * Display.getHeight()))) {
                    System.out.println("test");
                    setTexture(buttonDown);
                    setState(ButtonState.CLICK_DOWN);
                } else if (MouseMethods.checkBounds(Mouse.getX(), Mouse.getY(), (int) ((pos.x - (scale.x) + 1) / 2 * Display.getWidth()), (int) ((pos.y - (scale.y) + 1) / 2 * Display.getHeight()), (int) (scale.x * Display.getWidth()), (int) (scale.y * Display.getHeight()))) {
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