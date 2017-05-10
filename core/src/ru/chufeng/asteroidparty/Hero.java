package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import ru.chufeng.asteroidparty.input.EVENT;
import ru.chufeng.asteroidparty.input.ParametrizedEvent;

import java.util.Observable;
import java.util.Observer;

public class Hero implements Observer {
    private final int FIRE_RATE = 9;
    private Vector2 position;
    private float speed;
    private Texture texture;
    private int firetimer;
    private int exp = 0;
    private boolean isAlive = true;
    private boolean endGame = false;
    private int angle = 0;
    private Polygon polygon;
    private Bullet[] bullets;
    private Sound blast;
    private Sound crash;
    private float momentumX;
    private float momentumY;
    private float scaleX = 1;
    //private float rotationSpeed = 3;
    private int hp;
    private int armor;
    private int overheat;


    Hero(Bullet[] bullets) {
        this.bullets = bullets;
        position = new Vector2((float) Gdx.graphics.getWidth() / 2, 100.0f);
        speed = 6.0f;
        texture = new Texture("T50.png");
        firetimer = 0;
        hp = 300;
        polygon = new Polygon();
        polygon.setOrigin(texture.getWidth() / 2, 0);
        polygon.setVertices(new float[]{-texture.getWidth() / 2, 0, texture.getWidth() / 2, 0, 0, texture.getHeight()});
        blast = Gdx.audio.newSound(Gdx.files.internal("blast.ogg"));
        crash = Gdx.audio.newSound(Gdx.files.internal("crash.ogg"));
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    float getScaleX() {
        return scaleX;
    }

    float getMomentumX() {
        return momentumX;
    }

    void setMomentumX(float momentumX) {
        this.momentumX = momentumX;
    }

    float getMomentumY() {
        return momentumY;
    }

    void setMomentumY(float momentumY) {
        this.momentumY = momentumY;
    }

    Vector2 getPosition() {
        return position;
    }

    boolean isEndGame() {
        return endGame;
    }

    int getExp() {
        return exp;
    }

    void addExp(int ea) {
        exp += ea;
    }

    int getHp() {
        return hp;
    }

    void render(SpriteBatch batch) {
//        if (isAlive) batch.draw(texture, position.x, position.y);
//        else {
        batch.draw(texture, position.x, position.y, 50, 70, texture.getWidth(), texture.getHeight(), scaleX, 1, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
//        }
    }

    void getDamage(int x) {

        if (hp <= 0) {
            isAlive = false;
        } else {
            hp -= x;
            crash.play(1);
        }

    }

    Polygon getRect() {
        return polygon;
    }

    void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    void immediateUpdate() {
        if (!isAlive) {
            angle++;
            position.x += (int) (speed / 3);
            position.y -= (int) (speed / 2);
            endGame = true;
        } else if (momentumX > 0.1f || momentumY > 0.1f) {//TODO: add constraints
            position.x += momentumX;
            position.y += momentumY;
            polygon.setPosition(position.x + texture.getWidth() / 2, position.y);
            momentumX *= 0.8f;
            momentumY *= 0.8f;
        }
        scaleX = 1;

    }

    @Override
    public void update(Observable o, Object arg) {
        if (isAlive && momentumX < 1 && momentumY < 1) {
            float percent;
            EVENT event;
            if (arg instanceof ParametrizedEvent) {
                percent = ((ParametrizedEvent) arg).getParam();
                event = ((ParametrizedEvent) arg).getEvent();
            } else if (arg instanceof EVENT) {
                percent = 1;
                event = (EVENT) arg;
            } else return;
            switch (event) { //TODO: hardcode
                case UP:
                    if (position.y < Gdx.graphics.getHeight() - 100) position.y += speed * percent;
//                    if (angle < 0) angle++;
//                    else if (angle > 0) angle--;
                    break;
                case DOWN:
                    if (position.y > 50) position.y -= speed * percent;
//                    if (angle < 180) angle++;
//                    else if (angle > 180) angle--;
                    break;
                case LEFT:
                    if (position.x > 50) position.x -= speed * percent;
                    scaleX = 1.0f - percent * 0.3f;
                    //angle += rotationSpeed;
                    break;
                case RIGHT:
                    if (position.x < Gdx.graphics.getWidth() - 100) position.x += speed * percent;
                    scaleX = 1.0f - percent * 0.3f;
                    //angle -= rotationSpeed;
                    break;
                case FIRE:
                    firetimer++;
                    if (firetimer > FIRE_RATE) {
                        firetimer = 0;
                        for (Bullet bullet : bullets) {
                            if (!bullet.isActive()) {
                                blast.play();
                                bullet.setup(position.x, position.y);
                                break;
                            }
                        }
                    }

            }
        }
        polygon.setPosition(position.x + texture.getWidth() / 2, position.y);

    }
}

