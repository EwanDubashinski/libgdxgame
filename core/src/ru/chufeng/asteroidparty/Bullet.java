package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private float speed;
    private static Texture texture;
    private boolean active;
    private Polygon polygon;

    public boolean isActive() {
        return active;
    }

    public Bullet() {
        position = new Vector2(0.0f, 0.0f);
        speed = 8.0f;
        active = false;
        if (texture == null)
            texture = new Texture("bolt4.png");
        polygon = new Polygon();
        polygon.setOrigin(texture.getWidth()/2, 0);
        polygon.setVertices(new float[]{-texture.getWidth()/2,0, texture.getWidth()/2,0, 0, texture.getHeight()});
    }
    public Polygon getRect() {
        return polygon;
    }

    public void shapeRender(ShapeRenderer shapeRenderer){
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void destroy() {
        active = false;
    }

    public void setup(float x, float y) {
        position.x = x + 47;
        position.y = y + 130;
        polygon.setPosition(position.x+texture.getWidth()/2, position.y);
        active = true;
    }

    public void update() {
        position.y += speed;
        if (position.y > Gdx.graphics.getHeight() + 50) {
            destroy();
        }
        polygon.setPosition(position.x+texture.getWidth()/2, position.y);
    }
}
