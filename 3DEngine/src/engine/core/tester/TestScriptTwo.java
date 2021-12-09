/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.addons.gameLoopManager.Game;
import engine.core.input.KeyboardInputMethods;
import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketManager;
import engine.core.objects.Scene;
import engine.core.objects.SceneManager;
import engine.core.objects.lighting.Light;
import engine.core.objects.shapes.twoD.Square;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

public class TestScriptTwo extends Game {

    public static void main(String[] args) throws IOException {
        new TestScriptTwo();
    }

    @Override
    protected void preLoop() {
        Scene yabayo = new Scene("yabayo");
        Scene yabayo2 = new Scene("yabayo2");
        yabayo.processObject(new Square(new Vector2f(0, 0), new Vector2f(0, 0), 1, new ModelTexture(Loader.loadTexture("textures/test"))));
        yabayo2.processObject(new Square(new Vector2f(0, 0), new Vector2f(0, 0), 1, new ModelTexture(Loader.loadTexture("textures/test2"))));
        yabayo.addLight(new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));
        Scene.sceneManager.changeScene("yabayo");
    }

    @Override
    protected void postTick() {
        if (KeyboardInputMethods.isKeyDown(GLFW.GLFW_KEY_0)) {
            Scene.sceneManager.changeScene("yabayo2");
        } else {
            Scene.sceneManager.changeScene("yabayo");
        }
    }
}
