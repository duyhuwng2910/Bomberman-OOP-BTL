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
import main.java.Exception.LoadLevelException;
import main.java.Game;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class FileLevelLoader extends LevelLoader {
  /**
   * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được từ
   * ma trận bản đồ trong file dạng txt.
   */
  private static char[][] map;

  public FileLevelLoader(Board board, int level) throws LoadLevelException {
    super(board, level);
  }

  @Override
  public void loadLevel(int level) throws LoadLevelException {
    /**
     * đọc dữ liệu từ tệp cấu hình /levels/{level}.txt
     * cập nhật các giá trị đọc được vào bao
     * gồm width, height, level, map.
     */

    try {
      FileReader fr = new FileReader("res\\levels\\" + level + ".txt"); //Đọc file lưu màn chơi
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();

      while (line != null) {
        lineTiles.add(line);
        line = br.readLine();
      }
      String[] arrays = lineTiles.get(0).trim().split(" ");
      this.level = Integer.parseInt(arrays[0]);
      this.height = Integer.parseInt(arrays[1]);
      this.width = Integer.parseInt(arrays[2]);

      br.close();
    } catch (Exception e) {
      throw new LoadLevelException("Error loading level " + level, e);
    }
  }

  @Override
  public void createEntities() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        addLevelEntities(lineTiles.get(i + 1).charAt(j), i, j);
      }
    }
  }

  public void addLevelEntities(char c, int x, int y) {
    int pos = x + y * width;
    switch (c) {
      // Thêm Wall
      case '#':
        board.addEntity(pos, new Wall(x, y, Sprite.wall));
        break;
        // Thêm Portal
      case 'x':
        board.addEntity(pos, new LayeredEntity(x, y,
            new Grass(x, y, Sprite.grass), new Portal(x, y, board, Sprite.portal), new Brick(x, y, Sprite.brick)));
        break;
        // Thêm brick
      case '*':
        board.addEntity(x + y * width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
        break;
        // Thêm Bomber
      case 'p':
        board.addCharacter(new Bomber(Coordinates.tileToPixel(x),
            Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
        Screen.setOffset(0, 0);
        board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
        break;
        // Thêm balloom
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
        LayeredEntity layer = new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));

        if (!this.board.isItemUsed(x, y, level)) {
          layer.addBeforeTop(new BombItems(x, y, level, Sprite.powerup_bombs));
        }
        board.addEntity(pos, layer);
        break;
        // Thêm SpeedItem
      case 's':
        layer = new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));

        if (this.board.isItemUsed(x, y, level)) {
          layer.addBeforeTop(new SpeedItems(x, y, level, Sprite.powerup_speed));
        }
        board.addEntity(pos, layer);
        break;
        // Thêm FlameItem
      case 'f':
        layer = new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick));

        if (this.board.isItemUsed(x, y, level)) {
          layer.addBeforeTop(new FlameItems(x, y, level, Sprite.powerup_flames));
        }
        board.addEntity(pos, layer);
        break;
        // Thêm Grass
      default:
        board.addEntity(pos, new Grass(x, y, Sprite.grass));
        break;
    }
  }
}
