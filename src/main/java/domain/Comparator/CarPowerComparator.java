package domain.Comparator;

import java.util.Comparator;
import domain.Car;

/**
 * Сравнение автомобилей по мощности двигателя
 */
public class CarPowerComparator implements Comparator<Car> {
    public int compare(Car a, Car b) {
        return a.getPower() - b.getPower();
    }    
}