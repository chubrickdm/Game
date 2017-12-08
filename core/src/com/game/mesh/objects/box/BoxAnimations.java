package com.game.mesh.objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.DeleteObjectMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class BoxAnimations extends Box{
	private Box box;
	private Sprite currSprite;
	private Sprite triggeredSprite;
	private ObjectAnimation fall;
	
	
	public BoxAnimations (Box box){
		this.box = box;
		
		Texture texture = new Texture ("core/assets/images/other/box_3_triggered.png");
		triggeredSprite = new Sprite (texture);
		triggeredSprite.setSize (BOX_W, BOX_H);
		
		fall = new ObjectAnimation ("core/assets/images/other/box_3_fall.png", false, BOX_W, BOX_H,
				1, 5, 0.3f);
		currSprite = fall.getFirstFrame ();
		currSprite.setPosition (box.getSpriteX (), box.getSpriteY ());
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		switch (box.state){
		case fall:
			if (fall.isAnimationFinished ()){
				ObjectManager.getInstance ().sendMessage (new DeleteObjectMessage (box));
				box.clear ();
			}
			else{
				currSprite = fall.getCurrSprite ();
			}
			break;
		case stand:
			currSprite = fall.getFirstFrame ();
			if (box == triggeredBox){
				currSprite = triggeredSprite;
			}
			break;
		}
		
		currSprite.setPosition (box.getSpriteX (), box.getSpriteY ());
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}