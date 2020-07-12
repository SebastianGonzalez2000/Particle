package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;

    public Menu (Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if (game.gameState == Game.STATE.MENU) {

            // Play Button
            if (mouseOver(mx, my, 210, 150, 200, 64)) {


                game.gameState = Game.STATE.SELECT;
                return;
            }

            // Help Button
            if (mouseOver(mx, my, 210, 250, 200, 64)) {
                game.gameState = Game.STATE.HELP;
            }

            // Quit Button
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }

        if (game.gameState == Game.STATE.SELECT) {

            // Normal Button
            if (mouseOver(mx, my, 210, 150, 200, 64)) {

                handler.object.clear();
                game.gameState = Game.STATE.GAME;
                handler.addObject(new Player(Game.width / 2 - 32, Game.height / 2 - 32, ID.Player, handler));
                handler.addObject(new BasicEnemy(Game.width / 2 - 32, Game.height - 50, ID.BasicEnemy, handler));
                game.difficulty = 0;
            }

            // Insane Button
            if (mouseOver(mx, my, 210, 250, 200, 64)) {
                handler.object.clear();
                game.gameState = Game.STATE.GAME;
                handler.addObject(new Player(Game.width / 2 - 32, Game.height / 2 - 32, ID.Player, handler));
                handler.addObject(new HardEnemy(Game.width / 2 - 32, Game.height - 50, ID.BasicEnemy, handler));
                handler.addObject(new HardEnemy(Game.width / 2 - 32, Game.height - 50, ID.BasicEnemy, handler));
                game.difficulty = 1;
            }

            // Back Button
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                game.gameState = Game.STATE.MENU;
                return;
            }
        }

        // Back button for help
        if (game.gameState == Game.STATE.HELP) {
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                game.gameState = Game.STATE.MENU;
                return;
            }
        }





        if (game.gameState == Game.STATE.END) {
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                game.gameState = Game.STATE.MENU;
                hud.setLevel(1);
                hud.setScore(0);


            }
        }

    }

    public void mouseReleased (MouseEvent e) {

    }



    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else return false;
        } else return false;
    }

    public void tick() {}

    public void render(Graphics g) {

        if (game.gameState == Game.STATE.MENU) {

            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Particle", 220, 70);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 275, 190);

            g.setColor(Color.white);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 275, 290);

            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 275, 390);
        } else if (game.gameState == Game.STATE.HELP) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);
            Font fnt3 = new Font("arial", 1, 20);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 260, 70);

            g.setFont(fnt3);
            g.drawString("Use UP, DOWN, LEFT, and RIGHT arrows to move your player", 30, 200);
            g.drawString("Press P to pause the game", 180, 240);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 275, 390);
        } else if (game.gameState == Game.STATE.END) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);
            Font fnt3 = new Font("arial", 1, 20);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 180, 70);

            g.setFont(fnt3);
            g.drawString("Your final score: " + hud.getScore(), 210, 200);

            g.setFont(fnt3);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 260, 390);
        } else if (game.gameState == Game.STATE.SELECT) {

            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Select Difficulty", 150, 70);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Normal", 260, 190);

            g.setColor(Color.white);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Insane", 265, 290);

            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 264, 390);
        }
    }


}


