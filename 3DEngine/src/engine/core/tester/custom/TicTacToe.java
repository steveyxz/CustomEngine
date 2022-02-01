/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.tester.custom.objects.TicTacToeBackground;
import engine.core.tester.custom.objects.TicTacToeBoard;
import engine.core.tester.custom.objects.TicTacToeComputer;
import engine.core.tester.custom.objects.TicTacToePiece;
import engine.core.text.fontRendering.TextMaster;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static engine.core.tester.custom.objects.TicTacToePiece.getTextureOf;

public class TicTacToe extends Game {

    public static TicTacToeBoard gameboard;
    public static TicTacToeComputer computer;
    public static boolean isPlayerSideCross = new Random().nextBoolean();
    public static boolean isPlayerTurn = new Random().nextBoolean();
    public static List<TicTacToePiece> pieces = new ArrayList<>();

    public TicTacToe() {
        super(800, 800, false, "Tic Tac Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    @Override
    protected void preLoop() {
        startGame(5, 4);
    }

    private void startGame(float boardSize, int winLength) {
        //Create a scene
        Scene mainScene = new Scene("tictactoe");
        //Set color to gray
        mainScene.setBackgroundColor(new Vector3f(0.5f, 0.5f, 0.5f));
        //init the gameboard
        gameboard = new TicTacToeBoard((int) boardSize, winLength);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TicTacToePiece ticTacToePiece = new TicTacToePiece(0, (int) (i * boardSize + j));
                ticTacToePiece.setWidth(new RelativeConstraint(1 / (boardSize + 2)));
                ticTacToePiece.setHeight(new RelativeConstraint(1 /( boardSize + 2)));
                ticTacToePiece.setXPos(new RelativeConstraint((1 / boardSize) * i + 1/(boardSize*2), false));
                ticTacToePiece.setYPos(new RelativeConstraint((1 / boardSize) * j + 1/(boardSize*2), false));
                mainScene.processGui(ticTacToePiece);
                pieces.add(ticTacToePiece);
            }
        }
        //process the background
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                //Go through horizontal lines then vertical ones
                TicTacToeBackground bg = new TicTacToeBackground(j, (int) (boardSize), i == 0);
                mainScene.processGui(bg);
            }
        }
        Scene.sceneManager.changeScene("tictactoe");
        computer = new TicTacToeComputer(gameboard);
        if (!isPlayerTurn) {
            if (boardSize == 3) {
                int move = 4;
                TicTacToePiece piece = pieces.get(move);
                piece.setType(isPlayerSideCross ? 1 : -1);
                piece.setTexture(new GuiTexture(Loader.loadTexture(getTextureOf(piece.type()))));
                piece.setActive(false);
                gameboard.move(move / gameboard.boardSize(), move % gameboard.boardSize(), !isPlayerSideCross ? TicTacToeBoard.State.X : TicTacToeBoard.State.O);
                isPlayerTurn = true;
            } else {
                computerMove();
            }
        }
    }

    public static void computerMove() {
        if (isPlayerTurn) {
            return;
        }
        System.out.println(Arrays.deepToString(gameboard.board()));
        int move = computer.getNextMove(gameboard);
        System.out.println(Arrays.deepToString(gameboard.board()));
        if (move == -1) {
            System.out.println("tie!");
            return;
        }
        TicTacToePiece piece = pieces.get(move);
        piece.setType(isPlayerSideCross ? 1 : -1);
        piece.setTexture(new GuiTexture(Loader.loadTexture(getTextureOf(piece.type()))));
        piece.setActive(false);
        gameboard.move(move / gameboard.boardSize(), move % gameboard.boardSize(), !isPlayerSideCross ? TicTacToeBoard.State.X : TicTacToeBoard.State.O);
        isPlayerTurn = true;
    }

}
