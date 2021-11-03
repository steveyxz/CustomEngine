package engine.core.text;

import engine.core.renderEngine.Loader;
import engine.core.text.fontMeshCreator.FontType;

public class FontGlobal {

    public static final FontType segoe = new FontType(Loader.loadTexture("fonts/segoe"), "fonts/segoe.fnt");
    public static final FontType timesNewRoman = new FontType(Loader.loadTexture("fonts/times_new_roman2"), "fonts/times_new_roman2.fnt");

}
