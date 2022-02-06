package engine.core.renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLFWDisplayManager {

    private static long window;

    private static long lastTimeFrame;
    private static float delta;

    public static void init(int width, int height, boolean resizable, String name) {

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        //hidden after creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        //resizable
        if (resizable) {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        } else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        }

        // Create the window
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidmode != null) {
                // Center the window
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );
            }
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        GL.createCapabilities();
        // Make the window visible
        glfwShowWindow(window);
        lastTimeFrame = getCurrentTime();

    }

    public static void updateDisplay() {
        update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastTimeFrame) / 1000f;
        lastTimeFrame = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        GLFW.glfwDestroyWindow(window);
    }

    private static long getCurrentTime() {
        return (long) (GLFW.glfwGetTime() * 1000);
    }

    public static boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    public static int getWidth() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);

        org.lwjgl.glfw.GLFW.glfwGetWindowSize(window, buffer, null);

        return buffer.get();
    }

    public static long window() {
        return window;
    }

    public static int getHeight() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);

        GLFW.glfwGetWindowSize(window, null, buffer);

        return buffer.get();
    }

    private static void update() {
        glViewport(0, 0, getWidth(), getHeight());

        GLFW.glfwSwapBuffers(window);

        GLFW.glfwPollEvents();
    }
}
