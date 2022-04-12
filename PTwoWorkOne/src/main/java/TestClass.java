public class TestClass {

    public static void main(String[] args) {

//      _________Задание № 1 ____________________
        Human man = new Human("Alex", 25);
        System.out.println(man.run());
        System.out.println(man.jump());

        Robot bot = new Robot("Terminator");
        System.out.println(bot.run());
        System.out.println(bot.jump());

        Cat catty = new Cat("Felix", 4);
        System.out.println(catty.run());
        System.out.println(catty.jump());
        System.out.println("____________________________________________________");

// ______________Задание № 2__________________________
        Road road = new Road(250);                           // объявил дорожку
        Wall wall = new Wall(4);                             // объявил стену

        Actions roman = new Human("Roman", 22);          // объявил объект в типе Actions
        System.out.println(road.canRun(roman));
        System.out.println(wall.canJump(roman));

        Actions robocop = new Robot("Robocop");
        System.out.println(road.canRun(robocop));
        System.out.println(wall.canJump(robocop));

        Actions barsik = new Cat("Barsik",4);
        System.out.println(road.canRun(barsik));
        System.out.println(wall.canJump(barsik));
        System.out.println("____________________________________________________");


//____________Задание № 3____________________________

        Actions [] members = {roman, robocop, barsik};   // массив для участников
        Obstacle [] obstacle = new Obstacle[2];          // массив для препятствий
        obstacle[0] = new Road(500);
        obstacle[1] = new Wall(11);

        checkArrays(members, obstacle);
    }

    public static void checkArrays (Actions[] memberArray, Obstacle[] obstacleArray) {   // метод для проверки массивов
        for (int i = 0; i < obstacleArray.length; i++) {
            for (int y = 0; y < memberArray.length;y++) {
                System.out.println(obstacleArray[i].canDo(memberArray[y]));
            }
        }
    }

}
