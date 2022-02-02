/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.text;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.text.fontMeshCreator.FontType;

public class FontGlobal {

    public static final FontType segoe = new FontType(Loader.loadTexture("fonts/segoe"), "fonts/segoe.fnt");
    public static final FontType arial = new FontType(Loader.loadTexture("fonts/arial"), "fonts/arial.fnt");
    public static final FontType calibri = new FontType(Loader.loadTexture("fonts/calibri"), "fonts/calibri.fnt");
    public static final FontType tahoma = new FontType(Loader.loadTexture("fonts/tahoma"), "fonts/tahoma.fnt");
    public static final FontType harrington = new FontType(Loader.loadTexture("fonts/harrington"), "fonts/harrington.fnt");
    public static final FontType sans = new FontType(Loader.loadTexture("fonts/sans"), "fonts/sans.fnt");

}
