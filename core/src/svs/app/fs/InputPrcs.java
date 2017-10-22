package svs.app.fs;

import com.badlogic.gdx.InputProcessor;

public class InputPrcs implements InputProcessor
{
/*::|	FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private static ThreadKeyPressing threadPressing;
/*::|	CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public InputPrcs()
	{
		threadPressing = new ThreadKeyPressing();
		threadPressing.start();
	}
/*::|	SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private class ThreadKeyPressing extends Thread
	{
		public byte globalThreadID;
		public int keyCode = -1;

		public void run()
		{
			do
			{
				try
				{
					while (keyCode == -1)
					{
						Thread.sleep(1);
					}

					byte threadID = globalThreadID;
					Thread.sleep(750);

					while (threadID == globalThreadID)
					{
						onKeyDown(keyCode);
						Thread.sleep(50);
					}
				}

				catch (InterruptedException exc)
				{				}

			}
			while (true);
		}
	}
/*::|	F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public boolean touchDown(int x, int y, int event, int button)
	{
		if (FilePath.isMouseDownOnFilePath(x, y))
			FilePath.onMouseDown();

		return true;
	}

	public boolean touchUp(int x, int y, int event, int button)
	{
		return true;
	}

	public boolean touchDragged(int x, int y, int event)
	{
		return true;
	}

	public boolean mouseMoved(int x, int y)
	{
		return false;
	}

	public boolean scrolled(int event)
	{
		return false;
	}

	public boolean keyTyped(char keyChar)
	{
		if (!
				(
					Character.isLetterOrDigit(keyChar)
						||
					Character.isSpaceChar(keyChar)
						||
					isSymbol(keyChar)
				)
			)
			return false;

		switch (Lcynte_FileShower.controllComponent)
		{
			case Lcynte_FileShower.FILE_PATH_CONTROLL :
				FilePath.onKeyTyped(keyChar);
			break;

			case Lcynte_FileShower.FILE_SEARCH_CONTROLL:
				break;

			case Lcynte_FileShower.FILE_SHOWER_CONTROLL :
				break;

			case Lcynte_FileShower.PROCESS_OPERATION_CONTROLL :

		}

		return false;
	}

	public boolean keyDown(final int keyCode)
	{
		startPressing(keyCode);
		onKeyDown(keyCode);

		return false;
	}

	private static void onKeyDown(int keyCode)
	{
		switch (Lcynte_FileShower.controllComponent)
		{
			case Lcynte_FileShower.FILE_PATH_CONTROLL :
				FilePath.onKeyDown(keyCode);
				break;

			case Lcynte_FileShower.FILE_SEARCH_CONTROLL:
				break;

			case Lcynte_FileShower.FILE_SHOWER_CONTROLL :
				break;

			case Lcynte_FileShower.PROCESS_OPERATION_CONTROLL :
		}
	}

	public boolean keyUp(int keyCode)
	{
		cancelPressing();
		return true;
	}

	private static boolean isSymbol(char character)
	{
		if
		(
			character == '('
				||
			character == ')'
				||
			character == '['
				||
			character == ']'
				||
			character == '!'
			||
			character == '@'
			||
			character == '#'
			||
			character == '$'
			||
			character == '%'
			||
			character == '^'
			||
			character == '&'
			||
			character == '*'
			||
			character == '-'
			||
			character == '_'
			||
			character == '='
			||
			character == '+'
			||
			character == '\\'
			||
			character == '|'
			||
			character == ';'
			||
			character == ':'
			||
			character == '\''
			||
			character == '\"'
			||
			character == ','
			||
			character == '<'
			||
			character == '.'
			||
			character == '>'
			||
			character == '/'
			||
			character == '?'
				||
			character == '{'
			||
			character == '}'
		)
			return true;

		return false;
	}

	private static void startPressing(int keyCode)
	{
		threadPressing.keyCode = keyCode;
	}

	private static void cancelPressing()
	{
		threadPressing.keyCode = -1;
		++threadPressing.globalThreadID;
	}
}
