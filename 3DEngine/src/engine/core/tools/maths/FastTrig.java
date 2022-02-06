/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tools.maths;

public class FastTrig {

    private static final int precision = 100;
    private static final int modulus = 360 * precision;
    private static final float[] sin = new float[modulus];

    static {
        for (int i = 0; i < sin.length; i++) {
            sin[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
        }
    }

    private static float sinLookup(int a) {
        return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
    }

    public static float sin(float a) {
        return sinLookup((int) (a * precision + 0.5f));
    }

    public static float sin(double a) {
        return sinLookup((int) (a * precision + 0.5f));
    }

    public static float cos(float a) {
        return sinLookup((int) ((a + 90f) * precision + 0.5f));
    }

    public static float cos(double a) {
        return sinLookup((int) ((a + 90f) * precision + 0.5f));
    }

}
