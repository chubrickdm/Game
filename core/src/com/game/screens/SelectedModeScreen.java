package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.game.GameSystem;
import com.game.MyGame;
import com.game.addition.Font;
import com.game.mesh.objects.special.LevelManager;

public class SelectedModeScreen implements Screen{
	private TextButton.TextButtonStyle normalStyle;
	private TextButton.TextButtonStyle closedStyle;
	private WidgetGroup widgetGroup = new WidgetGroup ();
	private Stage stage = new Stage (new ScreenViewport ());
	
	
	private void createStyle (){
		TextureAtlas buttonAtlas = new TextureAtlas ("core/assets/images/button.atlas");
		Skin skin = new Skin ();
		skin.addRegions (buttonAtlas);
		
		
		normalStyle = new TextButton.TextButtonStyle ();
		normalStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		normalStyle.up = skin.getDrawable ("button_up");
		normalStyle.over = skin.getDrawable ("button_checked");
		normalStyle.down = skin.getDrawable ("button_checked");
		
		closedStyle = new TextButton.TextButtonStyle ();
		closedStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		closedStyle.up = skin.getDrawable ("button_closed");
	}
	
	private void createNewGameButton (){
		TextButton newGame;
		newGame = new TextButton ("Новая игра", normalStyle);
		newGame.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				LevelManager.getInstance ().newGame ();
				MyGame.getInstance ().setScreen (new PlayScreen ());
			}
		});
		newGame.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (newGame);
	}
	
	private void createContinueGameButton (){
		TextButton continueGame;
		continueGame = new TextButton ("Продолжить", normalStyle);
		if (GameSystem.IS_FIRST_GAME_START){
			continueGame.setStyle (closedStyle);
		}
		continueGame.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!GameSystem.IS_FIRST_GAME_START){
					MyGame.getInstance ().setScreen (new PlayScreen ());
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
		selectedLVL = new TextButton ("Уровни", normalStyle);
		if (GameSystem.IS_FIRST_GAME_START){
			selectedLVL.setStyle (closedStyle);
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
		back = new TextButton ("Назад", normalStyle);
		back.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new MainMenuScreen ());
			}
		});
		back.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (back);
	}
	
	private void createButton (){
		createStyle ();
		
		createNewGameButton ();
		createContinueGameButton ();
		createSelectedLVLButton ();
		createBackButton ();
		
		stage.addActor (widgetGroup);
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
			MyGame.getInstance ().setScreen (new MainMenuScreen ());
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
