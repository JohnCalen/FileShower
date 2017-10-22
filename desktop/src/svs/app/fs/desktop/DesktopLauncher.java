package svs.app.fs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import svs.app.fs.Lcynte_FileShower;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 975;
		config.height = 550;

		new LwjglApplication(new Lcynte_FileShower(), config);
	}
}
