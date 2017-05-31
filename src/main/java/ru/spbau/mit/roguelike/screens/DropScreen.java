package ru.spbau.mit.roguelike.screens;

import org.apache.log4j.Logger;
import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.inventory.Item;

/**
 * Created by Oleg on 5/8/2017.
 * Скрин для удаления элементов
 */
public class DropScreen extends InventoryBasedScreen {

    private final static Logger logger = Logger.getLogger(DropScreen.class);

    DropScreen(Creature player) {
        super(player);
        logger.debug("DropScreen");
    }

    /**
     * @return имя скрина
     */
    @Override
    protected String getVerb() {
        return "drop";
    }

    /**
     * @param item - элемент
     * @return true если элемент доступен
     */
    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
}
