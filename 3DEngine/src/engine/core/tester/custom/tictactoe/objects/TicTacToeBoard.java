/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.tictactoe.objects;

import engine.core.tester.custom.tictactoe.TicTacToe;

import java.util.*;

import static engine.core.tester.custom.tictactoe.TicTacToe.isPlayerSideCross;

public class TicTacToeBoard {

    private static final Map<Integer, Integer> winStateCache = new HashMap<>();
    public static int winStateCacheSize = 0;
    private static long[][][] zobristTable;
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
        if (boardSize != winStateCacheSize) {
            winStateCache.clear();
        }
    }

    public TicTacToeBoard(State[][] board, int winLength) {
        this.boardSize = board.length;
        this.winLength = winLength;
        this.moveCount = 0;
        this.board = board;
    }

    public static void initTable(int boardSize) {
        zobristTable = new long[boardSize][boardSize][2];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                for (int k = 0; k < 2; k++)
                    zobristTable[i][j][k] = new Random().nextInt(Integer.MAX_VALUE);
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
        for (State[] p : board) {
            for (State pp : p) {
                if (pp == State.BLANK) {
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
        if (getWinState() > 0) {
            TicTacToe.endGame(isPlayerSideCross ? WinState.O : WinState.X);
        } else if (getWinState() < 0) {
            TicTacToe.endGame(isPlayerSideCross ? WinState.X : WinState.O);
        } else if (getPossibleMoves().size() < 1) {
            TicTacToe.endGame(WinState.TIE);
        }
    }

    //Basically the computer evaluation but without depth and centre bias
    public int getWinState() {
        int hash = hash();
        if (winStateCache.containsKey(hash)) {
            return winStateCache.get(hash);
        }
        StringBuilder sum = new StringBuilder();
        int bWidth = boardSize();
        //The win sequences default to computer as X and player as O
        String aiWinSequence = String.valueOf(State.X.c()).repeat(winLength());
        String playerWinSequence = String.valueOf(State.O.c()).repeat(winLength());

        //Toggle if the player is X
        if (isPlayerSideCross) {
            String temp = aiWinSequence;
            aiWinSequence = playerWinSequence;
            playerWinSequence = temp;
        }

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                sum.append(getMarkAt(row, col).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }

        // Check columns for winner.
        sum = new StringBuilder();
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                sum.append(getMarkAt(row, col).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }

        //Loop through diagonals
        sum = new StringBuilder();
        for (int j = boardSize - 1; j >= 0; j--) {
            for (int k = 0; k < boardSize; k++) {
                if ((j + k) < boardSize) {
                    sum.append(getMarkAt(k, j + k).c());
                } else {
                    break;
                }
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }
        sum = new StringBuilder();
        for (int i = 1; i < boardSize; i++) {
            for (int j = i, k = 0; j < boardSize && k < boardSize; j++, k++) {
                sum.append(getMarkAt(j, k).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }

        // For each column start row is 0
        sum = new StringBuilder();
        for (int col = 0; col < boardSize; col++) {
            int startcol = col, startrow = 0;
            while (startcol >= 0 && startrow < boardSize) {
                sum.append(getMarkAt(startrow, startcol).c());
                startcol--;
                startrow++;
            }

            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }

        // For each row start column is N-1
        for (int row = 1; row < boardSize; row++) {
            int startrow = row, startcol = boardSize - 1;
            while (startrow < boardSize && startcol >= 0) {
                sum.append(getMarkAt(startrow, startcol).c());
                startcol--;
                startrow++;
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                winStateCache.put(hash, 10);
                return 10;
            } else if (sum.toString().contains(playerWinSequence)) {
                winStateCache.put(hash, -10);
                return -10;
            }
            sum = new StringBuilder();
        }
        winStateCache.put(hash, 0);
        return 0;
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

    public Integer hash() {
        int hash = 0;
        int i = 0;
        int j = 0;
        for (State[] s : board) {
            for (State ss : s) {
                if (ss != State.BLANK) {
                    int e = isPlayerSideCross ? (ss == State.X ? 0 : 1) : ss == State.O ? 0 : 1;
                    hash ^= zobristTable[i][j][e];
                }
                j++;
            }
            i++;
            j = 0;
        }
        return hash;
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
