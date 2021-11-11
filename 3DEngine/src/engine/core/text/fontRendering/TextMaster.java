/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.text.fontRendering;

import engine.core.renderEngine.Loader;
import engine.core.text.fontMeshCreator.FontType;
import engine.core.text.fontMeshCreator.Text;
import engine.core.text.fontMeshCreator.TextMeshData;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMaster {

    private static final Map<FontType, List<Text>> texts = new HashMap<>();
    private static FontRenderer renderer;

    public static void init() {
        renderer = new FontRenderer();
    }

    public static void render() {
        renderer.render(texts);
    }

    public static void clearTexts() {
        texts.clear();
    }

    public static void reloadTextScales() {
        for (FontType t : texts.keySet()) {
            List<Text> textList = texts.get(t);
            for (Text tt : textList) {
                if (tt.getScaleDir() == 1) {
                    tt.getScale().set(1f, 1f * Display.getWidth() / Display.getHeight());
                }
                if (tt.getScaleDir() == 2) {
                    tt.getScale().set(1f * Display.getHeight() / Display.getWidth(), 1f);
                }
                System.out.println(tt.getScale());
            }
            texts.put(t, textList);
        }
    }

    public static void loadText(Text text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = Loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<Text> textBatch = texts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public static void removeText(Text text) {
        List<Text> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if (textBatch.isEmpty()) {
            texts.remove(texts.get(text.getFont()));
        }
    }

    public static void cleanUp() {
        renderer.cleanUp();
    }

}
