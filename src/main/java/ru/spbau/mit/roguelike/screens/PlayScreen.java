package ru.spbau.mit.roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;
import ru.spbau.mit.roguelike.Creature;
import ru.spbau.mit.roguelike.StuffFactory;
import ru.spbau.mit.roguelike.inventory.Item;
import ru.spbau.mit.roguelike.world.World;
import ru.spbau.mit.roguelike.world.WorldBuilder;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
 * Screen для отображения игрового процесса
 */
public class PlayScreen implements Screen {

    private final static Logger logger = Logger.getLogger(PlayScreen.class);

    private World world;
    private int screenWidth;
    private int screenHeight;
    private Creature player;
    private List<String> messages;
    private Screen subscreen;

    PlayScreen() {
        logger.debug("PlayScreen");
        screenHeight = 21;
        screenWidth = 80;
        messages = new ArrayList<>();
        createWorld();

        StuffFactory stuffFactory = new StuffFactory(world);
        createCreatures(stuffFactory);
        createItems(stuffFactory);
    }

    /**
     * @param stuffFactory - принимает фабрику. Из этой фабрики выбираются объекты живых существ
     */
    private void createCreatures(StuffFactory stuffFactory) {
        player = stuffFactory.newPlayer(messages);
        for (int i = 0; i < 8; i++) {
            stuffFactory.newFungus();
        }
        for (int i = 0; i < 20; i++) {
            stuffFactory.newBat();
        }
    }

    /**
     * @param factory - принимает фабрику. Из этой фабрики выбираются элементы
     */
    private void createItems(StuffFactory factory) {
        for (int i = 0; i < world.getWidth() * world.getHeight() / 20; i++) {
            factory.newRock();
        }
        for (int i = 0; i < 10; i++) {
            factory.randomArmor();
            factory.randomWeapon();
        }
        factory.newVictoryItem();
    }

    private void createWorld() {
        world = new WorldBuilder(90, 31).makeCaves().build();
    }


    private int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    /**
     * @param terminal - отрисовка изменения на срине
     */
    @Override
    public void displayOutput(AsciiPanel terminal) {
        final int left = getScrollX();
        final int top = getScrollY();

        displayTiles(terminal, left, top);
        terminal.write(player.getGlyph(), player.x - left, player.y - top, player.getColor());
        terminal.writeCenter("-- press [escape] to end ---", 22);
        String stats = String.format("%3d/%3d getHp", player.getHp(), player.getMaxHp());
        terminal.write(stats, 1, 23);
        displayMessages(terminal, messages);
        if (subscreen != null)
            subscreen.displayOutput(terminal);
    }

    /**
     * @param key - событие пользовательского ввода
     * @return возвращает новый скрин
     */
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_G:
                case ',':
                    player.pickup();
                    break;
                case KeyEvent.VK_ESCAPE:
                    return userExits();
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_H:
                    player.moveBy(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_L:
                    player.moveBy(1, 0);
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_K:
                    player.moveBy(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_J:
                    player.moveBy(0, 1);
                    break;
                case KeyEvent.VK_Y:
                    player.moveBy(-1, -1);
                    break;
                case KeyEvent.VK_U:
                    player.moveBy(1, -1);
                    break;
                case KeyEvent.VK_B:
                    player.moveBy(-1, 1);
                    break;
                case KeyEvent.VK_N:
                    player.moveBy(1, 1);
                    break;
                case KeyEvent.VK_D:
                    subscreen = new DropScreen(player);
                    break;
                case KeyEvent.VK_W:
                    subscreen = new EquipScreen(player);
                    break;
            }
        }

        if (subscreen == null)
            world.update();

        if (player.getHp() < 1) {
            return new LoseScreen();
        }

        return this;
    }

    /**
     * @return Возвращает новый скрин после выхода.
     */
    private Screen userExits() {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getName().equals("teddy bear")) {
                return new WinScreen();
            }
        }
        player.modifyHp(0);
        return new LoseScreen();
    }

    /**
     * @param terminal
     * @param left
     * @param top
     * отображение тайлов
     */
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                Creature creature = world.getCreature(wx, wy);

                AsciiPanel tmp = creature != null ?
                        terminal.write(creature.getGlyph(), creature.x - left, creature.y - top, creature.getColor()) :
                        terminal.write(world.glyph(wx, wy), x, y, world.getColor(wx, wy));
            }
        }
    }

    /**
     * @param terminal - терминал на котором будут отображены сообщения
     * @param messages - сообещния для отображения
     */
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

}
