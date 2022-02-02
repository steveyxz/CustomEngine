/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static engine.core.renderEngine.GLFWDisplayManager.window;

public class KeyboardInputMethods {

    private static final GLFWKeyCallback keyCallback = GLFW.glfwSetKeyCallback(window(), KeyboardInputMethods::glfwKeyCallback);

    private static void glfwKeyCallback(long window, int key, int scancode, int action, int mods) {

    }

    public static void freeCallbacks() {
        try {
            if (keyCallback != null) {
                keyCallback.free();
            }
        } catch (NullPointerException ignored) {
        }
    }

    public static boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(window(), key) == GLFW.GLFW_PRESS;
    }

}
