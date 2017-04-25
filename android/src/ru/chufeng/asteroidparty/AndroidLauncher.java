package ru.chufeng.asteroidparty;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false; //Отключим лишние сенсоры для экономии энергии
		config.useCompass = false;
        config.useGyroscope = false;
		initialize(new AsteroidParty(), config);
	}
}
