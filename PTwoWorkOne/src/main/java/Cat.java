public class Cat implements Actions {

    private String name;
    private int age;

    public Cat (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName () {
        return name;
    }

    @Override
    public int run () {
        return 400;
    }

    @Override
    public int jump() {
        return 10;
    }
}
