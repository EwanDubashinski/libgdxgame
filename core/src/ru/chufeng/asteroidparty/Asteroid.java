package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;

public class Asteroid {
    private Vector2 position;
    private float speed; // vertical speed
    private float hSpeed; // horizontal speed
    private float rotationSpeed;
    private Texture texture;
    private float angle;
    private float scale;
    private int hp = 0;
    private int bonus;
    private Polygon polygon;
    private ArrayList<Integer> asteroidsInContact = new ArrayList<Integer>();
    private boolean needsReCreate = true;

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public int getMass() {
        return bonus;
    }

    public float getHSpeed() {
        return hSpeed;
    }

    public void setHSpeed(float hSpeed) {
        this.hSpeed = hSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public Polygon getRect() {
        return polygon;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void addContact(Integer astID){
        asteroidsInContact.add(astID);
    }

    public void delContact(Integer astID){
        asteroidsInContact.remove(astID);
    }

    public boolean inContact(Integer astID) {
        if (asteroidsInContact.contains(astID))
            return true;
        else return false;
    }


    public boolean inContact(){
        if (asteroidsInContact.size() == 0) return false;
        else return true;
    }


    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Asteroid() {
        if (texture == null){
            //texture = new Texture("asteroid1.png");
            texture = new Texture("asteroid" + (int)(Math.random() * 4) + ".png");
        }

        //recreate();
        needsReCreate = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y,
                   texture.getWidth()/2, texture.getHeight()/2,
                   texture.getWidth(), texture.getHeight(), scale, scale, angle, 0, 0,
                   texture.getWidth(), texture.getHeight(), false, false);

    }

    public void update() {
//        if (!needsReCreate) {
            angle += rotationSpeed;
            position.y -= speed;
            position.x += hSpeed;
            polygon.setRotation(angle);
            polygon.setPosition(position.x + texture.getWidth() / 2, position.y + texture.getHeight() / 2);
            if (position.y < -200) {
                needsReCreate = true;
            }
//        }
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

    public boolean isNeedsReCreate() {
        return needsReCreate;
    }

    public void recreate() {
        asteroidsInContact.clear();
        needsReCreate = false;
        position = new Vector2((float) (Math.random() * Gdx.graphics.getWidth()),
                Gdx.graphics.getHeight() + (float) Math.random() * Gdx.graphics.getHeight() + 200);
        speed = (2.0f + (float) Math.random() * 4.0f);
        rotationSpeed = (float) Math.random() * 6.0f - 3;
        angle = (float)Math.random() * 360;
        scale = 0.7f + (float)Math.random() * 0.5f;
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
