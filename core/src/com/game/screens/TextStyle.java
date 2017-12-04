package com.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.game.MyGame;

public class TextStyle{
	public Label.LabelStyle labelStyle;
	public TextButton.TextButtonStyle normalStyle;
	public TextButton.TextButtonStyle closedStyle;
	
	
	private void createStyle (){
		TextureAtlas buttonAtlas = new TextureAtlas ("core/assets/images/button/button.atlas");
		Skin skin = new Skin ();
		skin.addRegions (buttonAtlas);
		
		normalStyle = new TextButton.TextButtonStyle ();
		normalStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		normalStyle.up = skin.getDrawable ("button_up");
		normalStyle.over = skin.getDrawable ("button_checked");
		normalStyle.down = skin.getDrawable ("button_checked");
		
		labelStyle = new Label.LabelStyle ();
		labelStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		
		closedStyle = new TextButton.TextButtonStyle ();
		closedStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		closedStyle.up = skin.getDrawable ("button_closed");
	}
	
	private static class TextStyleHolder{
		private final static TextStyle instance = new TextStyle ();
	}
	
	private TextStyle (){
		createStyle ();
	}
	
	
	public static TextStyle getInstance (){
		return TextStyleHolder.instance;
	}
}
