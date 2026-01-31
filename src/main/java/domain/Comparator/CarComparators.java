package domain.Comparator;

import java.util.Comparator;
import java.util.Objects;

import domain.Car;
import domain.Comparator.*;

/**
 * Класс компаратора из всех возможных вариантов сортировок по полям
 */
public final class CarComparators {
	public CarComparators() {}
	
	/**
	 * Базовые компаратор по полю модели
	 */ 
    public static Comparator<Car> byModel() {
        return new CarModelComparator();
    }
	/**
	 * Базовые компаратор по полю мощности
	 */ 
    public static Comparator<Car> byPower() {
        return new CarPowerComparator();
    }
	/**
	 * Базовые компаратор по полю года производства
	 */ 
    public static Comparator<Car> byYear()  {
        return new CarYearComparator();
    }

    /**
	 * Базовая сортировка по 3 полям
	 */
    public static Comparator<Car> byAllFields() {
        return Car::compareTo;
    }

	/**
	 * Строитель комбинированного компаратора
	 */
	public static final class Builder {
		private Comparator<Car> comparator;

		/**
		* Добавление базового компаратора в комбинированный
		* @param field Enum-переменная  поля
		* @param asc по возврастанию или убыванию
		* @return возвращает комбинированный компаратор в дополнении с новым
		*/
		public Builder then(CarSortField field, boolean asc){
			Objects.requireNonNull(field, "Поле сортировки не должно быть пустым");

			comparator = comparator == null 
				? comparator = base(field, asc)
				: comparator.thenComparing(base(field, asc));
			return this;
		}	

		/**
		* Создает комбинированный компаратор из цепочки startWith->then-> ... ->then
		* @return Комбинированный компаратор
		*/
		public Comparator<Car> build(){
			if (comparator == null) {
				throw new IllegalStateException("Компаратор не собран: добавьте хотя бы одно поле");
			}
			return comparator;
		}
		
		/**
		* Вызов базового компаратора по полю
		* @param field Enum-переменная  поля
		* @param asc по возврастанию или убыванию
		* @return возвращает базовый компаратор
		*/
		private static Comparator<Car> base(CarSortField field, boolean asc){
			Comparator<Car> c = switch (field) {
					case MODEL -> new CarModelComparator();
					case POWER -> new CarPowerComparator();
					case YEAR -> new CarYearComparator();
				};
				return asc ? c : c.reversed();
		}
	}
}