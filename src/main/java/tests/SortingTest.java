package tests;

import collection.MyArrayList;
import domain.Car;
import domain.Comparator.CarComparators;
import sorting.SortContext;
import sorting.algorithms.InsertionSortStrategy;
import sorting.algorithms.SelectionSortStrategy;
import sorting.algorithms.MergeSortStrategy;
import sorting.algorithms.QuickSortStrategy;

import java.util.Comparator;

public final class SortingTest {
    public static void run() {
        testOneAlgorithm(new InsertionSortStrategy<>(), "Сортировка вставками");
        testOneAlgorithm(new SelectionSortStrategy<>(), "Сортировка выбором");
        testOneAlgorithm(new MergeSortStrategy<>(), "Сортировка слиянием");
        testOneAlgorithm(new QuickSortStrategy<>(), "Быстрая сортировка");

        // Отдельно проверим, что SortContext реально применяет стратегию
        SortContext<Car> ctx = new SortContext<>();
        ctx.setStrategy(new MergeSortStrategy<>());
        MyArrayList<Car> cars = sample();
        ctx.sort(cars, CarComparators.byAllFields());
        assertSortedByAllFields(cars, "SortContext: сортировка работает");
    }

    private static void testOneAlgorithm(sorting.SortStrategy<Car> strategy, String title) {
        MyArrayList<Car> cars = sample();
        Comparator<Car> cmp = CarComparators.byAllFields();

        strategy.sort(cars, cmp);
        assertSortedByAllFields(cars, title);
    }

    private static MyArrayList<Car> sample() {
        MyArrayList<Car> cars = new MyArrayList<>();
        // специально вразнобой
        cars.add(Car.builder().model("bmw").year(2020).power(200).build());
        cars.add(Car.builder().model("Audi").year(2019).power(150).build());
        cars.add(Car.builder().model("audi").year(2018).power(180).build());
        cars.add(Car.builder().model("BMW").year(2018).power(120).build());
        cars.add(Car.builder().model("Tesla").year(2022).power(300).build());
        return cars;
    }

    private static void assertSortedByAllFields(MyArrayList<Car> cars, String title) {
        Comparator<Car> cmp = CarComparators.byAllFields();
        for (int i = 1; i < cars.size(); i++) {
            Car prev = cars.get(i - 1);
            Car cur = cars.get(i);
            if (cmp.compare(prev, cur) > 0) {
                throw new AssertionError("Провал: " + title + " — коллекция не отсортирована на позициях "
                        + (i - 1) + " и " + i + ": " + prev + " > " + cur);
            }
        }
    }
}