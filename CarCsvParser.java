package io;

import domain.Car;
import validation.ValidationException;

/**
 * Чтение/запись автомобилей в CSV-файл.
 * Формат строки: model, year, power
 */

public final class CarCsvParser {
    private CarCsvParser() {}

    public static Car parseLine(String csvLine) {
        if (csvLine == null) {
            throw new ValidationException("Строка CSV равна null");
        }

        String line = csvLine.strip();
        if (line.isEmpty()) {
            throw new ValidationException("Пустая строка CSV");
        }

        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new ValidationException(
                    "Ошибка в строке [" + csvLine + "]: ожидалось 3 поля ( модель, год, мощность)"
            );
        }

        try {
            String model = parts[0].strip();
            int year = Integer.parseInt(parts[1].strip());
            int power = Integer.parseInt(parts[2].strip());

            return Car.builder()
                    .model(model)
                    .year(year)
                    .power(power)
                    .build();

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Ошибка в строке [" + csvLine + "]: мощность и год должны быть целыми числами"
            );
        } catch (ValidationException e) {
            throw e;
        }
    }

    public static String toLine(Car car) {
        if (car == null) {
            throw new ValidationException("Нельзя преобразовать null в CSV");
        }
        return  car.getModel() + "," + car.getYear() + "," + car.getPower();
    }
}







