package domain.Comparator;

import java.util.Comparator;
import domain.Car;
import domain.Comparator.CarSortField.*;

/**
 * Класс компаратора из всех возможных вариантов сортировок по полям
 */
public final class CarComparators {
	public CarComparators() {}
	
	/**
	* Текущий комбинированный компаратор
	*/
	private Comparator<Car> comparator;

	/**
	* Добавление базового компаратора в комбинированный
	* @param field Enum-переменная  поля
	* @param asc по возврастанию или убыванию
	* @return возвращает комбинированный компаратор в дополнении с новым
	*/
	public CarComparators then(CarSortField field, boolean asc){
		if (comparator == null) {
			comparator = base(field, asc)
		} else {
			comparator = comparator.thenComparing(base(field, asc));
		}
		return this;
	}
	
	/**
	* Создает комбинированный компаратор из цепочки startWith->then-> ... ->then
	* @return Комбинированный компаратор
	*/
	public Comparator<Car> build(){
		return comparator;
	}
	
	/**
	* Вызов базового компаратора по полю
	* @param field Enum-переменная  поля
	* @param asc по возврастанию или убыванию
	* @return возвращает базовый компаратор
	*/
	private static Comparator<Car> base(CarSortField field, boolean asc){
		return switch(field){
			case YEAR -> asc
				? new CarYearComparator()
				: new CarYearComparator().reversed();
			case MODEL -> asc
				? new CarModelComparator()
				: new CarModelComparator().reversed();
			case POWER -> asc
				? new CarPowerComparator()
				: new CarPowerComparator().reversed();
		}
	}
}
