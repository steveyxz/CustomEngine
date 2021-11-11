/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class PacketArgument {

    private String stringValue;
    private final Object data;

    public PacketArgument(Object data) {
        this.data = data;
        if (data instanceof Integer) {
            stringValue = Integer.toString((Integer) data);
        } else if (data instanceof Long) {
            stringValue = Long.toString((Long) data);
        } else if (data instanceof Double) {
            stringValue = Double.toString((Double) data);
        } else if (data instanceof Float) {
            stringValue = Float.toString((Float) data);
        } else if (data instanceof Character) {
            stringValue = Character.toString((Character) data);
        } else if (data instanceof String) {
            stringValue = (String) data;
            if (cont(stringValue, "[") || cont(stringValue, "]") || cont(stringValue, ";") || cont(stringValue, ":")) {
                throw new IllegalArgumentException("Illegal character, ([, ], ; or :) in string");
            }
        } else if (data instanceof Vector2f) {
            stringValue = "[" + ((Vector2f) data).x + "," + ((Vector2f) data).y + "]";
        }  else if (data instanceof Vector3f) {
            stringValue = "[" + ((Vector3f) data).x + "," + ((Vector3f) data).y + "," + ((Vector3f) data).z +"]";
        }
    }

    private boolean cont(String s, String c) {
        return s.contains(c);
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
