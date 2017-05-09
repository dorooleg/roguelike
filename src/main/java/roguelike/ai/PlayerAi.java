package roguelike.ai;

import roguelike.Creature;
import roguelike.Tile;

import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 */
public class PlayerAi extends CreatureAi {

    List<String> messages;

    public PlayerAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    @Override
    public void onEnter(int x, int y, Tile tile)
    {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }

    public void onNotify(String message){
        messages.add(message);
    }
}
