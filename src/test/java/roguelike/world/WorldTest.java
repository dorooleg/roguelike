package roguelike.world;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roguelike.Creature;
import roguelike.StuffFactory;
import roguelike.Tile;
import roguelike.ai.CreatureAi;
import roguelike.invenotry.Item;

/**
 * Created by Oleg on 5/9/2017.
 */
public class WorldTest {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 120;
    private World world;

    @Before
    public void before() {
        world = new WorldBuilder(WIDTH, HEIGHT).makeCaves().build();
    }

    @Test
    public void width() throws Exception {
        Assert.assertEquals(WIDTH, world.width());
    }

    @Test
    public void height() throws Exception {
        Assert.assertEquals(HEIGHT, world.height());
    }

    @Test
    public void creature() throws Exception {
        addAtEmptyLocation();
    }

    @Test
    public void tile() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                Assert.assertNotNull(world.tile(x, y));
    }

    @Test
    public void glyph() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                Assert.assertTrue(world.glyph(x, y) == 250 || world.glyph(x, y) == 177);
    }

    @Test
    public void color() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                Assert.assertTrue(world.color(x, y).equals(AsciiPanel.yellow) || world.color(x, y).equals(AsciiPanel.brightBlack));
    }

    @Test
    public void item() throws Exception {
        addAtEmptyLocation1();
    }

    @Test
    public void dig() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.tile(x, y).isDiggable()) {
                    world.dig(x, y);
                    Assert.assertEquals(Tile.FLOOR, world.tile(x, y));
                } else {
                    Tile tile = world.tile(x, y);
                    world.dig(x, y);
                    Assert.assertEquals(tile, world.tile(x, y));
                }
    }

    @Test
    public void addAtEmptyLocation() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Creature bat = factory.newBat();
        world.addAtEmptyLocation(bat);
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.creature(x, y) == bat)
                    return;
        Assert.fail("Is not found bat");
    }

    @Test
    public void addAtEmptyLocation1() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newSword();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found bat");
    }

    @Test
    public void remove() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Creature bat = factory.newBat();
        world.remove(bat);
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.creature(x, y) == bat) {
                    Assert.fail("Is found bat after remove");

                }
    }

    private class UpdateAi extends CreatureAi {

        public int counter;

        public UpdateAi(Creature creature) {
            super(creature);
        }

        public void onUpdate() {
            counter++;
        }
    }

    @Test
    public void update() throws Exception {
        Creature ai = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
        world.addAtEmptyLocation(ai);
        UpdateAi update = new UpdateAi(ai);
        world.update();
        Assert.assertEquals(1, update.counter);
    }

    @Test
    public void remove1() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newSword();

        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item) {
                    world.remove(x, y);
                    if (world.item(x, y) == item) {
                        Assert.fail("Is found bat after remove");
                    }
                }
    }

    @Test
    public void addAtEmptySpace() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newSword();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item) {
                    world.remove(x, y);
                }
        if (world.addAtEmptySpace(item, 0, 0)) {
            for (int x = 0; x < WIDTH; x++)
                for (int y = 0; y < HEIGHT; y++)
                    if (world.item(x, y) == item)
                        return;
            Assert.fail("Is not found item");
        }
    }

}