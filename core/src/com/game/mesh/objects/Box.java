package com.game.mesh.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.utils.Pools;
import com.game.mesh.TriggeredZone;
import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Box extends GameObject{
	private static final float BODY_BOX_W = UNIT - 1;
	private static final float BODY_BOX_H = UNIT * ANGLE - 1;
	private static final float BOX_W = UNIT;
	private static final float BOX_H = UNIT + UNIT * ANGLE;
	private static final float TRIGGERED_ZONE_W = 2 * BODY_BOX_W;
	private static final float TRIGGERED_ZONE_H = 2 * BODY_BOX_H;
	private static Box triggeredBox = null; //только один ящик может быть выбран
	
	private boolean isFall = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Sprite currSprite;
	private Sprite triggeredBoxSprite;
	private ObjectAnimation fall;
	
	
	private void checkTriggeredZone (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		if (!isFall){ //если этого условия не будет, то когда ящик падает и персонаж двигается возле триггеред зоны
			// спрайт с анимации падения меняется на обычный
			boolean triggered;
			triggered = body.checkTriggered (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH);
			if (triggered){
				if (triggeredBox == null){
					currSprite = triggeredBoxSprite;
					triggeredBox = this;
					//здесь по идеи должно создаваться сообщение о возбуждении объекта
				}
				else if (triggeredBox == this){
					currSprite = triggeredBoxSprite;
				}
			}
			else{
				currSprite = fall.getFirstFrame ();
				if (triggeredBox == this){
					triggeredBox = null;
				}
			}
			updateMoveAnimation ();
		}
	}
	
	private void movedByCharacterMessage (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		Character character = (Character) message.object;
		//такое разделение движения на 2 направлени по оси Х и У не случайно, могут быть ситуации когда персонаж
		//движется под углом к стене, но выталкиваться он будет только в одном направлении, что б он смог двигаться
		//вдоль стены
		if (msg.deltaX != 0 &&  body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
			if (character.getName () == CharacterName.first){ //первый не может двигать ящики
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, 0));
			}
			else if (character.getName () == CharacterName.second){
				ObjectManager.getInstance ().addMessage (new MoveMessage (this, msg.deltaX, 0,
						body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
				body.move (msg.deltaX, 0);
			}
		}
		if (msg.deltaY != 0 &&  body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
			if (character.getName () == CharacterName.first){ //первый не может двигать ящики
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, 0, -msg.deltaY));
			}
			else if (character.getName () == CharacterName.second){
				ObjectManager.getInstance ().addMessage (new MoveMessage (this, 0, msg.deltaY,
						body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
				body.move (0, msg.deltaY);
			}
		}
	}
	
	private void pushOutMessage (GameMessage message){
		//два флага нужны, что бы не было ситуации когда ящик упирается в два объекта, и они его 2 раза выталкивают,
		//вместо одного.
		PushOutMessage msg = (PushOutMessage) message;
		if (msg.deltaX != 0 && !pushOutHorizontal){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, msg.deltaX, 0,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (msg.deltaX, 0);
			pushOutHorizontal = true;
		}
		if (msg.deltaY != 0 && !pushOutVertical){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, 0, msg.deltaY,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (0, msg.deltaY);
			pushOutVertical = true;
		}
	}
	
	private void updateFallAnimation (){
		if (fall.isAnimationFinished ()){
			triggeredBox = null;
			ObjectManager.getInstance ().sendMessage (new DeleteObjectMessage (this));
			clear ();
		}
		else{
			currSprite = fall.getCurrSprite ();
			currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		}
	}
	
	private void updateMoveAnimation (){
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	
	public Box (){
		objectType = ObjectType.box;
		body = new AnimatedObject (0, 0, BOX_W, BOX_H, BODY_BOX_W, BODY_BOX_H);
		body.move (0, 0.5f);
		
		TriggeredZone triggeredZone = new TriggeredZone (0, 0, TRIGGERED_ZONE_W, TRIGGERED_ZONE_H);
		triggeredZone.setOrigin (TRIGGERED_ZONE_W / 2, TRIGGERED_ZONE_H / 2);
		body.setTriggeredZone (triggeredZone);
		
		Texture texture = new Texture ("core/assets/images/other/box_3_triggered.png");
		triggeredBoxSprite = new Sprite (texture);
		triggeredBoxSprite.setSize (BOX_W, BOX_H);
		
		fall = new ObjectAnimation ("core/assets/images/other/box_3_fall.png", false, BOX_W, BOX_H,
				1, 5, 0.3f);
		currSprite = fall.getFirstFrame ();
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		if (isFall){
			updateFallAnimation ();
		}
		else{
			updateMoveAnimation ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			checkTriggeredZone (message); //именно в таком порядке, иначе будет баг, спрайт будет заходить на стену
			movedByCharacterMessage (message);
			
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 &&  body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, 0));
			}
			if (msg.deltaY != 0 &&  body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, 0, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			pushOutMessage (message);
		}
		else if (message.type == MessageType.destroyObject && message.object == this){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.destroyer == ObjectType.hole){
				isFall = true;
			}
		}
		else if (message.type == MessageType.characterChange){
			if (isFall){
				currSprite = fall.getFirstFrame ();
				updateMoveAnimation ();
			}
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		isFall = false;
		triggeredBox = null;
		currSprite = fall.getFirstFrame ();
		Pools.free (this);
	}
}