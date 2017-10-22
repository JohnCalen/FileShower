package svs.app.fs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import svs.app.fs.additional.CharLine;

public final class Lcynte_FileShower extends ApplicationAdapter
{
/*::|	FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public static SpriteBatch spriteBatch;

	public static byte controllComponent;
	public static final byte FILE_PATH_CONTROLL = 0;
	public static final byte FILE_SEARCH_CONTROLL = 1;
	public static final byte FILE_SHOWER_CONTROLL = 2;
	public static final byte PROCESS_OPERATION_CONTROLL = 3;

	public static BitmapFont bitmapFont;
	public static float fontColorRed = 0.81960784313725490196078431372549f;
	public static float fontColorGreen = 0.5921568627450980392156862745098f;
	public static float fontColorBlue = 1;
/*::|	CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|	F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public void create ()
	{
		spriteBatch = new SpriteBatch();
		Texture texture = new Texture(Gdx.files.internal("atlas.png"));
		FilePath.filePathTexReg = new TextureRegion(texture, 850, 60);

		Gdx.input.setInputProcessor(new InputPrcs());

//======|	Font Initialization
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("AmphionBold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
		ftfp.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+=[]{};\':\",.<>/?|\\";
		ftfp.size = 16;
		ftfp.color = Color.WHITE;
		bitmapFont = ftfg.generateFont(ftfp);
		ftfg.dispose();

		CharLine.charLine = new CharLine(250);

		Thread parallelToRenderThread = new Thread()
		{
			public void run()
			{
				do
				{
					FilePath.parallelToRender();

					try
					{
						Thread.sleep(1);
					}

					catch (InterruptedException exc)
					{				}
				}
				while (true);
			}
		};
		parallelToRenderThread.start();
	}

	public void render ()
	{
		Gdx.gl.glClearColor
				(
					0.06274509803921568627450980392157f,
					0.08627450980392156862745098039216f,
					0.08627450980392156862745098039216f,
					1
				);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		FilePath.draw();
		spriteBatch.end();
	}
	
	public void dispose ()
	{

	}
}



/*::|	FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|	CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|	F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
