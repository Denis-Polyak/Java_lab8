import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AbiturientCollection abiturientCollection = new AbiturientCollection();

    public static void main(String[] args) {

        Abiturient abiturient1 = new Abiturient(1, "Іванов", "Іван", "Іванович",
                "Івано-Франківськ", "123456789", 85.5, 2000);
        Abiturient abiturient2 = new Abiturient(2, "Петров", "Петр", "Петрович",
                "Київ", "987654321", 90.2, 2000);
        Abiturient abiturient3 = new Abiturient(3, "Степанчук", "Степан", "Степанович",
                "Одеса", "555555555", 78.0, 2001);

        abiturientCollection.addAbiturient(abiturient1);
        abiturientCollection.addAbiturient(abiturient2);
        abiturientCollection.addAbiturient(abiturient3);

        while (true) {
            System.out.println("====== Меню ======");
            System.out.println("1. Додати абітурієнта");
            System.out.println("2. Вилучити абітурієнта");
            System.out.println("3. Пошук абітурієнтів за іменем");
            System.out.println("4. Фільтр за середнім балом");
            System.out.println("5. Топ N абітурієнтів за середнім балом");
            System.out.println("6. Сортування за прізвищем та іменем");
            System.out.println("7. Унікальні роки народження");
            System.out.println("8. Кількість абітурієнтів за роками народження");
            System.out.println("0. Вийти");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addAbiturient();
                    break;
                case 2:
                    removeAbiturient();
                    break;
                case 3:
                    searchAbiturientsByName();
                    break;
                case 4:
                    filterAbiturientsByScore();
                    break;
                case 5:
                    getTopNAbiturients();
                    break;
                case 6:
                    sortAbiturients();
                    break;
                case 7:
                    showUniqueBirthYears();
                    break;
                case 8:
                    countAbiturientsByBirthYear();
                    break;
                case 0:
                    System.out.println("Дякую за використання програми. До побачення!");
                    System.exit(0);
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static int getUserChoice() {
        System.out.print("Введіть номер опції: ");
        return scanner.nextInt();
    }

    private static void addAbiturient() {
        System.out.println("Додавання нового абітурієнта:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера після введення числа
        System.out.print("Прізвище: ");
        String lastName = scanner.nextLine();
        System.out.print("Ім'я: ");
        String firstName = scanner.nextLine();
        System.out.print("По батькові: ");
        String middleName = scanner.nextLine();
        System.out.print("Адреса: ");
        String address = scanner.nextLine();
        System.out.print("Телефон: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Середній бал: ");
        double averageScore = scanner.nextDouble();
        System.out.print("Рік народження: ");
        int birthYear = scanner.nextInt();

        Abiturient newAbiturient = new Abiturient(id, lastName, firstName, middleName, address, phoneNumber, averageScore, birthYear);
        abiturientCollection.addAbiturient(newAbiturient);

        System.out.println("Новий абітурієнт доданий до колекції.");
    }

    private static void removeAbiturient() {
        System.out.println("Вилучення абітурієнта:");
        System.out.print("Введіть ID абітурієнта для вилучення: ");
        int id = scanner.nextInt();

        abiturientCollection.removeAbiturient(id);

        System.out.println("Абітурієнт вилучено з колекції.");
    }

    private static void searchAbiturientsByName() {
        System.out.println("Пошук абітурієнтів за іменем:");
        System.out.print("Введіть ім'я для пошуку: ");
        String name = scanner.next();

        abiturientCollection.searchByName(name).forEach(System.out::println);
    }

    private static void filterAbiturientsByScore() {
        System.out.println("Фільтр за середнім балом:");
        System.out.print("Введіть мінімальний середній бал: ");
        double minScore = scanner.nextDouble();

        abiturientCollection.filterByAverageScore(minScore).forEach(System.out::println);
    }

    private static void getTopNAbiturients() {
        System.out.println("Топ N абітурієнтів за середнім балом:");
        System.out.print("Введіть кількість абітурієнтів: ");
        int n = scanner.nextInt();

        abiturientCollection.getTopNByAverageScore(n).forEach(System.out::println);
    }

    private static void sortAbiturients() {
        System.out.println("Сортування за прізвищем та іменем:");
        abiturientCollection.sortByLastNameAndFirstName().forEach(System.out::println);
    }

    private static void showUniqueBirthYears() {
        System.out.println("Унікальні роки народження:");
        abiturientCollection.getUniqueBirthYears().forEach(System.out::println);
    }

    private static void countAbiturientsByBirthYear() {
        System.out.println("Кількість абітурієнтів за роками народження:");
        abiturientCollection.countAbiturientsByBirthYear()
                .forEach((year, count) -> System.out.println(year + ": " + count));
    }
}

class AbiturientCollection {
    private Map<Integer, Abiturient> abiturients;

    public AbiturientCollection() {
        this.abiturients = new HashMap<>();
    }

    public void addAbiturient(Abiturient abiturient) {
        abiturients.put(abiturient.getId(), abiturient);
    }

    public void removeAbiturient(int id) {
        abiturients.remove(id);
    }

    public List<Abiturient> searchByName(String name) {
        return abiturients.values().stream()
                .filter(abiturient -> abiturient.getFirstName().equalsIgnoreCase(name) ||
                        abiturient.getLastName().equalsIgnoreCase(name) ||
                        abiturient.getMiddleName().equalsIgnoreCase(name))
                .sorted(Comparator.comparingDouble(Abiturient::getAverageScore).reversed())
                .collect(Collectors.toList());
    }

    public List<Abiturient> filterByAverageScore(double minScore) {
        return abiturients.values().stream()
                .filter(abiturient -> abiturient.getAverageScore() > minScore)
                .collect(Collectors.toList());
    }

    public List<Abiturient> getTopNByAverageScore(int n) {
        return abiturients.values().stream()
                .sorted(Comparator.comparingDouble(Abiturient::getAverageScore).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Abiturient> sortByLastNameAndFirstName() {
        return abiturients.values().stream()
                .sorted(Comparator.comparing(Abiturient::getLastName)
                        .thenComparing(Abiturient::getFirstName))
                .collect(Collectors.toList());
    }

    public Set<Integer> getUniqueBirthYears() {
        return abiturients.values().stream()
                .map(Abiturient::getBirthYear)
                .collect(Collectors.toSet());
    }

    public Map<Integer, Long> countAbiturientsByBirthYear() {
        return abiturients.values().stream()
                .collect(Collectors.groupingBy(Abiturient::getBirthYear, Collectors.counting()));
    }
}

class Abiturient {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String address;
    private String phoneNumber;
    private double averageScore;
    private int birthYear;

    public Abiturient(int id, String lastName, String firstName, String middleName,
                      String address, String phoneNumber, double averageScore, int birthYear) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.averageScore = averageScore;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getBirthYear() {
        return birthYear;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Abiturient{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", averageScore=" + averageScore +
                ", birthYear=" + birthYear +
                '}';
    }
}
