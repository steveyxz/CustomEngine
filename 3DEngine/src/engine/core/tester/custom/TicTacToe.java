/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.gui.constraints.types.RelativeConstraint;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.GuiTexture;
import engine.core.renderEngine.text.FontGlobal;
import engine.core.renderEngine.text.fontMeshCreator.Text;
import engine.core.tester.custom.objects.TicTacToeBackground;
import engine.core.tester.custom.objects.TicTacToeBoard;
import engine.core.tester.custom.objects.TicTacToeComputer;
import engine.core.tester.custom.objects.TicTacToePiece;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.*;

import static engine.core.global.Global.currentScene;
import static engine.core.tester.custom.objects.TicTacToePiece.getTextureOf;

public class TicTacToe extends Game {

    public static TicTacToeBoard gameboard;
    public static TicTacToeComputer computer;
    public static boolean isPlayerSideCross;
    public static boolean isPlayerTurn;
    public static List<TicTacToePiece> pieces;
    private static boolean startNewGame = false;
    private static int boardSize = 5;
    private static int winLength = 4;

    public TicTacToe() {
        super(800, 800, false, "Tic Tac Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    @Override
    protected void preLoop() {
        startGame(boardSize, winLength);
    }

    @Override
    protected void postTick() {
        if (startNewGame) {
            startNewGame = false;
            startGame(boardSize, winLength);
        }
    }

    private void startGame(float boardSize, int winLength) {

        isPlayerSideCross = new Random().nextBoolean();
        isPlayerTurn = new Random().nextBoolean();
        pieces = new ArrayList<>();

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
                ticTacToePiece.setHeight(new RelativeConstraint(1 / (boardSize + 2)));
                ticTacToePiece.setXPos(new RelativeConstraint((1 / boardSize) * i + 1 / (boardSize * 2), false));
                ticTacToePiece.setYPos(new RelativeConstraint((1 / boardSize) * j + 1 / (boardSize * 2), false));
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
        currentScene.render();
        int move = computer.getNextMove(gameboard);
        if (move == -1) {
            return;
        }
        TicTacToePiece piece = pieces.get(move);
        piece.setType(isPlayerSideCross ? 1 : -1);
        piece.setTexture(new GuiTexture(Loader.loadTexture(getTextureOf(piece.type()))));
        piece.setActive(false);
        currentScene.render();
        gameboard.move(move / gameboard.boardSize(), move % gameboard.boardSize(), !isPlayerSideCross ? TicTacToeBoard.State.X : TicTacToeBoard.State.O);
        isPlayerTurn = true;
    }

    public static void endGame(TicTacToeBoard.WinState state) {
        Scene transitionScene = new Scene("transition");
        Scene.sceneManager.changeScene("transition");
        Scene.sceneManager.removeScene("tictactoe");
        String text = "";
        switch (state) {
            case X -> text = isPlayerSideCross ? "Player wins!" : "Computer Wins!";
            case O -> text = !isPlayerSideCross ? "Player wins!" : "Computer Wins!";
            case TIE -> text = "Tie!";
        }
        transitionScene.setBackgroundColor(new Vector3f(0.5f, 0.5f, 0.5f));
        Text textObject = new Text(text, 5, FontGlobal.segoe, new Vector2f(0, 0.5f), 1, true);
        transitionScene.addText(textObject);
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                Scene.sceneManager.removeScene("transition");
                startNewGame = true;
            }
        };
        new Timer().schedule(t, 3000);
    }

}
