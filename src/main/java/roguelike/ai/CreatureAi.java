package roguelike.ai;

import roguelike.Creature;
import roguelike.Tile;

/**
 * Created by Oleg on 5/6/2017.
 */
public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        } else {
            creature.doAction("bump into a wall");
        }
    }

    public void wander() {
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;

        Creature other = creature.creature(creature.x + mx, creature.y + my);

        if (!(other != null && other.glyph() == creature.glyph())) {
            creature.moveBy(mx, my);
        }
    }

    public void onUpdate() {
    }

    public void onNotify(String s) {

    }
}
