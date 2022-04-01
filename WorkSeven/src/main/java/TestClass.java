public class TestClass {

    public static void main ( String [] args ) {
        Cat cat = new Cat ("Max", 5);
        Plate plate = new Plate ( 10 );

        plate.info();
        cat.eat(plate);
        cat.eat(plate);
        cat.eat(plate);
        plate.info();


        Cat [] catArray = new Cat [3];                           // массив котов
        catArray[0] = new Cat("Felix", 10);
        catArray[1] = new Cat("Kim", 7);
        catArray[2] = new Cat("Boris", 15);
        Plate arrayPlate = new Plate(30);            // тарелка из который будут есть коты из массива

        System.out.println("_____________________________");
        System.out.println("Результат работы с массивом: ");

        for ( int i = 0; i < catArray.length; i++) {
            catArray[i].eatArray(arrayPlate);
        }

        for ( int i = 0; i < catArray.length; i++) {
            System.out.println("Кот " + catArray[i].getName() + " сытый: " + catArray[i].getisSatity());
        }

        System.out.println("_____________________________");
        Plate plate2 = new Plate(100);                     // тарелка для проверки метода добавления еды
        plate2.info();
        plate2.addQuantity(17);
        plate2.info();

        plate2.addQuantity(100);
        plate2.info();

    }
}
