/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.multiplayertest;

import static engine.core.tester.custom.multiplayertest.Init.init;

public class RunServer {

    public static void main(String[] args) {
        init();
        new AddServer(4321);
    }

}
