public class Employee {

    private String fullName;
    private String major;
    private String email;
    private long phone;
    private int salary;
    private int age;

    public static void main ( String [] args) {
        Employee [] array = new Employee[5];
        array[0] = new Employee("Pavel Morozov", "Director", "bos@gmail.ru", 87775551122L, 500000, 33 );
        array[1] = new Employee("Maxim Nosov", "Manager", "manager@mail.ru", 89651234556L, 90000, 42);
        array[2] = new Employee("Svetlana Zayceva", "Accountant", "buh@yandex.ru", 84561237553L, 120000, 23);
        array[3] = new Employee("Jilia Petrova", "Vendor", "vendor@rambler.ru", 89637412558L, 70000, 25);
        array[4] = new Employee("Anton Gomov", "Vendor", "vendor1@mail.ru", 89854561265L, 70000, 48);

        for (int i = 0; i < array.length; i++) {
            if (array[i].age > 40) {
                array[i].infoMethod();
            }
        }

    }

    public Employee (String fullName, String major, String email, long phone, int salary, int age) {
        this.fullName = fullName;
        this. major = major;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public void infoMethod () {
        System.out.println("ФИО сотрудника: " + fullName + ", " + "специальность: " + major + ", " + "email: " + email + ", " + "телефон: " + phone + ", " + "зарплата: " + salary + ", " + "возраст: " + age );
    }


}
