package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.game.screens.MenuScreen;
import com.game.screens.PlayScreen;

public class MyGame extends Game{
	// Объявляем наш шрифт и символы для него (чтобы нормально читались русские буковки)
	public BitmapFont font, levels;
	private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНО" +
			"ПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][" +
			"_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	
	
	@Override
	public void create () {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/fonts/russoone.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter ();
		param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта.
		param.characters = FONT_CHARACTERS; // Наши символы
		font = generator.generateFont(param); // Генерируем шрифт
		param.size = Gdx.graphics.getHeight() / 20;
		levels = generator.generateFont(param);
		font.setColor(Color.WHITE); // Цвет белый
		levels.setColor(Color.WHITE);
		generator.dispose(); // Уничтожаем наш генератор за ненадобностью.
		
		//InputController inputProcessor = new InputController ();
		//Gdx.input.setInputProcessor(inputProcessor);
		setScreen (new MenuScreen (this));
	}
}
