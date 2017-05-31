package ru.spbau.mit.roguelike.inventory;

/**
 * Created by Oleg on 5/8/2017.
 * Объект для хренения элементов
 */
public class Inventory {
    private Item[] items;
    public Item[] getItems() { return items.clone(); }

    public Inventory(int max) {
        items = new Item[max];
    }

    /**
     * @param item - добавляемый элемент в ближайшую свободную позицию
     */
    public void add(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }

    /**
     * @param item - удаляемый элемент
     */
    public void remove(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    /**
     * @return true если полностью полный
     */
    public boolean isFull() {
        int size = 0;
        for (Item item : items) {
            if (item != null) {
                size++;
            }
        }
        return size == items.length;
    }
}
