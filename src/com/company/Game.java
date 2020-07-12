package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static void main(String[] args) {

        Game game = new Game();
        game.setVisible(true);

    }

    public static final int width = 640, height = 480;

    private Handler handler;
    private Random r = new Random();
    private Thread thread;
    private boolean running = false;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    public static boolean paused = false;
    public int difficulty = 0;

    // 0 is easy
    // 1 is normal


    public enum STATE {
        MENU,
        HELP,
        SELECT,
        GAME,
        END;
    }

    public static STATE gameState = STATE.MENU;

    public Game() {

        hud = new HUD();
        handler = new Handler();
        menu = new Menu(this, handler, hud);

        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);

        new Window(width, height, "Particle", this);

        spawner = new Spawn(handler, hud, this);


        if (gameState == STATE.GAME) {
            handler.object.clear();

            handler.addObject(new Player(width / 2 - 32, height / 2 - 32, ID.Player, handler));
            handler.addObject(new BasicEnemy(width / 2 - 32, height - 50, ID.BasicEnemy, handler));
        } else {
            for (int i = 0 ; i < 20 ; i++) {
                handler.addObject(new MenuParticle(r.nextInt(width), r.nextInt(height), ID.MenuParticle, handler));

            }
        }



        this.requestFocus();


    }



    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;

    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
                  frames++;}

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        this.setVisible(true);
        stop();


    }

    private void tick() {


        if (gameState == STATE.GAME) {

            if (! paused) {
                handler.tick();
                hud.tick();
                spawner.tick();

                if (hud.HEALTH <= 0) {

                    HUD.HEALTH = 100;
                    gameState = STATE.END;
                    handler.clearEnemies();
                    for (int i = 0 ; i < 20 ; i++) {
                        handler.addObject(new MenuParticle(r.nextInt(width), r.nextInt(height), ID.MenuParticle, handler));

                    }

                }
            }

        } else if (gameState == STATE.MENU || gameState == STATE.END || gameState == STATE.HELP || gameState == STATE.SELECT) {
            handler.tick();
            menu.tick();
        }


    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }


        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);

        g.fillRect(0, 0, width, height);

        handler.render(g);

        if (paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 200, 200);
        }

        if (gameState == STATE.GAME) {
            hud.render(g);
        } else if (gameState == STATE.MENU || gameState == STATE.HELP || gameState == STATE.END || gameState == STATE.SELECT) {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

     public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return var = max;
        }

        else if (var <= min) {
            return var = min;
        }

        else {
            return var;
        }
     }


}

