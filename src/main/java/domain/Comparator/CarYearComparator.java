package domain.Comparator;

import java.util.Comparator;
import domain.Car;

/**
 * Сравнение автомобилей по году выпуска
 */
public class CarYearComparator implements Comparator<Car>{
    public int compare(Car a, Car b){
        return Integer.compare(a.getYear(), b.getYear());
    }    
}