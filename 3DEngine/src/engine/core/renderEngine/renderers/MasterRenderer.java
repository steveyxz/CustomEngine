/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.renderers;

import engine.core.global.Global;
import engine.core.particles.ParticleMaster;
import engine.core.renderEngine.camera.Camera;
import engine.core.renderEngine.camera.EmptyCamera;
import org.lwjgl.opengl.GL11;

import static engine.core.global.Global.currentScene;

public class MasterRenderer {

    private final static GameRenderer gameRenderer = new GameRenderer();
    private final static GuiRenderer guiRenderer = new GuiRenderer();
    public static Camera camera = new EmptyCamera();

    public static void init() {
        ParticleMaster.init(gameRenderer.getProjectionMatrix());
        enableCulling();
    }

    public static void enableCulling() {
        if (!Global.allTwoD) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
        }
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void reloadProjections() {
        gameRenderer.reloadProjections();
        ParticleMaster.reloadProjections(gameRenderer.getProjectionMatrix());
    }

    public static void render() {
        if (camera.enabled()) {
            camera.frame();
        }
        renderObjects();
        renderGuis();
        ParticleMaster.update();
        ParticleMaster.render(camera);
    }

    private static void renderObjects() {
        gameRenderer.prepare(currentScene);
        gameRenderer.render(currentScene);
        gameRenderer.cleanUp();
    }

    private static void renderGuis() {
        guiRenderer.prepare();
        guiRenderer.render(currentScene);
        guiRenderer.cleanUp();
    }

}
