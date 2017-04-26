package ru.chufeng.asteroidparty;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class AsteroidParty extends ApplicationAdapter {
    //Определимся с железом
//	boolean hardwareKeyboard = Gdx.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
//	boolean multiTouch = Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
	ShapeRenderer shapeRenderer;

	final int ASTEROID_COUNT = 40;
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
		processingAsteroids();

		bullets = new Bullet[5];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet();
		}
		startNew = false;
		menu.setMode(GameMode.RUN);
	}

//	public boolean getIntersection(Polygon polygon) {
//		for (int i = 0; i < ASTEROID_COUNT; i++) {
//			if (Intersector.overlapConvexPolygons(asteroids[i].getRect(), polygon)) {
//				return true;
//			}
//		}
//		return false;
//	}

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

	private void intersect(int ast1){
		for (int ast2 = 0; ast2 < ASTEROID_COUNT; ast2++) {
			if (ast1 == ast2) return;
			if (Intersector.overlapConvexPolygons(asteroids[ast1].getRect(), asteroids[ast2].getRect())) {
				if (!asteroids[ast1].inContact(ast2) && !asteroids[ast2].inContact(ast1)) {
//					float speed = asteroids[ast1].getSpeed();
//					asteroids[ast1].setSpeed(asteroids[ast2].getSpeed());
//					asteroids[ast2].setSpeed(speed);
					boom(ast1, ast2);
					asteroids[ast1].addContact(ast2);
					asteroids[ast2].addContact(ast1);
				}
			} else {
				if (asteroids[ast1].inContact(ast2)) {
					asteroids[ast1].delContact(ast2);
					asteroids[ast2].delContact(ast1);
				}
			}
		}
	}

	private void boom(int a1, int a2){
		float v1 = asteroids[a1].getSpeed();
		float v2 = asteroids[a2].getSpeed();
		float v1n;
		float v2n;
		float m1 = asteroids[a1].getMass();
		float m2 = asteroids[a2].getMass();
		v1n = (2*m2*v2 + (m1-m2)*v1)/(m1+m2);
		v2n = (2*m1*v1 + (m2-m1)*v2)/(m1+m2);
		asteroids[a1].setSpeed(v1n);
		asteroids[a2].setSpeed(v2n);

	}

	private void processingAsteroids(){
		for (int i = 0; i < ASTEROID_COUNT; i++) {
			if (asteroids[i].isNeedsReCreate()){
				do {
					asteroids[i].recreate();
					intersect(i);
				} while (asteroids[i].inContact());
			} else {
				intersect(i);
			}
			asteroids[i].update();
		}
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
			processingAsteroids();
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].isActive())
					bullets[i].update();
			}
			for (int i = 0; i < asteroids.length; i++) {

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
			//exp.setText("Experience: " + hero.getExp());
		}
	}
}
