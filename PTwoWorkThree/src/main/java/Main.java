import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main (String [] args) {

        //___________________________________Задание № 1________________________________________________________________
        List<String> WordsList = new ArrayList<>();              // создал коллекцию для повторяющихся слов
        Set<String> uniqueWordsList = new LinkedHashSet<>();     // создал коллекцию типа Set где могут храниться только уникальные элементы колекции
        WordsList.add("Dog");
        WordsList.add("Cat");
        WordsList.add("Bird");
        WordsList.add("Lion");
        WordsList.add("Dog");
        WordsList.add("Dog");
        WordsList.add("Bird");
        WordsList.add("Elephant");
        WordsList.add("Elephant");
        WordsList.add("Dog");
        WordsList.add("Elephant");
        WordsList.add("Lion");
        System.out.println(WordsList);

        for (String element: WordsList) {            // с помощью цикла for each каждый элемент коллекции типа List попробовал положить в коллекцию типа Set
            uniqueWordsList.add(element);            // знаю, что повторяющиеся элементы в коллекцию типа Set не добавятся
        }
        System.out.println("Cписок уникальный  слов: " + uniqueWordsList);


        for (String word: uniqueWordsList) {            // использовал вложенный цикл for each: сначала взял элемент коллекции с уникальными элементами
            int sum = 0;                                // кол-во потворений
            for ( String checkWord: WordsList) {        // здесь беру каждый элемент коллекции и сравниваю с элементом уникальной коллекции
                if ( word.equals(checkWord) ) {
                    sum++;
                }
            }
            System.out.println("Слово " + word + " встречалось " + sum + " раз/а"); // в конце вывожу информацию о повторениях
        }

        //___________________________________Задание № 2________________________________________________________________

        PhoneDirectory book = new PhoneDirectory();
        book.add( 890912375, "Kozlov");
        book.add(899992345,"Bobrov" );
        book.add(877766655,"Volkov");
        book.add(867676767,"Bobrov" );
        book.add(811111111,"Bobrov" );
        book.add(833344490,"Nosov");
        book.get("Bobrov");
        book.get("Kozlov");

    }


}
