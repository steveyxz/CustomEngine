/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.flappybird;

import engine.addons.gameLoopManager.Game;
import engine.core.objects.Scene;
import engine.core.objects.shapes.twoD.Square;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.tools.maths.vectors.Vector2f;

public class FlappyBird extends Game {

    public FlappyBird() {
        super(800, 600, false, "Flappy Bird");
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    @Override
    protected void preLoop() {
        Scene s = new Scene("main");
        s.addObject(new Square(new Vector2f(0, 0), new Vector2f(0, 0), 1, new ModelTexture(Loader.loadTexture("textures/circle"))));
        Scene.sceneManager.changeScene("main");
    }
}
