package ru.chufeng.asteroidparty.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.chufeng.asteroidparty.GameEvent;

import java.util.Observable;

/**
 * @author Chufeng
 * @version 1.0
 */
public class KBInput extends Observable implements Driver {
    //private Set<Integer> keysSet = new HashSet<Integer>();
    //private ArrayList<Integer> keys = new ArrayList<Integer>();
    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setChanged();
            super.notifyObservers(GameEvent.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setChanged();
            super.notifyObservers(GameEvent.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setChanged();
            super.notifyObservers(GameEvent.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setChanged();
            super.notifyObservers(GameEvent.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            setChanged();
            super.notifyObservers(GameEvent.FIRE);
        } else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            setChanged();
            super.notifyObservers(GameEvent.NOFIRE);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            setChanged();
            super.notifyObservers(GameEvent.NEXT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            setChanged();
            super.notifyObservers(GameEvent.PREV);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setChanged();
            super.notifyObservers(GameEvent.PAUSE);
        }
//        for (Integer keycode : keysSet) {
//            switch (keycode) {
//                case Input.Keys.UP:
//                    setChanged();
//                    super.notifyObservers(GameEvent.UP);
//                    break;
//                case Input.Keys.DOWN:
//                    setChanged();
//                    super.notifyObservers(GameEvent.DOWN);
//                    break;
//                case Input.Keys.LEFT:
//                    setChanged();
//                    super.notifyObservers(GameEvent.LEFT);
//                    break;
//                case Input.Keys.RIGHT:
//                    setChanged();
//                    super.notifyObservers(GameEvent.RIGHT);
//                    break;
//                case Input.Keys.SPACE:
//                    setChanged();
//                    super.notifyObservers(GameEvent.FIRE);
//                    break;
//                case Input.Keys.SHIFT_LEFT:
//                    setChanged();
//                    super.notifyObservers(GameEvent.NEXT);
//                    break;
//                case Input.Keys.CONTROL_LEFT:
//                    setChanged();
//                    super.notifyObservers(GameEvent.PREV);
//                    break;
//                case Input.Keys.ESCAPE:
//                    setChanged();
//                    super.notifyObservers(GameEvent.PAUSE);
//                    break;
//            }
        }
//    }

    @Override
    public String getDebugInfo() {
        return "";
    }

    @Override
    public boolean keyDown(int keycode) {
//        switch (keycode) {
//            case Input.Keys.ESCAPE:
//                setChanged();
//                super.notifyObservers(GameEvent.PAUSE);
//                break;
//            default:
//                keysSet.add(keycode);
//        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
//        if (keys.contains((Integer)keycode)) {
//            keys.remove((Integer)keycode);
//            return true;
//        } else return false;
//        for (int i = 0; i < keysSet.size()-1; i++) {
//            if (keysSet[i].equals()){}
//        }
//        if (keysSet.contains((Integer) keycode)) {
//            keysSet.remove((Integer) keycode);
//            return true;
//        } else
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
