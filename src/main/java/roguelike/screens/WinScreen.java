package roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;

import java.awt.event.KeyEvent;

/**
 * Created by Oleg on 5/6/2017.
 */
public class WinScreen implements Screen {

    private final static Logger logger = Logger.getLogger(WinScreen.class);
    WinScreen() {
        super();
        logger.debug("WinScreen");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
