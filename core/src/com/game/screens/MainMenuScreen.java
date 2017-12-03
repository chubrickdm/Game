package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.game.MyGame;

public class MainMenuScreen implements Screen{
	private WidgetGroup widgetGroup;
	private Stage stage;
	
	
	private void createPlayButton (){
		TextButton play;
		play = new TextButton ("Играть", TextStyle.getInstance ().normalStyle);
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
			}
		});
		play.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (play);
	}
	
	private void createSettingsButton (){
		TextButton settings;
		settings = new TextButton ("Настройки", TextStyle.getInstance ().normalStyle);
		settings.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (SettingsScreen.getInstance ());
			}
		});
		settings.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (settings);
	}
	
	private void createExitButton (){
		TextButton exit;
		exit = new TextButton ("Выход", TextStyle.getInstance ().normalStyle);
		exit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (QuitGameScreen.getInstance ());
			}
		});
		exit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (exit);
	}
	
	private void createButton (){
		createPlayButton ();
		createSettingsButton ();
		createExitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class MainMenuScreenHolder{
		private final static MainMenuScreen instance = new MainMenuScreen ();
	}
	
	private MainMenuScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		createButton ();
	}
	
	
	public static MainMenuScreen getInstance (){
		return MainMenuScreenHolder.instance;
	}
	
	@Override
	public void show (){
		createButton ();
		
		// Устанавливаем нашу сцену основным процессором для ввода
		Gdx.input.setInputProcessor (stage);
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act (delta);
		stage.draw ();
	}
	
	@Override
	public void resize (int width, int height){ }
	
	@Override
	public void pause (){ }
	
	@Override
	public void resume (){ }
	
	@Override
	public void hide (){ }
	
	@Override
	public void dispose (){ }
}
