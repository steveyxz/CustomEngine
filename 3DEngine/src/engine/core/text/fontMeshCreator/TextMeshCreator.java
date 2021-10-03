package engine.core.text.fontMeshCreator;

import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class TextMeshCreator {

    protected static final double LINE_HEIGHT = 0.03f;
    protected static final int SPACE_ASCII = 32;

    private final MetaFile metaData;

    protected TextMeshCreator(String metaFile) {
        metaData = new MetaFile(metaFile);
    }

    private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY, int scaleDir) {
        vertices.add((float) x);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) y);

    }

    private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY, int scaleDir) {
        texCoords.add((float) x);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) y);
    }

    private static float[] listToArray(List<Float> listOfFloats) {
        float[] array = new float[listOfFloats.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = listOfFloats.get(i);
        }
        return array;
    }

    protected TextMeshData createTextMesh(Text text) {
        List<Line> lines = createStructure(text);
        TextMeshData data = createQuadVertices(text, lines);
        return data;
    }

    private List<Line> createStructure(Text text) {
        char[] chars = text.getTextString().toCharArray();
        List<Line> lines = new ArrayList<Line>();
        Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
        Word currentWord = new Word(text.getFontSize());
        for (char c : chars) {
            int ascii = c;
            if (ascii == SPACE_ASCII) {
                boolean added = currentLine.attemptToAddWord(currentWord);
                if (!added) {
                    lines.add(currentLine);
                    currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
                    currentLine.attemptToAddWord(currentWord);
                }
                currentWord = new Word(text.getFontSize());
                continue;
            }
            Character character = metaData.getCharacter(ascii);
            currentWord.addCharacter(character);
        }
        completeStructure(lines, currentLine, currentWord, text);
        return lines;
    }

    private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, Text text) {
        boolean added = currentLine.attemptToAddWord(currentWord);
        if (!added) {
            lines.add(currentLine);
            currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
            currentLine.attemptToAddWord(currentWord);
        }
        lines.add(currentLine);
    }

    private TextMeshData createQuadVertices(Text text, List<Line> lines) {
        text.setNumberOfLines(lines.size());
        double cursorX = 0f;
        double cursorY = 0f;
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();
        for (Line line : lines) {
            if (text.isCentered()) {
                cursorX = (line.getMaxLength() - line.getLineLength()) / 2;
            }
            for (Word word : line.getWords()) {
                for (Character letter : word.getCharacters()) {
                    addVerticesForCharacter(cursorX, cursorY, letter, text.getFontSize(), vertices, text.getScaleDir());
                    addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
                            letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord(), text.getScaleDir());
                    cursorX += letter.getxAdvance() * text.getFontSize();
                }
                cursorX += metaData.getSpaceWidth() * text.getFontSize();
            }
            cursorX = 0;
            cursorX += LINE_HEIGHT * text.getFontSize();
        }
        return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
    }

    private void addVerticesForCharacter(double cursorX, double cursorY, Character character, double fontSize,
                                         List<Float> vertices, int scaleDir) {
        double x = cursorX + (character.getxOffset() * fontSize);
        double y = cursorY + (character.getyOffset() * fontSize);
        double maxX = x + (character.getSizeX() * fontSize);
        double maxY = y + (character.getSizeY() * fontSize);
        double properX = (2 * x) - 1;
        double properY = (-2 * y) + 1;
        double properMaxX = (2 * maxX) - 1;
        double properMaxY = (-2 * maxY) + 1;
        addVertices(vertices, properX, properY, properMaxX, properMaxY, scaleDir);
    }

}
