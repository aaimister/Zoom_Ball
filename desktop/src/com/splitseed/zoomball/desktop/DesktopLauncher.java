package com.splitseed.zoomball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.splitseed.zoomball.ZoomBall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Zoom Ball";
		config.width = 1080 / 3;
		config.height = 1920 / 3;
		config.samples = 4;
		new LwjglApplication(new ZoomBall(), config);
	}
}
