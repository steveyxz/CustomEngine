package engine.core.renderEngine;

import engine.core.renderEngine.models.ModelTexture;
import engine.core.renderEngine.models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static engine.core.global.Global.pathQuicker;

public class Loader {

    //Lists of the textures, vaos and the vbos for easy clean up.
    private static final List<Integer> vaos = new ArrayList<>();
    private static final List<Integer> vbos = new ArrayList<>();
    private static final List<Integer> textures = new ArrayList<>();

    /**
     * Loads a series of positions, texture offsets and sequence orders
     * into a RawModel.
     *
     * @param positions     The positions of the points
     * @param textureCoords The corresponding positions of the texture to render onto the points.
     * @param indices       The order on which to render the points in.
     * @return The raw model
     */
    public static RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices, float[] normals) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public static RawModel loadToVAO(float[] positions) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 2);
    }

    public static void updateVbo(int vbo, float[] data, FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4L, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public static int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, 2, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return vaoID;
    }

    public static int createEmptyVbo(int floatCount) {
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4L, GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return vbo;
    }

    public static void addInstancedAttribute(int vao, int vbo, int attributeNumber, int dataSize, int instancedDataLength, int offset) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL30.glBindVertexArray(vao);
        GL20.glVertexAttribPointer(attributeNumber, dataSize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4L);
        GL33.glVertexAttribDivisor(attributeNumber, 1);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    /**
     * Loads a texture to be used inside a textured model
     *
     * @param path The path to the texture, excluding the .png (Must be png)
     * @return The ID of the texture
     */
    public static int loadTexture(String path) {
        Texture texture = null;

        if (pathQuicker.containsKey(path)) {
            return pathQuicker.get(path).getTextureID();
        }
        path += ".png";
        try {
            texture = TextureLoader.getTexture("PNG", Objects.requireNonNull(Loader.class.getClassLoader().getResourceAsStream(path)));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                    GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + path + " , didn't work");
            System.exit(-1);
        }
        textures.add(texture.getTextureID());
        pathQuicker.put(path, new ModelTexture(texture.getTextureID()));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        return texture.getTextureID();
    }

    /**
     * Cleans up (on window close)
     */
    public static void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vba : vbos) {
            GL15.glDeleteBuffers(vba);
        }
        for (int texture : textures) {
            GL11.glDeleteTextures(texture);
        }
    }

    /**
     * Binds the indices of the model to the vbo ID
     *
     * @param indices The indices to bind.
     */
    private static void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a new VAO
     *
     * @return The ID of the VAO
     */
    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Stores a piece of data into a VBO
     *
     * @param attributeNumber The ID of the VBO.
     * @param coordSize       The dimensions of the data.
     * @param data            The data.
     */
    private static void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        //Get the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        //Store the data into the VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        //Bind to the VAO
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }

}
