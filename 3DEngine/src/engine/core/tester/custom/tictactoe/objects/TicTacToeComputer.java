/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.tictactoe.objects;

import java.util.HashMap;
import java.util.Map;

import static engine.core.tester.custom.tictactoe.TicTacToe.isPlayerSideCross;

public class TicTacToeComputer {

    private static final int MAX_DEPTH = 4;
    //How much the depth of an evaluation affects the score
    private static final int WIN_POINTS = 1000000;
    private static final double DEPTH_BIAS = WIN_POINTS / (MAX_DEPTH * 5f);
    private static final int CENTRE_BIAS = 100;
    private static final int OPENNESS_BIAS = 1;
    private static final Map<Integer, Integer> transpositionTableMax = new HashMap<>();
    private static final Map<Integer, Integer> transpositionTableMin = new HashMap<>();
    public static int transpositionTableBoardSize = 0;
    //fields
    private final TicTacToeBoard pieces;
    private final int winLength;
    private final int boardSize;

    public TicTacToeComputer(TicTacToeBoard pieces) {
        this.pieces = pieces;
        this.winLength = pieces.winLength();
        this.boardSize = pieces.boardSize();
        if (boardSize != transpositionTableBoardSize) {
            transpositionTableMin.clear();
            transpositionTableMax.clear();
        }
    }

    /**
     * Gets the best possible next move
     *
     * @param board The board that you are finding the next for move
     * @return a combined index (x * boardWidth + y) or -1 if the board is full
     */
    public int getNextMove(TicTacToeBoard board) {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        //Loop through every empty square
        for (int row = 0; row < board.boardSize(); row++) {
            for (int col = 0; col < board.boardSize(); col++) {
                if (!board.isTileMarked(row, col)) {
                    //Simulate a move that the computer might do
                    if (isPlayerSideCross) {
                        board.setMarkAt(row, col, TicTacToeBoard.State.O);
                    } else {
                        board.setMarkAt(row, col, TicTacToeBoard.State.X);
                    }
                    //Get the score for that move
                    int moveValue = miniMax(board, MAX_DEPTH, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
                    board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                    if (moveValue > bestValue) {
                        bestMove = row * boardSize + col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        System.out.println(bestValue);
        //Return the highest scoring move
        return bestMove;
    }

    /**
     * Performs a basic minimax evaluation that gets the quality of the current board.
     *
     * @param board The board to perform minimax on.
     * @param depth The new depth / maximum depth (if original minimax call)
     * @param alpha The alpha value
     * @param beta  The beta value
     * @param isMax If this is maximising or minimising the player.
     * @return A number representing the quality of the board
     */
    public int miniMax(TicTacToeBoard board, int depth, int alpha, int beta,
                       boolean isMax) {
        Integer hash = board.hash();
        if (isMax) {
            if (transpositionTableMax.containsKey(hash)) {
                return transpositionTableMax.get(hash);
            }
        } else {
            if (transpositionTableMin.containsKey(hash)) {
                return transpositionTableMin.get(hash);
            }
        }
        // Return the evalution if this is the end of the chain (if this sequence ends with a win / loss / tie) or if
        // the maximum depth is exceeded
        if (board.getWinState() != 0 || depth == 0
                || board.getPossibleMoves().size() < 1) {
            return evaluate(board, depth);
        }

        //If we are maximising the player
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            //Loop through every empty square
            for (int row = 0; row < board.boardSize(); row++) {
                for (int col = 0; col < board.boardSize(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        //Simulate the computer placing a tile there
                        if (isPlayerSideCross) {
                            board.setMarkAt(row, col, TicTacToeBoard.State.O);
                        } else {
                            board.setMarkAt(row, col, TicTacToeBoard.State.X);
                        }
                        //Update highest val by calling minimax for the computer
                        highestVal = Math.max(highestVal, miniMax(board,
                                depth - 1, alpha, beta, false));
                        board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                        //Alpha-beta pruning
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta) {
                            return highestVal;
                        }
                    }
                }
            }
            transpositionTableMax.put(board.hash(), highestVal);
            return highestVal;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            //Loop through every empty square
            for (int row = 0; row < board.boardSize(); row++) {
                for (int col = 0; col < board.boardSize(); col++) {
                    if (!board.isTileMarked(row, col)) {
                        //Simulate a player placing a tile
                        if (isPlayerSideCross) {
                            board.setMarkAt(row, col, TicTacToeBoard.State.X);
                        } else {
                            board.setMarkAt(row, col, TicTacToeBoard.State.O);
                        }
                        //Update the values
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth - 1, alpha, beta, true));
                        board.setMarkAt(row, col, TicTacToeBoard.State.BLANK);
                        //Alpha-beta pruning
                        beta = Math.min(beta, lowestVal);
                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            transpositionTableMin.put(board.hash(), lowestVal);
            return lowestVal;
        }
    }

    /**
     * Evaluates a board from the computer's perspective.
     *
     * @param board The board to evaluate.
     * @param depth The current depth (the lower the depth, the worse the score,
     *              as it will take longer to get to that point)
     * @return A final score, which will be 0 if tied or with no win, + if it's a computer victory,
     * or - if it's a player victory.
     */
    public int evaluate(TicTacToeBoard board, int depth) {
        StringBuilder sum = new StringBuilder();
        int bWidth = board.boardSize();
        //The win sequences default to computer as X and player as O
        String aiWinSequence = String.valueOf(TicTacToeBoard.State.X.c()).repeat(board.winLength());
        String playerWinSequence = String.valueOf(TicTacToeBoard.State.O.c()).repeat(board.winLength());
        TicTacToeBoard.State aiState = TicTacToeBoard.State.X;
        int score = 0;

        //Toggle if the player is X
        if (isPlayerSideCross) {
            String temp = aiWinSequence;
            aiWinSequence = playerWinSequence;
            playerWinSequence = temp;
            aiState = TicTacToeBoard.State.O;
        }

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                sum.append(board.getMarkAt(row, col).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += (-WIN_POINTS - depth * DEPTH_BIAS);
            }
            sum.setLength(0);
        }

        // Check columns for winner.
        sum.setLength(0);
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                sum.append(board.getMarkAt(row, col).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += (-WIN_POINTS - depth * DEPTH_BIAS);
            }
            sum.setLength(0);
        }

        //Loop through diagonals
        sum.setLength(0);
        for (int j = bWidth - 1; j >= 0; j--) {
            for (int k = 0; k < bWidth; k++) {
                if ((j + k) < bWidth) {
                    sum.append(board.getMarkAt(k, j + k).c());
                } else {
                    break;
                }
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += (-WIN_POINTS - depth * DEPTH_BIAS);
            }
            sum.setLength(0);
        }
        sum.setLength(0);
        for (int i = 1; i < bWidth; i++) {
            for (int j = i, k = 0; j < bWidth && k < bWidth; j++, k++) {
                sum.append(board.getMarkAt(j, k).c());
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += (-WIN_POINTS - depth * DEPTH_BIAS);
            }
            sum.setLength(0);
        }

        // Check anti-diagonals
        sum.setLength(0);
        for (int col = 0; col < bWidth; col++) {
            int startcol = col, startrow = 0;
            while (startcol >= 0 && startrow < bWidth) {
                sum.append(board.getMarkAt(startrow, startcol).c());
                startcol--;
                startrow++;
            }

            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += (-WIN_POINTS - depth * DEPTH_BIAS);
            }
            sum.setLength(0);
        }

        // For each row start column is N-1
        for (int row = 1; row < bWidth; row++) {
            int startrow = row, startcol = bWidth - 1;
            while (startrow < bWidth && startcol >= 0) {
                sum.append(board.getMarkAt(startrow, startcol).c());
                startcol--;
                startrow++;
            }
            //Check if the row contained a victory sequence
            if (sum.toString().contains(aiWinSequence)) {
                score += (WIN_POINTS + depth * DEPTH_BIAS);
            } else if (sum.toString().contains(playerWinSequence)) {
                score += ((-WIN_POINTS - depth * DEPTH_BIAS));
            }
            sum.setLength(0);
        }

        //Adding score based on how many possible connections the target could get
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bWidth; j++) {
                if (board.isTileMarked(i, j)) {
                    //Score is better if the piece in the centre square / the centre box
                    TicTacToeBoard.State markAt = board.getMarkAt(i, j);
                    if ((bWidth ^ 1) == bWidth + 1) {
                        //Even board size, 4 squares
                        for (int k = 0; k < 2; k++) {
                            for (int l = 0; l < 2; l++) {
                                if (i == (bWidth / 2 - 1) + k && j == (bWidth / 2 - 1) + l) {
                                    if (markAt == aiState) {
                                        score += CENTRE_BIAS;
                                    } else {
                                        score -= CENTRE_BIAS;
                                    }
                                }
                            }
                        }
                    } else {
                        if (i == (bWidth - 1) / 2 && j == (bWidth - 1) / 2) {
                            if (markAt == aiState) {
                                score += CENTRE_BIAS;
                            } else {
                                score -= CENTRE_BIAS;
                            }
                        }
                    }
                    int rowBehind = i - 1;
                    int rowFront = i + 1;
                    int colBehind = j - 1;
                    int colFront = j + 1;
                    if (rowBehind > -1) {
                        //Tile to the left
                        if (!board.isTileMarked(rowBehind, j)) {
                            if (markAt == aiState) {
                                score += OPENNESS_BIAS;
                            } else {
                                score -= OPENNESS_BIAS;
                            }
                        }
                        //Tile to the bottom left
                        if (colFront < bWidth) {
                            if (!board.isTileMarked(rowBehind, colFront)) {
                                if (markAt == aiState) {
                                    score += OPENNESS_BIAS;
                                } else {
                                    score -= OPENNESS_BIAS;
                                }
                            }
                        }
                        //Tile to the top left
                        if (colBehind > -1) {
                            if (!board.isTileMarked(rowBehind, colBehind)) {
                                if (markAt == aiState) {
                                    score += OPENNESS_BIAS;
                                } else {
                                    score -= OPENNESS_BIAS;
                                }
                            }
                        }
                    }
                    if (colBehind > -1) {
                        //Tile to the top
                        if (!board.isTileMarked(i, colBehind)) {
                            if (markAt == aiState) {
                                score += OPENNESS_BIAS;
                            } else {
                                score -= OPENNESS_BIAS;
                            }
                        }

                        //Tile to the top right
                        if (rowFront < bWidth) {
                            if (!board.isTileMarked(rowFront, colBehind)) {
                                if (markAt == aiState) {
                                    score += OPENNESS_BIAS;
                                } else {
                                    score -= OPENNESS_BIAS;
                                }
                            }
                        }
                    }
                    if (rowFront < bWidth) {
                        //Tile to the right
                        if (!board.isTileMarked(rowFront, j)) {
                            if (markAt == aiState) {
                                score += OPENNESS_BIAS;
                            } else {
                                score -= OPENNESS_BIAS;
                            }
                        }
                        //Tile to the bottom right
                        if (colFront < bWidth) {
                            if (!board.isTileMarked(rowFront, colFront)) {
                                if (markAt == aiState) {
                                    score += OPENNESS_BIAS;
                                } else {
                                    score -= OPENNESS_BIAS;
                                }
                            }
                        }
                    }
                    if (colFront < bWidth) {
                        //Tile to the bottom
                        if (!board.isTileMarked(i, colFront)) {
                            if (markAt == aiState) {
                                score += OPENNESS_BIAS;
                            } else {
                                score -= OPENNESS_BIAS;
                            }
                        }
                    }
                }
            }
        }
        return score;
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
