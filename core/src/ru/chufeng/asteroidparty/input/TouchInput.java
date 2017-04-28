package ru.chufeng.asteroidparty.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.Observable;

/**
 * Created by Chufeng on 24.04.2017.
 */
public class TouchInput extends Observable implements Driver {
    private Touchpad touchpad;
    private TouchInput(){}//Hide default constructor
    private Circle fireButton;
    private ParametrizedEvent parametrizedEvent;
    public TouchInput(Touchpad touchpad){
        this.touchpad = touchpad;
        fireButton = new Circle(Gdx.graphics.getWidth()-50, 50, 50); //TODO: hardcode
        parametrizedEvent = new ParametrizedEvent();
        Gdx.input.setCatchBackKey(true);
    }
    public void splineRender(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(Gdx.graphics.getWidth()-50, 50, 50);
    }
    @Override
    public void update() {
        if (touchpad.getKnobPercentX() < 0) {
            setChanged();
            parametrizedEvent.setEvent(EVENT.LEFT);
            parametrizedEvent.setParam(Math.abs(touchpad.getKnobPercentX()));
            super.notifyObservers(parametrizedEvent);
        }
        if (touchpad.getKnobPercentX() > 0) {
            setChanged();
            parametrizedEvent.setEvent(EVENT.RIGHT);
            parametrizedEvent.setParam(Math.abs(touchpad.getKnobPercentX()));
            super.notifyObservers(parametrizedEvent);
        }
        if (touchpad.getKnobPercentY() < 0) {
            setChanged();
            parametrizedEvent.setEvent(EVENT.DOWN);
            parametrizedEvent.setParam(Math.abs(touchpad.getKnobPercentY()));
            super.notifyObservers(parametrizedEvent);
        }
        if (touchpad.getKnobPercentY() > 0) {
            setChanged();
            parametrizedEvent.setEvent(EVENT.UP);
            parametrizedEvent.setParam(Math.abs(touchpad.getKnobPercentY()));
            super.notifyObservers(parametrizedEvent);
        }
        if (Gdx.input.isTouched()){
            if (fireButton.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                setChanged();
                super.notifyObservers(EVENT.FIRE);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            setChanged();
            super.notifyObservers(EVENT.PAUSE);
        }
    }

}
