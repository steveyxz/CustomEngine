/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import java.util.List;

import static engine.core.tester.custom.TicTacToe.isPlayerSideCross;

public class TicTacToeComputer {

    private final TicTacToeBoard pieces;
    private final int winLength;
    private final int boardSize;
    private static final int MAX_DEPTH = 12;

    public TicTacToeComputer(TicTacToeBoard pieces) {
        this.pieces = pieces;
        this.winLength = pieces.winLength();
        this.boardSize = pieces.boardSize();
    }

    public int getNextMove() {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;
        for (int i : pieces.getPossibleMoves()) {
            if (isPlayerSideCross) {
                pieces.board()[i / boardSize][i % boardSize] = TicTacToeBoard.State.O;
                int moveValue = minimax(pieces, MAX_DEPTH, false, TicTacToeBoard.State.O, Integer.MIN_VALUE, Integer.MAX_VALUE);
                pieces.board()[i / boardSize][i % boardSize] = TicTacToeBoard.State.BLANK;
                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            } else {
                pieces.board()[i / boardSize][i % boardSize] = TicTacToeBoard.State.X;
                int moveValue = minimax(pieces, MAX_DEPTH, false, TicTacToeBoard.State.X, Integer.MIN_VALUE, Integer.MAX_VALUE);
                pieces.board()[i / boardSize][i % boardSize] = TicTacToeBoard.State.BLANK;
                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

    public int minimax(TicTacToeBoard ticTacToeBoard, int depth, boolean player, TicTacToeBoard.State aiState, int alpha, int beta) {
        List<Integer> possibleMoves = ticTacToeBoard.getPossibleMoves();
        TicTacToeBoard.State[][] board = ticTacToeBoard.board();
        int eval = evaluate(ticTacToeBoard, depth);
        if (Math.abs(eval) > 0 || depth == 0 || possibleMoves.size() < 1) {
            return eval;
        }
        int bestVal;
        if (player) {
            bestVal = Integer.MIN_VALUE;
            for (int i : possibleMoves) {
                board[i / pieces.boardSize()][i % pieces().boardSize()] = aiState;
                int score = minimax(ticTacToeBoard, depth - 1, false, aiState, alpha, beta);
                bestVal = Math.max(bestVal, score);
                board[i / pieces.boardSize()][i % pieces().boardSize()] = TicTacToeBoard.State.BLANK;
                alpha = Math.max(alpha, bestVal);
                if (alpha > beta) {
                    return bestVal;
                }
            }
            //if (bestVal != 0)
            //System.out.println("best player value for depth " + depth + ": " + bestVal);
        } else {
            bestVal = Integer.MAX_VALUE;
            for (int i : possibleMoves) {
                board[i / pieces.boardSize()][i % pieces().boardSize()] = aiState == TicTacToeBoard.State.X ? TicTacToeBoard.State.O : TicTacToeBoard.State.X;
                int score = minimax(ticTacToeBoard, depth - 1, true, aiState, alpha, beta);
                bestVal = Math.min(bestVal, score);
                board[i / pieces.boardSize()][i % pieces().boardSize()] = TicTacToeBoard.State.BLANK;
                beta = Math.min(beta, bestVal);
                if (beta <= alpha) {
                    return bestVal;
                }
            }
            //if (bestVal != 0)
            //System.out.println("best ai value for depth " + depth + ": " + bestVal);
        }
        return bestVal;
    }

    private int evaluate(TicTacToeBoard board, int depth) {
        StringBuilder rowSum = new StringBuilder();
        int bWidth = board.boardSize();
        String Xwin = String.valueOf(TicTacToeBoard.State.X.c()).repeat(board.winLength());
        String Owin = String.valueOf(TicTacToeBoard.State.O.c()).repeat(board.winLength());

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum.append(board.getMarkAt(row, col).c());
            }
            if (rowSum.toString().contains(Xwin)) {
                return 10 + depth;
            } else if (rowSum.toString().contains(Owin)) {
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
            if (rowSum.toString().contains(Xwin)) {
                return 10 + depth;
            } else if (rowSum.toString().contains(Owin)) {
                return -10 - depth;
            }
            rowSum = new StringBuilder();
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = new StringBuilder();
        for (int i = 0; i < bWidth; i++) {
            rowSum.append(board.getMarkAt(i, i).c());
        }
        if (rowSum.toString().contains(Xwin)) {
            return 10 + depth;
        } else if (rowSum.toString().contains(Owin)) {
            return -10 - depth;
        }

        // Top-right to bottom-left diagonal.
        rowSum = new StringBuilder();
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum.append(board.getMarkAt(i, indexMax - i).c());
        }
        if (rowSum.toString().contains(Xwin)) {
            return 10 + depth;
        } else if (rowSum.toString().contains(Owin)) {
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
