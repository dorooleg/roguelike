package ru.spbau.mit.roguelike.ai;

import ru.spbau.mit.roguelike.Creature;

/**
 * Created by Oleg on 5/7/2017.
 * Ai для bat
 */
public class BatAi extends CreatureAi {
    public BatAi(Creature creature) {
        super(creature);
    }

    /**
     * Обновление действия
     */
    @Override
    public void onUpdate() {
        wander();
        wander();
    }
}
