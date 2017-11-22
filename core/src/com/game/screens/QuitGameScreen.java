package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.MyGame;
import com.game.addition.Font;

public class QuitGameScreen implements Screen{
	private Label.LabelStyle labelStyle;
	private TextButton.TextButtonStyle normalStyle;
	private WidgetGroup widgetGroup = new WidgetGroup ();
	private Stage stage = new Stage (new ScreenViewport ());
	
	
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
	}
	
	private void createQuestionLabel (){
		Label question = new Label ("Вы действительно хотите выйти?", labelStyle);
		question.setPosition (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_FONT_SIZE * 8,
				Gdx.graphics.getHeight () / 2);
		widgetGroup.addActor (question);
	}
	
	private void createReturnButton (){
		TextButton returnn;
		returnn = new TextButton ("Вернуться", normalStyle);
		returnn.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new MainMenuScreen ());
			}
		});
		returnn.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (returnn);
	}
	
	private void createQuitButton (){
		TextButton quit;
		quit = new TextButton ("Выйти", normalStyle);
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
		createStyle ();
		
		createQuestionLabel ();
		createReturnButton ();
		createQuitButton ();
		
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
	public void dispose (){
		stage.dispose ();
	}
}
