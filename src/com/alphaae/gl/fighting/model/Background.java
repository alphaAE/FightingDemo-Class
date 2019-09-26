package com.alphaae.gl.fighting.model;

import com.alphaae.gl.fighting.Utils;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class Background {
    private GL2 gl;

    private float[] size = new float[]{500.0f, 500.0f};
    private float[] position = new float[]{0.0f, 0.0f};

    private int textureInt;

    public Background(GL2 gl) {
        this.gl = gl;
        try {
            File textureFile = new File("image\\background.png");
            Texture texture = TextureIO.newTexture(textureFile, true);
            textureInt = texture.getTextureObject(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rander() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                float tempX = position[0] + i * size[0];
                float tempY = position[1] + j * size[1];
                Utils.drawRectGL(gl, textureInt, new float[]{tempX, tempY}, size);
            }
        }
        position[1] -= 4;
        if (position[1] <= -size[1]) position[1] = 0;
    }
}
