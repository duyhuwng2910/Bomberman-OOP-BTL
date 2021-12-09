package main.java.Entities.staticEntities.Items;

import main.java.Game;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Graphics.Sprite;
import main.java.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class FlameItems extends Items {

	public FlameItems(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}
	
	@Override
	public boolean collide(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		
		if(entity instanceof Bomber) {
			((Bomber) entity).addPowerup(this);
			Sound.play("item");
			remove();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setValues() {
		active = true;
		Game.addBombRadius(1);
	}
	


}
