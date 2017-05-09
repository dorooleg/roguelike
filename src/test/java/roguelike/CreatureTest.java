package roguelike;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roguelike.ai.PlayerAi;
import roguelike.invenotry.Item;
import roguelike.world.World;
import roguelike.world.WorldBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 5/9/2017.
 */
public class CreatureTest {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 120;

    private Creature creature;
    private World world;
    private StuffFactory factory;


    @Before
    public void before() throws Exception {

        world = new WorldBuilder(WIDTH, HEIGHT).makeCaves().build();
        creature = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);

        factory = new StuffFactory(world);
        for (int i = 0; i < world.width() * world.height() / 20; i++) {
            factory.newRock();
        }
        for (int i = 0; i < 10; i++) {
            factory.randomArmor();
            factory.randomWeapon();
        }
        factory.newVictoryItem();
    }

    @Test
    public void glyph() throws Exception {
        Assert.assertEquals('b', creature.glyph());
    }

    @Test
    public void color() throws Exception {
        Assert.assertEquals(AsciiPanel.yellow, creature.color());
    }

    @Test
    public void inventory() throws Exception {
        pickup();
        before();
        drop();
    }

    @Test(expected = NullPointerException.class)
    public void setCreatureAi() {
        int x = creature.x;
        creature.moveBy(1, 1);
        Assert.assertEquals(x + 1, creature.x);
    }

    @Test
    public void name() throws Exception {
        Assert.assertEquals("bat", creature.name());
    }

    @Test
    public void moveBy() throws Exception {
        creature.setCreatureAi(new PlayerAi(creature, new ArrayList<>()));
        int x = creature.x;
        int y = creature.y;
        creature.moveBy(1, 1);
        Assert.assertEquals(x + 1, creature.x);
        Assert.assertEquals(y + 1, creature.y);
    }

    @Test
    public void maxHp() throws Exception {
        Assert.assertEquals(15, creature.maxHp());
    }

    @Test
    public void hp() throws Exception {
        Assert.assertEquals(15, creature.hp());
    }

    @Test
    public void attackValue() throws Exception {
        Assert.assertEquals(5, creature.attackValue());
    }

    @Test
    public void defenseValue() throws Exception {
        Assert.assertEquals(0, creature.defenseValue());
    }

    @Test
    public void attack() throws Exception {
        creature.attack(creature);
        Assert.assertTrue(creature.hp() < creature.maxHp());

    }

    @Test
    public void modifyHp() throws Exception {
        creature.modifyHp(10);
        Assert.assertEquals(25, creature.hp());
    }

    @Test
    public void doAction() throws Exception {
        List<String> list = new ArrayList<String>();
        creature.setCreatureAi(new PlayerAi(creature, list));
        world.addAtEmptyLocation(creature);
        begin:
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                if (world.creature(x, y) != creature) {
                    creature.x = x ;
                    creature.y = y ;
                    break begin;
                }
            }
        creature.doAction("Hello");
        Assert.assertEquals("You Hello.", list.get(0));
    }

    @Test
    public void pickup() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                Item item = world.item(x, y);
                if (item != null) {
                    creature.x = x;
                    creature.y = y;
                    creature.pickup();
                    Item[] items = creature.inventory().getItems();
                    int count = 0;
                    for (int i = 0; i < items.length; i++) {
                        if (items[i] != null)
                            count++;
                    }
                    Assert.assertEquals(1, count);
                    return;
                }
            }
    }

    @Test
    public void drop() throws Exception {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                Item item = world.item(x, y);
                if (item != null) {
                    creature.x = x;
                    creature.y = y;
                    creature.pickup();
                    Item[] items = creature.inventory().getItems();
                    creature.drop(items[0]);
                    int count = 0;
                    for (int i = 0; i < items.length; i++) {
                        if (items[i] != null)
                            count++;
                    }
                    Assert.assertEquals(0, count);
                    return;
                }
            }
    }

    @Test
    public void weapon() throws Exception {
        Assert.assertNull(creature.weapon());
    }

    @Test
    public void armor() throws Exception {
        Assert.assertNull(creature.armor());
    }

}