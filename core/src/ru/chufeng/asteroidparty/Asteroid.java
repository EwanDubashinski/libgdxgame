package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;

public class Asteroid {
    private Vector2 position;
    private float speed;
    private float rotationSpeed;
    private Texture texture;
    private float angle;
    private float scale;
    private int hp = 0;
    private int bonus;
    private Polygon polygon;

    public Polygon getRect() {
        return polygon;
    }

    public Asteroid() {
        if (texture == null){
            //texture = new Texture("asteroid1.png");
            texture = new Texture("asteroid" + (int)(Math.random() * 4) + ".png");
        }
        recreate();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y,
                   texture.getWidth()/2, texture.getHeight()/2,
                   texture.getWidth(), texture.getHeight(), scale, scale, angle, 0, 0,
                   texture.getWidth(), texture.getHeight(), false, false);

    }

    public void update() {
        angle += rotationSpeed;
        position.y -= speed;
        polygon.setRotation(angle);
        polygon.setPosition(position.x + texture.getWidth()/2, position.y + texture.getHeight()/2);
        if (position.y < -200) {
            recreate();
        }
    }

    public void damage(Hero hero){
        hp -= 1;
        if (hp <= 0) {
            hero.addExp(bonus);
            recreate();
        }
    }

    private float[] getVertices(){
        return new float[]{
                -texture.getWidth()/2, 0,
                (-texture.getWidth()/2)*0.7f, (texture.getHeight()/2)*0.7f,
                0, texture.getHeight()/2,
                (texture.getWidth()/2)*0.7f, (texture.getHeight()/2)*0.7f,
                texture.getWidth()/2, 0,
                (texture.getWidth()/2)*0.7f, (-texture.getHeight()/2)*0.7f,
                0, -texture.getHeight()/2,
                (-texture.getWidth()/2)*0.7f, (-texture.getHeight()/2)*0.7f
        };
    }

    public void splineRender(ShapeRenderer shapeRenderer){
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    public void recreate() {
        position = new Vector2((float) (Math.random() * Gdx.graphics.getWidth()),
                Gdx.graphics.getHeight() + (float) Math.random() * Gdx.graphics.getHeight() + 200);
        speed = (2.0f + (float) Math.random() * 4.0f);
        rotationSpeed = (float) Math.random() * 6.0f - 3;
        angle = (float)Math.random() * 360;
        scale = 0.5f + (float)Math.random();
        //scale = 1;
        hp = (int)(scale * texture.getHeight() * texture.getWidth() * 0.0001);
        bonus = hp;

        polygon = new Polygon(getVertices());
        //polygon.setOrigin(texture.getWidth()/2, texture.getHeight()/2);
        //polygon.setPosition(position.x, position.y);
        polygon.setPosition(position.x + texture.getWidth()/2, position.y + texture.getHeight()/2);
        polygon.setRotation(angle);
        polygon.setScale(scale, scale);
    }
}
