package com.alphaae.gl.fighting;

import com.alphaae.gl.fighting.model.AircraftEnemy;
import com.alphaae.gl.fighting.model.AircraftPlayer;
import com.alphaae.gl.fighting.model.Ammunition;
import com.alphaae.gl.fighting.model.Background;
import com.jogamp.opengl.GL2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameScene {
    private GL2 gl;

    private AircraftPlayer player;
    private Background background;

    private List<Ammunition> ammunitionList = new ArrayList<>();
    private List<AircraftEnemy> enemieList = new ArrayList<>();


    int enemyTime = 0;

    public GameScene(GL2 gl) {
        this.gl = gl;

        background = new Background(gl);
        //创建玩家对象
        player = new AircraftPlayer(gl);
    }

    //主渲染进程
    public void rander() {
        background.rander();
        player.rander();
        randerAmmunition();
        randerAircraftEnemy();

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
        enemyTime++;
        if (enemyTime % 20 == 0) {
            float randPosX = new Random().nextFloat() * 1600;
            enemieList.add(new AircraftEnemy(gl, new float[]{randPosX, 1600}));
        }

        for (AircraftEnemy temp : enemieList) {
            temp.rander();
        }
    }

    private void collision() {
        Iterator<AircraftEnemy> aircraftEnemyIterator = enemieList.iterator();
        Iterator<Ammunition> ammunitionIterator = ammunitionList.iterator();

        //遍历判断碰撞 适当减小碰撞体积以达到理想效果
        for (int i = 0; i < enemieList.size(); i++) {
            for (int j = 0; j < ammunitionList.size(); j++) {
                if (Utils.isIntersect(enemieList.get(i).getPosition(),
                        enemieList.get(i).getSize()[0] - 30,
                        ammunitionList.get(j).getPosition(),
                        1)) {
                    enemieList.remove(i);
                    ammunitionList.remove(j);
                    //动画未编写
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
