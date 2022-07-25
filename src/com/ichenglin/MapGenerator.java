package com.ichenglin;

public class MapGenerator {

    private int[] dimension;
    public byte[][] generated_map;
    public char[][] generated_map_characters;

    /**
     * Initialize MapGenerator object for generating maps
     * @param dimension the dimension of the map
     */
    public MapGenerator(int[] dimension) {
        this.dimension = dimension;
        this.generated_map = new byte[dimension[0]][dimension[1]];
        this.generated_map_characters = new char[dimension[0]][dimension[1]];
    }

    /**
     * Generates map with random available map components
     */
    public void generate() {
        int begin_index = (int) Math.round((dimension[0]) / 2.0);
        for (int row_index = 5; row_index < dimension[1] - 5; row_index++) {
            byte[][][] available_components = basic_component(begin_index);
            int selected_component = -1;
            while (selected_component == -1 || (Math.floor(Math.random() * 100) + 1) > available_components[selected_component][available_components[selected_component].length - 1][1]) {
                selected_component = (int) Math.floor(Math.random() * available_components.length);
            }
            for (int component_row_index = 0; component_row_index < available_components[selected_component].length - 1; component_row_index++) {
                for (int component_column_index = 0; component_column_index < available_components[selected_component][component_row_index].length; component_column_index++) {
                    if (dimension[1] - component_row_index - row_index < 0) {
                        continue;
                    }
                    byte position_block = available_components[selected_component][available_components[selected_component].length - 1 - component_row_index - 1][component_column_index];
                    generated_map[component_column_index][dimension[1] - component_row_index - row_index] = position_block;
                    if (position_block == 3) {
                        generated_map_characters[component_column_index][dimension[1] - component_row_index - row_index] = random_character();
                    }
                }
            }
            begin_index = available_components[selected_component][available_components[selected_component].length - 1][0];
            row_index += available_components[selected_component].length - 2;
        }
        for (int column_index = 0; column_index < dimension[0]; column_index++) {
            generated_map[column_index][0] = 5;
        }
    }

    /**
     * Returns all the available map components
     * @param begin_index the beginning location of the player (x/width axis)
     * @return available map components
     */
    public byte[][][] basic_component(int begin_index) {
        byte end_index = (byte) Math.floor(Math.random() * (dimension[0] - 4) + 2);;
        while (end_index == begin_index) {
            end_index = (byte) Math.floor(Math.random() * (dimension[0] - 4) + 2);
        }
        int component_2_prefix = (int) Math.floor(Math.random() * (dimension[0] - 4) + 2);
        int component_3_border = (int) Math.floor((dimension[0] - 29) / 2.0);
        return new byte[][][] {
                {
                        text_to_row("3" + "0".repeat(dimension[0] - 2) + "3"),
                        text_to_row("2".repeat(begin_index) + "0" + "2".repeat(dimension[0] - begin_index - 1)),
                        {end_index, 45}
                },
                {
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("0".repeat(component_2_prefix) + 333 + "0".repeat(dimension[0] - component_2_prefix - 3)),
                        text_to_row("0".repeat(component_2_prefix) + 333 + "0".repeat(dimension[0] - component_2_prefix - 3)),
                        text_to_row("0".repeat(component_2_prefix) + 333 + "0".repeat(dimension[0] - component_2_prefix - 3)),
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("2".repeat(begin_index) + "0" + "2".repeat(dimension[0] - begin_index - 1)),
                        {end_index, 45}
                },
                {
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("0".repeat(component_3_border) + "40004044444040000040000044444" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004040000040000040000040004" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "44444044444040000040000040004" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004040000040000040000040004" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004044444044444044444044444" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("2".repeat(begin_index) + "0" + "2".repeat(dimension[0] - begin_index - 1)),
                        {end_index, 5}
                },
                {
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("0".repeat(component_3_border) + "04440000400040004040004044444" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004000400040004044044040000" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "44444044444044444040404044444" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004000400000004040004040000" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(component_3_border) + "40004000400000004040004044444" + "0".repeat(component_3_border)),
                        text_to_row("0".repeat(dimension[0])),
                        text_to_row("2".repeat(begin_index) + "0" + "2".repeat(dimension[0] - begin_index - 1)),
                        {end_index, 5}
                }
        };
    }

    /**
     * Converts map layout string to row of map
     * @param text map layout string
     * @return row of map
     */
    private byte[] text_to_row(String text) {
        byte[] result = new byte[text.length()];
        for (int index = 0; index < text.length(); index++) {
            result[index] = Byte.parseByte(text.substring(index, index + 1));
        }
        return result;
    }

    /**
     * Generates a random character
     * @return random character
     */
    private char random_character() {
        String available_characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return available_characters.charAt((int) (Math.floor(Math.random() * available_characters.length())));
    }

}
