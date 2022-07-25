package com.ichenglin.window;

import com.ichenglin.map.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GameRenderer extends JPanel {

    private MapGenerator map_generator;

    private final int[] dimension;
    private byte[][] game_map;
    private char[][] game_map_characters;
    private int[] player_position;
    private int camera_position;
    private String clipboard = "";

    private final byte[] walkable_block = new byte[]{1, 0, 0, 1, 1, 1};
    private final Color[] map_color = new Color[]{Color.WHITE, Color.RED, Color.GRAY, Color.MAGENTA, Color.ORANGE, Color.YELLOW};

    /**
     * Initialize GameRenderer object for rendering map
     * @param width the width of window (in "size" units)
     * @param height the height of window (in "size" units)
     * @param size pixels per unit
     */
    public GameRenderer(int width, int height, int size) {
        this.dimension = new int[]{width, height, size};
        this.new_map();
    }

    /**
     * Generates a new map with MapGenerator object
     */
    private void new_map() {
        this.map_generator = new MapGenerator(dimension);
        this.map_generator.generate();
        this.game_map = map_generator.generated_map;
        this.game_map_characters = map_generator.generated_map_characters;
        this.player_position = new int[]{(int) Math.round((dimension[0]) / 2.0), dimension[1] - 2};
        this.game_map[player_position[0]][player_position[1]] = 1;
        this.camera_position = (int) (player_position[1] - Math.round(Math.min(dimension[1], 30) / 2.0));
    }

    /**
     * Shift the location of player, with x-axis & y-axis offset
     * @param x the offset of shift in player's x-axis location
     * @param y the offset of shift in player's y-axis location
     */
    public void player_position_offset(int x, int y) {
        int[] next_position = new int[]{player_position[0] + x, player_position[1] + y};
        if (!position_is_path(next_position)) {
            return;
        }
        switch (game_map[next_position[0]][next_position[1]]) {
            case 3 -> clipboard += game_map_characters[next_position[0]][next_position[1]];
            case 5 -> {
                new_map();
                this.repaint();
                return;
            }
        }
        /*if (game_map[next_position[0]][next_position[1]] == 3) {
            clipboard += game_map_characters[next_position[0]][next_position[1]];
            System.out.println(game_map_characters[next_position[0]][next_position[1]]);
        }*/
        game_map[player_position[0]][player_position[1]] = 0;
        game_map[next_position[0]][next_position[1]] = 1;
        player_position = next_position;
        camera_position = (int) (player_position[1] - Math.round(Math.min(dimension[1], 30) / 2.0));
        this.copy_to_clipboard();
        this.repaint();
    }

    /**
     * Check whether a location in map is available for player (within outer border)
     * @param position the location of the player
     * @return the availability of the location (within outer border)
     */
    private boolean position_available(int[] position) {
        return (position[0] >= 0 && position[0] < dimension[0] && position[1] >= 0 && position[1] < dimension[1]);
    }

    /**
     * Check whether a location in map is available for player (walkable path)
     * @param position the location of the player
     * @return the availability of the location (walkable path)
     */
    private boolean position_is_path(int[] position) {
        return position_available(position) && walkable_block[game_map[position[0]][position[1]]] == (byte) 1;
    }

    /**
     * Paint the map onto window, overrides method in JComponent
     * @param g the graphics object from caller
     */
    @Override
    public void paint(Graphics g) {
        for (int width_index = 0; width_index < dimension[0]; width_index++) {
            for (int height_index = 0; height_index < dimension[1]; height_index++) {
                int x_coordinate = width_index * dimension[2];
                int y_coordinate = height_index * dimension[2];
                if (position_available(new int[]{width_index, height_index + camera_position})) {
                    g.setColor(map_color[game_map[width_index][height_index + camera_position]]);
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(x_coordinate, y_coordinate, dimension[2], dimension[2]);
                if (position_available(new int[]{width_index, height_index + camera_position}) && game_map[width_index][height_index + camera_position] == 3) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, dimension[2]));
                    g.drawChars(new char[]{game_map_characters[width_index][height_index + camera_position]}, 0, 1, x_coordinate, y_coordinate + dimension[2]);
                }
            }
            //System.out.println();
        }
        screen_text(g, "Clipboard: \"" + clipboard + "\"", 3, 3 + 20, 20);
        screen_text(g, "(Press Backspace to Undo)", 3, 3 + 40, 20);
    }

    /**
     * Paint text onto the screen
     * @param graphics the graphics object
     * @param text the string to display
     * @param x the x-axis of the location
     * @param y the y-axis of the location
     * @param size the size of the font
     */
    private void screen_text(Graphics graphics, String text, int x, int y, int size) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, size));
        graphics.drawChars(text.toCharArray(), 0, text.length(), x, y);
    }

    /**
     * Copies saved string onto user's clipboard
     */
    public void copy_to_clipboard() {
        StringSelection string_selection = new StringSelection(clipboard);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(string_selection, null);
    }

    /**
     * Removes last appended character from saved string
     */
    public void undo_clipboard() {
        if (clipboard.length() <= 0) {
            return;
        }
        clipboard = clipboard.substring(0, clipboard.length() - 1);
        this.repaint();
    }
}
