package main.java.Entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import main.java.Graphics.Sprite;
import main.java.Input.Keyboard;

public class Bomber extends Entities {
    protected Keyboard keyboard;
    protected String key;
    public Bomber(double x, double y, Image img, Keyboard keyboard) {
        super(x, y, img);
        this.keyboard = keyboard;
    }

    @Override
    public void update() {
        keyboard.update();
        key = keyboard.getKey();
        switch (key) {
            case "UP":
                if (y > 1) {
                    y--;
                }
                break;
            case "LEFT":
                if (x > 1) {
                    x--;
                }
                break;
            case "RIGHT":
                if (y < Sprite.DEFAULT_SIZE) {
                    y++;
                }
                break;
            case "DOWN":
                if (x < Sprite.DEFAULT_SIZE) {
                    x++;
                }
                break;
            default:
                System.out.println("Waiting");
                break;
        }
    }
}
