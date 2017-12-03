package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.game.addition.Font;
import com.game.addition.parsers.ParseSettings;
import com.game.mesh.objects.GameObject;
import com.game.screens.MainMenuScreen;

public class MyGame extends Game{
	public World world = new World (new Vector2 (0, 0), true);
	
	public static TextButton.TextButtonStyle normalStyle = null;
	public static Label.LabelStyle labelStyle = null;
	public static TextButton.TextButtonStyle closedStyle = null;
	
	public static final float BUTTON_W = 250 * GameObject.ASPECT_RATIO;
	public static final float BUTTON_H = 55 * GameObject.ASPECT_RATIO;
	public static final float DISTANCE_BETWEEN_BUTTONS = 15 * GameObject.ASPECT_RATIO;
	public static final int   BUTTON_FONT_SIZE = (int) (3 * BUTTON_H / 5);
	
	
	public void createStyle (){
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
	
	private static class MyGameHolder{
		private final static MyGame instance = new MyGame ();
	}
	
	private MyGame (){ }
	
	
	public static MyGame getInstance (){
		return MyGameHolder.instance;
	}
	
	@Override
	public void create (){
		ParseSettings.parseSettings ();
		setScreen (MainMenuScreen.getInstance ());
	}
}