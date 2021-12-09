package main.java.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;



import main.java.Board;
import main.java.Game;
import main.java.Entities.LayeredEntity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Enemy.Balloom;
import main.java.Entities.dynamicEntities.Enemy.Doll;
import main.java.Entities.dynamicEntities.Enemy.Kondoria;
import main.java.Entities.dynamicEntities.Enemy.Minvo;
import main.java.Entities.dynamicEntities.Enemy.Oneal;
import main.java.Entities.staticEntities.Grass;
import main.java.Entities.staticEntities.Portal;
import main.java.Entities.staticEntities.Wall;
import main.java.Entities.staticEntities.Destroyable.Brick;
import main.java.Entities.staticEntities.Items.BombItems;
import main.java.Entities.staticEntities.Items.FlameItems;
import main.java.Entities.staticEntities.Items.SpeedItems;
import main.java.Exception.LoadLevelException;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class FileLevelLoader extends LevelLoader {
	
	public FileLevelLoader(String path, Board board) throws LoadLevelException {
		super(path, board);
	}
	
	@Override
	public void loadLevel(String path) throws LoadLevelException {
		try {
			URL absPath = FileLevelLoader.class.getResource("/" + path);
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(absPath.openStream()));

			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);
			
			level = Integer.parseInt(tokens.nextToken());
			height = Integer.parseInt(tokens.nextToken());
			width = Integer.parseInt(tokens.nextToken());

			lineTiles = new String[height];
			
			for(int i = 0; i < height; ++i) {
				lineTiles[i] = in.readLine().substring(0, width);
 			}
			
			in.close();
		} catch (IOException e) {
			throw new LoadLevelException("Error loading level " + path, e);
		}
	}
	
	@Override
	public void createEntities() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				addLevelEntity( lineTiles[y].charAt(x), x, y );
			}
		}
	}
	
	public void addLevelEntity(char c, int x, int y) {
		int pos = x + y * getWidth();
		
		switch(c) { // TODO: minimize this method
			case '#': 
				board.addEntitie(pos, new Wall(x, y, Sprite.wall));
				break;
			case 'b': 
				LayeredEntity layer = new LayeredEntity(x, y,
						new Grass(x ,y, Sprite.grass),
						new Brick(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new BombItems(x, y, level, Sprite.bombs));
				}
				
				board.addEntitie(pos, layer);
				break;
			case 's':
				layer = new LayeredEntity(x, y, 
						new Grass(x ,y, Sprite.grass),
						new Brick(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new SpeedItems(x, y, level, Sprite.speed));
				}
				
				board.addEntitie(pos, layer);
				break;
			case 'f': 
				layer = new LayeredEntity(x, y, 
						new Grass(x ,y, Sprite.grass),
						new Brick(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new FlameItems(x, y, level, Sprite.flames));
				}
				
				board.addEntitie(pos, layer);
				break;
			case '*': 
				board.addEntitie(pos, new LayeredEntity(x, y,
						new Grass(x ,y, Sprite.grass),
						new Brick(x ,y, Sprite.brick)) );
				break;
			case 'x': 
				board.addEntitie(pos, new LayeredEntity(x, y,
						new Grass(x ,y, Sprite.grass),
						new Portal(x ,y, board, Sprite.portal),
						new Brick(x ,y, Sprite.brick)) );
				break;
			case ' ': 
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			case 'p': 
				board.addMob( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board) );
				Screen.setOffset(0, 0);
				
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			//Enemies
			case '1':
				board.addMob( new Balloom(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			case '2':
				board.addMob( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			case '3':
				board.addMob( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			case '4':
				board.addMob( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			case '5':
				board.addMob( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			default: 
				board.addEntitie(pos, new Grass(x, y, Sprite.grass) );
				break;
			}
	}
	
}
