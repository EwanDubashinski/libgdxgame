package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Vector2 position;
    float speed;
    private Texture texture;
    private int firetimer;
    private final int FIRE_RATE = 9;
    private int exp = 0;
    boolean isAlive = true;
    boolean endGame = false;
    private int angle = 0;
    private Polygon polygon;

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

    public Hero() {
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

    public void update() {
        if (isAlive) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(position.y < Gdx.graphics.getHeight() - 100) position.y += speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if(position.y > 50) position.y -= speed;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                if (position.x > 50) position.x -= speed;

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                if (position.x < Gdx.graphics.getWidth() - 100) position.x += speed;

            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                firetimer++;
                if(firetimer > FIRE_RATE) {
                    firetimer = 0;
                    for (int i = 0; i < AsteroidParty.bullets.length; i++) {
                        if(!AsteroidParty.bullets[i].isActive()) {
                            AsteroidParty.bullets[i].setup(position.x, position.y);
                            break;
                        }
                    }
                }
            }
            polygon.setPosition(position.x+texture.getWidth()/2, position.y);
        }
        else {
            angle++;
            position.x += (int) (speed / 3);
            position.y -= (int) (speed / 2);
            endGame = true;
        }
    }
}
