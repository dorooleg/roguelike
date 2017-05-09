package roguelike;

import asciiPanel.AsciiPanel;
import roguelike.ai.BatAi;
import roguelike.ai.FungusAi;
import roguelike.ai.PlayerAi;
import roguelike.invenotry.Item;
import roguelike.world.World;

import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 */
public class StuffFactory {
    private World world;

    public StuffFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages) {
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "player", 100, 20, 5);
        world.addAtEmptyLocation(player);
        new PlayerAi(player, messages);
        return player;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 0, 0);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newBat() {
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }

    public Item newRock() {
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock);
        return rock;
    }

    public Item newVictoryItem() {
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newDagger() {
        Item item = new Item(')', AsciiPanel.white, "dagger");
        item.modifyAttackValue(5);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newSword() {
        Item item = new Item(')', AsciiPanel.brightWhite, "sword");
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newStaff() {
        Item item = new Item(')', AsciiPanel.yellow, "staff");
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newLightArmor(){
        Item item = new Item('[', AsciiPanel.green, "tunic");
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newMediumArmor(){
        Item item = new Item('[', AsciiPanel.white, "chainmail");
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item newHeavyArmor(){
        Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item);
        return item;
    }

    public Item randomWeapon(){
        switch ((int)(Math.random() * 3)){
            case 0: return newDagger();
            case 1: return newSword();
            default: return newStaff();
        }
    }

    public Item randomArmor(){
        switch ((int)(Math.random() * 3)){
            case 0: return newLightArmor();
            case 1: return newMediumArmor();
            default: return newHeavyArmor();
        }
    }
}
