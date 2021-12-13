package main.java.Entities.dynamicEntities.Enemy.AI_enemies;

import java.util.ArrayList;
import main.java.Board;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Enemy.Enemy;
import main.java.Game;

/**
 * Class AI phiên bản nâng cấp.
 */
public class AIIntermediate extends AI {
	Bomber bomber;
	Enemy enemy;
	Board board;
	int radius = Game.getBombRadius();
	
	public AIIntermediate(Bomber bomber, Enemy enemy, Board board) {
		this.bomber = bomber;
		this.enemy = enemy;
		this.board = board;
	}

	/**     x1 x2 x3
	 * y1   3  2  1
	 * y2   4  e  0
	 * y3   5  6  7
	 * @param Xb hàonh độ của bom
	 * @param Yb tung độ của bom
	 * @return vị chí của bom trong khu vực dò của enenmy
	 * @return -1 nếu không thuộc phạm vi dò
	 */
	public int detectBombInRanger(int Xb,int Yb){
		int Xe = this.enemy.getXTile();
		int Ye = this.enemy.getYTile();
		if (Yb == Ye) {
			// bom ở bên trái
			if((Xe - Xb) > 0 && (Xe - Xb) <= radius) {
				return 4;
			}
			//  bom ở bên phải
			if ((Xb - Xe) > 0 && (Xb - Xe) <= radius) {
				return 0;
			}
		}
		if (Xb == Xe) {
			//  bom ở phía trên
			if((Ye - Yb) > 0 && (Ye - Yb) <= radius) {
				return 2;
			}
			//  bom ở phía dưới
			if ((Yb - Ye) > 0 && (Yb - Ye) <= radius) {
				return 6;
			}
		}
		if ((Ye - Yb > 0) && (Ye - Yb <= radius)) {
			// bom ở góc trái trên
			if ((Xe - Xb) > 0 && (Xe - Xb) <= radius) {
				return 3;
			}
			// bom ở góc phải trên
			if ((Xb - Xe) > 0 && (Xb - Xe) <= radius) {
				return 1;
			}
		}
		if ((Yb - Ye > 0) && (Yb - Ye <=radius)) {
			// bomb ở góc trái dưới
			if ((Xe - Xb) > 0 && (Xe - Xb) <= radius) {
				return 5;
			}
			// bomb ở góc phải dưới
			if ((Xb - Xe) > 0 && (Xb - Xe) <= radius) {
				return 7;
			}
		}
		return -1;
	}

	@Override
	public int calculateDirection() {
		int Xe = this.enemy.getXTile();
		int Ye = this.enemy.getYTile();

		boolean[] canGo = {true,true,true,true,true};

		ArrayList<Integer> way = new ArrayList<>();

		int thread = 0;

		for (int i = 0 ;i < this.board.getBombs().size(); i++){
			int Xb = this.board.getBombs().get(i).getXTile();
			int Yb = this.board.getBombs().get(i).getYTile();

			if (this.detectBombInRanger(Xb, Yb)!= -1){
				thread++;

				switch (this.detectBombInRanger(Xb, Yb)) {
					case 0:
						if (Xb - Xe == this.radius) {
							canGo[4] = canGo[1] = false;
						} else {
							canGo[4] = canGo[1] = canGo[3] = false;
						}
						break;
					case 1:
						canGo[1] = canGo[0] = false;
						break;
					case 2:
						if (Ye - Yb == this.radius) {
							canGo[4] = canGo[0] = false;
						} else {
							canGo[4] = canGo[0] = canGo[2] = false;
						}
						break;
					case 3:
						canGo[3] = canGo[0] = false;
						break;
					case 4:
						if (Xe - Xb == this.radius) {
							canGo[4] = canGo[3] = false;
						} else {
							canGo[4] = canGo[1] = canGo[3] = false;
						}
						break;
					case 5:
						canGo[2] = canGo[3] = false;
						break;
					case 6:
						if (Yb - Ye == this.radius){
							canGo[4] = canGo[2] = false;
						} else {
							canGo[4] = canGo[0] = canGo[2] = false;
						}
						break;
					case 7:
						canGo[1] = canGo[2] = false;
						break;
				}
			}
		}
		for (int k = 0; k < canGo.length; k++) {
			if (canGo[k]) {
				if (k == 4) {
					way.add(-1); // chuyển 4 là -1
				}
				else {
					way.add(k);
				}
			}
		}
		if (thread == 0) {
			int vertical = random.nextInt(2);

			if (vertical == 1) {
				int v = calculateRowDirection();
        if (v != -1) {
          return v;
        } else {
          return calculateColDirection();
				}
			} else {
				int h = calculateColDirection();

        if (h != -1) {
          return h;
        } else {
          return calculateRowDirection();
				}
			}
		}
		if (way.size() == 0) {
			return random.nextInt(4);
		}
		if (way.size() == 1) {
			return way.get(0);
		}
		return way.get(random.nextInt(way.size()));
	}
	
	protected int calculateColDirection() {
    if (bomber.getXTile() < enemy.getXTile()) {
			if (this.bomber.isPutBombSuccess()) {
				return random.nextInt(4);
			}
      return 3;
    } else if (bomber.getXTile() > enemy.getXTile()) {
			if (this.bomber.isPutBombSuccess()) {
				return random.nextInt(4);
			}
      return 1;
		}
		return -1;
	}
	
	protected int calculateRowDirection() {
		if(bomber.getYTile() < enemy.getYTile())
			return 0;
		else if(bomber.getYTile() > enemy.getYTile())
			return 2;
		return -1;
	}
	

}
