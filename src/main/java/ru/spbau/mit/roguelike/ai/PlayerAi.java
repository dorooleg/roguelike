package ru.spbau.mit.roguelike.ai;

import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.Tile;

import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 * Ai для игрока
 */
public class PlayerAi extends CreatureAi {

    List<String> messages;

    public PlayerAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    /**
     * @param x    - координаты x
     * @param y    - коодинаты y
     * @param tile - getTile в который переходят или копают
     */
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

    /**
     * @param message - сообщение для нотификации
     */
    public void onNotify(String message){
        messages.add(message);
    }
}
