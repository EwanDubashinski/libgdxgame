package ru.chufeng.asteroidparty.input;

import java.util.Observer;

/**
 * Created by Chufeng on 24.04.2017.
 */
public interface Driver {
    void update();
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObservers();
    void notifyObservers(Object arg);
    void deleteObservers();
    int countObservers();
}
