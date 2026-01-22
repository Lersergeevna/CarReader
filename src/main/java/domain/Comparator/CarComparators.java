package domain.Comparator;

import java.util.Comparator;
import domain.Car;

/**
 * Класс с набором всех вариантов сортировок по полям
 */
public final class CarComparators {
    private CarComparators() {}

    /**
     * Сортировка по годам
     * @param ascending Возврастание по году
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byYear(boolean ascending){
        return ascending
            ? new CarYearComparator()
            : new CarYearComparator().reversed();
    }
    /**
     * Последовательная сортировка по мощности
     * @param ascending Возврастание по мощности
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byPower(boolean ascending){
        return ascending
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed();
    }
    /**
     * Последовательная сортировка по модели
     * @param ascending Возврастание по модели
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byModel(boolean ascending){
        return ascending
            ? new CarModelComparator()
            : new CarModelComparator().reversed();
    }


    /**
     * Последовательная сортировка по годам и модели
     * @param ascending1 Возврастание по году
     * @param ascending2 Возврастание по модели
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byYearAndModel(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarYearComparator()
            : new CarYearComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarModelComparator()
            : new CarModelComparator().reversed());
    }
    /**
     * Последовательная сортировка по годам и мощности
     * @param ascending1 Возврастание по году
     * @param ascending2 Возврастание по мощности
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byYearAndPower(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarYearComparator()
            : new CarYearComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed());
    }

    /**
     * Последовательная сортировка по модели и годам 
     * @param ascending1 Возврастание по модели
     * @param ascending2 Возврастание по году
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byModelAndYear(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarModelComparator()
            : new CarModelComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarYearComparator()
            : new CarYearComparator().reversed());
    }
    /**
     * Последовательная сортировка по модели и мощности
     * @param ascending1 Возврастание по модели
     * @param ascending2 Возврастание по мощности
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byModelAndPower(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarModelComparator()
            : new CarModelComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed());
    }

    /**
     * Последовательная сортировка по мощности и годам
     * @param ascending1 Возврастание по мощности
     * @param ascending2 Возврастание по году
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byPowerAndYear(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarYearComparator()
            : new CarYearComparator().reversed());
    }
    /**
     * Последовательная сортировка по мощности и модели
     * @param ascending1 Возврастание по мощности
     * @param ascending2 Возврастание по модели
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byPowerAndModel(boolean ascending1, boolean ascending2){
        Comparator<Car> t = ascending1
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed();
        return t.thenComparing(ascending2
            ? new CarModelComparator()
            : new CarModelComparator().reversed());
    }
    
    /**
     * Последовательная сортировка по годам, модели и мощности
     * @param ascending1 Возврастание по году
     * @param ascending2 Возврастание по модели
     * @param ascending3 Возврастание по мощности
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byYearAndModelAndPower(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byYearAndModel(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed());
    }
    /**
     * Последовательная сортировка по годам, мощности и модели
     * @param ascending1 Возврастание по году
     * @param ascending2 Возврастание по мощности
     * @param ascending3 Возврастание по модели
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byYearAndPowerAndModel(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byYearAndPower(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarModelComparator()
            : new CarModelComparator().reversed());
    }

    /**
     * Последовательная сортировка по модели, годам и мощности
     * @param ascending1 Возврастание по модели
     * @param ascending2 Возврастание по году
     * @param ascending3 Возврастание по мощности
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byModelAndYearAndPower(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byModelAndYear(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarPowerComparator()
            : new CarPowerComparator().reversed());
    }
    /**
     * Последовательная сортировка по модели, мощности и годам
     * @param ascending1 Возврастание по модели
     * @param ascending2 Возврастание по мощности
     * @param ascending3 Возврастание по году
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byModelAndPowerAndYear(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byModelAndPower(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarYearComparator()
            : new CarYearComparator().reversed());
    }

    /**
     * Последовательная сортировка по мощности, модели и годам
     * @param ascending1 Возврастание по мощности
     * @param ascending2 Возврастание по году
     * @param ascending3 Возврастание по модели
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byPowerAndYearAndModel(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byPowerAndYear(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarModelComparator()
            : new CarModelComparator().reversed());
    }
    /**
     * Последовательная сортировка по мощности, модели и годам
     * @param ascending1 Возврастание по мощности
     * @param ascending2 Возврастание по модели
     * @param ascending3 Возврастание по году
     * @return Отсортрованный массив автомобилей
     */
    public static Comparator<Car> byPowerAndModelAndYear(boolean ascending1, boolean ascending2, boolean ascending3){
        Comparator<Car> t = byPowerAndModel(ascending1, ascending2);
        return t.thenComparing(ascending3
            ? new CarYearComparator()
            : new CarYearComparator().reversed());
    }
}
