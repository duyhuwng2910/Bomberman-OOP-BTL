package main.java.Entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.java.Graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    /**
     * Phương thức render được gọi liên tục trong vòng lặp game
     * để cập nhật hình ảnh của các entity theo trạng thái.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    /**
     * Phương thức update được gọi liên tục trong vòng lặp game
     * để xử lý sự kiện và cập nhật trạng thái của toàn bộ Entity.
     */
    public abstract void update();

    /**
     * Phương thức collied dùng để xử lý va chạm của 2 entity.
     */
    public abstract void collided(Entity entity);
}
