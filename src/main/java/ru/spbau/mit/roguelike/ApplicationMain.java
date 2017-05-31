package ru.spbau.mit.roguelike;

import asciiPanel.AsciiPanel;
import org.apache.log4j.Logger;
import ru.spbau.mit.roguelike.screens.Screen;
import ru.spbau.mit.roguelike.screens.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Oleg on 5/6/2017.
 * Main игры
 */
public class ApplicationMain extends JFrame implements KeyListener {

    private final static Logger logger = Logger.getLogger(ApplicationMain.class);

    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private Screen screen;

    private ApplicationMain() {
        super();
        logger.debug("Application start");
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    /**
     * перерисовка
     */
    public void repaint() {
        logger.debug("Repaint");
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     * @param e событие набора
     *
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * @param e - событие
     * Событие нажатия кнопки
     */
    @Override
    public void keyPressed(KeyEvent e) {
        logger.debug("KeyPressed = " + e.toString());
        screen = screen.respondToUserInput(e);
        repaint();
    }

    /**
     * @param e - событие
     * Событие кнопка отпущена
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * @param args аргументы командной строки
     * main - начало проекта
     */
    public static void main(String[] args) {
        logger.debug("main");
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
