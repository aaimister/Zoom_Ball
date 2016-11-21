package com.splitseed.zoomball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.splitseed.zoomball.Etheric;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.minWidth = 512;
		settings.minHeight = 512;
		//TexturePacker.process(settings, "D:\\Software\\Android Apps\\Zoom Ball\\android\\assets\\data\\asset manager\\spload input", "D:\\Software\\Android Apps\\Zoom Ball\\android\\assets\\data\\asset manager\\spload output", "512");
		//TexturePacker.process(settings, "D:\\Software\\Android Apps\\Zoom Ball\\android\\assets\\data\\asset manager\\rest input", "D:\\Software\\Android Apps\\Zoom Ball\\android\\assets\\data\\asset manager\\rest output", "512");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Etheric";
		config.width = 1080 / 3;
		config.height = 1920 / 3;
		config.samples = 4;
		new LwjglApplication(new Etheric(), config);
	}
}
