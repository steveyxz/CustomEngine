/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;

public class PacketArgument {

    private String stringValue;
    private String contents;
    private Object data;
    private ArgumentType type;

    public PacketArgument(Object data) {
        this.data = data;
        stringValue = "";
        if (data instanceof Integer) {
            stringValue = Integer.toString((Integer) data);
            this.type = ArgumentType.INT;
        } else if (data instanceof Short) {
            stringValue = Short.toString((Short) data);
            this.type = ArgumentType.SHORT;
        } else if (data instanceof Long) {
            stringValue = Long.toString((Long) data);
            this.type = ArgumentType.LONG;
        } else if (data instanceof Double) {
            stringValue = Double.toString((Double) data);
            this.type = ArgumentType.DOUBLE;
        } else if (data instanceof Float) {
            stringValue = Float.toString((Float) data);
            this.type = ArgumentType.FLOAT;
        } else if (data instanceof Character) {
            stringValue = Character.toString((Character) data);
            this.type = ArgumentType.CHAR;
        } else if (data instanceof Vector2f) {
            stringValue = "[" + ((Vector2f) data).x + "," + ((Vector2f) data).y + "]";
            this.type = ArgumentType.V2;
        } else if (data instanceof Vector3f) {
            stringValue = "[" + ((Vector3f) data).x + "," + ((Vector3f) data).y + "," + ((Vector3f) data).z + "]";
            this.type = ArgumentType.V3;
        } else {
            stringValue = (String) data;
            if (cont(stringValue, "[") || cont(stringValue, "]") || cont(stringValue, ";") || cont(stringValue, ":")) {
                throw new IllegalArgumentException("Illegal character, ([, ], ; or :) in string");
            }
            this.type = ArgumentType.STRING;
        }
        this.contents = stringValue;
        stringValue = type.identifier() + "[" + stringValue + "]";
    }

    public PacketArgument(String stringValue) throws IllegalArgumentException {
        int separatorIndex = stringValue.indexOf('[');
        if (separatorIndex == -1) {
            throw new IllegalArgumentException(stringValue + " does not contain a separator");
        }
        this.stringValue = stringValue;
        this.type = ArgumentType.valueOf(stringValue.substring(0, separatorIndex).toUpperCase());
        this.contents = stringValue.substring(separatorIndex + 1, stringValue.length() - 1);
        try {
            switch (type) {
                case INT -> data = Integer.parseInt(contents);
                case LONG -> data = Long.parseLong(contents);
                case STRING -> data = contents;
                case SHORT -> data = Short.parseShort(contents);
                case DOUBLE -> data = Double.parseDouble(contents);
                case FLOAT -> data = Float.parseFloat(contents);
                case V3 -> {
                    String num = contents.substring(1, contents.length() - 1);
                    String[] a = num.split(",");
                    data = new Vector3f(Float.parseFloat(a[0]), Float.parseFloat(a[1]), Float.parseFloat(a[2]));
                }
                case V2 -> {
                    String num = contents.substring(1, contents.length() - 1);
                    String[] a = num.split(",");
                    data = new Vector2f(Float.parseFloat(a[0]), Float.parseFloat(a[1]));
                }
                case CHAR -> data = contents.toCharArray()[0];
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid string value for type.");
        }
    }

    public String stringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String contents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Object data() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ArgumentType type() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    private boolean cont(String s, String c) {
        return s.contains(c);
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
