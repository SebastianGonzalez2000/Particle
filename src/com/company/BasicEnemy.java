package com.company;

import java.awt.*;
import java.util.Random;

public class BasicEnemy extends GameObject {

    Random r = new Random();

    private Handler handler;


    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;



        velX = r.nextInt(5) + 1;
        velY = r.nextInt(5) + 1;
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        if ( y <= 0 || y >= Game.height - 32) velY *= -1;
        if ( x <= 0 || x >= Game.width - 16) velX *= -1;

        handler.addObject(new Trail((int) x,(int) y, ID.Trail, Color.red, 16, 16, 0.1, handler));

    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 16, 16);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 16, 16);
    }
}
