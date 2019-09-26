package com.alphaae.gl.fighting;

import com.alphaae.gl.fighting.model.*;
import com.jogamp.opengl.GL2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameScene {
    private GL2 gl;

    private AircraftPlayer player;
    private Boss boss;
    private Background background;

    private List<Ammunition> ammunitionList = new ArrayList<>();
    private List<AircraftEnemy> aircraftEnemies = new ArrayList<>();

    int time = 0;
    boolean randerAircraftEnemyNO = true;
    boolean bossNO = false;

    private int score = 0;

    public GameScene(GL2 gl) {
        this.gl = gl;

        background = new Background(gl);
        player = new AircraftPlayer(gl);

        //测试用
//        if (boss == null) boss = new Boss(gl);
    }

    //主渲染进程
    public void rander() {
        time++;
        background.rander();
        player.rander();

        if (boss != null) boss.rander();
        randerAircraftEnemy();

        randerAmmunition();
        collision();
    }

    //子弹间隔计时变量
    private int ammunitionSpace = 0;

    private void randerAmmunition() {
        ammunitionSpace++;
        if (ammunitionSpace > 10) {
            ammunitionSpace = 0;
            ammunitionList.add(new Ammunition(gl, player.getPosition()));
        }
        for (Ammunition amm : ammunitionList) {
            amm.rander();
        }
    }

    private void randerAircraftEnemy() {
        if (randerAircraftEnemyNO) {
            if (time % 20 == 0) {
                float randPosX = new Random().nextFloat() * 1600;
                aircraftEnemies.add(new AircraftEnemy(gl, new float[]{randPosX, 1600}));
            }
        }

        for (AircraftEnemy temp : aircraftEnemies) {
            temp.rander();
        }
    }

    private void collision() {
        Iterator<AircraftEnemy> aircraftEnemyIterator = aircraftEnemies.iterator();
        Iterator<Ammunition> ammunitionIterator = ammunitionList.iterator();

        //遍历判断碰撞 适当减小碰撞体积以达到理想效果
        for (int i = 0; i < aircraftEnemies.size(); i++) {
            for (int j = 0; j < ammunitionList.size(); j++) {
                if (Utils.isIntersect(aircraftEnemies.get(i).getPosition(),
                        aircraftEnemies.get(i).getCollisionBox(),
                        ammunitionList.get(j).getPosition(),
                        1)) {
                    //判断死亡则移除
                    if (aircraftEnemies.get(i).beAttacked()) {
                        score++;
                        System.out.println("score:" + score);

                        //boss!!!!!!!!
                        if (score > 5) {
                            randerAircraftEnemyNO = false;
                            bossNO = true;
                            if (boss == null) boss = new Boss(gl);
                        }
                    }
                    ammunitionList.remove(j);

                    break;
                }
            }
        }

    }


    public KeyListener getKeyListener() {
        return new TheKey();
    }

    class TheKey implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.controlUpPressed();
                    break;
                case KeyEvent.VK_S:
                    player.controlDownPressed();
                    break;
                case KeyEvent.VK_A:
                    player.controlLeftPressed();
                    break;
                case KeyEvent.VK_D:
                    player.controlRigthPressed();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.controlUpReleased();
                    break;
                case KeyEvent.VK_S:
                    player.controlDownReleased();
                    break;
                case KeyEvent.VK_A:
                    player.controlLeftReleased();
                    break;
                case KeyEvent.VK_D:
                    player.controlRigthReleased();
                    break;
            }
        }
    }

}
