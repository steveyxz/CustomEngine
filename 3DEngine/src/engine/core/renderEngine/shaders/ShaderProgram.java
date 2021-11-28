/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.shaders;

import engine.core.tools.maths.vectors.Matrix4f;
import engine.core.tools.maths.vectors.Vector2f;
import engine.core.tools.maths.vectors.Vector3f;
import engine.core.tools.maths.vectors.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    //ID of the shader program
    private final int programID;
    //ID of the vertexShader
    private final int vertexShaderID;
    //ID of the fragmentShader
    private final int fragmentShaderID;

    /**
     * Basic rules for a new shader program.
     *
     * @param vertexFile   The vertex shader file path (from src, src/renderEngine.shaders...)
     * @param fragmentFile The fragment shader file path
     */
    public ShaderProgram(String vertexFile, String fragmentFile) {
        //Load both renderEngine.shaders
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        //Create a shader program
        programID = GL20.glCreateProgram();
        //Attach the renderEngine.shaders to the program
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        //Bind the attributes to the renderEngine.shaders (VBOS from the VAO)
        bindAttributes();
        //Initialize the program.
        GL20.glLinkProgram(programID);
        GL20.glUseProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    /**
     * Loads a shader file into the display.
     *
     * @param file The path to the shader file (From src, e.g., src/renderEngine.shaders/...);
     * @param type The type of the shader, either GL20.GL_VERTEX_SHADER or GL20.GL_FRAGMENT_SHADER
     * @return The ID of the shader created.
     */
    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        InputStream is = ShaderProgram.class.getClassLoader().getResourceAsStream(file);
        if (is == null) {
            System.out.println(file + " is not a valid file");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

    protected int getUniformVariable(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector3f(int location, Vector3f vector3f) {
        GL20.glUniform3f(location, vector3f.x, vector3f.y, vector3f.z);
    }

    protected void loadVector4f(int location, Vector4f vector3f) {
        GL20.glUniform4f(location, vector3f.x, vector3f.y, vector3f.z, vector3f.w);
    }

    protected void loadVector3Array(int location, Vector3f[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length * 3);
        for (Vector3f vector3f : array) {
            buffer.put(vector3f.getX());
            buffer.put(vector3f.getY());
            buffer.put(vector3f.getZ());
        }
        buffer.flip();
        GL20.glUniform3fv(location, buffer);
    }

    protected void loadVector2f(int location, Vector2f vector2f) {
        GL20.glUniform2f(location, vector2f.x, vector2f.y);
    }

    protected void loadBoolean(int location, boolean value) {
        float i = value ? 1 : 0;
        GL20.glUniform1f(location, i);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    protected abstract void getAllUniformLocations();

    public void start() {
        //Start
        GL20.glUseProgram(programID);
    }

    public void stop() {
        //Stop
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        //Calls on window close.
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    /**
     * This method will contain all the attributes from the VAO the shader
     * program will take in.
     */
    protected abstract void bindAttributes();

    /**
     * This method is used to bind a VAO to a specific input name in the shader
     *
     * @param attribute    The ID of the attribute to add (This is a VBO ID)
     * @param variableName The name of the variable to bind this to.
     */
    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    protected void loadInt(int location, int integer) {
        GL20.glUniform1i(location, integer);
    }
}
