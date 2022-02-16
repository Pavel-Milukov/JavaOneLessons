package ru.geekbrains.Test;

public class Main {

    public static void main(String[] args) {

        //создаем объект r типа Reader и говорим ему что он равен новому объекту типа Reader с пустыми параметрами ()
        Reader r = new Reader();// мы создали переменную r которая является ссылкой на все то что есть в классе Reader(методы, переменные)

        r.Scan();// вызов метода Scan из класса Reader делается это с помощью ссылки на объект

        r.i = count(r.i);
        r.k = count(r.k);
        System.out.println("Теперь ваше первое число = " + r.i);
        System.out.println("Теперь ваше второе число = " + r.k);
    }


    private static int count(int x) {
        x = x + 1;
        return x;
    }
}
// вызов метода Scan из класса Reader делается это с помощью ссылки на объект