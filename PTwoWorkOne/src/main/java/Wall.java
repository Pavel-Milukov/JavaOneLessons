public class Wall implements Obstacle{

    private int height;

    public Wall (int height) {
        this.height = height;
    }

    public int getHeight () {
        return height;
    }

    public String canJump (Actions object) {              // объявил метод который на прием берет параметры типа интерфейса Actions
        if (object.jump() >= height) {
            return "Перепрыгнуть смог";
        } else {
            return  "Перепрыгнуть  не смог";
        }
    }

    @Override
    public String canDo(Actions tip) {
        if (tip.jump() >= height) {
            return "Перепрыгнуть смог";
        } else {
            return  "Перепрыгнуть  не смог";
        }
    }
}
