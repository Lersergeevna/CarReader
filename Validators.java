public final class Validators {
    private Validators() {}

    public static void validatePower(int power, String csvLine) {
        if (power <= 0) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Мощность должна быть положительной.");
        }
    }

    public static void validateModel(String model, String csvLine) {
        if (model == null || model.strip().isEmpty()) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Модель не может быть пустой.");
        }
    }

    public static void validateYear(int year, String csvLine) {
        // Проверка актуальна для 2026 года
        if (year <= 1886 || year > 2026) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Год должен быть в диапазоне от 1886 до 2026.");
        }
    }
    
    // Если ошибка возникает еще на этапе парсинга (строка не является числом)
    public static void validateIsNumeric(String value, String fieldName, String csvLine) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Поле '" + fieldName + "' не является числом.");
        }
    }
}






