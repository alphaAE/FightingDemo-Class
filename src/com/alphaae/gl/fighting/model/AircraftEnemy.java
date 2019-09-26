package com.alphaae.gl.fighting.model;

import com.alphaae.gl.fighting.Utils;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class AircraftEnemy {
    private GL2 gl;

    private float[] size = new float[]{100.0f, 100.0f};
    private float[] position;

    private int textureInt;

    public float[] getSize() {
        return size.clone();
    }

    public float[] getPosition() {
        return position.clone();
    }

    public int getTextureInt() {
        return textureInt;
    }

    public AircraftEnemy(GL2 gl, float[] position) {
        this.gl = gl;
        this.position = position;
        try {
            File textureFile = new File("image\\enemy.png");
            Texture texture = TextureIO.newTexture(textureFile, true);
            textureInt = texture.getTextureObject(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rander() {
        Utils.drawRectGL(gl, textureInt, position, size);
        position[1] -= 8;
    }
}
