package engine.core.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class DisplayManager {

    //ADJUSTABLE VARIABLES
    public static int WIDTH = 920;
    public static int HEIGHT = 920;
    public static final int FPS_CAP = 500;
    public static final String TITLE = "TITLE";
    private static final String[] ICON_PATHS = new String[]{};


    private static long lastTimeFrame;
    private static float delta;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 3)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Display.setTitle("TITLE");
        ByteBuffer[] icon_array = new ByteBuffer[ICON_PATHS.length];

        try {
            for (int i = 0; i < ICON_PATHS.length; i++) {
                icon_array[i] = ByteBuffer.allocateDirect(1);
                String path = ICON_PATHS[i];
                icon_array[i] = loadIcon(DisplayManager.class.getResourceAsStream(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Display.setIcon(icon_array);
        Display.setResizable(true);
        try {
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastTimeFrame = getCurrentTime();
    }

    private static ByteBuffer loadIcon(InputStream path) throws IOException {
        try {
            PNGDecoder decoder = new PNGDecoder(path);
            ByteBuffer bytebuf = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(bytebuf, decoder.getWidth() * 4, PNGDecoder.RGBA);
            bytebuf.flip();
            return bytebuf;
        } finally {
            path.close();
        }
    }

    public static void updateDisplay() {

        Display.sync(FPS_CAP);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastTimeFrame) / 1000f;
        lastTimeFrame = currentFrameTime;

    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }


}
