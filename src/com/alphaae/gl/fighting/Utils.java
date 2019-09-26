package com.alphaae.gl.fighting;

import com.jogamp.opengl.GL2;

public class Utils {

    public static void drawRectGL(GL2 gl, int textureInt, float[] position, float[] size) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureInt);
        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glAlphaFunc(GL2.GL_GREATER, 0.5f);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(position[0] - size[0] / 2, position[1] - size[1] / 2);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(position[0] + size[0] / 2, position[1] - size[1] / 2);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(position[0] + size[0] / 2, position[1] + size[1] / 2);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(position[0] - size[0] / 2, position[1] + size[1] / 2);
        gl.glEnd();

        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public static boolean isIntersect(float[] position, float size, float[] position2, float size2) {
        return isIntersection(position, size, position2, size2);
    }

    //判断圆是否跟我相交
    private static boolean isIntersection(float[] position, float size, float[] position2, float size2) {
        //两个半径之和
        float rr = size + size2;
        //两圆心之间距离
        double dis = pointDistance(position, position2);
        if (dis <= rr) {
            //相交
            return true;
        } else {
            return false;
        }
    }

    //计算两点距离的方法
    private static double pointDistance(float[] p1, float[] p2) {
        float dx = p1[0] - p2[0];
        float dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

}
