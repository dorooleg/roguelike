package ru.spbau.mit.roguelike;

import asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Created by Oleg on 5/6/2017.
 * Класс для представления тайла
 */
public enum Tile {
    FLOOR((char)250, AsciiPanel.yellow),
    WALL((char)177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack);

    private char glyph;

    /**
     * @return символьное представление тайла
     */
    public char getGlyph() { return glyph; }

    private Color color;

    /**
     * @return цвет тайла
     */
    public Color getColor() { return color; }


    /**
     * @return true - если стена
     */
    public boolean isDiggable() {
        return this == Tile.WALL;
    }


    /**
     * @return true если земля
     */
    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }
}
