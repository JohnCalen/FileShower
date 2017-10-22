package svs.app.fs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import svs.app.fs.additional.CharLine;

import java.io.File;

public final class FilePath
{
/*::|	FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public static TextureRegion filePathTexReg;
	public static TextureRegion caretTexReg;

	public static final short Y = 490; // 550 - 60

	public static CharLine filePathLine = new CharLine(250);

	public static int chosenRollIndex;

	private static final byte VERTICAL_DOWN = 0;
	private static final byte VERTICAL_UP = 1;

	private static int caretPosition = 0;
	private static int caretDrawXPosition = 28;
	private static float alpha = 1;
	private static float freq = 0.001f;
/*::|	CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|	F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public static boolean isMouseDownOnFilePath(int x, int y)
	{
		if
		(
			x > 24 && x < 522
				&&
			y > 15 && y < 42
		)
			return true;

		return false;
	}

	public static void onMouseDown()
	{
		Lcynte_FileShower.controllComponent = Lcynte_FileShower.FILE_PATH_CONTROLL;
	}

	public static void onKeyTyped(char keyChar)
	{
		filePathLine.concat(keyChar);
		++caretPosition;

		caretOfsX();
	}

	private static void caretOfsX()
	{
		GlyphLayout glyphLayout = new GlyphLayout
				(
						Lcynte_FileShower.bitmapFont,
						filePathLine.subSequence(0, caretPosition)
				);
		caretDrawXPosition = 25 + (int)glyphLayout.width;
		alpha = 1;
	}

	public static void onKeyDown(int keyCode)
	{
		switch (keyCode)
		{
			case Input.Keys.BACKSPACE :
				if (caretPosition > 0)
				{
					filePathLine.remove(--caretPosition);
					caretOfsX();
				}
			return;

			case Input.Keys.FORWARD_DEL :
				if (caretPosition < filePathLine.filledLength)
				{
					filePathLine.remove(caretPosition);
					caretOfsX();
				}
			return;

			case Input.Keys.LEFT :
				if (caretPosition > 0)
				{
					--caretPosition;
					caretOfsX();
				}
			return;

			case Input.Keys.RIGHT :
				++caretPosition;
				caretOfsX();
			return;
		}

		int index = filePathLine.getLastChar('\\');
		CharLine charLine = (CharLine) filePathLine.subSequence(0, ++index);

		File file = new File(charLine.toString());
		if (!file.exists())
			return;

		String sqcFile[] = file.list();

		switch (keyCode)
		{
			case Input.Keys.TAB :
				onTab(index, sqcFile);
			break;

			case Input.Keys.DOWN :
				onVerical(VERTICAL_DOWN, index, sqcFile);
			break;

			case Input.Keys.UP :
				onVerical(VERTICAL_UP, index, sqcFile);
			break;
		}
	}

	private static void onTab(int index, String sqcFile[])
	{
		int length = sqcFile.length;
		if (index < filePathLine.filledLength)
		{
			CharLine charLine = (CharLine) filePathLine.subSequence(index, filePathLine.filledLength);

			for (int itera = chosenRollIndex; itera < length; ++itera)
				if (charLine.equateWithString(sqcFile[itera].toCharArray()))
				{
					filePathLine.removeFrom(index);
					filePathLine.concat(sqcFile[itera]);

					caretPosition = filePathLine.filledLength;
					caretOfsX();
				}

			return;
		}

		onVerical(VERTICAL_DOWN, index, sqcFile);
	}

	private static void onVerical(byte direction, int index, String sqcFile[])
	{
		if (direction == VERTICAL_DOWN)
		{
			if (++chosenRollIndex == sqcFile.length)
				chosenRollIndex = 0;
		}

		else
		{
			if (--chosenRollIndex == -1)
				chosenRollIndex = sqcFile.length - 1;
		}

		filePathLine.removeFrom(index);
		filePathLine.concat(sqcFile[chosenRollIndex]);
	}

	public static void draw()
	{
		Lcynte_FileShower.spriteBatch.draw(filePathTexReg, 0, Y);

		if (filePathLine.filledLength > 0)
		{
			Lcynte_FileShower.bitmapFont.setColor
					(
						Lcynte_FileShower.fontColorRed,
						Lcynte_FileShower.fontColorGreen,
						Lcynte_FileShower.fontColorBlue,
						1
					);
			Lcynte_FileShower.bitmapFont.draw
					(
							Lcynte_FileShower.spriteBatch,
							filePathLine,
							30,
							527 //550 - 42 + 18(winHeight - downTextFieldBorder + overFontSize)
					);
		}

		Lcynte_FileShower.bitmapFont.setColor
				(
					0.76078431372549019607843137254902f,
					1,
					0,
					alpha
				);
		Lcynte_FileShower.bitmapFont.draw
				(
					Lcynte_FileShower.spriteBatch,
					"|",
					caretDrawXPosition,
					527
				);
	}

	public static void parallelToRender()
	{
		alpha -= freq;

		if (alpha < 0)
			alpha = 1;
	}
}
