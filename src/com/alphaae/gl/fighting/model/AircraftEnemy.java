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
    private float collisionBox = 60f;

    private int textureInt;
    private int[] texturebombInt;
    private int texturebombIntIndex = 0;

    private int health = 2;
    private long time = 0;

    public float[] getSize() {
        return size.clone();
    }

    public float getCollisionBox() {
        return collisionBox;
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
            textureInt = TextureIO.newTexture(textureFile, true).getTextureObject(gl);
            texturebombInt = new int[]{
                    TextureIO.newTexture(new File("image\\bomb\\bomb1.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb2.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb3.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb4.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb5.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb6.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb7.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb8.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb9.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb10.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb11.gif"), true).getTextureObject(gl),
                    TextureIO.newTexture(new File("image\\bomb\\bomb12.gif"), true).getTextureObject(gl),
            };

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //死亡返回true
    public boolean beAttacked() {
        health--;
        if (health <= 0) {
            collisionBox = 0f;
            return true;
        } else {
            return false;
        }
    }

    public void rander() {

        //小于0则不渲染
        if (time >= 0) time++;
        else return;

        if (health > 0) {
            Utils.drawRectGL(gl, textureInt, position, size);
            position[1] -= 8;
        } else {
            Utils.drawRectGL(gl, texturebombInt[texturebombIntIndex], position, size);
            if (time % 2 == 0 && texturebombIntIndex < texturebombInt.length - 1) {
                texturebombIntIndex++;
            } else if (texturebombIntIndex == texturebombInt.length - 1) {
                //销毁生命周期
                time = -1;
            }


        }
    }
}
