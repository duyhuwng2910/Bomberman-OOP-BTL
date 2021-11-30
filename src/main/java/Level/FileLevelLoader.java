package main.java.Level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import main.java.Board;
import main.java.Entities.LayeredEntity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Enemies.Balloom;
import main.java.Entities.dynamicEntities.Enemies.Oneal;
import main.java.Entities.staticEntities.Destroyable.Brick;
import main.java.Entities.staticEntities.Grass;
import main.java.Entities.staticEntities.Items.BombItems;
import main.java.Entities.staticEntities.Items.FlameItems;
import main.java.Entities.staticEntities.Items.SpeedItems;
import main.java.Entities.staticEntities.Portal;
import main.java.Entities.staticEntities.Wall;
import main.java.Game;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class FileLevelLoader extends LevelLoader {
  /**
   * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được từ
   * ma trận bản đồ trong tệp cấu hình
   */
  private static char[][] map;

  public FileLevelLoader(Board board, int level) throws LoadLevelException {
    super(board, level);
  }

  @Override
  public void loadLevel(int level) {
    // TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
    // TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
    List<String> list = new ArrayList<>();

    try {
      FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");//doc tep luu map
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      while (!line.equals("")) {
        list.add(line);
        line = br.readLine();
        //doc file txt luu vao list
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String[] arrays = list.get(0).trim().split(" ");
    this.level = Integer.parseInt(arrays[0]);
    this.height = Integer.parseInt(arrays[1]);
    this.width = Integer.parseInt(arrays[2]);
    map = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        map[i][j] = list.get(i + 1).charAt(j);
      }
    }
  }

  @Override
  public void createEntities() {
    // TODO: tạo các Entity của màn chơi
    // TODO: sau khi tạo xong, gọi board.addEntity() để thêm Entity vào game

    // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
    // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        int pos = x + y * getWidth();
        char c = map[y][x];
        switch (c) {
          // Thêm Wall
          case '#':
            board.addEntity(pos, new Wall(x, y, Sprite.wall));
            break;
          // Thêm Portal
          case 'x':
            board.addEntity(pos, new LayeredEntity(x, y,
                new Grass(x, y, Sprite.grass),
                new Portal(x, y, board, Sprite.portal),
                new Brick(x, y, Sprite.brick)));
            break;
          // Thêm brick
          case '*':
            board.addEntity(x + y * width,
                new LayeredEntity(x, y,
                    new Grass(x, y, Sprite.grass),
                    new Brick(x, y, Sprite.brick)
                )
            );
            break;
          // Thêm Bomber
          case 'p':
            board.addCharacter(new Bomber(Coordinates.tileToPixel(x),
                Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            Screen.setOffset(0, 0);
            board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
            break;

          // Thêm balloon
          case '1':
            board.addCharacter(new Balloom(Coordinates.tileToPixel(x),
                Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
            break;
          // Thêm oneal
          case '2':
            board.addCharacter(new Oneal(Coordinates.tileToPixel(x),
                Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            break;
          // Thêm BomItem
          case 'b':
            LayeredEntity layer = new LayeredEntity(x, y,
                new Grass(x, y, Sprite.grass),
                new BombItems(x, y, Sprite.powerup_bombs),
                new Brick(x, y, Sprite.brick));
            board.addEntity(pos, layer);
            break;
          // Thêm SpeedItem
          case 's':
            layer = new LayeredEntity(x, y,
                new Grass(x, y, Sprite.grass),
                new SpeedItems(x, y, Sprite.powerup_speed),
                new Brick(x, y, Sprite.brick));
            board.addEntity(pos, layer);
            break;
          // Thêm FlameItem
          case 'f':
            layer = new LayeredEntity(x, y,
                new Grass(x, y, Sprite.grass),
                new FlameItems(x, y, Sprite.powerup_flames),
                new Brick(x, y, Sprite.brick));
            board.addEntity(pos, layer);
            break;
          // Thêm Grass
          default:
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            break;
        }
      }
    }
  }
}
