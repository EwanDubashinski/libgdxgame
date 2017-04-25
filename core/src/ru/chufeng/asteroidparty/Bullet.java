package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private float speed;
    private static Texture texture;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public Bullet() {
        position = new Vector2(0.0f, 0.0f);
        speed = 8.0f;
        active = false;
        if (texture == null)
            texture = new Texture("bolt4.png");
    }
    public Rectangle getRect() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
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
        active = true;
    }

    public void update() {
        position.y += speed;
        if (position.y > Gdx.graphics.getHeight() + 50) {
            destroy();
        }
    }
}
