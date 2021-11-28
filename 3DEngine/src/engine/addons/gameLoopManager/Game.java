/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.addons.gameLoopManager;

import engine.core.global.Global;
import engine.core.renderEngine.GLFWDisplayManager;
import engine.core.renderEngine.renderers.MasterRenderer;
import engine.core.text.fontRendering.TextMaster;

import static engine.core.global.Global.currentScene;

public abstract class Game {

    public Game() {
        preInit();
        init();
        postInit();

        Global.globalInit();

        //Tick Info
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        preLoop();

        //Game loop
        while (!GLFWDisplayManager.isCloseRequested()) {
            start();

            if (currentScene == null) {
                continue;
            }

            //Tick
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                preTick();
                //Things to run / tick
                if (currentScene != null) {
                    currentScene.tick();
                }
                postTick();
                delta--;
            }

            middle();

            if (currentScene != null) {
                preRender();
                currentScene.render();
                TextMaster.render();
                postRender();
            }

            //Easy FPS Counter
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }

            end();

            //Update
            GLFWDisplayManager.updateDisplay();
            MasterRenderer.reloadProjections();
            TextMaster.reloadTextScales();

            post();
        }

        GLFWDisplayManager.closeDisplay();
        Global.globalCleanUp();

        finish();
        cleanUp();
    }

    protected void preRender() {
    }

    protected void postRender() {
    }

    protected void finish() {
    }

    protected void end() {
    }

    protected void post() {
    }

    protected void start() {
    }

    protected void postTick() {
    }

    protected void middle() {
    }

    protected void preTick() {
    }

    protected void preLoop() {
    }

    protected void postInit() {
    }

    protected void preInit() {
    }

    private void cleanUp() {
        Global.globalCleanUp();
    }

    private void init() {
        Global.globalInit();
    }


}

