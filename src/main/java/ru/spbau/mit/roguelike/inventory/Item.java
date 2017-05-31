package ru.spbau.mit.roguelike.inventory;

import java.awt.*;

/**
 * Created by Oleg on 5/8/2017.
 * Элемент
 */
public class Item {

    private int attackValue;

    /**
     * @return атакуемое значение
     */
    public int getAttackValue() { return attackValue; }

    /**
     * @param amount добавит атакуемое значение
     */
    public void modifyAttackValue(int amount) { attackValue += amount; }

    private int defenseValue;

    /**
     * @return значение защиты
     */
    public int getDefenseValue() { return defenseValue; }

    /**
     * @param amount добавить значение защиты
     */
    public void modifyDefenseValue(int amount) { defenseValue += amount; }

    private char glyph;

    /**
     * @return символьное представление элемента
     */
    public char getGlyph() { return glyph; }

    private Color color;

    /**
     * @return цвет элемента
     */
    public Color getColor() { return color; }

    private String name;

    /**
     * @return название элемента
     */
    public String getName() { return name; }

    public Item(char glyph, Color color, String name) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }
}
