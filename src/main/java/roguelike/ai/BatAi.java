package roguelike.ai;

import roguelike.Creature;

/**
 * Created by Oleg on 5/7/2017.
 */
public class BatAi extends CreatureAi {
    public BatAi(Creature creature) {
        super(creature);
    }

    @Override
    public void onUpdate() {
        wander();
        wander();
    }
}
