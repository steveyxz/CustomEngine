/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.objects;

import engine.core.tester.custom.TicTacToe;

import static engine.core.tester.custom.TicTacToe.*;

public class TicTacToeCalculationThread extends Thread {
    @Override
    public void run() {
        if (isPlayerTurn) {
            return;
        }
        int move = computer.getNextMove(gameboard);
        if (move == -1) {
            return;
        }
        TicTacToe.nextMove = move;
    }
}
