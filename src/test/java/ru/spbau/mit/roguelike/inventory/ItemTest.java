package ru.spbau.mit.roguelike.inventory;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Oleg on 5/9/2017.
 */
public class ItemTest {
    @Test
    public void attackValue() throws Exception {
        Item item = new Item('x', AsciiPanel.yellow, "xor");
        item.modifyAttackValue(10);
        Assert.assertEquals(10, item.getAttackValue());
    }

    @Test
    public void modifyAttackValue() throws Exception {
        attackValue();
    }

    @Test
    public void defenseValue() throws Exception {
        Item item = new Item('x', AsciiPanel.yellow, "xor");
        item.modifyDefenseValue(10);
        Assert.assertEquals(10, item.getDefenseValue());
    }

    @Test
    public void modifyDefenseValue() throws Exception {
        defenseValue();
    }

    @Test
    public void glyph() throws Exception {
        Item item = new Item('x', AsciiPanel.yellow, "xor");
        Assert.assertEquals('x', item.getGlyph());
    }

    @Test
    public void color() throws Exception {
        Item item = new Item('x', AsciiPanel.yellow, "xor");
        Assert.assertEquals(AsciiPanel.yellow, item.getColor());
    }

    @Test
    public void name() throws Exception {
        Item item = new Item('x', AsciiPanel.yellow, "xor");
        Assert.assertEquals("xor", item.getName());
    }

}