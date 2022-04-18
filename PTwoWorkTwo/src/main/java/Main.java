public class Main {

    public static void main(String[] args) {

//______________________Проверка задания № 1_______________________________________________________________
        String[][] rightArray = new String[4][4];                   //создал правильный массив 4*4
        String[][] wrongArray = new String[5][2];                   //создал неправильный массив 5*4


        try {                                                       // создал блок try где происходит контроль генерирования исключения
            checkArray(wrongArray);
            System.out.println("Двумерный массив подходит по условию");
        } catch (MyArraySizeException variable) {                   // создал блок catch где обрабатываю появления исключения в случае его генерации
            System.out.println(variable.getMessage());             // через метод getMessage обращаюсь к сообщению объекта моего исключения
        }
        System.out.println("_________________________________");


//______________________Проверка задания № 2____________________________________________________________________
        String[][] rightArray2 = { {"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"},{"1", "1", "1", "1"} }; //создал правильный массив
        String[][] wrongArray2 = { {"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "wwww"} }; //создал неправильный массив

        try {
            int summa = checkArray2(rightArray2);
            System.out.println("Все числа преобразовались," + "сумма массива: " + summa);
        } catch (MyArraySizeException variable2) {                           // сначала проверка на валидность массива
            System.out.println(variable2.getMessage());
        } catch (MyArrayDataException e) {                                   // потом проверка на преобразование ячейки массива
            System.out.println("Массив преобразовать не получилось!!!");
            System.out.println(e.getMessage());
        }
    }


    //__________________Метод для задания № 1_______________________________________________________________________________
    public static void checkArray(String[][] testArray) {
        if (testArray.length != 4) {                                // проверяю двумерный массив на длину
            throw new MyArraySizeException();                       // выбрасываю тип моего исключения при прохождения условия
        }
        for (int i = 0; i < testArray.length; i++) {               // цикл для проверки длинны массива во всех ячейках массива
            if (testArray[i].length != testArray.length) {
                throw new MyArraySizeException();                   // выбрасываю тип моего исключения при прохождения условия
            }
        }
    }

//__________________Метод для задания № 2_______________________________________________________________________________

    public static int checkArray2(String[][] testArray)  {  //

        int sum = 0;                    // объявления сумматора
        int row1 =0;                    // переменная где храниться первый индекс двумерного массива для отчета
        int row2 =0;                    // переменная, где храниться второй индекс двумерного массива для отчета

        if (testArray.length != 4) {
            throw new MyArraySizeException();
        }
        for (int i = 0; i < testArray.length; i++) {
            if (testArray[i].length != testArray.length) {
                throw new MyArraySizeException();
            }
        }
            try {                                                      // цикл где преобразую элементы массива кладу в блок try
                for (int y = 0; y < testArray.length; y++) {           // вложенный цикл где считаю все элементы массива
                    for (int x = 0; x < testArray[y].length; x++) {
                        row1 = y;       // каждый раз перед проверкой записываю значения индекса массива, чтобы зафиксировать их перед возможным исключением
                        row2 = x;       // каждый раз перед проверкой записываю значения индекса массива, чтобы зафиксировать их перед возможным исключением
                        int number = Integer.parseInt(testArray[y][x]);
                        sum += number;
                    }

                }
            } catch (NumberFormatException s) {
                throw new MyArrayDataException("Неверные данные в ячейке " + row1 + "," + row2); //если в цикле ловлю ошибку, то на основании ее выбрасываю свою и в текст записываю номера индексов массива
            }
        return sum;
    }

}


