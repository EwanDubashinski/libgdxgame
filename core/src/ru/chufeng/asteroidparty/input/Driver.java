package ru.chufeng.asteroidparty.input;

import com.badlogic.gdx.InputProcessor;

import java.util.Observer;

/**
 * @author Chufeng
 */
public interface Driver extends InputProcessor {
    void update();
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObservers();
    void notifyObservers(Object arg);
    void deleteObservers();
    int countObservers();

    String getDebugInfo();
}
