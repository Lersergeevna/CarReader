package domain.Comparator;

import java.util.Comparator;
import domain.Car;

/**
 * Сравнение автомобилей по модели
 */
public class CarModelComparator implements Comparator<Car> {
    public int compare(Car a, Car b) {
        return a.getModel().compareTo(b.getModel());
    }    
}