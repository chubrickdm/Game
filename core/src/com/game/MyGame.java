package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.game.screens.MenuScreen;

public class MyGame extends Game{
	public BitmapFont font, levels;
	private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНО" + "ПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][" + "_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	
	
	private static class MyGameHolder{
		private final static MyGame instance = new MyGame ();
	}
	
	private MyGame (){}
	
	
	public static MyGame getInstance (){
		return MyGameHolder.instance;
	}
	
	
	@Override
	public void create (){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator (Gdx.files.internal ("core/assets/fonts/russoone.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter ();
		
		param.size = Gdx.graphics.getHeight () / 18; //Размер шрифта.
		param.characters = FONT_CHARACTERS; //Наши символы
		font = generator.generateFont (param); //Генерируем шрифт
		font.setColor (Color.WHITE); //Цвет белый
		
		param.size = Gdx.graphics.getHeight () / 20;
		levels = generator.generateFont (param);
		levels.setColor (Color.WHITE);
		
		generator.dispose (); //Уничтожаем наш генератор за ненадобностью.
		
		//InputController inputProcessor = new InputController ();
		//Gdx.input.setInputProcessor(inputProcessor);
		setScreen (new MenuScreen ());
	}
}
