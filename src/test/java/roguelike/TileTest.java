package roguelike;

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
        Assert.assertEquals(250, tile.glyph());
        tile = Tile.WALL;
        Assert.assertEquals(177, tile.glyph());
        tile = Tile.BOUNDS;
        Assert.assertEquals('x', tile.glyph());

    }

    @Test
    public void color() throws Exception {
        Tile tile = Tile.FLOOR;
        Assert.assertEquals(AsciiPanel.yellow, tile.color());
        tile = Tile.WALL;
        Assert.assertEquals(AsciiPanel.yellow, tile.color());
        tile = Tile.BOUNDS;
        Assert.assertEquals(AsciiPanel.brightBlack, tile.color());
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