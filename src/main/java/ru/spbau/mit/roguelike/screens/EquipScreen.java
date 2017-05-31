package ru.spbau.mit.roguelike.screens;

import org.apache.log4j.Logger;
import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.inventory.Item;

/**
 * Created by Oleg on 5/9/2017.
 * Скрин для работы с экипировкой
 */
public class EquipScreen extends InventoryBasedScreen {

    private final static Logger logger = Logger.getLogger(EquipScreen.class);

    EquipScreen(Creature player) {
        super(player);
        logger.debug("EquipScreen");
    }

    /**
     * @return Название скрина
     */
    @Override
    protected String getVerb() {
        return "wear or wield";
    }

    /**
     * @param item - элемент
     * @return true если элемент доступен
     */
    @Override
    protected boolean isAcceptable(Item item) {
        return item.getAttackValue() > 0 || item.getDefenseValue() > 0;
    }

    /**
     * @param item - элемент
     * @return возвращает скрин с используемым элементом
     */
    @Override
    protected Screen use(Item item) {
        if (player.isEquipped(item)) {
            player.unequip(item);
        } else {
            player.equip(item);
        }
        return null;
    }
}
