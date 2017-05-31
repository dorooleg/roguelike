package ru.spbau.mit.roguelike;

import org.apache.log4j.Logger;
import ru.spbau.mit.roguelike.ai.CreatureAi;
import ru.spbau.mit.roguelike.inventory.Inventory;
import ru.spbau.mit.roguelike.inventory.Item;
import ru.spbau.mit.roguelike.world.World;

import java.awt.*;

/**
 * Created by Oleg on 5/6/2017.
 * Базовый класс для создаваемых объектов
 */
public class Creature {

    private final static Logger logger = Logger.getLogger(Creature.class);

    private World world;


    public int x;
    public int y;

    private char glyph;

    /**
     * @return символьное прдеставление объекта
     */
    public char getGlyph() {
        return glyph;
    }

    private Color color;

    /**
     * @return цвет объекта
     */
    public Color getColor() {
        return color;
    }

    private Inventory inventory;

    /**
     * @return инвентарь
     */
    public Inventory getInventory() {
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

    /**
     * @param ai установка ai
     */
    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    private String name;

    /**
     * @return имя объекта
     */
    public String getName() {
        return name;
    }


    /**
     * @param wx положение по оси x
     * @param wy положение по оси y
     * Копать
     */
    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    /**
     * @param mx координата x
     * @param my координата y
     * сдвиг объекта
     */
    public void moveBy(int mx, int my) {
        logger.debug("MoveBy " + mx + " " + my);
        if (mx == 0 && my == 0) {
            return;
        }
        Creature other = world.getCreature(x + mx, y + my);

        if (other == null)
            ai.onEnter(x + mx, y + my, world.getTile(x + mx, y + my));
        else
            attack(other);
    }

    private int maxHp;

    /**
     * @return Максимальное здоровье
     */
    public int getMaxHp() {
        return maxHp;
    }

    private int hp;

    /**
     * @return текущее здоровье
     */
    public int getHp() {
        return hp;
    }

    private int attackValue;

    /**
     * @return уровень атаки
     */
    int getAttackValue() {
        return attackValue
                + (weapon == null ? 0 : weapon.getAttackValue())
                + (armor == null ? 0 : armor.getAttackValue());
    }

    private int defenseValue;

    /**
     * @return Уровень защиты
     */
    int getDefenseValue() {
        return defenseValue
                + (weapon == null ? 0 : weapon.getDefenseValue())
                + (armor == null ? 0 : armor.getDefenseValue());
    }

    /**
     * @param other - атакуемый обхект
     * Атаковать объект
     */
    public void attack(Creature other) {
        int amount = Math.max(0, getAttackValue() - other.getDefenseValue());
        logger.debug("attack the" + other.getGlyph() + " damage" + amount);
        amount = (int) (Math.random() * amount) + 1;
        other.modifyHp(-amount);
        doAction("attack the '%s' for %d damage", other.glyph, amount);
    }

    /**
     * @param amount изменить здоровье
     */
    public void modifyHp(int amount) {
        hp += amount;
        if (hp < 1) {
            doAction("die");
            logger.debug("die");
            world.remove(this);
        }
    }

    /**
     * @param message сообщение
     * @param params параметры
     * Информирование о действии
     */
    public void doAction(String message, Object... params) {
        int r = 9;
        for (int ox = -r; ox < r + 1; ox++) {
            for (int oy = -r; oy < r + 1; oy++) {
                if (ox * ox + oy * oy > r * r)
                    continue;

                Creature other = world.getCreature(x + ox, y + oy);

                if (other == null)
                    continue;


                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);

            }
        }
    }

    /**
     * @param text - строка для преборазования
     * @return строка после обработки
     */
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

    /**
     * Поднять объект
     */
    public void pickup() {
        logger.debug("pickup");
        Item item = world.getItem(x, y);
        if (inventory.isFull() || item == null) {
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", item.getName());
            world.remove(x, y);
            inventory.add(item);
        }
    }

    /**
     * @param item - удаляемый объект
     */
    public void drop(Item item) {
        logger.debug("drop " + item.toString());
        if (world.addAtEmptySpace(item, x, y)) {
            doAction("drop a " + item.getName());
            inventory.remove(item);
            unequip(item);
        } else {
            notify("There's nowhere to drop the %s.", item.getName());
        }
    }

    /**
     * @param message - сообщение
     * @param params - параметры
     * Нотификация ai
     */
    private void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    private Item weapon;

    /**
     * @return выбранное оружие
     */
    public Item getWeapon() {
        return weapon;
    }

    private Item armor;

    /**
     * @return выбранная броня
     */
    public Item getArmor() {
        return armor;
    }

    /**
     * @param item удалить обмундирование
     */
    public void unequip(Item item) {
        if (item == null)
            return;

        logger.debug("unequip " + item.toString());

        if (item == armor) {
            doAction("remove a " + item.getName());
            armor = null;
        } else if (item == weapon) {
            doAction("put away a " + item.getName());
            weapon = null;
        }
    }

    /**
     * @param item элемент для проверки
     * @return true если такой элемент в текущий момент используется
     */
    public boolean isEquipped(Item item) {
        return item == weapon || item == armor;
    }

    /**
     * @param item - заиспользовать элемент
     */
    public void equip(Item item) {
        logger.debug("equip " + item.toString());
        if (item.getAttackValue() == 0 && item.getDefenseValue() == 0)
            return;

        if (item.getAttackValue() >= item.getDefenseValue()) {
            unequip(weapon);
            doAction("wield a " + item.getName());
            weapon = item;
        } else {
            unequip(armor);
            doAction("put on a " + item.getName());
            armor = item;
        }
    }


    /**
     * Обновление ai
     */
    public void update() {
        ai.onUpdate();
    }

    /**
     * @param wx - x координата клетки
     * @param wy - y координата клетки
     * @return true - если клетка пустая
     */
    public boolean canEnter(int wx, int wy) {
        return world.getTile(wx, wy).isGround() && world.getCreature(wx, wy) == null;
    }

    /**
     * @param wx - x координата getCreature
     * @param wy - y координата getCreature
     * @return объект getCreature
     */
    public Creature getCreature(int wx, int wy) {
        return world.getCreature(wx, wy);
    }
}
