package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Asteroid {
    private static Sound destroy;

    static {
        destroy = Gdx.audio.newSound(Gdx.files.internal("destroy.ogg"));
    }

    private Vector2 position;
    private float speed; // vertical speed
    private float baseSpeed = 2.0f; // To increase speed with increasing experience
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

    Asteroid() {
        if (texture == null) {
            //texture = new Texture("asteroid1.png");
            texture = new Texture("asteroid" + (int) (Math.random() * 4) + ".png");
        }

        //recreate();
        needsReCreate = true;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = 2.0f + baseSpeed;
    }

    Vector2 getPosition() {
        return position;
    }

    float getSpeed() {
        return speed;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }

    int getMass() {
        return bonus;
    }

    public float getHSpeed() {
        return hSpeed;
    }

    void setHSpeed(float hSpeed) {
        this.hSpeed = hSpeed;
    }

    float getRotationSpeed() {
        return rotationSpeed;
    }

    void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    Polygon getRect() {
        return polygon;
    }

    void addContact(Integer astID) {
        asteroidsInContact.add(astID);
    }

    void delContact(Integer astID) {
        asteroidsInContact.remove(astID);
    }

    boolean inContact(Integer astID) {
        return asteroidsInContact.contains(astID);
    }

    boolean inContact() {
        return asteroidsInContact.size() != 0;
    }

    void render(SpriteBatch batch) {
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

    void damage(Hero hero) {
        hp -= 1;
        if (hp <= 0) {
            destroy.play();
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

    void splineRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    boolean isNeedsReCreate() {
        return needsReCreate;
    }

    void recreate() {
        asteroidsInContact.clear();
        needsReCreate = false;
        position = new Vector2((float) (Math.random() * Gdx.graphics.getWidth()),
                Gdx.graphics.getHeight() + (float) Math.random() * Gdx.graphics.getHeight() + 200);
        speed = (baseSpeed + (float) Math.random() * 4.0f);
        rotationSpeed = (float) Math.random() * 6.0f - 3;
        angle = (float)Math.random() * 360;
        scale = 0.7f + (float)Math.random() * 0.5f;
        //scale = 1;
        hp = (int) (scale * texture.getHeight() * texture.getWidth() * 0.0002);
        bonus = hp;

        polygon = new Polygon(getVertices());
        //polygon.setOrigin(texture.getWidth()/2, texture.getHeight()/2);
        //polygon.setPosition(position.x, position.y);
        polygon.setPosition(position.x + texture.getWidth()/2, position.y + texture.getHeight()/2);
        polygon.setRotation(angle);
        polygon.setScale(scale, scale);

    }
}
