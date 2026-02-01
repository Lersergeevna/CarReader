import domain.Car;

import validation.ValidationException;

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
                    "Ошибка в строке [" + csvLine + "]: ожидалось 3 поля (мощность, модель, год)"
            );
        }

        try {
            int power = Integer.parseInt(parts[0].strip());
            String model = parts[1].strip();
            int year = Integer.parseInt(parts[2].strip());

            // Валидация будет выполнена внутри Car.Builder через Validators
            return Car.builder()
                    .power(power)
                    .model(model)
                    .year(year)
                    .build();

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Ошибка в строке [" + csvLine + "]: мощность и год должны быть целыми числами"
            );
        } catch (ValidationException e) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: " + e.getMessage());
        }
    }

    public static String toLine(Car car) {
        if (car == null) {
            throw new ValidationException("Нельзя преобразовать null в CSV");
        }
        return car.getPower() + "," + car.getModel() + "," + car.getYear();
    }
}






