package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.game.MyGame;

public class QuitGameScreen implements Screen{
	private WidgetGroup widgetGroup;
	private Stage stage;
	
	
	private void createQuestionLabel (){
		Label question = new Label ("Вы действительно хотите выйти?", MyGame.labelStyle);
		question.setPosition (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_FONT_SIZE * 8,
				Gdx.graphics.getHeight () / 2);
		widgetGroup.addActor (question);
	}
	
	private void createReturnButton (){
		TextButton returnn;
		returnn = new TextButton ("Вернуться", MyGame.normalStyle);
		returnn.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
			}
		});
		returnn.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (returnn);
	}
	
	private void createQuitButton (){
		TextButton quit;
		quit = new TextButton ("Выйти", MyGame.normalStyle);
		quit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit ();
			}
		});
		quit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - 2 * (MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS),
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (quit);
	}
	
	private void createButton (){
		createQuestionLabel ();
		createReturnButton ();
		createQuitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class QuitGameScreenHolder{
		private final static QuitGameScreen instance = new QuitGameScreen ();
	}
	
	private QuitGameScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		createButton ();
	}
	
	
	public static QuitGameScreen getInstance (){
		return QuitGameScreenHolder.instance;
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
