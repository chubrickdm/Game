package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.MyGame;


public class MenuScreen implements Screen{
	final MyGame game;
	private Label.LabelStyle labelStyle;
	private Stage stage;
	private TextButton play, exit;
	private Table table;
	
	
	public MenuScreen (final MyGame game){
		this.game = game;
		
		
		stage = new Stage (new ScreenViewport ());
		
		Skin skin = new Skin ();
		TextureAtlas buttonAtlas = new TextureAtlas (Gdx.files.internal ("core\\assets\\images\\game\\images.pack"));
		skin.addRegions (buttonAtlas);
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle ();
		textButtonStyle.font = game.font;
		textButtonStyle.up = skin.getDrawable ("button-up");
		textButtonStyle.down = skin.getDrawable ("button-down");
		textButtonStyle.checked = skin.getDrawable ("button-up");
		
		labelStyle = new Label.LabelStyle ();
		labelStyle.font = game.font;
		table = new Table ();
		table.setFillParent (true);
		
		//Кнопка играть. Добавляем новый listener, чтобы слушать события касания.
		//После касания, переключает на экран выбора уровней, а этот экран уничтожается
		play = new TextButton ("Играть", textButtonStyle);
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				game.setScreen (new PlayScreen (game));
				dispose ();
			}
		});
		
		// Кнопка выхода.
		exit = new TextButton ("Выход", textButtonStyle);
		exit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit ();
				dispose ();
			}
		});
		table.add (play);
		table.row ();
		table.add (exit);
		stage.addActor (table);
		
		// Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
		Gdx.input.setInputProcessor (stage);
	}
	
	@Override
	public void show (){
	
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
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
		game.dispose ();
	}
}
