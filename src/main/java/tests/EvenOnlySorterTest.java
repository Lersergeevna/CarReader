package tests;

import collection.MyArrayList;
import domain.Car;
import domain.Comparator.CarComparators;
import sorting.special.EvenOnlySorter;

public final class EvenOnlySorterTest {
    public static void run() {
        MyArrayList<Car> cars = new MyArrayList<>();
        // Индексы 1 и 3 — нечётные мощности, должны остаться на месте
        cars.add(Car.builder().model("X").year(2000).power(4).build()); // even
        cars.add(Car.builder().model("A").year(2001).power(3).build()); // odd
        cars.add(Car.builder().model("B").year(2002).power(2).build()); // even
        cars.add(Car.builder().model("C").year(2003).power(5).build()); // odd

        EvenOnlySorter.sortEvenOnly(cars, Car::getPower, CarComparators.byPower());

        TestUtil.assertEquals(2, cars.get(0).getPower(), "EvenOnlySorter: чётные сортируются и перемещаются");
        TestUtil.assertEquals(3, cars.get(1).getPower(), "EvenOnlySorter: нечётный остаётся на исходной позиции");
        TestUtil.assertEquals(4, cars.get(2).getPower(), "EvenOnlySorter: чётные сортируются и перемещаются");
        TestUtil.assertEquals(5, cars.get(3).getPower(), "EvenOnlySorter: нечётный остаётся на исходной позиции");
    }
}