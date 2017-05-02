package ru.chufeng.asteroidparty.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.Observable;

/**
 * @author Chufeng
 * @version 1.0
 */
public class TouchInput extends Observable implements Driver {
    private Touchpad touchpad;
    private Circle fireButton;
    private ParametrizedEvent parametrizedEvent;
    private float fbRadius = Gdx.graphics.getWidth() / 14.0f;

    private TouchInput() {
    }//Hide default constructor
    public TouchInput(Touchpad touchpad){
        this.touchpad = touchpad;
        fireButton = new Circle(Gdx.graphics.getWidth() - fbRadius, fbRadius, fbRadius);
        parametrizedEvent = new ParametrizedEvent();
        Gdx.input.setCatchBackKey(true);
    }
    public void splineRender(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(Gdx.graphics.getWidth() - fbRadius, fbRadius, fbRadius);
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
