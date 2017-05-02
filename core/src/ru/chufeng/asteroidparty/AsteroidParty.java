package ru.chufeng.asteroidparty;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import ru.chufeng.asteroidparty.input.Driver;
import ru.chufeng.asteroidparty.input.KBInput;
import ru.chufeng.asteroidparty.input.TouchInput;

public class AsteroidParty extends ApplicationAdapter {
    static boolean pause = false;
    static boolean startNew = false;
    private final int ASTEROID_COUNT = 40;
    private boolean hardwareKeyboard;
    private boolean multiTouch;
    private ShapeRenderer shapeRenderer;
    private boolean showPolygons = false;
    private SpriteBatch batch;
    private Background bg;
    private Hero hero;
    private Asteroid[] asteroids;
	private Bullet[] bullets;
    private Stage stage;
    private Label hitpoints;
    private Label exp;
    private Driver driver;
    private Menu menu;
	private Texture go;
    private Image fireImage;
    private Touchpad touchpad;
    private AssetManager assetManager;
    private Sound explode;
    private Music music;
    private float maxY;

    @Override
    public void create() {
        hardwareKeyboard = Gdx.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
        multiTouch = Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
//        hardwareKeyboard = false;
//        multiTouch = true;

		assetManager = new AssetManager();
		assetManager.load("gameover.png", Texture.class);
		if (!hardwareKeyboard) assetManager.load("fire.png", Texture.class);

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
		assetManager.finishLoading();
        go = assetManager.get("gameover.png", Texture.class);
        maxY = Gdx.graphics.getHeight() * 2 + 200;

//        fireImage = new Image(assetManager.get("fire.png", Texture.class));
//        fireImage.setPosition(Gdx.graphics.getWidth()-100, 0);
//        fireImage.setVisible(false);
//        stage.addActor(fireImage);

		shapeRenderer = new ShapeRenderer();
        explode = Gdx.audio.newSound(Gdx.files.internal("explode.ogg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music2.ogg"));
        music.setLooping(true);
        music.setVolume(0.6f);
        music.play();

        if (hardwareKeyboard) {
            driver = new KBInput();
        } else if (multiTouch) {
            fireImage = new Image(assetManager.get("fire.png", Texture.class));
            float fbSize = Gdx.graphics.getWidth() / 7.0f;
            fireImage.setSize(fbSize, fbSize);
            fireImage.setPosition(Gdx.graphics.getWidth() - fbSize, 0);
            fireImage.setVisible(false);
            stage.addActor(fireImage);

            Skin touchpadSkin= new Skin();
            touchpadSkin.add("touchBackground", new Texture("touchBG.png"));
            touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
            Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
            style.background = touchpadSkin.getDrawable("touchBackground");
            style.knob = touchpadSkin.getDrawable("touchKnob");
            touchpad = new Touchpad(20, style);
            touchpad.setBounds(15, 15, Gdx.graphics.getWidth() / 5.0f, Gdx.graphics.getWidth() / 5.0f);
            stage.addActor(touchpad);
            Gdx.input.setInputProcessor(stage);
            touchpad.setVisible(false);
            driver = new TouchInput(touchpad);


        } else Gdx.app.exit();


	}
	private void reStart(){
        bullets = new Bullet[5];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Bullet();
        }
		hero = new Hero(bullets);
		driver.addObserver(hero);
		driver.addObserver(menu);
		asteroids = new Asteroid[ASTEROID_COUNT];
		for (int i = 0; i < asteroids.length; i++) {
			asteroids[i] = new Asteroid();
		}
		processingAsteroids();


		startNew = false;
		menu.setMode(GameMode.RUN);
		if (multiTouch) {
		    touchpad.setVisible(true);
            fireImage.setVisible(true);
		}
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
            for (Bullet bullet : bullets) {
                if (bullet.isActive())
                    bullet.render(batch);
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
		if (menu.getMode() != GameMode.NEW && showPolygons) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids[i].splineRender(shapeRenderer);
			}
			hero.shapeRender(shapeRenderer);
            for (Bullet bullet : bullets) {
                if (bullet.isActive())
                    bullet.shapeRender(shapeRenderer);
            }
            if (driver instanceof TouchInput) ((TouchInput)driver).splineRender(shapeRenderer);
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
        explode.play(asteroids[a1].getPosition().y / maxY);
        float v1 = asteroids[a1].getSpeed();
        float v2 = asteroids[a2].getSpeed();
        float v1n;
		float v2n;
		float m1 = asteroids[a1].getMass();
		float m2 = asteroids[a2].getMass();
		float rs1 = asteroids[a1].getRotationSpeed();
		float rs2 = asteroids[a1].getRotationSpeed();
		float rs1n;
		float rs2n;
		v1n = (2*m2*v2 + (m1-m2)*v1)/(m1+m2);
		v2n = (2*m1*v1 + (m2-m1)*v2)/(m1+m2);
		asteroids[a1].setSpeed(v1n*0.9f);
		asteroids[a2].setSpeed(v2n*0.9f);
		if (asteroids[a1].getPosition().x < asteroids[a2].getPosition().x){
			asteroids[a1].setHSpeed(-1);
			asteroids[a2].setHSpeed(1);
		} else if(asteroids[a1].getPosition().x > asteroids[a2].getPosition().x){
			asteroids[a2].setHSpeed(-1);
			asteroids[a1].setHSpeed(1);
		}
		rs1n = (2*m2*rs2 + (m1-m2)*rs1)/(m1+m2);
		rs2n = -(2*m1*rs1 + (m2-m1)*rs2)/(m1+m2);
		asteroids[a1].setRotationSpeed(rs1n*0.9f);
		asteroids[a2].setRotationSpeed(rs2n*0.9f);
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
            hero.immediateUpdate();
            driver.update();

			processingAsteroids();
            for (Bullet bullet : bullets) {
                if (bullet.isActive())
                    bullet.update();
            }
            for (Asteroid asteroid : asteroids) {

                for (Bullet bullet : bullets) {
                    if (Intersector.overlapConvexPolygons(asteroid.getRect(), bullet.getRect())) {
                        if (bullet.isActive()) {
                            asteroid.damage(hero);

                            bullet.destroy();
                        }
                    }
                }
                if (Intersector.overlapConvexPolygons(asteroid.getRect(), hero.getRect())) {
                    hero.getDamage(1);
                    Vector2 posA = new Vector2(asteroid.getRect().getX(), asteroid.getRect().getY());
                    Vector2 posH = new Vector2(hero.getRect().getX(), hero.getRect().getY());
                    if (posA.x > posH.x) hero.setMomentumX(-5);
                    else hero.setMomentumX(5);
                    if (posA.y > posH.y) hero.setMomentumY(-5);
                    else hero.setMomentumY(5);
                    if (hero.isEndGame()) {
                        menu.setMode(GameMode.GAMEOVER);
                        if (multiTouch) {
                            touchpad.setVisible(false);
                            fireImage.setVisible(false);
                        }
                    }
                }
            }

			hitpoints.setText("Health: " + hero.getHp());
            //exp.setText("Experience: " + hero.getExp());
            exp.setText(" " + hero.getScaleX());
        }
    }
}
