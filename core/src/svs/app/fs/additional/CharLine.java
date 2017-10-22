package svs.app.fs.additional;

import svs.app.fs.FilePath;

public class CharLine implements CharSequence
{
/*::|	FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public char sqcChar[];
	public int filledLength;

	public static CharLine charLine;
/*::|	CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public CharLine(int capacity)
	{
		sqcChar = new char[capacity];
	}

	public CharLine(char ... sqcChar)
	{
		this.sqcChar = sqcChar;
		filledLength = sqcChar.length;
	}

	public CharLine(int capacity, char ... sqcCharIn)
	{
		this(capacity);
		int length = sqcCharIn.length;
		for (int i = 0; i < length; ++i)
			sqcChar[i] = sqcCharIn[i];

		filledLength = length;
	}
/*::|	F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public int length()
	{
		return filledLength;
	}

	public char charAt(int index)
	{
		return sqcChar[index];
	}

	public CharSequence subSequence(int init, int end)
	{
		charLine.filledLength = 0;
		char sqcRes[] = charLine.sqcChar;
		for (int itera = 0, iteraOri = init; iteraOri < end; ++itera, ++iteraOri)
			sqcRes[itera] = sqcChar[iteraOri];

		charLine.filledLength = end - init;

		return charLine;
	}

	public void concat(char character)
	{
		sqcChar[filledLength] = character;
		++filledLength;
	}

	public void concat(String string)
	{
		char sqcCharString[] = string.toCharArray();
		int length = sqcCharString.length;

		for (int iteraCL = filledLength, iteraStr = 0; iteraStr < length; ++iteraCL, ++iteraStr)
			sqcChar[iteraCL] = sqcCharString[iteraStr];

		filledLength += length;
	}

	public void put(char character, int index)
	{

	}

	public void swap(int index1, int index2)
	{

	}

	public void remove(int index)
	{
		for (int i = index; i < filledLength;)
			sqcChar[i] = sqcChar[++i];

		--filledLength;
		sqcChar[filledLength] = 0;	
	}

	public void removeFrom(int index)
	{
		for (int i = index; i < filledLength; ++i)
			sqcChar[i] = 0;

		filledLength = index;
	}

	public int getLastChar(char character)
	{
		for (int i = filledLength - 1; i > -1; --i)
			if (sqcChar[i] == character)
				return i;

		return -1;
	}

	public boolean equateWithString(char sqcCharInner[])
	{
		int length = filledLength > sqcCharInner.length ? sqcCharInner.length : filledLength;

		for (int i = 0; i < length; ++i)
			if (sqcCharInner[i] != sqcChar[i])
				return false;

		return true;
	}

	public void toCharArray(char sqcCharInner[])
	{
		sqcCharInner = new char[filledLength];
		for (int i = 0; i > filledLength; ++i)
			sqcCharInner[i] = sqcChar[i];
	}

	public String toString()
	{
		return new String(sqcChar, 0, filledLength);
	}
}
