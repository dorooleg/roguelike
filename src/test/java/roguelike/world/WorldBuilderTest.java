package roguelike.world;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Oleg on 5/9/2017.
 */
public class WorldBuilderTest {
    @Test
    public void build() throws Exception {
        WorldBuilder worldBuilder = new WorldBuilder(100, 101);
        World world = worldBuilder.build();
        for (int x = 0; x < 100; x++)
            for (int y = 0; y < 101; y++)
                Assert.assertNull(world.tile(x, y));
    }

    @Test
    public void randomizeTiles() throws Exception {
        WorldBuilder worldBuilder = new WorldBuilder(100, 101);
        World world = worldBuilder.randomizeTiles().build();
        for (int x = 0; x < 100; x++)
            for (int y = 0; y < 101; y++)
                Assert.assertTrue(world.glyph(x, y) == 250 || world.glyph(x, y) == 177);
    }

    @Test
    public void makeCaves() throws Exception {
        WorldBuilder worldBuilder = new WorldBuilder(100, 101);
        World world = worldBuilder.makeCaves().build();
        for (int x = 0; x < 100; x++)
            for (int y = 0; y < 101; y++)
                Assert.assertTrue(world.glyph(x, y) == 250 || world.glyph(x, y) == 177);
    }

}