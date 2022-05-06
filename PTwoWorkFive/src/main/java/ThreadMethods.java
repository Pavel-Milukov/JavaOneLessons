public class ThreadMethods {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE/2;

    public static void main(String[] args) throws InterruptedException{
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        float [] arrayOne = new float[SIZE];
        for (int i = 0; i < arrayOne.length; i++) {
            arrayOne[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for ( int i = 0; i < arrayOne.length; i++) {
            arrayOne[i] = (float)(arrayOne[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        System.out.println("Время выполнения первого метода составило " + ( System.currentTimeMillis() - startTime) + " миллисекунд");
    }

    public static void secondMethod () throws InterruptedException {

        float [] arrayTwo = new float [SIZE];
        for (int i = 0; i < arrayTwo.length; i++) {
            arrayTwo[i] = 1.0f;
        }

        float [] partOne = new float[HALF];
        float [] partTwo = new float [HALF];
        float [] full = new float[SIZE];
        long start = System.currentTimeMillis();
        System.arraycopy(arrayTwo,0,partOne,0,HALF);
        System.arraycopy(arrayTwo,0,partTwo,0,HALF);
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for ( int i = 0; i < partOne.length; i++) {
                    partOne[i] = (float)(partOne[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for ( int i = 0; i < partTwo.length; i++) {
                    partTwo[i] = (float)(partTwo[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
            }
        });
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.arraycopy(partOne,0,full,0,partOne.length);
        System.arraycopy(partTwo,0,full,HALF,partTwo.length);
        System.out.println("Время выполнения второго метода составило " + ( System.currentTimeMillis() - start) + " миллисекунд");







    }

}
