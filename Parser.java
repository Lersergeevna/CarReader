public final class Parser {
    private Parser() {}

    /**
     * Парсинг строки в формате: мощность,модель,год
     */
    public static Car fromCsv(String csvLine) {
        if (csvLine == null || csvLine.isBlank()) {
            throw new ValidationException("Ошибка: Передана пустая строка.");
        }

        String[] parts = csvLine.split(",");
        if (parts.length != 3) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Ожидалось 3 параметра (мощность, модель, год).");
        }

        try {
            // Читаем в новом порядке: Power(0), Model(1), Year(2)
            int power = Integer.parseInt(parts[0].strip());
            String model = parts[1].strip();
            int year = Integer.parseInt(parts[2].strip());

            return new Car.Builder()
                    .setPower(power)
                    .setModel(model)
                    .setYear(year)
                    .build();
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Мощность или год не являются числами.");
        } catch (ValidationException e) {
            // Перебрасываем ошибку валидации с указанием проблемной строки
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: " + e.getMessage());
        }
    }

    public static String toCsv(Car car) {
        if (car == null) {
            throw new ValidationException("Ошибка: Попытка преобразовать null-объект. Операция отменена во избежание записи пустых строк.");
        }
        return String.format("%d,%s,%d", car.getPower(), car.getModel(), car.getYear());
    }
}





