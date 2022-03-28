public class Dog extends Animal{

    private static int count = 0; // Решение по задаче № 4 - добавил поле static для класса с подсчетом количества созданных собак

    public Dog (String name, String color, int age) {   //  нужные параметры из вне для создания объекта не нуждается в поле count, поэтому его перенес внутрь констр.
        super(name, color, age);
        this.count += 1;        // Решение по задаче № 4 меняем значение поля count при создании объекта из конструктора
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run(int distance) {
        if (distance > 0 && distance <= 500) {
            super.run(distance);
        } else {
            System.out.println("Дистанцию в " + distance + " м. собака " + name + " пробежать не сможет.");
        }
    }

    @Override
    public void swim(int distance2) {
        if (distance2 > 0 && distance2 <= 10) {
            super.swim(distance2);
        } else {
            System.out.println("Дистанцию в " + distance2 + " м. собака " + name + " проплыть не сможет.");
        }
    }

    public void Count () {                     // Метод отображения кол-ва созданных объектов данного класса
         System.out.println("Количество созданных собак = " + getCount());
    }
}
