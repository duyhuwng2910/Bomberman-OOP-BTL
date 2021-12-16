# Bài tập lớn OOP - Bomberman Game

Trong bài tập lớn này, nhiệm vụ của là viết một phiên bản Java mô phỏng lại trò chơi [Bomberman](https://www.youtube.com/watch?v=mKIOVwqgSXM) kinh điển của NES.

<img src="res/Demo%20game.png" alt="drawing" width="400"/>

## Sinh viên thực hiện

| Họ và tên                                              | Mã sinh viên |
|--------------------------------------------------------|--------------|
| [Nguyễn Hoàng Lâm](https://gitlab.com/HoangLam4721) | 20020432     |
| [Nguyễn Duy Hưng](https://gitlab.com/duyhuwng2910)      | 20020108   |

## Mô tả về các đối tượng trong trò chơi
Các đối tượng trong trò chơi được chia làm hai loại chính là nhóm đối tượng động (*Bomber*, *Enemy*, *Bomb*) và nhóm đối tượng tĩnh (*Grass*, *Wall*, *Brick*, *Door*, *Item*).

- ![](res/sprites/player_down.png) *Bomber* là nhân vật do người chơi điều khiển. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- ![](res/sprites/balloom_left1.png) *Enemy* là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua màn chơi tiếp theo. Có nhiều loại Enemy khác nhau với các thông số khác nhau. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](res/sprites/bomb.png) *Bomb* là đối tượng mà Bomber sẽ đặt tại các ô Grass. Bomber và Enemy không thể di chuyển vào vị trí Bomb. Sau khi đặt 2.5s, Bomb sẽ phát nổvà tạo ra các đối tượng *Flame* ![](res/sprites/explosion_horizontal.png).


- ![](res/sprites/grass.png) *Grass* là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](res/sprites/wall.png) *Wall* là đối tượng cố định, không thể phá hủy, đặt Bomb tại vị trí này. Bomber và 1 số loại Enemy không thể di chuyển qua vị trí này.
- ![](res/sprites/brick.png) *Brick* là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Flame do Bomb tạo ra khi nổ. Bomber và một số loại Enemy không thể di chuyển qua vị trí Brick khi nó chưa bị phá hủy.


- ![](res/sprites/portal.png) *Portal* là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra. Nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

Các *Item* cũng được giấu phía sau Brick và hiện ra khi Brick bị phá hủy. Bomber có thể nhặt Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:
- ![](res/sprites/powerup_speed.png) *SpeedItem* giúp Bomber tăng tốc độ di chuyển.
- ![](res/sprites/powerup_flames.png) *FlameItem*  giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (tăng độ dài các Flame).
- ![](res/sprites/powerup_bombs.png) *BombItem* Ban đầu, Bomber chỉ có thể đặt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb mà Bomber có thể đặt thêm một.

Thông tin về các loại *Enemy* được liệt kê như dưới đây:

- ![](res/sprites/minvo_left1.png) *Minvo* là Emnemy đơn giản nhất, di chyển ngẩu nhiên và chậm.
- ![](res/sprites/balloom_left1.png) *Balloom* là Enemy di chuyển ngẫu nhiên với vận tốc cố định.
- ![](res/sprites/oneal_left1.png) *Oneal* có tốc độ di chuyển thay đổi, lúc nhanh, lúc chậm, biết đuổi theo Bomber và né tránh Bomb.
- ![](res/sprites/doll_left1.png) *Doll* Là Enemy di chuyển rất nhanh, biết đuổi theo Bomber và né Bomb.
- ![](res/sprites/kondoria_left1.png) *Kondoria* Là Enemy có tốc độ di chuyển khá nhanh, có thể đi xuyên qua Brick và Wall, biết đuổi theo Bomber.

## Mô tả game play
### Điều khiển
- Đối với Bomber sử dụng các phím `W`, `A`, `S`, `D` hoặc ` ↑`, `←`, `↓`, `→` để điều khiển Bomber di chuyển đi lên, trái, xuống, phải. `Space` hoăc `X`để đặt bomb.
- Khi đang chơi, có thể sử tổ hợp phím `ctrl`+`P` để dừng lại trò chơi. Nhấn tổ hợp `ctrl`+`R` để tiếp tục trò chơi.

### Cơ chế game

- Bomber sẽ bị giết khi va chạm với Enemy hoặc Flame của bom. Bomber sẽ có 3 mạng cho mỗi lần chơi, sau khi chết cả 3 mạng thì trò chơi kết thúc.
- Enemy bị tiêu diệt khi chạm vào Flame của bom.

- Khi Bomb nổ, một Flame trung tâm![](res/sprites/bomb_exploded.png) tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb sẽ xuất hiện theo bốn hướng trên![](res/sprites/explosion_vertical.png), dưới![](res/sprites/explosion_vertical.png), trái![](res/sprites/explosion_horizontal.png), phải![](res/sprites/explosion_horizontal.png). Độ dài bốn Flame xung quanh ban đầu là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi Bomb chạm phải Flame sẽ nổ ngay lập tức mà không có thời gian chờ.
- Các Flame sẽ xuất hiện khi Bomb phát nổ, chúng có thể xuyên qua các Enemy và sẽ bị chặn bởi Wall và phát hủy được các Brick ngay cạnh.
- Các Flame, Bomb và Speed Item sẽ được sử dụng cho đến khi đạt đến hạn mức tối đa.

## Tóm tắt các tính năng trong bài tập lớn
- Thiết kế cây thừa kế cho các đối tượng game.
- Xây dựng bản đồ màn chơi từ tệp cấu hình.
- Di chuyển Bomber theo sự điều khiển từ người chơi.
- Tự động di chuyển các Enemy.
- Xử lý va chạm cho các đối tượng Bomber, Enemy, Wall, Brick, Bomb.
- Xử lý bom nổ.
- Xử lý khi Bomber sử dụng các Item.
- Xử lí khi qua Portal và kết thúc màn chơi.
- Nâng cấp thuật toán tránh bom cho Enemy.
- Phát triển thêm các loại Enemy khác (tổng cộng 5 loại).
- Tạo menu dựa trên java Swing, bao gồm bảng top scores, hướng dẫn chơi, dừng/ tiếp tục game.
- Xử lý hiệu ứng âm thanh (thêm sound effects).