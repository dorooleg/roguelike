package ru.spbau.mit.roguelike.ai;

import asciiPanel.AsciiPanel;
import org.junit.Test;
import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.world.World;
import ru.spbau.mit.roguelike.world.WorldBuilder;

/**
 * Created by Oleg on 5/9/2017.
 */
public class BatAiTest {
    @Test(timeout = 3000)
    public void onUpdate() throws Exception {
        while (true) {
            World world = new WorldBuilder(100, 120).makeCaves().build();
            Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
            BatAi ai = new BatAi(bat);
            int x = bat.x;
            int y = bat.y;

            ai.onUpdate();
            if (bat.x != x || bat.y != y) {
                return;
            }
        }
    }

}