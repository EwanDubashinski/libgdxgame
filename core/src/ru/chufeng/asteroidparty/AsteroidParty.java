package ru.chufeng.asteroidparty;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class AsteroidParty extends ApplicationAdapter {
    //Определимся с железом
//	boolean hardwareKeyboard = Gdx.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
//	boolean multiTouch = Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
	ShapeRenderer shapeRenderer;

	final int ASTEROID_COUNT = 10;
	SpriteBatch batch;
	Background bg;
	Hero hero;
	Asteroid[] asteroids;
	public static Bullet[] bullets;
	Stage stage;
	Label hitpoints;
	Label exp;
	static boolean pause = false;
	static boolean startNew = false;
	Menu menu;
	static Texture go;
	@Override
	public void create() {
		menu = new Menu();
		menu.setMode(GameMode.NEW);
		batch = new SpriteBatch();
		bg = new Background();
		hitpoints = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		hitpoints.setPosition(20, 20);
		exp = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		exp.setPosition(20, Gdx.graphics.getHeight() - 50);
		stage = new Stage();
		stage.addActor(hitpoints);
		stage.addActor(exp);
		go = new Texture("gameover.png");

		shapeRenderer = new ShapeRenderer();
	}
	private void reStart(){
		hero = new Hero();
		asteroids = new Asteroid[ASTEROID_COUNT];
		for (int i = 0; i < asteroids.length; i++) {
			asteroids[i] = new Asteroid();
		}
		bullets = new Bullet[5];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet();
		}
		startNew = false;
		menu.setMode(GameMode.RUN);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1); // RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		bg.render(batch);
		if (menu.getMode() != GameMode.NEW) {
			hero.render(batch);
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids[i].render(batch);
			}
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].isActive())
					bullets[i].render(batch);
			}


		}
		if (menu.getMode() == GameMode.GAMEOVER) batch.draw(go, Gdx.graphics.getWidth()/2 - 200, Gdx.graphics.getHeight()/2 - 50);
        menu.render(batch);
		if (!pause) {
			update();
		} else {
			menu.update();
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				pause = false;
				menu.setMode(GameMode.RUN);
			}
		}
        batch.end();
		if (menu.getMode() != GameMode.NEW) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids[i].splineRender(shapeRenderer);
			}
			hero.shapeRender(shapeRenderer);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.end();
		}

		stage.draw();
	}

	public void update() {
		menu.update();
		if (startNew) reStart();
		bg.update();

		if (menu.getMode() != GameMode.NEW) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				pause = true;
				menu.setMode(GameMode.PAUSE);
			}

			hero.update();
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids[i].update();
			}
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].isActive())
					bullets[i].update();
			}
			for (int i = 0; i < asteroids.length; i++) {
				for (int j = 0; j < ASTEROID_COUNT; j++) {
					if (Intersector.overlapConvexPolygons(asteroids[i].getRect(), asteroids[j].getRect())) {
						float speed = asteroids[i].getSpeed();
						asteroids[i].setSpeed(asteroids[j].getSpeed());
						asteroids[j].setSpeed(speed);
					}
				}
				for (int j = 0; j < bullets.length; j++) {
//					if (asteroids[i].getRect().overlaps(bullets[j].getRect())) {
//						if (bullets[j].isActive()) {
//							asteroids[i].damage(hero);
//							bullets[j].destroy();
//						}
//					}
				}
				if (Intersector.overlapConvexPolygons(asteroids[i].getRect(), hero.getRect())) {
					hero.getDamage(1);
					if (hero.endGame) {
						menu.setMode(GameMode.GAMEOVER);
					}
				}
			}

			hitpoints.setText("Health: " + hero.getHp());
			exp.setText("Experience: " + hero.getExp());
		}
	}
}
