package ru.chufeng.asteroidparty.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.chufeng.asteroidparty.AsteroidParty;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width =  1280;
//		config.height = 720;
		config.fullscreen = true;
		config.width =  LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		new LwjglApplication(new AsteroidParty(), config);
	}
}
