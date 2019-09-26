package com.alphaae.gl.fighting.model;

import com.alphaae.gl.fighting.Utils;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class Boss {
    private GL2 gl;

    private float[] size = new float[]{600.0f, 400.0f};
    private float[] position = new float[]{100.0f, 150.0f};
    private float collisionBox = 200f;

    private int textureInt;
    private int textureAInt;

    private int health = 1000;
    private long time = 0;

    public Boss(GL2 gl) {
        this.gl = gl;
        try {
            File textureFile = new File("image\\boss.png");
            Texture texture = TextureIO.newTexture(textureFile, true);
            textureInt = texture.getTextureObject(gl);
            File textureAFile = new File("image\\ammunition_boss.png");
            Texture textureA = TextureIO.newTexture(textureAFile, true);
            textureAInt = textureA.getTextureObject(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int accTime = 0;

    public void rander() {
        if (time >= 0) time++;
        else return;

//        in
        if (time < 500) {
            position[0] = time * 3 - 740f;
            position[1] = time * 3 - 740f;

        } else {
            float r = accTime += 10;

            for (int i = 0; i < 360; i += 10) {
                float tx = (float) ((r) * Math.cos(i) - (r) * Math.sin(i) + position[0]);
                float ty = (float) ((r) * Math.sin(i) + (r) * Math.cos(i) + position[1]);

                Utils.drawRectGL(gl, textureAInt, new float[]{tx, ty}, new float[]{40, 40});

            }

            if (accTime > 1000) accTime = 0;

        }

        Utils.drawRectGL(gl, textureInt, position, size);
    }

}
