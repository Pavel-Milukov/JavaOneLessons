public class Human implements Actions {

    private String name;
    private int age;

    public Human (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName () {
        return name;
    }

    @Override
    public int run() {
        return 300;
    }

    @Override
    public int jump() {
        return 1;
    }


}
