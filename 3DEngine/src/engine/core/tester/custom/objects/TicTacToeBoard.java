/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import engine.core.tester.custom.TicTacToe;

import java.util.ArrayList;
import java.util.List;

import static engine.core.tester.custom.TicTacToe.isPlayerSideCross;

public class TicTacToeBoard {

    private final int boardSize;

    private final int winLength;

    private final State[][] board;
    private int moveCount;
    public TicTacToeBoard(int boardSize, int winLength) {
        this.boardSize = boardSize;
        this.winLength = winLength;
        this.moveCount = 0;
        this.board = new State[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = State.BLANK;
            }
        }
    }
    public TicTacToeBoard(State[][] board, int winLength) {
        this.boardSize = board.length;
        this.winLength = winLength;
        this.moveCount = 0;
        this.board = board;
    }

    public State getMarkAt(int row, int col) {
        return board[row][col];
    }

    public void setMarkAt(int row, int col, State x) {
        board[row][col] = x;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isTileMarked(int x, int y) {
        return board[x][y] != State.BLANK;
    }

    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        int count = 0;
        for (TicTacToeBoard.State[] p : board) {
            for (TicTacToeBoard.State pp : p) {
                if (pp == TicTacToeBoard.State.BLANK) {
                    possibleMoves.add(count);
                }
                count++;
            }
        }
        return possibleMoves;
    }

    public void move(int x, int y, State s) {
        if (board[x][y] == State.BLANK) {
            board[x][y] = s;
        }
        if (TicTacToe.computer.evaluate(this, 0) > 0) {
            TicTacToe.endGame(isPlayerSideCross ? WinState.O : WinState.X);
        } else if (TicTacToe.computer.evaluate(this, 0) < 0) {
            TicTacToe.endGame(isPlayerSideCross ? WinState.X : WinState.O);
        } else if (getPossibleMoves().size() < 1) {
            TicTacToe.endGame(WinState.TIE);
        }
    }

    public int boardSize() {
        return boardSize;
    }

    public int winLength() {
        return winLength;
    }

    public int moveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public State[][] board() {
        return board;
    }

    public enum State {
        BLANK(' '), X('X'), O('O');
        private final char c;

        State(char c) {
            this.c = c;
        }

        public char c() {
            return c;
        }
    }

    public enum WinState {TIE, X, O, NONE}
}
