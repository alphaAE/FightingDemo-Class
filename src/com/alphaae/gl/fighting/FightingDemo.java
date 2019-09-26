package com.alphaae.gl.fighting;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class FightingDemo implements GLEventListener {

    private GL2 gl;

    private GLCanvas glcanvas;             //类似java.awt.Canvas, GLCanvas主要用来显示各种OPENGL的效果
    private GLCapabilities capabilities;   //指定了一套OpenGL的功能：渲染内容必须支持，如色彩深度，以及立体是否已启用。

    private JFrame frame;
    private GameScene gameScene;

    public FightingDemo() {
        capabilities = new GLCapabilities(GLProfile.get(GLProfile.GL2));

        glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(this);
        glcanvas.setSize(1600 / 1, 1600 / 1);

        frame = new JFrame("FightingDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.addWindowListener(new WindowAdapter() {     //给窗体添加关闭事件
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        //注册动画控制器
        final FPSAnimator animator = new FPSAnimator(glcanvas, 60, true);
        animator.start();

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0.4f, 0.4f, 1.0f, 0.0f);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        //创建游戏场景 注册全局键盘事件
        gameScene = new GameScene(gl);
        glcanvas.addKeyListener(gameScene.getKeyListener());
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


        gl.glListBase(1000);
        gl.glRasterPos2f(100f, 150f);
        String content = "Hello";
        gl.glCallLists(20, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(content.getBytes()));

        gameScene.rander();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int w, int h) {
        System.out.println("W:" + w + " H:" + h);
        if (h == 0) {
            h = 1;
        }

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(0.0f, w, 0.0f, h, 1.0f, -1.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    public static void main(String[] args) {
        new FightingDemo();
    }

}
