public class HomeWorkThree {


    public static void main (String [] args) {

        // Задача № 1

        int [] numberArray = {1,1,0,0,1,0,1,1,0,0};
        for (int i=0; i < numberArray.length;i++) {
            if (numberArray[i]==1) {                 // если значение ячейки массива 1, то меняем его на 0
                numberArray[i]=0;
            } else {                                 // в противном случае другое значение меняем на 1
                numberArray[i]=1;
            }
            System.out.print(numberArray[i]);        // сразу печатаем данную ячейку массива
        }
        System.out.println();

        // Задача № 2

        int [] array = new int [100];                 // по дефолту значения ячеек равны 0
        for (int j=0; j < array.length; j++) {
            array[j]= j + 1;                         // присваиваем значение ячейки массива из расчета индекс + 1
            System.out.print(array[j] + " ");
        }
        System.out.println();

        // Задача № 3

        int [] arr = {1,5,3,2,11,4,5,2,4,8,9,1};
        for (int y = 0; y < arr.length;y++) {
            if (arr[y] < 6) {
                arr[y] *= 2;                         // использую составной оператор для изменения значения переменной
            }
            System.out.print(arr[y] + " ");
        }
        System.out.println();

        // Задача № 4

        int [][] arrayFour = new int [6][6];         // ячейки массива по умолчанию заполнились нулями
        for (int x = 0; x < arrayFour.length; x++) {
            for (int z = 0; z < arrayFour[x].length; z++) {
                if (x == z) {                        // зависимость первой диоганали x == z
                    arrayFour[x][z] = 1;
                }
                if (arrayFour[x].length - 1 - z == x) { // зависимость второй диагонали
                    arrayFour[x][z] = 1;
                }
                System.out.print(arrayFour[x][z] + " ");
            }
            System.out.println();
        }
        System.out.println();

        int [] test = arrayReturn(5,7);     // проверка метода из задания № 5 присваиваю полученный массив, затем распечатываю с помощью цикла
        for ( int c=0; c < test.length;c++) {
            System.out.print(test[c] + " ");
        }
        System.out.println();

        // Задача № 6

        int [] arraySix = {1,25,40,33,3,77,95};
        int min = arraySix[0];                           // задаем минимальное число по нулевому индексу массива
        int max = arraySix[0];                           // задаем максимальное число по нулевому индексу массива
        for (int p= 0; p < arraySix.length; p++) {
            if (arraySix[p] > max) {                    // сравниваем каждый элемент массива с максимальным значением
                max = arraySix[p];
            }else if (arraySix[p] < min) {              // сравниваем каждый элемент массива с минимальным значением
                min = arraySix[p];
            }
        }
        System.out.println("Минимальное число массива равно: " + min + " , максимальное число массива равно: " + max);


        // Проверка задания № 7
        int [] testarray = {7,6,3,8,8};
        System.out.println(checkArraySum(testarray));

    }

       // Задача № 5
        public static int[] arrayReturn ( int len, int initialValue) {
            int [] massiv = new int[len];               // размер массива
            for ( int q= 0; q < massiv.length; q++) {
                massiv[q]= initialValue;                // присваиваю значение ячейки массива равное initialValue
            }
            return massiv;
        }

        // Задача № 7
        public static boolean checkArraySum ( int[] check) {
            int leftsum = 0;                             // объявляю переменную суммы левой части массива
            int rightsum = 0;                            // объявляю переменную суммы правой части массива
            for (int i=0; i < check.length - 2; i++) {   // цикл подсчета суммы ячеек массива без последний 2-ух индексов
                leftsum+=check[i];
            }
            for (int y = check.length -2; y < check.length;y++) {  // // цикл подсчета суммы ячеек массива последний 2-ух индексов
                rightsum+=check[y];
            }
            return leftsum == rightsum;
        }
}
