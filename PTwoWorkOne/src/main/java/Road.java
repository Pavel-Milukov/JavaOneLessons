public class Road implements Obstacle {

    private int length;

    public Road(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String canRun(Actions subject) {    // объявил метод который на прием берет параметры типа интерфейса Actions
        if (subject.run() >= length) {
            return "Пробежал успешно";
        } else {
            return "Пробежать не смог";
        }
    }


    @Override
    public String canDo(Actions tip) {
        if (tip.run() >= length) {
            return "Пробежал успешно(Ы)";
        } else {
            return "Пробежать не смог(Ы)";
        }
    }

}
