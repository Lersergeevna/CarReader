public final class Validators {
    private Validators() {}

    // 1. Валидация мощности
    public static void validatePower(String powerStr, String csvLine) {
        try {
            int power = Integer.parseInt(powerStr);
            if (power <= 0) {
                throw new ValidationException("Ошибка в строке [" + csvLine + "]: Мощность должна быть положительной.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Мощность не является целым числом.");
        }
    }

    // 2. Валидация года
    public static void validateYear(String yearStr, String csvLine) {
        try {
            int year = Integer.parseInt(yearStr);
            // Проверка диапазона актуальна для 2026 года
            if (year <= 1886 || year > 2026) {
                throw new ValidationException("Ошибка в строке [" + csvLine + "]: Год должен быть в диапазоне от 1886 до 2026.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Год не является целым числом.");
        }
    }

    // 3. Валидация модели
    public static void validateModel(String model, String csvLine) {
        if (model == null || model.strip().isEmpty()) {
            throw new ValidationException("Ошибка в строке [" + csvLine + "]: Модель не может быть пустой.");
        }
    }
}








