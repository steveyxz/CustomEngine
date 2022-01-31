/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static engine.core.renderEngine.GLFWDisplayManager.window;

public class MouseInputMethods {

    private static final GLFWScrollCallback scrollCallback = GLFW.glfwSetScrollCallback(window(), MouseInputMethods::glfwScrollCallback);
    private static final GLFWCursorEnterCallback enterCallback = GLFW.glfwSetCursorEnterCallback(window(), MouseInputMethods::glfwCursorEnterCallback);
    private static double lastX;
    private static double lastY;
    // Create the callbacks
    private static final GLFWCursorPosCallback cursorPosCallback = GLFW.glfwSetCursorPosCallback(window(), MouseInputMethods::glfwCursorPosCallback);

    public static double getMouseX() {
        return lastX;
    }

    public static double getMouseY() {
        return lastY;
    }

    private static void glfwCursorPosCallback(long window, double xpos, double ypos) {
        lastX = xpos;
        lastY = ypos;
    }

    private static void glfwScrollCallback(long window, double xpos, double ypos) {

    }

    private static void glfwCursorEnterCallback(long window, boolean isInside) {

    }

    public static boolean checkBounds(double mouseX, double mouseY, double x, double y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < height + y;
    }

    public static void freeCallbacks() {
        if (cursorPosCallback != null) {
            cursorPosCallback.free();
        }
        if (scrollCallback != null) {
            scrollCallback.free();
        }
        if (enterCallback != null) {
            enterCallback.free();
        }
    }

    public static boolean isMouseClickWithin(double x, double y, int width, int height) {
        if (!(GLFW.glfwGetMouseButton(window(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS)) {
            return false;
        }
        return checkBounds((int) getMouseX(), (int) getMouseY(), x, y, width, height);
    }
}
