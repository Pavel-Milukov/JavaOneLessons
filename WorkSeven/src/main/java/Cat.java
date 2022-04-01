public class Cat {

    private String name;
    private int appetite;
    private boolean isSatiety;  // поле сытости

    public Cat ( String name, int appetite ) {
        this.name = name;
        this.appetite = appetite;
    }

    public boolean getisSatity () {
        return isSatiety;
    }

    public String getName () {
        return name;
    }


    public void eat ( Plate plate ) {
        System.out.println ("Кот " + name + " начал есть.");
        isSatiety = plate.decreasedFood(appetite, name); // сюда записываем значение в зависимости от удалось ли поесть или нет в методе
        System.out.println("Кот " + name +" сытый : " + isSatiety);
    }

    public void eatArray ( Plate plate ) {                    // метод для массива задание № 4
        isSatiety = plate.decreasedFoodArray(appetite, name);
    }


}
