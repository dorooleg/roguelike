package roguelike.world;

import roguelike.*;
import roguelike.Creature;
import roguelike.invenotry.Item;
import roguelike.Point;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 */
public class World {
    private List<Creature> creatures = new ArrayList<>();
    private Tile[][] tiles;
    private Item[][] items;
    private int width;
    public int width() { return width; }

    private int height;
    public int height() { return height; }

    public World(Tile[][] tiles) {
        width = tiles.length;
        if (width == 0) {
            throw new IllegalArgumentException("Tile width must be greater than 0");
        }
        height = tiles[0].length;
        this.tiles = tiles;
        items = new Item[width][height];
    }

    public Creature creature(int x, int y) {
        for (Creature c : creatures) {
            if (c.x == x && c.y == y) {
                return c;
            }
        }
        return null;
    }

    public Tile tile(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height ? Tile.BOUNDS : tiles[x][y];
    }

    public char glyph(int x, int y) {
        Creature creature = creature(x, y);
        if (creature != null)
            return creature.glyph();

        if (item(x, y) != null)
            return item(x,y).glyph();

        return tile(x, y).glyph();
    }

    public Color color(int x, int y) {
        Creature creature = creature(x, y);
        if (creature != null)
            return creature.color();

        if (item(x, y) != null)
            return item(x, y).color();

        return tile(x, y).color();
    }

    public Item item(int x, int y) {
        return items[x][y];
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width());
            y = (int)(Math.random() * height());
        }
        while(!tile(x, y).isGround());

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

    public void addAtEmptyLocation(Item item) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width());
            y = (int)(Math.random() * height());
        }
        while(!tile(x, y).isGround());

        items[x][y] = item;
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    public void remove(int x, int y) {
        items[x][y] = null;
    }

    public boolean addAtEmptySpace(Item item, int x, int y) {
        if (item == null)
            return true;

        List<Point> points = new ArrayList<>();
        List<Point> checked = new ArrayList<>();

        points.add(new Point(x, y));

        while (!points.isEmpty()) {
            Point p = points.remove(0);
            checked.add(p);

            if (!tile(p.x, p.y).isGround())
                continue;

            if (items[p.x][p.y] == null) {
                items[p.x][p.y] = item;
                Creature c = this.creature(p.x, p.y);
                //if (c != null)
                //    c.notify("A %s lands betwwn your feet.", item.name());
                return true;
            } else {
                List<Point> neighbors = p.neighbors8();
                neighbors.removeAll(checked);
                points.addAll(neighbors);
            }
        }
        return false;
    }
}
