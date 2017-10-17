package com.game.screens;

import com.badlogic.gdx.Gdx;
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
import com.game.MyGame;


public class SettingsScreen implements Screen{
	private Stage stage;
	
	
	public SettingsScreen (){
		WidgetGroup widgetGroup = new WidgetGroup ();
		TextButton video;
		TextButton control;
		TextButton sound;
		TextButton back;
		
		
		stage = new Stage (new ScreenViewport ());
		
		
		TextureAtlas buttonAtlas = new TextureAtlas ("core/assets/images/button.atlas");
		Skin skin = new Skin ();
		skin.addRegions (buttonAtlas);
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle ();
		textButtonStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf",
				MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		textButtonStyle.up = skin.getDrawable ("button_up");
		textButtonStyle.over = skin.getDrawable ("button_checked");
		textButtonStyle.down = skin.getDrawable ("button_checked");
		
		
		video = new TextButton ("Видео", textButtonStyle);
		video.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				/////////////////////////
			}
		});
		video.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		
		control = new TextButton ("Управление", textButtonStyle);
		control.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				//////////////////////
			}
		});
		control.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		
		sound = new TextButton ("Звук", textButtonStyle);
		sound.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				////////////////////
			}
		});
		sound.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		
		back = new TextButton ("Назад", textButtonStyle);
		back.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new MainMenuScreen ());
			}
		});
		back.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		
		widgetGroup.addActor (video);
		widgetGroup.addActor (control);
		widgetGroup.addActor (sound);
		widgetGroup.addActor (back);
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
