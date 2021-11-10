package engine.core.multiplayer.packets;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class PacketArgument {

    private String stringValue;

    public PacketArgument(Object value) throws IllegalArgumentException {
        if (value instanceof Integer) {
            stringValue = Integer.toString((Integer) value);
        } else if (value instanceof Long) {
            stringValue = Long.toString((Long) value);
        } else if (value instanceof Double) {
            stringValue = Double.toString((Double) value);
        } else if (value instanceof Float) {
            stringValue = Float.toString((Float) value);
        } else if (value instanceof Character) {
            stringValue = Character.toString((Character) value);
        } else if (value instanceof String) {
            stringValue = (String) value;
            if (cont(stringValue, "[") || cont(stringValue, "]") || cont(stringValue, ";") || cont(stringValue, ":")) {
                throw new IllegalArgumentException("Illegal character, ([, ], ; or :) in string");
            }
        } else if (value instanceof Vector2f) {
            stringValue = "[" + ((Vector2f) value).x + "," + ((Vector2f) value).y + "]";
        }  else if (value instanceof Vector3f) {
            stringValue = "[" + ((Vector3f) value).x + "," + ((Vector3f) value).y + "," + ((Vector3f) value).z +"]";
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
