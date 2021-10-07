package engine.core.text;

import engine.core.renderEngine.Loader;
import engine.core.text.fontMeshCreator.FontType;

import java.awt.*;

public class FontGlobal {

    public static final FontType calibri = new FontType(Loader.loadTexture("fonts/segoe"), "fonts/segoe.fnt");
    public static final FontType timesNewRoman = new FontType(Loader.loadTexture("fonts/berlin_sans"), "fonts/berlin_sans.fnt");

}
