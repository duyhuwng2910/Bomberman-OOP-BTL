package main.java.Exception;

/**
 * Class đại diện cho ngoại lệ khi load level của game.
 */
public class LoadLevelException extends GameException {
	public LoadLevelException() {
	}
	
	public LoadLevelException(String str) {
		super(str);
	}
	
	public LoadLevelException(String str, Throwable cause) {
		super(str, cause);
	}
	
	public LoadLevelException(Throwable cause) {
		super(cause);
	}
	
}
