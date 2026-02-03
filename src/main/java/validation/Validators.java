package validation;

import java.time.Year;

public final class Validators  {
    private Validators() {}

    public static void validateModel(String model) {
        if (model == null || model.strip().isEmpty()) {
            throw new ValidationException("Модель не может быть пустой");
        }
    }

    public static void validateYear(int year) {
        int maxYear = Year.now().getValue() + 1;
        if (year < 1886 || year > maxYear) {
            throw new ValidationException(
                    "Год должен быть в диапазоне от 1886 до " + maxYear
            );
        }
    }

    public static void validatePower(int power) {
        if (power <= 0) {
            throw new ValidationException("Мощность должна быть положительной");
        }
    }
}











