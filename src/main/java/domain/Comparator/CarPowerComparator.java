package domain.Comparator;

import java.util.Comparator;
import domain.Car;

/**
 * Сравнение автомобилей по мощности двигателя
 */
public class CarPowerComparator implements Comparator<Car> {
    @Override
    public int compare(Car a, Car b) {
        return Integer.compare(a.getPower(), b.getPower());
    }
}