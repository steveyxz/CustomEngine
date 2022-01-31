/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom;

import engine.core.multiplayer.packets.PacketManager;

import java.io.IOException;
import java.util.Random;

import static engine.core.tester.custom.Init.init;

public class RunClient {
    public static void main(String[] args) throws IOException {
        init();
        AddClient client = new AddClient(4321);
        Random r = new Random();
        for (int i = 0; i < 2000; i++) {
            System.out.println(client.sendPacket(new AddPacket(r.nextInt(30), r.nextInt(30))));
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
