package ru.spbau.mit.roguelike.inventory;

import asciiPanel.AsciiPanel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Oleg on 5/9/2017.
 */
public class InventoryTest {
    @Test
    public void getItems() throws Exception {
        add();
    }

    @Test
    public void add() throws Exception {
        Inventory inventory = new Inventory(5);
        Item item = new Item('*', AsciiPanel.yellow, "star");
        inventory.add(item);
        Item[] items = inventory.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                return;
            }
        }
        Assert.fail();
    }

    @Test
    public void remove() throws Exception {
        Inventory inventory = new Inventory(5);
        Item item = new Item('*', AsciiPanel.yellow, "star");
        inventory.add(item);
        inventory.remove(item);
        Item[] items = inventory.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                Assert.fail();
            }
        }
    }

    @Test
    public void isFull() throws Exception {
        Inventory inventory = new Inventory(5);
        for (int i = 0; i < 5; i++) {
            inventory.add(new Item('*', AsciiPanel.green, "star"));
        }
        Assert.assertTrue(inventory.isFull());
    }

}