package roguelike;

import org.apache.log4j.Logger;
import roguelike.ai.CreatureAi;
import roguelike.invenotry.Inventory;
import roguelike.invenotry.Item;
import roguelike.world.World;

import java.awt.*;

/**
 * Created by Oleg on 5/6/2017.
 */
public class Creature {

    private final static Logger logger = Logger.getLogger(Creature.class);

    private World world;


    public int x;
    public int y;

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    private Inventory inventory;

    public Inventory inventory() {
        return inventory;
    }

    public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        hp = maxHp;
        attackValue = attack;
        defenseValue = defense;
        this.name = name;
        this.inventory = new Inventory(20);
    }

    private CreatureAi ai;

    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    private String name;

    public String name() {
        return name;
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        logger.debug("MoveBy " + mx + " " + my);
        if (mx == 0 && my == 0) {
            return;
        }
        Creature other = world.creature(x + mx, y + my);

        if (other == null)
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        else
            attack(other);
    }

    private int maxHp;

    public int maxHp() {
        return maxHp;
    }

    private int hp;

    public int hp() {
        return hp;
    }

    private int attackValue;

    public int attackValue() {
        return attackValue
                + (weapon == null ? 0 : weapon.attackValue())
                + (armor == null ? 0 : armor.attackValue());
    }

    private int defenseValue;

    public int defenseValue() {
        return defenseValue
                + (weapon == null ? 0 : weapon.defenseValue())
                + (armor == null ? 0 : armor.defenseValue());
    }

    public void attack(Creature other) {
        int amount = Math.max(0, attackValue() - other.defenseValue());
        logger.debug("attack the" + other.glyph() + " damage" + amount);
        amount = (int) (Math.random() * amount) + 1;
        other.modifyHp(-amount);
        doAction("attack the '%s' for %d damage", other.glyph, amount);
    }

    public void modifyHp(int amount) {
        hp += amount;
        if (hp < 1) {
            doAction("die");
            logger.debug("die");
            world.remove(this);
        }
    }

    public void doAction(String message, Object... params) {
        int r = 9;
        for (int ox = -r; ox < r + 1; ox++) {
            for (int oy = -r; oy < r + 1; oy++) {
                if (ox * ox + oy * oy > r * r)
                    continue;

                Creature other = world.creature(x + ox, y + oy);

                if (other == null)
                    continue;


                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);

            }
        }
    }

    private String makeSecondPerson(String text) {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }

    public void pickup() {
        logger.debug("pickup");
        Item item = world.item(x, y);
        if (inventory.isFull() || item == null) {
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", item.name());
            world.remove(x, y);
            inventory.add(item);
        }
    }

    public void drop(Item item) {
        logger.debug("drop " + item.toString());
        if (world.addAtEmptySpace(item, x, y)) {
            doAction("drop a " + item.name());
            inventory.remove(item);
            unequip(item);
        } else {
            notify("There's nowhere to drop the %s.", item.name());
        }
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    private Item weapon;

    public Item weapon() {
        return weapon;
    }

    private Item armor;

    public Item armor() {
        return armor;
    }

    public void unequip(Item item) {
        logger.debug("unequip " + item.toString());
        if (item == null)
            return;

        if (item == armor) {
            doAction("remove a " + item.name());
            armor = null;
        } else if (item == weapon) {
            doAction("put away a " + item.name());
            weapon = null;
        }
    }

    public boolean isEquipped(Item item) {
        return item == weapon || item == armor;
    }

    public void equip(Item item) {
        logger.debug("equip " + item.toString());
        if (item.attackValue() == 0 && item.defenseValue() == 0)
            return;

        if (item.attackValue() >= item.defenseValue()) {
            unequip(weapon);
            doAction("wield a " + item.name());
            weapon = item;
        } else {
            unequip(armor);
            doAction("put on a " + item.name());
            armor = item;
        }
    }


    public void update() {
        ai.onUpdate();
    }

    public boolean canEnter(int wx, int wy) {
        return world.tile(wx, wy).isGround() && world.creature(wx, wy) == null;
    }

    public Creature creature(int wx, int wy) {
        return world.creature(wx, wy);
    }
}
