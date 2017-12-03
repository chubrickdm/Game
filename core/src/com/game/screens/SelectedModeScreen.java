package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.GameSystem;
import com.game.MyGame;

import com.game.mesh.objects.singletons.special.LevelManager;

public class SelectedModeScreen implements Screen{
	private WidgetGroup widgetGroup;
	private Stage stage;
	
	
	private void createNewGameButton (){
		TextButton newGame;
		newGame = new TextButton ("Новая игра", MyGame.normalStyle);
		newGame.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				LevelManager.getInstance ().newGame ();
				MyGame.getInstance ().setScreen (PlayScreen.getInstance ());
			}
		});
		newGame.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (newGame);
	}
	
	private void createContinueGameButton (){
		TextButton continueGame;
		continueGame = new TextButton ("Продолжить", MyGame.normalStyle);
		if (GameSystem.IS_FIRST_GAME_START){
			continueGame.setStyle (MyGame.closedStyle);
		}
		continueGame.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!GameSystem.IS_FIRST_GAME_START){
					MyGame.getInstance ().setScreen (PlayScreen.getInstance ());
				}
			}
		});
		continueGame.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W,
				MyGame.BUTTON_H);
		widgetGroup.addActor (continueGame);
	}
	
	private void createSelectedLVLButton (){
		TextButton selectedLVL;
		selectedLVL = new TextButton ("Уровни", MyGame.normalStyle);
		if (GameSystem.IS_FIRST_GAME_START){
			selectedLVL.setStyle (MyGame.closedStyle);
		}
		selectedLVL.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!GameSystem.IS_FIRST_GAME_START){
					/////////////////////
				}
			}
		});
		selectedLVL.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (selectedLVL);
	}
	
	private void createBackButton (){
		TextButton back;
		back = new TextButton ("Назад", MyGame.normalStyle);
		back.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
			}
		});
		back.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (back);
	}
	
	private void createButton (){
		createNewGameButton ();
		createContinueGameButton ();
		createSelectedLVLButton ();
		createBackButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class SelectedModeScreenHolder{
		private final static SelectedModeScreen instance = new SelectedModeScreen ();
	}
	
	private SelectedModeScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		createButton ();
	}
	
	
	public static SelectedModeScreen getInstance (){
		return SelectedModeScreenHolder.instance;
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
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
		}
		
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
