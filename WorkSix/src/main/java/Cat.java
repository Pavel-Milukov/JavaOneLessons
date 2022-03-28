public class Cat extends Animal{

    private static int count2 = 0;  // Решение по задаче № 4 - добавил поле static для класса с подсчетом количества созданных котов

    public Cat (String name, String color, int age) {   //  нужные параметры из вне для создания объекта не нуждается в поле count, поэтому его перенес внутрь констр.
        super(name, color, age);
        this.count2+= 1;            // Решение по задаче № 4 меняем значение поля count при создании объекта из конструктора
    }

    public int getCount2 () {
        return count2;
    }


    @Override
    public void run(int distance) {
        if (distance > 0 && distance <= 200) {
            super.run(distance);
        } else {
            System.out.println("Дистанцию в " + distance + " м. кот " + name + " пробежать не сможет.");
        }
    }

    @Override
    public void swim(int distance2) {
        System.out.println("Кот не умеет плавать");
    }

    public void Count2 () {                    // Метод отображения кол-ва созданных объектов данного класса
        System.out.println("Количество созданных котов = " + getCount2());
    }
}
