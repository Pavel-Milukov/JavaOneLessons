public class Animal {
    protected String name;
    protected String color;
    protected int age;


    public Animal (String name, String color, int age) {
        this.name = name;
        this.color= color;
        this.age = age;

    }

    public void run (int distance) {
        System.out.println(name + " пробежал " + distance + "м");
    }

    public void swim (int distance2) {
        System.out.println(name + " проплыл " + distance2 + "м");
    }
}
