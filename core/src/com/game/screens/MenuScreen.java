package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.MyGame;


public class MenuScreen implements Screen{
	private Stage stage;
	private TextButton play, exit;
	private WidgetGroup table;
	
	
	public MenuScreen (){
		stage = new Stage (new ScreenViewport ());
		table = new Table ();
		//table.setFillParent (true);
		
		Skin skin = new Skin ();
		TextureAtlas buttonAtlas = new TextureAtlas ("core\\assets\\images\\button.atlas");
		skin.addRegions (buttonAtlas);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle ();
		textButtonStyle.font = MyGame.getInstance ().font;
		
		textButtonStyle.up = skin.getDrawable ("button_up");
		textButtonStyle.over = skin.getDrawable ("button_checked");
		textButtonStyle.down = skin.getDrawable ("button_checked");
		
		
		//Кнопка играть. Добавляем новый listener, чтобы слушать события касания.
		//После касания, переключает на экран выбора уровней, а этот экран уничтожается
		play = new TextButton ("Играть", textButtonStyle);
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new PlayScreen ());
			}
		});
		//play.setPosition (Gdx.graphics.getWidth () / 2, Gdx.graphics.getHeight () / 2);
		play.setBounds (100, 100, 340, 43);
		
		// Кнопка выхода.
		exit = new TextButton ("Выход", textButtonStyle);
		exit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit ();
			}
		});
		exit.setBounds (400, 500, 186, 43);
		
		table.addActor (play);
		//table.row ();
		table.addActor (exit);
		//table.setPosition (700, 500);
		//table.setFillParent (false);
		stage.addActor (table);
		
		// Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
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
		// Уничтожаем сцену и объект game.
		stage.dispose ();
	}
}
