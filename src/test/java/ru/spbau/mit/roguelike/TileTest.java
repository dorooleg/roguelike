package ru.spbau.mit.roguelike;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Oleg on 5/9/2017.
 */
public class TileTest {
    @Test
    public void glyph() throws Exception {
        Tile tile = Tile.FLOOR;
        Assert.assertEquals(250, tile.getGlyph());
        tile = Tile.WALL;
        Assert.assertEquals(177, tile.getGlyph());
        tile = Tile.BOUNDS;
        Assert.assertEquals('x', tile.getGlyph());

    }

    @Test
    public void color() throws Exception {
        Tile tile = Tile.FLOOR;
        Assert.assertEquals(AsciiPanel.yellow, tile.getColor());
        tile = Tile.WALL;
        Assert.assertEquals(AsciiPanel.yellow, tile.getColor());
        tile = Tile.BOUNDS;
        Assert.assertEquals(AsciiPanel.brightBlack, tile.getColor());
    }

    @Test
    public void isDiggable() throws Exception {
        Tile tile = Tile.FLOOR;
        Assert.assertEquals(false, tile.isDiggable());
        tile = Tile.WALL;
        Assert.assertEquals(true, tile.isDiggable());
        tile = Tile.BOUNDS;
        Assert.assertEquals(false, tile.isDiggable());
    }

    @Test
    public void isGround() throws Exception {
        Tile tile = Tile.FLOOR;
        Assert.assertEquals(true, tile.isGround());
        tile = Tile.WALL;
        Assert.assertEquals(false, tile.isGround());
        tile = Tile.BOUNDS;
        Assert.assertEquals(false, tile.isGround());
    }

}