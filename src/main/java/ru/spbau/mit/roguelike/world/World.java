package ru.spbau.mit.roguelike.world;

import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.Point;
import ru.spbau.mit.roguelike.Tile;
import ru.spbau.mit.roguelike.inventory.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 * Класс представления мира
 */
public class World {
    private List<Creature> creatures = new ArrayList<>();
    private Tile[][] tiles;
    private Item[][] items;
    private int width;

    /**
     * @return ширина мира
     */
    public int getWidth() { return width; }

    private int height;

    /**
     * @return высота мира
     */
    public int getHeight() { return height; }

    public World(Tile[][] tiles) {
        width = tiles.length;
        if (width == 0) {
            throw new IllegalArgumentException("Tile getWidth must be greater than 0");
        }
        height = tiles[0].length;
        this.tiles = tiles;
        items = new Item[width][height];
    }

    /**
     * @param x - координаты x
     * @param y - координаты y
     * @return найти getCreature
     */
    public Creature getCreature(int x, int y) {
        for (Creature c : creatures) {
            if (c.x == x && c.y == y) {
                return c;
            }
        }
        return null;
    }

    /**
     * @param x - координаты x
     * @param y - координаты y
     * @return вернуть тайл
     */
    public Tile getTile(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height ? Tile.BOUNDS : tiles[x][y];
    }

    public char glyph(int x, int y) {
        Creature creature = getCreature(x, y);
        if (creature != null)
            return creature.getGlyph();

        if (getItem(x, y) != null)
            return getItem(x,y).getGlyph();

        return getTile(x, y).getGlyph();
    }

    /**
     * @param x - координата x
     * @param y - координата y
     * @return цвет элемента на карте
     */
    public Color getColor(int x, int y) {
        Creature creature = getCreature(x, y);
        if (creature != null)
            return creature.getColor();

        if (getItem(x, y) != null)
            return getItem(x, y).getColor();

        return getTile(x, y).getColor();
    }

    /**
     * @param x - коордианата x
     * @param y - коордиана y
     * @return вернуть элемент
     */
    public Item getItem(int x, int y) {
        return items[x][y];
    }

    /**
     * @param x - коордианта x
     * @param y - коордианта y
     *  копать
     */
    public void dig(int x, int y) {
        if (getTile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    /**
     * @param creature - добавляемый объект в случайное пустое место
     */
    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * getWidth());
            y = (int)(Math.random() * getHeight());
        }
        while(!getTile(x, y).isGround());

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

    /**
     * @param item - добавляемый элемент в случайное пустое место
     */
    public void addAtEmptyLocation(Item item) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * getWidth());
            y = (int)(Math.random() * getHeight());
        }
        while(!getTile(x, y).isGround());

        items[x][y] = item;
    }

    /**
     * @param other - удалить getCreature объект
     */
    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    /**
     * @param x - координата x
     * @param y - координата y
     * Удалить элемент по таким коордиантам
     */
    public void remove(int x, int y) {
        items[x][y] = null;
    }

    /**
     * Добавление элемента в окрестности
     * @param item - элемент
     * @param x - коордианта x
     * @param y - координата y
     * @return true если получилось добавить
     */
    public boolean addAtEmptySpace(Item item, int x, int y) {
        if (item == null)
            return true;

        List<Point> points = new ArrayList<>();
        List<Point> checked = new ArrayList<>();

        points.add(new Point(x, y));

        while (!points.isEmpty()) {
            Point p = points.remove(0);
            checked.add(p);

            if (!getTile(p.x, p.y).isGround())
                continue;

            if (items[p.x][p.y] == null) {
                items[p.x][p.y] = item;
                Creature c = this.getCreature(p.x, p.y);
                //if (c != null)
                //    c.notify("A %s lands betwwn your feet.", getItem.getName());
                return true;
            } else {
                List<Point> neighbors = p.getNeighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
        return false;
    }
}
