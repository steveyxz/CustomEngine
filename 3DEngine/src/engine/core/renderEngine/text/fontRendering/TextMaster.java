package engine.core.renderEngine.text.fontRendering;

import engine.core.renderEngine.Loader;
import engine.core.renderEngine.text.fontMeshCreator.FontType;
import engine.core.renderEngine.text.fontMeshCreator.Text;
import engine.core.renderEngine.text.fontMeshCreator.TextMeshData;

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

    public static void clearTexts() {
        texts.clear();
    }

    public static void reloadTextScales() {
        /*
        for (FontType t : texts.keySet()) {
            List<GUIText> textList = texts.get(t);
            for (GUIText tt : textList) {
                tt.getScale().set(1f * GLFWDisplayManager.getHeight() / GLFWDisplayManager.getWidth(), 1f);
            }
            texts.put(t, textList);
        }
         */
        // TODO reload text scales
    }
}
