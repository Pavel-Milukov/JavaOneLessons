public class MyArraySizeException extends IllegalArgumentException{  // наследую от класса исключений чтобы создать свое исключение

    public MyArraySizeException (){                                  // создал конструктор с сообщением
        super("Размер двумерного массива не подходит по условию");   // задал сообщение
    }
}
