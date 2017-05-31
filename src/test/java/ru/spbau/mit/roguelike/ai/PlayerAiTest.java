package ru.spbau.mit.roguelike.ai;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;
import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.Tile;
import ru.spbau.mit.roguelike.world.World;
import ru.spbau.mit.roguelike.world.WorldBuilder;

import java.util.ArrayList;

/**
 * Created by Oleg on 5/9/2017.
 */
public class PlayerAiTest {
    @Test
    public void onEnter() throws Exception {
        World world = new WorldBuilder(100, 120).makeCaves().build();
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "bat", 100, 20, 5);
        PlayerAi ai = new PlayerAi(player, new ArrayList<>());
        ai.onEnter(20, 30, world.getTile(20, 30));
        if (world.getTile(20, 30).isGround()) {
            Assert.assertTrue(player.x == 20 && player.y == 30);
        } else if (world.getTile(20, 30).isDiggable()) {
            Assert.assertEquals(Tile.WALL, world.getTile(20, 30));
        }
    }

}