package roguelike.screens;

import org.apache.log4j.Logger;
import roguelike.Creature;
import roguelike.invenotry.Item;

/**
 * Created by Oleg on 5/9/2017.
 */
public class EquipScreen extends InventoryBasedScreen {

    private final static Logger logger = Logger.getLogger(EquipScreen.class);

    public EquipScreen(Creature player) {
        super(player);
        logger.debug("EquipScreen");
    }

    @Override
    protected String getVerb() {
        return "wear or wield";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.attackValue() > 0 || item.defenseValue() > 0;
    }

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
