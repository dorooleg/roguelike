package roguelike.ai;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;
import roguelike.Creature;
import roguelike.StuffFactory;
import roguelike.world.World;
import roguelike.world.WorldBuilder;

/**
 * Created by Oleg on 5/9/2017.
 */
public class FungusAiTest {
    @Test
    public void onUpdate() throws Exception {
        World world = new WorldBuilder(100, 120).makeCaves().build();
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 0, 0);
        StuffFactory factory = new StuffFactory(world);
        FungusAi ai = new FungusAi(fungus, factory);

        for (int i = 0; i < 10000; i++) {
            ai.onUpdate();
        }

        int counter = 0;
        for (int x = 0; x < 100; x++)
            for (int y = 0; y < 100; y++)
                if (world.creature(x, y) != null && world.creature(x, y).name() == "fungus") {
                    counter++;
                }
        Assert.assertTrue(counter > 1);
    }

}