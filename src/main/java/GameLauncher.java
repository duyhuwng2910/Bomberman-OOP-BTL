package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import main.java.Entities.Balloom;
import main.java.Entities.Bomber;
import main.java.Entities.Brick;
import main.java.Entities.Entity;
import main.java.Entities.Grass;
import main.java.Entities.Items;
import main.java.Entities.Oneal;
import main.java.Entities.Portal;
import main.java.Entities.Wall;
import main.java.Graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class GameLauncher extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> itemsList = new ArrayList<>();
    private List<String> map = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(GameLauncher.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        timer.start();

        createMap();

    }

    // hàm tạo map
    public void createMap() {
        try {
            File file = new File("F:\\Bomberman-BTL-N10\\res\\levels\\1.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                map.add(line);
            }
            br.close();
            fr.close();
        } catch (Exception ignored) {
            System.out.println("Error");
        }

        Entity staticObject;
        Entity dynamicObject;
        Items items;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                switch (map.get(i).charAt(j)) {
                    case '#':
                        staticObject = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    case '*':
                        staticObject = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    case 'x':
                        staticObject = new Portal(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(staticObject);
                        staticObject = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    case 'p':
            dynamicObject = new Bomber(j, i, Sprite.player_right.getFxImage(), canvas.getScene());
                        entities.add(dynamicObject);
                        break;
                    case '1':
                        dynamicObject = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                        entities.add(dynamicObject);
                        break;
                    case '2':
                        dynamicObject = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                        entities.add(dynamicObject);
                        break;
                    case 'b':
                        items = new Items(j, i, Sprite.bomb.getFxImage());
                        itemsList.add(items);
                        staticObject = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    case 's':
                        items = new Items(j, i, Sprite.powerup_speed.getFxImage());
                        itemsList.add(items);
                        staticObject = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    case 'f':
                        items = new Items(j, i, Sprite.powerup_flames.getFxImage());
                        itemsList.add(items);
                        staticObject = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                    default:
                        staticObject = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(staticObject);
                        break;
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        itemsList.forEach(g -> g.render(gc));
    }
}
