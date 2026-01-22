public final class Validators {
    private Validators() {}

    public static void validatePower(int power) {
        if (power <= 0) {
            throw new ValidationException("Ошибка: Мощность должна быть положительной.");
        }
    }

    public static void validateModel(String model) {
        if (model == null || model.strip().isEmpty()) {
            throw new ValidationException("Ошибка: Модель не может быть пустой.");
        }
    }

    public static void validateYear(int year) {
        // Актуально для 2026 года
        if (year <= 1886 || year > 2026) {
            throw new ValidationException("Ошибка: Год должен быть в диапазоне от 1886 до 2026.");
        }
    }
}










