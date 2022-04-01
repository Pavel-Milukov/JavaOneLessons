public class Plate {

    private int foodQuantity;

    public Plate ( int foodQuantity ) {
        this.foodQuantity = foodQuantity;
    }

    public int getFoodQuantity () {
        return foodQuantity;
    }

    public void info () {
        System.out.println ("Текущее кол-во еды " + foodQuantity);
    }

    public boolean decreasedFood ( int foodQuantity, String name) {
        if ( this.foodQuantity - foodQuantity >= 0) {                  // проверка на то, чтобы не было отриц. кол-ва еды в тарелке
            this.foodQuantity -= foodQuantity;
            return true;                            // если коту удалось покушать: узнаем по разнице еды в тарелке и аппетиту кота, возвращаем true для поля сытости
        } else {
            System.out.println ("Кот " + name + " поесть не может");
            return false;                   // если коту не удалось покушать: будет ветвление else и возвращаем значение false в поле сытости
        }
    }

    public boolean decreasedFoodArray ( int foodQuantity, String name) {      // метод для массива пункт № 4
        if ( this.foodQuantity - foodQuantity >= 0) {
            this.foodQuantity -= foodQuantity;
            return true;
        } else {
            return false;
        }
    }

    public void addQuantity (int addFood) {       // Метод добавления еды в тарелку пункт № 5
        foodQuantity += addFood;
    }      // метод  пункт № 5

}
