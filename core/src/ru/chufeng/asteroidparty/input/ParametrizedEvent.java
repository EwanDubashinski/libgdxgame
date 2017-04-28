package ru.chufeng.asteroidparty.input;

/**
 * Created by Chufeng on 28.04.2017.
 */
public class ParametrizedEvent {
    private EVENT event;
    private float param;

    public ParametrizedEvent() {
    }

    public ParametrizedEvent(EVENT event, float param) {
        this.event = event;
        this.param = param;
    }

    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public float getParam() {
        return param;
    }

    public void setParam(float param) {
        this.param = param;
    }
}
