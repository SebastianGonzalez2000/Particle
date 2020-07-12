package com.company;

import java.awt.*;
import java.util.Random;

public class EnemyBoss extends GameObject {

    Random r = new Random();

    private Handler handler;
    private Game game;
    private int timer = 60;
    private int timer2 = 50;



    public EnemyBoss (int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;

        velX = 0;
        velY = 2;
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        if (timer <= 0) velY = 0;
        else timer--;

        if (timer <= 0) timer2--;
        if (timer2<=0){
            if (velX == 0) velX = 2;

            if (velX > 0) velX += 0.005f;
            else if (velX < 0) velX -= 0.005f;

            velX = Game.clamp(velX, -10, 10);

            int spawn;
            if (game.difficulty == 0) spawn = r.nextInt(10);
            else if (game.difficulty == 1) spawn = r.nextInt(3);
            else spawn = r.nextInt(10);


            if (spawn == 0) handler.addObject(new EnemyBossBullet((int) x +48, (int) y +48, ID.BasicEnemy, handler));

        }

        //if ( y <= 0 || y >= Game.height - 32) velY *= -1;
        if ( x <= 0 || x >= Game.width - 97) velX *= -1;

        handler.addObject(new Trail((int) x,(int) y, ID.Trail, Color.red, 96, 96, 0.05f, handler));

    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 96, 96);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 96, 96);
    }
}
