package app;

import collection.MyArrayList;
import collection.MyCollectors;
import concurrency.OccurrenceCounter;
import domain.Car;
import io.FileCarRepository;
import sorting.SortContext;
import sorting.SortType;
import sorting.algorithms.InsertionSortStrategy;
import sorting.algorithms.MergeSortStrategy;
import sorting.algorithms.QuickSortStrategy;
import sorting.algorithms.SelectionSortStrategy;
import sorting.comparator.CarComparators;
import sorting.comparator.CarSortField;
import sorting.special.EvenOnlySorter;
import validation.ValidationException;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public final class Main {
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
                    case "1" -> {
                        cars = createOrLoadCars();
                        System.out.println("Готово. Количество автомобилей: " + cars.size());
                    }
                    case "2" -> printCars(cars);

                    case "3" -> chooseSortStrategy(sortContext);

                    case "4" -> {
                        if (cars.size() == 0) {
                            System.out.println("Массив пуст. Сначала загрузите/создайте данные (пункт 1).");
                            break;
                        }
                        sortContext.sort(cars, CarComparators.byAllFields());
                        System.out.println("Отсортировано. Алгоритм: " + sortContext.currentName());
                    }

                    case "5" -> {
                        if (cars.size() == 0) {
                            System.out.println("Массив пуст. Сначала загрузите/создайте данные (пункт 1).");
                            break;
                        }
                        ComparatorChoice choice = chooseComparatorForEvenOnly();
                        EvenOnlySorter.sortEvenOnly(cars, choice.numericExtractor(), choice.comparator());
                        System.out.println("Готово. Отсортированы только чётные значения, нечётные остались на местах.");
                    }

                    case "6" -> {
                        if (cars.size() == 0) {
                            System.out.println("Массив пуст. Нечего записывать.");
                            break;
                        }
                        appendToFile(cars);
                    }

                    case "7" -> {
                        if (cars.size() == 0) {
                            System.out.println("Массив пуст. Нечего считать.");
                            break;
                        }
                        countOccurrences(cars);
                    }

                    case "0" -> running = false;

                    default -> System.out.println("Неизвестная команда. Повторите ввод.");
                }

            } catch (ValidationException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Выход из программы.");
    }

    // ====== 1) Создание/загрузка ======

    private static MyArrayList<Car> createOrLoadCars() {
        System.out.println();
        System.out.println("Выберите способ ввода:");
        System.out.println("1) Из файла");
        System.out.println("2) Случайно");
        System.out.println("3) Вручную");
        System.out.print("Ваш выбор: ");
        String m = SC.nextLine().trim();

        return switch (m) {
            case "1" -> loadFromFile();
            case "2" -> generateRandomCars(askLength());
            case "3" -> readManualCars(askLength());
            default -> throw new RuntimeException("Неверный вариант ввода");
        };
    }

    private static MyArrayList<Car> loadFromFile() {
        System.out.print("Введите путь к CSV-файлу: ");
        String path = SC.nextLine().trim();
        try {
            return FileCarRepository.readCars(path);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + e.getMessage());
        }
    }

    private static int askLength() {
        System.out.print("Введите длину (>0): ");
        String s = SC.nextLine().trim();
        try {
            int n = Integer.parseInt(s);
            if (n <= 0) throw new RuntimeException("Длина должна быть > 0");
            return n;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Длина должна быть целым числом");
        }
    }

    private static MyArrayList<Car> generateRandomCars(int n) {
        Random rnd = new Random();

        return IntStream.range(0, n)
                .mapToObj(i -> {
                    int power = 50 + rnd.nextInt(451);   // 50..500
                    int year = 1990 + rnd.nextInt(36);   // 1990..2025
                    String model = "Модель-" + (char) ('A' + rnd.nextInt(26)) + rnd.nextInt(100);
                    return Car.builder().power(power).model(model).year(year).build();
                })
                .collect(MyCollectors.toMyArrayList());
    }

    private static MyArrayList<Car> readManualCars(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    System.out.println();
                    System.out.println("Автомобиль #" + (i + 1));
                    int power = readInt("Мощность (>0): ");
                    String model = readString("Модель (не пустая): ");
                    int year = readInt("Год (1886..текущий+1): ");

                    return Car.builder().power(power).model(model).year(year).build();
                })
                .collect(MyCollectors.toMyArrayList());
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String s = SC.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Ожидалось целое число");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    // ====== 2) Печать ======

    private static void printCars(MyArrayList<Car> cars) {
        if (cars.size() == 0) {
            System.out.println("(массив пуст)");
            return;
        }
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    // ====== 3) Strategy: выбор алгоритма ======

    private static void chooseSortStrategy(SortContext<Car> ctx) {
        System.out.println();
        System.out.println("Текущий алгоритм: " + ctx.currentName());
        System.out.println("Выберите алгоритм:");
        System.out.println("1) INSERTION (вставками)");
        System.out.println("2) SELECTION (выбором)");
        System.out.println("3) MERGE (слиянием)");
        System.out.println("4) QUICK (быстрая)");
        System.out.print("Ваш выбор: ");

        String s = SC.nextLine().trim();

        SortType type = switch (s) {
            case "1" -> SortType.INSERTION;
            case "2" -> SortType.SELECTION;
            case "3" -> SortType.MERGE;
            case "4" -> SortType.QUICK;
            default -> throw new RuntimeException("Неверный выбор");
        };

        switch (type) {
            case INSERTION -> ctx.setStrategy(new InsertionSortStrategy<>());
            case SELECTION -> ctx.setStrategy(new SelectionSortStrategy<>());
            case MERGE -> ctx.setStrategy(new MergeSortStrategy<>());
            case QUICK -> ctx.setStrategy(new QuickSortStrategy<>());
        }

        System.out.println("Выбран алгоритм: " + ctx.currentName());
    }

    // ====== 5) Спец. сортировка: чётные ======

    private record ComparatorChoice(ToIntFunction<Car> numericExtractor,
                                    java.util.Comparator<Car> comparator) {}

    private static ComparatorChoice chooseComparatorForEvenOnly() {
        System.out.println();
        System.out.println("Выберите поле для проверки чётности и сортировки:");
        System.out.println("1) Мощность");
        System.out.println("2) Год");
        System.out.print("Ваш выбор: ");
        String s = SC.nextLine().trim();

        CarSortField field = switch (s) {
            case "1" -> CarSortField.POWER;
            case "2" -> CarSortField.YEAR;
            default -> throw new RuntimeException("Неверный выбор поля");
        };

        // Сортируем по выбранному полю по возрастанию
        var cmp = new CarComparators.Builder()
                .then(field, true)
                .build();

        ToIntFunction<Car> extractor =
                (field == CarSortField.POWER)
                        ? Car::getPower
                        : Car::getYear;

        return new ComparatorChoice(extractor, cmp);
    }

    // ====== 6) Append в файл ======

    private static void appendToFile(MyArrayList<Car> cars) {
        System.out.print("Введите путь к выходному файлу: ");
        String path = SC.nextLine().trim();
        try {
            FileCarRepository.appendCars(path, cars);
            System.out.println("Записано (добавлением) автомобилей: " + cars.size());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл: " + e.getMessage());
        }
    }

    // ====== 7) Многопоточность ======

    private static void countOccurrences(MyArrayList<Car> cars) {
        System.out.println();
        System.out.println("Выберите числовое поле для подсчёта:");
        System.out.println("1) Мощность");
        System.out.println("2) Год");
        System.out.print("Ваш выбор: ");
        String f = SC.nextLine().trim();

        var extractor = switch (f) {
            case "1" -> (java.util.function.ToIntFunction<Car>) Car::getPower;
            case "2" -> (java.util.function.ToIntFunction<Car>) Car::getYear;
            default -> throw new RuntimeException("Неверный выбор поля");
        };

        int target = readInt("Введите значение N: ");
        int threads = readInt("Введите количество потоков: ");

        int count = OccurrenceCounter.countOccurrencesParallel(cars, extractor, target, threads);
        System.out.println("Количество вхождений N = " + target + ": " + count);
    }
}
