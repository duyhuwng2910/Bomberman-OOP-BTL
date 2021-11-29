package main.java;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {
    private final String FONT_PATH = "/res/spites/font.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/res/spites/button_pressed.png');";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/res/spites/red_button.png');";

    public GameButton(String text) {
        setText(text);
        setButtonFont();
        setPrefHeight(49);
        setPrefWidth(190);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initializeButtonListeners() {

        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }

        });
        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }

        });
        setOnMouseEntered(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }

        });
        setOnMouseExited(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }

        });
    }
}
