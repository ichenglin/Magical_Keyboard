package com.ichenglin.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private GameRenderer renderer;

    /**
     * Initialize GameKeyListener object with GameRenderer object
     * @param renderer the GameRenderer object
     */
    public GameKeyListener(GameRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Currently not useful for this project
     * @param e the KeyEvent object returned by caller
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles the press of keyboard keys for user
     * @param e the KeyEvent object returned by caller
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        switch (e.getKeyCode()) {
            case 87 -> renderer.player_position_offset(0, -1);
            case 83 -> renderer.player_position_offset(0, 1);
            case 65 -> renderer.player_position_offset(-1, 0);
            case 68 -> renderer.player_position_offset(1, 0);
            case 8 -> renderer.undo_clipboard();
        }
    }

    /**
     * Currently not useful for this project
     * @param e the KeyEvent object returned by caller
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
