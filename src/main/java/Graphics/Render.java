package main.java.Graphics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Interface đại diện cho quá trình
 * cập nhật và xuất hình ảnh của game.
 */
public interface Render {

	void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	void render(Screen screen);
}
