package roguelike.ai;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;
import roguelike.Creature;
import roguelike.world.World;
import roguelike.world.WorldBuilder;

/**
 * Created by Oleg on 5/9/2017.
 */
public class CreatureAiTest {
    @Test
    public void onEnter() throws Exception {
        World world = new WorldBuilder(100, 120).makeCaves().build();
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
        CreatureAi ai = new CreatureAi(bat);
        ai.onEnter(20, 30, world.tile(20, 30));
        if (world.tile(20, 30).isGround()) {
            Assert.assertTrue(bat.x == 20 && bat.y == 30);
        }
    }

    @Test(timeout = 1000)
    public void wander() throws Exception {
        while (true) {
            World world = new WorldBuilder(100, 120).makeCaves().build();
            Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
            CreatureAi ai = new CreatureAi(bat);
            int x = bat.x;
            int y = bat.y;

            ai.wander();
            if (bat.x != x || bat.y != y) {
                return;
            }
        }
    }

    @Test
    public void onUpdate() throws Exception {
        //nothing
    }

}