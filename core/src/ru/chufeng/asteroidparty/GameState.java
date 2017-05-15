package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;

import java.util.Observable;
import java.util.Observer;

/**
 * <p>Singleton state class</p>
 *
 * @author Chufeng
 * @version 1.0
 */
public class GameState implements Observer {
    private static GameState instance;
    private GameMode mode;

    private GameState() {
    }

    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
            instance.setMode(GameMode.NEW);
        }
        return instance;
    }

    public GameMode getMode() {
        return mode;
    }

    private void setMode(GameMode mode) {
        this.mode = mode;
    }

    @Override
    public void update(Observable o, Object arg) { //TODO: State automata needs in better realization
        if (arg instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) arg;
            if (gameEvent == GameEvent.PAUSE && (instance.getMode() == GameMode.NEW ||
                    instance.getMode() == GameMode.GAMEOVER)) {
                Gdx.app.exit();
            } else if (gameEvent == GameEvent.PAUSE && instance.getMode() == GameMode.RUN) {
                GameState.getInstance().setMode(GameMode.PAUSE);
            } else if (gameEvent == GameEvent.PAUSE && instance.getMode() == GameMode.PAUSE) {
                instance.setMode(GameMode.RUN);
            } else if (gameEvent == GameEvent.EXIT) {
                Gdx.app.exit();
            } else if (gameEvent == GameEvent.STARTNEW) {
                instance.setMode(GameMode.START);
            } else if (gameEvent == GameEvent.STARTED) {
                instance.setMode(GameMode.RUN);
            }
        }
    }
}
