/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import engine.core.objects.gui.components.buttons.Button;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;

import static engine.core.tester.custom.TicTacToe.isPlayerSideCross;

public class TicTacToePiece extends Button {

    private static final String circle = "textures/circle";
    private static final String cross = "textures/cross";
    private static final String blank = "textures/blank";
    private int type;

    public TicTacToePiece(int type) {
        super(getTextureOf(type), getTextureOf(type), getTextureOf(type), 0);
        this.type = type;
    }

    private static String getTextureOf(int type) {
        if (type == -1) {
            return cross;
        } else if (type == 1) {
            return circle;
        } else {
            return blank;
        }
    }

    @Override
    public void click() {
        if (type == 0) {
            if (isPlayerSideCross) {
                type = -1;
            } else {
                type = 1;
            }
            this.setTexture(new GuiTexture(Loader.loadTexture(getTextureOf(type))));
            this.setActive(false);
        }
    }

    @Override
    public void hover() {

    }
}
