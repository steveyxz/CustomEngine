/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import java.util.Arrays;
import java.util.List;

import static engine.core.tester.custom.TicTacToe.isPlayerSideCross;

public class TicTacToeComputer {

    private final TicTacToeBoard pieces;
    private final int winLength;
    private final int boardSize;
    private static final int MAX_DEPTH = 3;

    public TicTacToeComputer(TicTacToeBoard pieces) {
        this.pieces = pieces;
        this.winLength = pieces.winLength();
        this.boardSize = pieces.boardSize();
    }

    public int getNextMove(TicTacToeBoard board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < board.boardSize(); row++) {
            for (int col = 0; col < board.boardSize(); col++) {
                if (!board.isTileMarked(row, col)) {
                    if (isPlayerSideCross) {
                        board.setMarkAt(row, col, TicTacToeBoard.State.O);
                    } else {
                        board.setMarkAt(row, col, TicTacToeBoard.State.X);
                    }
                    int moveValue = miniMax(board, MAX_DEPTH, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
                    board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove[0] * board.boardSize() + bestMove[1];
    }

    public int miniMax(TicTacToeBoard board, int depth, int alpha, int beta,
                              boolean isMax) {
        int boardVal = evaluate(board, depth);

        if (Math.abs(boardVal) > 0 || depth == 0
                || board.getPossibleMoves().size() < 1) {
            return boardVal;
        }

        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < board.boardSize(); row++) {
                for (int col = 0; col < board.boardSize(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        if (isPlayerSideCross) {
                            board.setMarkAt(row, col, TicTacToeBoard.State.O);
                        } else {
                            board.setMarkAt(row, col, TicTacToeBoard.State.X);
                        }
                        highestVal = Math.max(highestVal, miniMax(board,
                                depth - 1, alpha, beta, false));
                        board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta) {
                            return highestVal;
                        }
                    }
                }
            }
            return highestVal;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < board.boardSize(); row++) {
                for (int col = 0; col < board.boardSize(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        if (isPlayerSideCross) {
                            board.setMarkAt(row, col, TicTacToeBoard.State.X);
                        } else {
                            board.setMarkAt(row, col, TicTacToeBoard.State.O);
                        }
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth - 1, alpha, beta, true));
                        board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                        beta = Math.min(beta, lowestVal);
                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            return lowestVal;
        }
    }

    private int evaluate(TicTacToeBoard board, int depth) {
        StringBuilder rowSum = new StringBuilder();
        int bWidth = board.boardSize();
        String aiWinSequence = String.valueOf(TicTacToeBoard.State.X.c()).repeat(board.winLength());
        String playerWinSequence = String.valueOf(TicTacToeBoard.State.O.c()).repeat(board.winLength());

        if (isPlayerSideCross) {
            String temp = aiWinSequence;
            aiWinSequence = playerWinSequence;
            playerWinSequence = temp;
        }

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum.append(board.getMarkAt(row, col).c());
            }
            if (rowSum.toString().contains(aiWinSequence)) {
                return 10 + depth;
            } else if (rowSum.toString().contains(playerWinSequence)) {
                return -10 - depth;
            }
            rowSum = new StringBuilder();
        }

        // Check columns for winner.
        rowSum = new StringBuilder();
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                rowSum.append(board.getMarkAt(row, col).c());
            }
            if (rowSum.toString().contains(aiWinSequence)) {
                return 10 + depth;
            } else if (rowSum.toString().contains(playerWinSequence)) {
                return -10 - depth;
            }
            rowSum = new StringBuilder();
        }

        rowSum = new StringBuilder();
        for (int i = 0; i < bWidth; i++) {
            rowSum.append(board.getMarkAt(i, i).c());
        }
        if (rowSum.toString().contains(aiWinSequence)) {
            return 10 + depth;
        } else if (rowSum.toString().contains(playerWinSequence)) {
            return -10 - depth;
        }

        rowSum = new StringBuilder();
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum.append(board.getMarkAt(i, indexMax - i).c());
        }
        if (rowSum.toString().contains(aiWinSequence)) {
            return 10 + depth;
        } else if (rowSum.toString().contains(playerWinSequence)) {
            return -10 - depth;
        }

        return 0;
    }

    public TicTacToeBoard pieces() {
        return pieces;
    }

    public int winLength() {
        return winLength;
    }

    public int boardSize() {
        return boardSize;
    }
}
