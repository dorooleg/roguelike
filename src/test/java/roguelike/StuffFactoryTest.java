package roguelike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roguelike.invenotry.Item;
import roguelike.world.World;
import roguelike.world.WorldBuilder;

/**
 * Created by Oleg on 5/9/2017.
 */
public class StuffFactoryTest {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 120;
    private World world;

    @Before
    public void before() {
        world = new WorldBuilder(WIDTH, HEIGHT).makeCaves().build();
    }
    @Test
    public void newPlayer() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Creature player = factory.newPlayer(null);
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.creature(x, y) == player)
                    return;
        Assert.fail("Is not found player");
    }

    @Test
    public void newFungus() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Creature fungus = factory.newFungus();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.creature(x, y) == fungus)
                    return;
        Assert.fail("Is not found fungus");
    }

    @Test
    public void newBat() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Creature bat = factory.newBat();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.creature(x, y) == bat)
                    return;
        Assert.fail("Is not found bat");
    }

    @Test
    public void newRock() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newRock();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found rock");
    }

    @Test
    public void newVictoryItem() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newVictoryItem();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found victory");
    }

    @Test
    public void newDagger() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newDagger();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found dagger");
    }

    @Test
    public void newSword() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newSword();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found sword");
    }

    @Test
    public void newStaff() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newStaff();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found staff");
    }

    @Test
    public void newLightArmor() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newLightArmor();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found light armor");
    }

    @Test
    public void newMediumArmor() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newMediumArmor();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found medium armor");
    }

    @Test
    public void newHeavyArmor() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        Item item = factory.newHeavyArmor();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) == item)
                    return;
        Assert.fail("Is not found heavy armor");
    }

    @Test
    public void randomWeapon() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        factory.randomWeapon();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) != null)
                    return;
        Assert.fail("Is not found random weapon");
    }

    @Test
    public void randomArmor() throws Exception {
        StuffFactory factory = new StuffFactory(world);
        factory.randomArmor();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (world.item(x, y) != null)
                    return;
        Assert.fail("Is not found random armor");
    }

}