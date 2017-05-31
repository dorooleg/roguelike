package ru.spbau.mit.roguelike.ai;

import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.Tile;

/**
 * Created by Oleg on 5/6/2017.
 * Класс представления Ai объектов
 */
public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    /**
     * @param x - координаты x
     * @param y - коодинаты y
     * @param tile - getTile в который переходят
     */
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        } else {
            creature.doAction("bump into a wall");
        }
    }

    /**
     * Пермещение объекта
     */
    void wander() {
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;

        Creature other = creature.getCreature(creature.x + mx, creature.y + my);

        if (!(other != null && other.getGlyph() == creature.getGlyph())) {
            creature.moveBy(mx, my);
        }
    }

    public void onUpdate() {
    }

    public void onNotify(String s) {

    }
}
