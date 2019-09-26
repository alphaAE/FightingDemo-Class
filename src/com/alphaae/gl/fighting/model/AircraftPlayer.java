package com.alphaae.gl.fighting.model;

import com.alphaae.gl.fighting.Utils;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class AircraftPlayer {
    private GL2 gl;

    private float[] size = new float[]{100.0f, 100.0f};
    private float[] position = new float[]{100.0f, 150.0f};

    private int textureInt;

    private int textureDefInt;
    private int textureLeftInt;
    private int textureRightInt;

    //移动步幅
    private float Amplitude = 20f;

    boolean isUp = false;
    boolean isDown = false;
    boolean isLeft = false;
    boolean isRight = false;

    public float[] getSize() {
        return size.clone();
    }

    public float[] getPosition() {
        return position.clone();
    }

    public int getTextureInt() {
        return textureInt;
    }

    public AircraftPlayer(GL2 gl) {
        this.gl = gl;
        try {
            File textureFile = new File("image\\player.png");
            Texture texture = TextureIO.newTexture(textureFile, true);
            textureDefInt = texture.getTextureObject(gl);
            File textureLeftFile = new File("image\\player_left.png");
            Texture textureLeft = TextureIO.newTexture(textureLeftFile, true);
            textureLeftInt = textureLeft.getTextureObject(gl);
            File textureRightFile = new File("image\\player_right.png");
            Texture textureRight = TextureIO.newTexture(textureRightFile, true);
            textureRightInt = textureRight.getTextureObject(gl);
            textureInt = textureDefInt;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rander() {
        if (textureInt != textureDefInt) textureInt = textureDefInt;
        if (isUp) position[1] += Amplitude;
        if (isDown) position[1] -= Amplitude;
        if (isLeft) {
            position[0] -= Amplitude;
            textureInt = textureLeftInt;
        }
        if (isRight) {
            position[0] += Amplitude;
            textureInt = textureRightInt;
        }

        Utils.drawRectGL(gl, textureInt, position, size);

    }

    public void controlUpPressed() {
        isUp = true;
    }

    public void controlDownPressed() {
        isDown = true;
    }

    public void controlLeftPressed() {
        isLeft = true;
    }

    public void controlRigthPressed() {
        isRight = true;
    }

    public void controlUpReleased() {
        isUp = false;
    }

    public void controlDownReleased() {
        isDown = false;
    }

    public void controlLeftReleased() {
        isLeft = false;
    }

    public void controlRigthReleased() {
        isRight = false;
    }

}