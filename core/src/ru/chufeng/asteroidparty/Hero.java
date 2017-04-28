package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
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
    private Vector2 position;
    private float speed;
    private Texture texture;
    private int firetimer;
    private final int FIRE_RATE = 9;
    private int exp = 0;
    boolean isAlive = true;
    boolean endGame = false;
    private int angle = 0;
    private Polygon polygon;
    private Bullet[] bullets;

    public int getExp() {
        return exp;
    }

    public void addExp(int ea) {
        exp += ea;
    }

    public int getHp() {
        return hp;
    }

    private int hp;

    public Hero(Bullet[] bullets) {
        this.bullets = bullets;
        position = new Vector2((float)Gdx.graphics.getWidth()/2, 100.0f);
        speed = 6.0f;
        texture = new Texture("T50.png");
        firetimer = 0;
        hp = 300;
        polygon = new Polygon();
        polygon.setOrigin(texture.getWidth()/2, 0);
        polygon.setVertices(new float[]{-texture.getWidth()/2,0, texture.getWidth()/2,0, 0, texture.getHeight()});
    }

    public void render(SpriteBatch batch) {
        if (isAlive) batch.draw(texture, position.x, position.y);
        else {
            batch.draw(texture, position.x, position.y,50,70,texture.getWidth(), texture.getHeight(),1,1,angle,0,0,texture.getWidth(), texture.getHeight(),false,false);
        }
    }

    public void getDamage(int x) {

        if (hp <= 0) {
            isAlive = false;
        } else hp -= x;
    }
    public Polygon getRect(){
        return polygon;
    }

    public void shapeRender(ShapeRenderer shapeRenderer){
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }
    public void deadUpdate(){
        if (!isAlive) {
            angle++;
            position.x += (int) (speed / 3);
            position.y -= (int) (speed / 2);
            endGame = true;
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if (isAlive) {
            float percent;
            EVENT event;
            if (arg instanceof ParametrizedEvent) {
                 percent = ((ParametrizedEvent)arg).getParam();
                 event = ((ParametrizedEvent)arg).getEvent();
            } else if (arg instanceof EVENT) {
                percent = 1;
                event = (EVENT)arg;
            } else return;
            switch (event) { //TODO: hardcode
                case UP:
                    if (position.y < Gdx.graphics.getHeight() - 100) position.y += speed*percent;
                    break;
                case DOWN:
                    if (position.y > 50) position.y -= speed*percent;
                    break;
                case LEFT:
                    if (position.x > 50) position.x -= speed*percent;
                    break;
                case RIGHT:
                    if (position.x < Gdx.graphics.getWidth() - 100) position.x += speed*percent;
                    break;
                case FIRE:
                        firetimer++;
                        if (firetimer > FIRE_RATE) {
                            firetimer = 0;
                            for (int i = 0; i < bullets.length; i++) {
                                if (!bullets[i].isActive()) {
                                    bullets[i].setup(position.x, position.y);
                                    break;
                                }
                            }
                        }
                    }

            }
            polygon.setPosition(position.x+texture.getWidth()/2, position.y);

        }
    }

