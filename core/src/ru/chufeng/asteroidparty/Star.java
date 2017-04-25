package ru.chufeng.asteroidparty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Star {
    Vector2 position; // x y
    float speed;
    int size;

    public Star() {
        position = new Vector2((float) (Math.random() * Gdx.graphics.getWidth()), (float) (Math.random() * Gdx.graphics.getHeight()));
        speed = 0.2f + (float)(Math.random());
        size = (int)(7 * this.speed);
    }

    public void update() {
        position.y -= speed;
        if (position.y < -80)
            position.y = Gdx.graphics.getHeight() + 100;
    }
}
