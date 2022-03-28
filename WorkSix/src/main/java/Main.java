public class Main {

    public static void main (String [] args) {

        Dog sobakaOne = new Dog("Jack", "black", 7);
        Dog sobakaTwo = new Dog("Rock", "brown", 4);
        Dog sobakaThree = new Dog("Rax", "white", 2);
        sobakaOne.run(500);
        sobakaOne.run(600);
        sobakaOne.swim(15);
        sobakaTwo.swim(10);
        sobakaThree.Count();


        Cat kotOne = new Cat("John", "white", 4);
        Cat kotTwo = new Cat("Boris", "gray", 2);
        Cat kotThree = new Cat("Fox", "black", 6);
        Cat kotFour = new Cat("Tod", "orange", 1);

        kotOne.run(35);
        kotOne.swim(77);
        kotFour.run(202);
        kotTwo.swim(5);
        kotFour.Count2();
    }

}
