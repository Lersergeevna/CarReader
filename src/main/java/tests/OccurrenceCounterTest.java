package tests;

import collection.MyArrayList;
import concurrency.OccurrenceCounter;
import domain.Car;

public final class OccurrenceCounterTest {
    public static void run() {
        MyArrayList<Car> cars = new MyArrayList<>();

        for (int i = 0; i < 1000; i++) {
            int power = (i % 10 == 0) ? 123 : 200; // 100 элементов с мощностью 123
            cars.add(Car.builder().model("M").year(2000).power(power).build());
        }

        int count = OccurrenceCounter.countOccurrencesParallel(cars, Car::getPower, 123, 4);
        TestUtil.assertEquals(100, count, "OccurrenceCounter: многопоточный подсчёт совпадает с ожиданием");

        int countSingle = OccurrenceCounter.countOccurrencesParallel(cars, Car::getPower, 123, 1);
        TestUtil.assertEquals(100, countSingle, "OccurrenceCounter: однопоточный режим тоже корректен");
    }
}