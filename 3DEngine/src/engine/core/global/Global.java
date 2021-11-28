/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.global;

import engine.core.objects.Scene;
import engine.core.particles.ParticleMaster;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.text.fontRendering.TextMaster;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.HashMap;

public class Global {

    //This will contain global variables and methods for easy packing

    //########################## GAME INFO ##############################

    //########################## SHAPE VERTICES #############################
    public static final float[] squareVertices = {0f, 0f, 0f, 0f, -1, 0f, 1, -1, 0f, 1, 0, 0f,};
    public static final int[] squareIndices = {0, 1, 3, 3, 1, 2};
    public static final float[] squareTextureCoords = {0, 0, 0, 1, 1, 1, 1, 0};
    public static final float[] squareNormals = {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};
    public static final float[] triangleVertices = {0f, 0f, 0f, 1f, 0f, 0f, 0f, -1f, 0f};
    public static final int[] triangleIndices = {0, 1, 2};
    public static final float[] triangleTextureCoords = {0, 0, 1, 0, 0, 1};
    public static final float[] triangleNormals = {0, 1, 0, 0, 1, 0, 0, 1, 0};
    //########################## CAMERA INFO ################################
    public static final float movementSpeed = 2;
    //######################### TEXTURE INFO ###############################
    public static final HashMap<String, ModelTexture> pathQuicker = new HashMap<>();
    public static final boolean allTwoD = false;
    //######################### PARTICLE INFO ##############################
    public static final int PARTICLE_GRAVITY = -50;
    //2D Games
    public static float gameTileWidth = 0.4f;
    public static float gameTileHeight = 0.4f;
    public static float gameTileDepth = 0.4f;
    //########################## SCENE INFO #################################
    public static Scene currentScene = null;

    public static void globalCleanUp() {
        Loader.cleanUp();
        TextMaster.cleanUp();
        ParticleMaster.cleanUp();
        GLFWDisplayManager.closeDisplay();
    }

    public static void globalInit() {
        GLFWDisplayManager.init();
        MasterRenderer.init();
        TextMaster.init();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Vector3f rotateVector(Vector3f vec, Vector3f axis, double theta) {
        float x, y, z;
        float u, v, w;
        x = vec.getX();
        y = vec.getY();
        z = vec.getZ();
        u = axis.getX();
        v = axis.getY();
        w = axis.getZ();
        float v1 = u * x + v * y + w * z;
        float xPrime = (float) (u * v1 * (1 - Math.cos(theta))
                + x * Math.cos(theta)
                + (-w * y + v * z) * Math.sin(theta));
        float yPrime = (float) (v * v1 * (1 - Math.cos(theta))
                + y * Math.cos(theta)
                + (w * x - u * z) * Math.sin(theta));
        float zPrime = (float) (w * v1 * (1 - Math.cos(theta))
                + z * Math.cos(theta)
                + (-v * x + u * y) * Math.sin(theta));
        return new Vector3f(xPrime, yPrime, zPrime);
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
