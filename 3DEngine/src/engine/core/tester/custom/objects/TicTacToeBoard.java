/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import engine.core.tester.custom.TicTacToe;

import javax.swing.event.CaretEvent;

import java.util.ArrayList;
import java.util.List;

import static engine.core.tester.custom.TicTacToe.isPlayerSideCross;

public class TicTacToeBoard {

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
    ;

    enum WinState {TIE, X, O, NONE}
    ;

    private final int boardSize;
    private final int winLength;
    private int moveCount;
    private final State[][] board;

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

    public WinState getWinState() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                WinState w = getWinState(i, j, State.X);
                if (w == WinState.X) {
                    return WinState.X;
                }
                WinState w2 = getWinState(i, j, State.O);
                if (w2 == WinState.O) {
                    return WinState.O;
                }
            }
        }
        return WinState.NONE;
    }

    public WinState getWinState(int x, int y, State s) {
        int longestColumnLength = 0;
        int longestWidthLength = 0;
        int longestDiagonalLength = 0;
        int longestAntiDiagonalLength = 0;

        //check col
        for (int i = 0; i < boardSize; i++) {
            if (board[x][i] != s)
                longestColumnLength = 0;
            else
                longestColumnLength++;
            if (longestColumnLength >= winLength) {
                return s == State.X ? WinState.X : WinState.O;
            }
        }

        //check row
        for (int i = 0; i < boardSize; i++) {
            if (board[i][y] != s)
                longestWidthLength = 0;
            else
                longestWidthLength++;
            if (longestWidthLength >= winLength) {
                return s == State.X ? WinState.X : WinState.O;
            }
        }

        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < boardSize; i++) {
                if (board[i][i] != s)
                    longestDiagonalLength = 0;
                else
                    longestDiagonalLength++;
                if (longestDiagonalLength >= winLength) {
                    return s == State.X ? WinState.X : WinState.O;
                }
            }
        }

        //check anti diag
        if (x + y == boardSize - 1) {
            for (int i = 0; i < boardSize; i++) {
                if (board[i][(boardSize - 1) - i] != s)
                    longestAntiDiagonalLength = 0;
                else
                    longestAntiDiagonalLength++;
                if (longestAntiDiagonalLength >= winLength) {
                    return s == State.X ? WinState.X : WinState.O;
                }
            }
        }

        //check draw
        if (moveCount == (Math.pow(boardSize, 2) - 1)) {
            return WinState.TIE;
        }
        return WinState.NONE;
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

    public WinState move(int x, int y, State s) {
        if (board[x][y] == State.BLANK) {
            board[x][y] = s;
        }
        moveCount++;

        int longestColumnLength = 0;
        int longestWidthLength = 0;
        int longestDiagonalLength = 0;
        int longestAntiDiagonalLength = 0;

        //check col
        for (int i = 0; i < boardSize; i++) {
            if (board[x][i] != s)
                longestColumnLength = 0;
            else
                longestColumnLength++;
            if (longestColumnLength >= winLength) {
                return s == State.X ? WinState.X : WinState.O;
            }
        }

        //check row
        for (int i = 0; i < boardSize; i++) {
            if (board[i][y] != s)
                longestWidthLength = 0;
            else
                longestWidthLength++;
            if (longestWidthLength >= winLength) {
                return s == State.X ? WinState.X : WinState.O;
            }
        }

        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < boardSize; i++) {
                if (board[i][i] != s)
                    longestDiagonalLength = 0;
                else
                    longestDiagonalLength++;
                if (longestDiagonalLength >= winLength) {
                    return s == State.X ? WinState.X : WinState.O;
                }
            }
        }

        //check anti diag
        if (x + y == boardSize - 1) {
            for (int i = 0; i < boardSize; i++) {
                if (board[i][(boardSize - 1) - i] != s)
                    longestAntiDiagonalLength = 0;
                else
                    longestAntiDiagonalLength++;
                if (longestAntiDiagonalLength >= winLength) {
                    return s == State.X ? WinState.X : WinState.O;
                }
            }
        }

        //check draw
        if (moveCount == (Math.pow(boardSize, 2) - 1)) {
            return WinState.TIE;
        }
        return WinState.NONE;
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
}
