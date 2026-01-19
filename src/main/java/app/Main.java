package app;

import collection.MyArrayList;
import collection.MyCollectors;
import concurrency.OccurrenceCounter;
import domain.Car;
import io.FileCarRepository;
import sorting.SortContext;
import sorting.SortType;
import sorting.algorithms.*;
import sorting.comparator.CarComparators;
import sorting.special.EvenOnlySorter;
import validation.Validators;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        MyArrayList<Car> cars = new MyArrayList<>();
        SortContext<Car> sortContext = new SortContext<>();
        sortContext.setStrategy(new MergeSortStrategy<>());

        boolean running = true;
        while (running) {
            Menu.print();
            String cmd = SC.nextLine().trim();

            try {
                switch (cmd) {
                    case "1":
                        cars = createOrLoadCars();
                        System.out.println("Автомобили загружены. Количество: " + cars.size());
                        break;
                    case "2":
                        printCars(cars);
                        break;
                    case "3":
                        chooseSortStrategy(sortContext);
                        break;
                    case "4":
                        sortContext.sort(cars, CarComparators.byAllFields());
                        System.out.println("Сортировка выполнена. Алгоритм: " + sortContext.currentName());
                        break;
                    case "5":
                        EvenOnlySorter.sortEvenOnly(cars, Car::getPower, CarComparators.byAllFields());
                        System.out.println("Применена специальная сортировка (чётная мощность).");
                        break;
                    case "6":
                        appendToFile(cars);
                        break;
                    case "7":
                        countOccurrences(cars);
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Неизвестная команда.");
                }
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Выход из программы.");
    }

    private static MyArrayList<Car> createOrLoadCars() {
        System.out.println("Выберите способ ввода данных: 1) из файла  2) случайная генерация  3) ручной ввод");
        System.out.print("Ваш выбор: ");
        String m = SC.nextLine().trim();

        switch (m) {
            case "1":
                System.out.print("Введите путь к файлу: ");
                String path = SC.nextLine().trim();
                try {
                    return FileCarRepository.readCars(path);
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка чтения файла: " + e.getMessage());
                }
            case "2":
                int n = askLength();
                return generateRandomCars(n);
            case "3":
                int k = askLength();
                return readManualCars(k);
            default:
                throw new RuntimeException("Неверный способ ввода данных");
        }
    }

    private static int askLength() {
        System.out.print("Введите количество элементов (>0): ");
        String s = SC.nextLine().trim();
        int n;
        try { n = Integer.parseInt(s); } catch (NumberFormatException e) { throw new RuntimeException("Ожидалось целое число."); }
        return Validators.requirePositiveInt(n, "количество");
    }

    private static MyArrayList<Car> generateRandomCars(int n) {
        Random rnd = new Random();
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    int power = 50 + rnd.nextInt(451); // 50..500
                    int year = 1990 + rnd.nextInt(36); // 1990..2025
                    String model = "Модель - " + (char) ('A' + rnd.nextInt(26)) + rnd.nextInt(100);
                    return Car.builder().power(power).model(model).year(year).build();
                })
                .collect(MyCollectors.toMyArrayList());
    }

    private static MyArrayList<Car> readManualCars(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    System.out.println("Автомобиль №#" + (i + 1));
                    int power = readInt("Мощность (>0): ");
                    String model = readString("Модель (обязательно): ");
                    int year = readInt("Год выпуска (1886..2100): ");

                    Validators.requirePositiveInt(power, "мощность");
                    Validators.requireNonBlank(model, "модель");
                    Validators.requireYear(year);

                    return Car.builder().power(power).model(model).year(year).build();
                })
                .collect(MyCollectors.toMyArrayList());
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String s = SC.nextLine().trim();
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new RuntimeException("Ожидалось целое число"); }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    private static void printCars(MyArrayList<Car> cars) {
        if (cars.size() == 0) {
            System.out.println("(список пуст)");
            return;
        }
        for (Car c : cars) System.out.println(c);
    }

    private static void chooseSortStrategy(SortContext<Car> ctx) {
        System.out.println("Текущий алгоритм сортировки: " + ctx.currentName());
        System.out.println("Выберите алгоритм:");
        System.out.println("1) INSERTION (вставками)");
        System.out.println("2) SELECTION (выбором)");
        System.out.println("3) MERGE (слиянием)");
        System.out.println("4) QUICK (быстрая)");
        System.out.print("Ваш выбор: ");
        String s = SC.nextLine().trim();
        SortType type;
        switch (s) {
            case "1": type = SortType.INSERTION; break;
            case "2": type = SortType.SELECTION; break;
            case "3": type = SortType.MERGE; break;
            case "4": type = SortType.QUICK; break;
            default: throw new RuntimeException("Неверный выбор алгоритма.");
        }

        switch (type) {
            case INSERTION: ctx.setStrategy(new InsertionSortStrategy<>()); break;
            case SELECTION: ctx.setStrategy(new SelectionSortStrategy<>()); break;
            case MERGE: ctx.setStrategy(new MergeSortStrategy<>()); break;
            case QUICK: ctx.setStrategy(new QuickSortStrategy<>()); break;
        }

        System.out.println("Выбран алгоритм: " + ctx.currentName());
    }

    private static void appendToFile(MyArrayList<Car> cars) {
        System.out.print("Введите путь к выходному файлу: ");
        String path = SC.nextLine().trim();
        try {
            FileCarRepository.appendCars(path, cars);
            System.out.println("Данные успешно добавлены в файл. Количество записей: " + cars.size());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private static void countOccurrences(MyArrayList<Car> cars) {
        int target = readInt("Введите значение мощности: ");
        int threads = readInt("Количество потоков: ");
        int count = OccurrenceCounter.countOccurrencesParallel(cars, Car::getPower, target, threads);
        System.out.println("Количество автомобилей с мощностью " + target + ": " + count);
    }
}

