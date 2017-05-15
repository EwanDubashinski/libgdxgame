package ru.chufeng.asteroidparty.input;

import ru.chufeng.asteroidparty.GameEvent;

/**
 * Created by Chufeng on 28.04.2017.
 */
public class ParametrizedEvent {
    private GameEvent gameEvent;
    private float param;

    public ParametrizedEvent() {
    }

    public ParametrizedEvent(GameEvent gameEvent, float param) {
        this.gameEvent = gameEvent;
        this.param = param;
    }

    public GameEvent getGameEvent() {
        return gameEvent;
    }

    public void setGameEvent(GameEvent gameEvent) {
        this.gameEvent = gameEvent;
    }

    public float getParam() {
        return param;
    }

    public void setParam(float param) {
        this.param = param;
    }
}
