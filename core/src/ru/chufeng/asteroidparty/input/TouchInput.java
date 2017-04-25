package ru.chufeng.asteroidparty.input;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chufeng on 24.04.2017.
 */
public class TouchInput extends Observable implements Driver {
    private Touchpad touchpad;
    private TouchInput(){}//Hide default constructor
    TouchInput(Touchpad touchpad){
        this.touchpad = touchpad;
    }

    @Override
    public void update() {
        if (touchpad.getKnobX() < 0) {
            setChanged();
            super.notifyObservers(EVENT.LEFT);
        }
        if (touchpad.getKnobX() > 0) {
            setChanged();
            super.notifyObservers(EVENT.RIGHT);
        }
        if (touchpad.getKnobY() < 0) {
            setChanged();
            super.notifyObservers(EVENT.DOWN);
        }
        if (touchpad.getKnobY() > 0) {
            setChanged();
            super.notifyObservers(EVENT.UP);
        }
    }

}
