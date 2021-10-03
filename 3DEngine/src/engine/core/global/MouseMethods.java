package engine.core.global;

import org.lwjgl.input.Mouse;

public class MouseMethods {

    public static boolean checkBounds(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < height + y;
    }

    public static boolean isMouseClickWithin(int x, int y, int width, int height) {
        if (!Mouse.isButtonDown(0)) {
            return false;
        }
        return checkBounds(Mouse.getX(), Mouse.getY(), x, y, width, height);
    }
}
