package main.java;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;


public class MenuGame {
    private static final int HEIGHT = 483;
    private static final int WIDTH = 724;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTON_START_X = 200;
    private final static int MENU_BUTTON_START_Y = 100;

    List<GameButton> menuButton;

    public MenuGame() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT );
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createBackground();
        createButton();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(GameButton gameButton) {
        gameButton.setLayoutX(MENU_BUTTON_START_X);
        gameButton.setLayoutY(MENU_BUTTON_START_Y);
        menuButton.add(gameButton);
        mainPane.getChildren().add(gameButton);
    }

    private void createButton() {
        createPlayButton();
        createModeButton();
        createLevelButton();
        createExitButton();
    }

    private void createPlayButton() {
        GameButton playButton = new GameButton("START");
        addMenuButton(playButton);

        //playButton.setOnAction(event -> showSubScene(shipChooserSubscene));
    }

    private void createModeButton() {
        GameButton modeButton = new GameButton("MODE");
        addMenuButton(modeButton);
    }

    private void createLevelButton() {
        GameButton levelButton = new GameButton("LEVEL");
        addMenuButton(levelButton);
    }

    private void createExitButton() {
        GameButton exitButton = new GameButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(event -> mainStage.close());
    }

    private void createBackground() {
        Image bgImg = new Image("/res/spites/backgrounds.png", 724, 483, true, true);
        BackgroundImage bg = new BackgroundImage(bgImg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(bg));
    }


}
