/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.tictactoe.objects;

import engine.core.objects.gui.components.GuiComponent;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;

public class TicTacToeBackground extends GuiComponent {
    public TicTacToeBackground(int position, int maxPosition, boolean isHorizontal) {
        super(new GuiTexture(Loader.loadTexture("textures/line"), 1));
        if (isHorizontal) {
            this.setWidth(new RelativeConstraint(1));
            this.setHeight(new RelativeConstraint(0.1f / maxPosition));
            this.setXPos(new RelativeConstraint(0.5f, false));
            this.setYPos(new RelativeConstraint((position + 1) / (maxPosition * 1f), false));
        } else {
            this.setHeight(new RelativeConstraint(1));
            this.setWidth(new RelativeConstraint(0.1f / maxPosition));
            this.setYPos(new RelativeConstraint(0.5f, false));
            this.setXPos(new RelativeConstraint((position + 1) / (maxPosition * 1f), false));
        }
    }
}
