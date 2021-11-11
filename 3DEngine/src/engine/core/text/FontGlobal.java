/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.text;

import engine.core.renderEngine.Loader;
import engine.core.text.fontMeshCreator.FontType;

public class FontGlobal {

    public static final FontType segoe = new FontType(Loader.loadTexture("fonts/segoe"), "fonts/segoe.fnt");
    public static final FontType timesNewRoman = new FontType(Loader.loadTexture("fonts/times_new_roman2"), "fonts/times_new_roman2.fnt");

}
