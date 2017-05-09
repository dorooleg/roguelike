package roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;

import java.awt.event.KeyEvent;

/**
 * Created by Oleg on 5/6/2017.
 */
public class StartScreen implements Screen {

    private final static Logger logger = Logger.getLogger(StartScreen.class);

    public StartScreen() {
        logger.debug("StartScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Roguelike", 1, 1);
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
