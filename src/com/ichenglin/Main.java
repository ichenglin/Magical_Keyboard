package com.ichenglin;

import com.ichenglin.window.GameKeyListener;
import com.ichenglin.window.GameRenderer;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        game_window(31, 150, 15);
    }

    /**
     * Opens the main window of the program
     * @param width the width of window (in "size" units)
     * @param height the height of window (in "size" units)
     * @param size pixels per unit
     */
    public static void game_window(int width, int height, int size) {
        JFrame frame = new JFrame();

        GameRenderer renderer = new GameRenderer(width, height, size);
        renderer.addKeyListener(new GameKeyListener(renderer));
        renderer.setFocusable(true);

        frame.pack();
        int window_width = width * size + frame.getInsets().left + frame.getInsets().right;
        int window_height = height * size + frame.getInsets().top + frame.getInsets().bottom;

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("resources/icon.jpg")).getPath());
        frame.getContentPane().add(renderer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(window_width, Math.min(window_height, size * 30));
        frame.setTitle("Your Favorite Magical Keyboard :)");
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
