package roguelike.screens;

import org.apache.log4j.Logger;
import roguelike.Creature;
import roguelike.invenotry.Item;

/**
 * Created by Oleg on 5/8/2017.
 */
public class DropScreen extends InventoryBasedScreen {

    private final static Logger logger = Logger.getLogger(DropScreen.class);

    public DropScreen(Creature player) {
        super(player);
        logger.debug("DropScreen");
    }

    @Override
    protected String getVerb() {
        return "drop";
    }

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
