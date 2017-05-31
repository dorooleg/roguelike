package ru.spbau.mit.roguelike.ai;

import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.StuffFactory;

/**
 * Created by Oleg on 5/7/2017.
 * Создание врага fungus
 */
public class FungusAi extends CreatureAi {

    private StuffFactory factory;
    private int spreadcount;

    public FungusAi(Creature creature, StuffFactory factory) {
        super(creature);
        this.factory = factory;
    }

    /**
     * Оновить положение объекта
     */
    public void onUpdate() {
        /*
        if (spreadcount < 5 && Math.random() < 0.02) {
            spread();
        }
        */
    }

    private void spread() {
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;
        if (!creature.canEnter(x, y)) {
            return;
        }

        creature.doAction("spawn a child");

        Creature child = factory.newFungus();
        child.x = x;
        child.y = y;
        spreadcount++;
    }
}
