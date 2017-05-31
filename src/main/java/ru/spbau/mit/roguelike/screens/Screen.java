package ru.spbau.mit.roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by Oleg on 5/6/2017.
 * Базовый класс для скринов
 */
public interface Screen {
    /**
     * @param terminal - отрисовка изменения на срине
     */
    void displayOutput(AsciiPanel terminal);

    /**
     * @param key - событие пользовательского ввода
     * @return возвращает Screen после обработки пользовательского ввода
     */
    Screen respondToUserInput(KeyEvent key);
}
