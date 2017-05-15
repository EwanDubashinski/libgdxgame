package ru.chufeng.asteroidparty;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Menu /*implements Observer */ {
    private Texture resume;
    private Texture exit;
    private Texture newgame;
    private Rectangle rresume;
    private Rectangle rexit;
    private Rectangle rnewgame;
    //private GameMode mode = GameMode.NEW;

    public Menu() {
        resume = new Texture("resume.png");
        newgame = new Texture("new.png");
        exit = new Texture("exit.png");
        rresume = new Rectangle(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 100, 400, 100);
        rnewgame = new Rectangle(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 200, 400, 100);
        rexit = new Rectangle(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 300, 400, 100);
    }

//    public GameMode getMode() {
//        return mode;
//    }
//
//    public void setMode(GameMode mode) {
//        this.mode = mode;
//
//    }

    public void render(SpriteBatch batch){
        if (GameState.getInstance().getMode() == GameMode.PAUSE) {
            batch.draw(resume, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 100);
        }
        if (GameState.getInstance().getMode() != GameMode.RUN) {
            batch.draw(newgame, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 200);
            batch.draw(exit, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 300);
        }
    }
    public void update() {
        if (GameState.getInstance().getMode() != GameMode.RUN) {
            if (Gdx.input.isTouched()) {
                if (rresume.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    //AsteroidParty.pause = false;
                    GameState.getInstance().update(null, GameEvent.RESUME);
                } else if (rexit.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {

                    GameState.getInstance().update(null, GameEvent.EXIT);
                } else if (rnewgame.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    GameState.getInstance().update(null, GameEvent.STARTNEW);
                    //AsteroidParty.startNew = true;
                    //AsteroidParty.pause = false;
                }
            }
        }
    }
//    public void update(Observable o, Object arg) {
//
//    }
}
