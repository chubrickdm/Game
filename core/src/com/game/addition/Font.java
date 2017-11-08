package com.game.addition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public abstract class Font{
	private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНО" +
			"ПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,?!";
	
	
	public static BitmapFont generateFont (String path, int size, Color color){
		BitmapFont font;
		FreeTypeFontParameter parameter = new FreeTypeFontParameter ();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator (Gdx.files.internal (path));
		
		parameter.characters = FONT_CHARACTERS;
		parameter.size = size;
		font = generator.generateFont (parameter);
		font.setColor (color);
		
		generator.dispose ();
		return font;
	}
}
