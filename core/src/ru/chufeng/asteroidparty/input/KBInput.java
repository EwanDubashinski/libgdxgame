package ru.chufeng.asteroidparty.input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Chufeng on 24.04.2017.
 */
public class KBInput extends Observable implements Driver {
    //TODO: Add xml conf support. Hardcode must die.
    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setChanged();
            super.notifyObservers(EVENT.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setChanged();
            super.notifyObservers(EVENT.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setChanged();
            super.notifyObservers(EVENT.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setChanged();
            super.notifyObservers(EVENT.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            setChanged();
            super.notifyObservers(EVENT.FIRE);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            setChanged();
            super.notifyObservers(EVENT.NEXT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            setChanged();
            super.notifyObservers(EVENT.PREV);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setChanged();
            super.notifyObservers(EVENT.PAUSE);
        }
    }

}
