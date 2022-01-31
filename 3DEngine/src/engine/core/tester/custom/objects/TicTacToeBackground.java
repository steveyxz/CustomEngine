/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import engine.core.objects.gui.components.GuiComponent;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.objects.shapes.twoD.Square;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.tools.maths.vectors.Vector2f;

public class TicTacToeBackground extends GuiComponent {
    public TicTacToeBackground() {
        super(new GuiTexture(Loader.loadTexture("textures/background"), 1));
        this.setHeight(new RelativeConstraint(1));
        this.setWidth(new RelativeConstraint(1));
    }
}
