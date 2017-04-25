package ru.chufeng.asteroidparty;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture texStar;
    private Star[] stars;
    private final int STARS_COUNT = 350;

    public Background() {
        texStar = new Texture("star12.tga");
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < stars.length; i++) {
            batch.draw(texStar, stars[i].position.x, stars[i].position.y, stars[i].size, stars[i].size);
        }
    }

    public void update() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update();
        }
    }
}
