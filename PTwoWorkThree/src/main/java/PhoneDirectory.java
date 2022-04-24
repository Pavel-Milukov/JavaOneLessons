import java.util.HashMap;
import java.util.Map;

public class PhoneDirectory {

    private HashMap<Integer, String> collection;     // объявил поле класса коллекцией
                                                     // K - Integer наш номер ( он как бы уникальный), а вот V - String (фамилия) могут повторяться

    public PhoneDirectory () {
        this.collection = new HashMap<>();           // обязательно инициализировал коллекцию при создании объекта класса
    }


    public void add (Integer number, String name) {          // метод add можно добавлять записи в коллекцию
        collection.put(number, name);                        // вызываю метод put у самого поля класса - коллекции, передаю значения
    }

    public void get (String name) {                                     // метод get, передаю интересующую нас фамилию
        for (Map.Entry<Integer, String> o : collection.entrySet()) {    // использую цикл for each чтобы пройтись по всем элементам коллекции
            if ( name.equals(o.getValue())) {                           // если фамилия совпадает с значением элемента , то вывожу ключ элемента коллекции
                System.out.println(o.getKey());
            }
        }

    }


}
