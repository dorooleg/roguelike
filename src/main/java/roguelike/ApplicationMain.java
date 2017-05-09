package roguelike;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;
import roguelike.screens.Screen;
import roguelike.screens.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Oleg on 5/6/2017.
 */
public class ApplicationMain extends JFrame implements KeyListener {

    private final static Logger logger = Logger.getLogger(ApplicationMain.class);

    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private Screen screen;

    ApplicationMain() {
        super();
        logger.debug("Application start");
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint() {
        logger.debug("Repaint");
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        logger.debug("KeyPressed = " + e.toString());
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        logger.debug("main");
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
