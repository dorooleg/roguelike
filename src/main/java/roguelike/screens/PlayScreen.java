package roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;
import roguelike.Creature;
import roguelike.StuffFactory;
import roguelike.invenotry.Item;
import roguelike.world.World;
import roguelike.world.WorldBuilder;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 5/6/2017.
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

    private void createCreatures(StuffFactory stuffFactory) {
        player = stuffFactory.newPlayer(messages);
        for (int i = 0; i < 8; i++) {
            stuffFactory.newFungus();
        }
        for (int i = 0; i < 20; i++) {
            stuffFactory.newBat();
        }
    }

    private void createItems(StuffFactory factory) {
        for (int i = 0; i < world.width() * world.height() / 20; i++) {
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
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    private int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        final int left = getScrollX();
        final int top = getScrollY();

        displayTiles(terminal, left, top);
        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
        terminal.writeCenter("-- press [escape] to end ---", 22);
        String stats = String.format("%3d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, 1, 23);
        displayMessages(terminal, messages);
        if (subscreen != null)
            subscreen.displayOutput(terminal);
    }

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

        if (player.hp() < 1) {
            return new LoseScreen();
        }

        return this;
    }

    private Screen userExits() {
        for (Item item : player.inventory().getItems()) {
            if (item != null && item.name().equals("teddy bear")) {
                return new WinScreen();
            }
        }
        player.modifyHp(0);
        return new LoseScreen();
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                Creature creature = world.creature(wx, wy);

                AsciiPanel tmp = creature != null ?
                        terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color()) :
                        terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

}
