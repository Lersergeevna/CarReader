/**
 * Утилитный класс для преобразования данных между форматом CSV и объектами Java.
 */
public final class Parser {
    private Parser() {}

    /**
     * Преобразует строку CSV (модель,мощность,год) в объект Car.
     */
    public static Car fromCsv(String csvLine) {
        if (csvLine == null || csvLine.isBlank()) {
            throw new ValidationException("Ошибка: Строка данных пуста.");
        }
        
        // Разделяем строку по запятой
        String[] parts = csvLine.split(",");
        if (parts.length != 3) {
            throw new ValidationException("Ошибка: Неверный формат CSV. Нужно: модель,мощность,год");
        }

        try {
            // Используем Builder для создания объекта с автоматической валидацией
            return new Car.Builder()
                    .setModel(parts[0].trim())
                    .setPower(Integer.parseInt(parts[1].trim()))
                    .setYear(Integer.parseInt(parts[2].trim()))
                    .build();
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка: Мощность и год должны быть целыми числами.");
        }
    }

    /**
     * Преобразует объект Car обратно в строку формата CSV.
     */
    public static String toCsv(Car car) {
        if (car == null) return "";
        return String.format("%s,%d,%d", car.getModel(), car.getPower(), car.getYear());
    }
}



