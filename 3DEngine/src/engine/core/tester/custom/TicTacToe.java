/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.tester.custom.objects.TicTacToeBackground;
import engine.core.tester.custom.objects.TicTacToePiece;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.Random;

public class TicTacToe extends Game {

    private static final TicTacToePiece[][] gameboard = new TicTacToePiece[3][3];
    public static boolean isPlayerSideCross = new Random().nextBoolean();

    public TicTacToe() {
        super(500, 500, false, "Tic Tac Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    @Override
    protected void preLoop() {
        //init the gameboard
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToePiece ticTacToePiece = new TicTacToePiece(0);
                ticTacToePiece.setWidth(new RelativeConstraint(1 / 5f));
                ticTacToePiece.setHeight(new RelativeConstraint(1 / 5f));
                ticTacToePiece.setXPos(new RelativeConstraint((1 / 3f) * i + 1 / 24f));
                ticTacToePiece.setYPos(new RelativeConstraint((1 / 3f) * j + 1 / 24f));
                gameboard[i][j] = ticTacToePiece;
            }
        }
        //Create a scene
        Scene mainScene = new Scene("tictactoe");
        //Set color to gray
        mainScene.setBackgroundColor(new Vector3f(0.5f, 0.5f, 0.5f));
        TicTacToeBackground bg = new TicTacToeBackground();
        //Process the objects
        for (TicTacToePiece[] p : gameboard) {
            for (TicTacToePiece pp : p) {
                mainScene.processGui(pp);
            }
        }
        //process the background
        mainScene.processGui(bg);
        Scene.sceneManager.changeScene("tictactoe");
    }

}
