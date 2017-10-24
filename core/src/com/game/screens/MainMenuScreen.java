package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.MyGame;
import com.game.objects.ObjectManager;
import com.game.level.ParseXML;


public class MainMenuScreen implements Screen{
	private Stage stage;
	
	
	public MainMenuScreen (){
		ObjectManager.getInstance ();
		ParseXML.parseLVL (1);
		
		WidgetGroup widgetGroup = new WidgetGroup ();
		TextButton play;
		TextButton settings;
		TextButton exit;
		
		
		stage = new Stage (new ScreenViewport ());
		
		
		TextureAtlas buttonAtlas = new TextureAtlas ("core/assets/images/button.atlas");
		Skin skin = new Skin ();
		skin.addRegions (buttonAtlas);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle ();
		textButtonStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf",
				MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		textButtonStyle.up = skin.getDrawable ("button_up");
		textButtonStyle.over = skin.getDrawable ("button_checked");
		textButtonStyle.down = skin.getDrawable ("button_checked");
		
		
		play = new TextButton ("Играть", textButtonStyle);
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new PlayScreen ());
			}
		});
		play.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		
		settings = new TextButton ("Настройки", textButtonStyle);
		settings.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new SettingsScreen ());
			}
		});
		settings.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2, MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		
		exit = new TextButton ("Выход", textButtonStyle);
		exit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit ();
			}
		});
		exit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		widgetGroup.addActor (play);
		widgetGroup.addActor (exit);
		widgetGroup.addActor (settings);
		stage.addActor (widgetGroup);
		
		// Устанавливаем нашу сцену основным процессором для ввода
		Gdx.input.setInputProcessor (stage);
	}
	
	@Override
	public void show (){
	
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act (delta);
		stage.draw ();
	}
	
	@Override
	public void resize (int width, int height){
	
	}
	
	@Override
	public void pause (){
	
	}
	
	@Override
	public void resume (){
	
	}
	
	@Override
	public void hide (){
	
	}
	
	@Override
	public void dispose (){
		stage.dispose ();
	}
}
