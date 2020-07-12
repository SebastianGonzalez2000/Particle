package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    public Window(int width, int height, String title, Game game) {

        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setBackground(Color.black);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(panel);
        panel.add(game);

        frame.setVisible(true);
        panel.setVisible(true);
        frame.pack();
        game.start();






    }


}
