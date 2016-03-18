package org.academiadecodigo.bootcamp.milenos.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.academiadecodigo.bootcamp.milenos.DogTrials;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DogTrials.WIDTH;
		config.height = DogTrials.HEIGHT;
		config.title = DogTrials.TITLE;
		new LwjglApplication(new DogTrials(), config);
	}
}
