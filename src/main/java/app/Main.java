package app;

import collection.MyArrayList;
import collection.MyArrayListCollector;
import concurrency.OccurrenceCounter;
import domain.Car;
import sorting.Comparator.CarComparators;
import sorting.Comparator.CarSortField;
import io.FileCarRepository;
import sorting.SortContext;
import sorting.SortType;
import sorting.algorithms.InsertionSortStrategy;
import sorting.algorithms.MergeSortStrategy;
import sorting.algorithms.QuickSortStrategy;
import sorting.algorithms.SelectionSortStrategy;
import sorting.special.EvenOnlySorter;

import java.io.IOException;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public final class Main {
    private static final Scanner SC = new Scanner(System.in);

    private static Comparator<Car> currentComparator = CarComparators.byAllFields();

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
                        System.out.println("Готово. Загружено автомобилей: " + cars.size());
                    }
                    case "2" -> printCars(cars);
                    case "3" -> chooseSortStrategy(sortContext);
                    case "4" -> {
                        currentComparator = chooseComparatorOrder(); // ручной порядок полей
                        sortContext.sort(cars, currentComparator);
                        System.out.println("Отсортировано. Алгоритм: " + sortContext.currentName());
                    }
                    case "5" -> {
                        ComparatorChoice choice = chooseNumericFieldForEvenOnly();
                        EvenOnlySorter.sortEvenOnly(cars, choice.extractor(), choice.comparator());
                        System.out.println("Готово. Отсортированы только элементы с чётным значением выбранного поля.");
                    }
                    case "6" -> appendToFile(cars);
                    case "7" -> countOccurrences(cars);
                    case "0" -> running = false;
                    default -> System.out.println("Неизвестная команда.");
                }
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("До свидания!");
    }

    // ======= Создание / загрузка =======

    private static MyArrayList<Car> createOrLoadCars() {
        System.out.println();
        System.out.println("Выберите способ заполнения:");
        System.out.println("1) Из файла");
        System.out.println("2) Случайно (рандом)");
        System.out.println("3) Вручную");
        System.out.print("Ваш выбор: ");
        String m = SC.nextLine().trim();

        return switch (m) {
            case "1" -> {
                System.out.print("Введите путь к файлу: ");
                String path = SC.nextLine().trim();
                try {
                    yield FileCarRepository.readCars(path);
                } catch (IOException e) {
                    throw new RuntimeException("Не удалось прочитать файл: " + e.getMessage(), e);
                }
            }
            case "2" -> generateRandomCars(askLength());
            case "3" -> readManualCars(askLength());
            default -> throw new RuntimeException("Неверный выбор способа заполнения.");
        };
    }

    private static int askLength() {
        System.out.print("Введите длину (>0): ");
        String s = SC.nextLine().trim();
        int n;
        try {
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Длина должна быть целым числом.");
        }
        if (n <= 0) throw new RuntimeException("Длина должна быть > 0.");
        return n;
    }

    private static MyArrayList<Car> generateRandomCars(int n) {
        Random rnd = new Random();
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    String model = "Модель-" + (char) ('A' + rnd.nextInt(26)) + rnd.nextInt(100);
                    int year = 1990 + rnd.nextInt(YearNowPlusOne() - 1990 + 1);
                    int power = 50 + rnd.nextInt(451);
                    return Car.builder().model(model).year(year).power(power).build();
                })
                .collect(MyArrayListCollector.toMyArrayList());
    }

    private static int YearNowPlusOne() {
        return java.time.Year.now().getValue() + 1;
    }

    private static MyArrayList<Car> readManualCars(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    System.out.println();
                    System.out.println("Автомобиль #" + (i + 1));
                    String model = readString("Модель: ");
                    int year = readInt("Год: ");
                    int power = readInt("Мощность: ");

                    return Car.builder().model(model).year(year).power(power).build();
                })
                .collect(MyArrayListCollector.toMyArrayList());
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String s = SC.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Ожидалось целое число.");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    private static void printCars(MyArrayList<Car> cars) {
        if (cars == null || cars.size() == 0) {
            System.out.println("(список пуст)");
            return;
        }
        for (Car c : cars) System.out.println(c);
    }

    // ======= Выбор алгоритма =======

    private static void chooseSortStrategy(SortContext<Car> ctx) {
        System.out.println();
        System.out.println("Текущий алгоритм: " + ctx.currentName());
        System.out.println("Выберите алгоритм сортировки:");
        System.out.println("1) Вставками");
        System.out.println("2) Выбором");
        System.out.println("3) Слиянием");
        System.out.println("4) Быстрая");
        System.out.print("Ваш выбор: ");

        String s = SC.nextLine().trim();
        SortType type = switch (s) {
            case "1" -> SortType.INSERTION;
            case "2" -> SortType.SELECTION;
            case "3" -> SortType.MERGE;
            case "4" -> SortType.QUICK;
            default -> throw new RuntimeException("Неверный выбор алгоритма.");
        };

        switch (type) {
            case INSERTION -> ctx.setStrategy(new InsertionSortStrategy<>());
            case SELECTION -> ctx.setStrategy(new SelectionSortStrategy<>());
            case MERGE -> ctx.setStrategy(new MergeSortStrategy<>());
            case QUICK -> ctx.setStrategy(new QuickSortStrategy<>());
        }

        System.out.println("Выбран алгоритм: " + ctx.currentName());
    }

    // ======= Ручной выбор порядка полей =======

    private static Comparator<Car> chooseComparatorOrder() {
        System.out.println();
        System.out.println("Настройка порядка полей сортировки.");
        System.out.println("Добавляйте поля по одному. Когда закончите — введите 0.");
        System.out.println("1) MODEL (модель)");
        System.out.println("2) YEAR (год)");
        System.out.println("3) POWER (мощность)");

        CarComparators.Builder builder = new CarComparators.Builder();

        while (true) {
            System.out.print("Добавить поле (0-3): ");
            String s = SC.nextLine().trim();
            if ("0".equals(s)) break;

            CarSortField field = switch (s) {
                case "1" -> CarSortField.MODEL;
                case "2" -> CarSortField.YEAR;
                case "3" -> CarSortField.POWER;
                default -> throw new RuntimeException("Неверный выбор поля.");
            };

            boolean asc = chooseAsc();
            builder.then(field, asc);
            System.out.println("Поле добавлено: " + field + (asc ? " (по возрастанию)" : " (по убыванию)"));
        }

        return builder.build();
    }

    private static boolean chooseAsc() {
        System.out.print("Порядок: 1) по возрастанию  2) по убыванию: ");
        String s = SC.nextLine().trim();
        return switch (s) {
            case "1" -> true;
            case "2" -> false;
            default -> throw new RuntimeException("Неверный выбор порядка.");
        };
    }

    // ======= EvenOnlySorter (выбор числового поля) =======

    private static ComparatorChoice chooseNumericFieldForEvenOnly() {
        System.out.println();
        System.out.println("Выберите числовое поле для проверки чётности и сортировки:");
        System.out.println("1) POWER (мощность)");
        System.out.println("2) YEAR (год)");
        System.out.print("Ваш выбор: ");
        String s = SC.nextLine().trim();

        CarSortField field = switch (s) {
            case "1" -> CarSortField.POWER;
            case "2" -> CarSortField.YEAR;
            default -> throw new RuntimeException("Неверный выбор поля.");
        };

        Comparator<Car> cmp = new CarComparators.Builder()
                .then(field, true)
                .build();

        ToIntFunction<Car> extractor =
                (field == CarSortField.POWER) ? Car::getPower : Car::getYear;

        return new ComparatorChoice(extractor, cmp);
    }

    private record ComparatorChoice(ToIntFunction<Car> extractor, Comparator<Car> comparator) {}

    // ======= Запись в файл =======

    private static void appendToFile(MyArrayList<Car> cars) {
        System.out.print("Введите путь к выходному файлу: ");
        String path = SC.nextLine().trim();
        try {
            FileCarRepository.appendCars(path, cars);
            System.out.println("Готово. Записано строк: " + (cars == null ? 0 : cars.size()));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл: " + e.getMessage(), e);
        }
    }

    // ======= ДОП. ЗАДАНИЕ 4: многопоточность с выбором поля =======

    private static void countOccurrences(MyArrayList<Car> cars) {
        if (cars == null || cars.size() == 0) {
            System.out.println("Список пуст — считать нечего.");
            return;
        }

        System.out.println();
        System.out.println("Выберите поле для подсчёта вхождений N:");
        System.out.println("1) POWER (мощность)");
        System.out.println("2) YEAR (год)");
        System.out.print("Ваш выбор: ");
        String s = SC.nextLine().trim();

        ToIntFunction<Car> extractor = switch (s) {
            case "1" -> Car::getPower;
            case "2" -> Car::getYear;
            default -> throw new RuntimeException("Неверный выбор поля.");
        };

        int target = readInt("Введите N: ");
        int threads = readInt("Количество потоков: ");

        int count = OccurrenceCounter.countOccurrencesParallel(cars, extractor, target, threads);
        System.out.println("Количество вхождений значения " + target + ": " + count);
    }
}
