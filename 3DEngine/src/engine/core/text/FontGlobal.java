package engine.core.text;

import engine.core.renderEngine.Loader;
import engine.core.text.fontMeshCreator.FontType;

import java.awt.*;

public class FontGlobal {

    public static final FontType calibri = new FontType(Loader.loadTexture("fonts/calibri"), "fonts/calibri.fnt");
    public static final FontType timesNewRoman = new FontType(Loader.loadTexture("fonts/times_new_roman"), "fonts/times_new_roman.fnt");

}
