/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

public enum ArgumentType {
    INT("int"),
    LONG("long"),
    STRING("string"),
    DOUBLE("double"),
    FLOAT("float"),
    V2("v2"),
    V3("v3"),
    SHORT("short"),
    CHAR("char");

    private final String identifier;

    ArgumentType(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
