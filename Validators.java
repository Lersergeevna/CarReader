public final class Validators {

    public static void validateModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new ValidationException("Модель не может быть пустой или состоять только из пробелов");
        }
    }

    public static void validateYear(int year) {
        if (year <= 1886) {
            throw new ValidationException("Год выпуска должен быть больше 1886");
        }
        if (year >= 2026) {
            throw new ValidationException("Год выпуска должен быть меньше 2026");
        }
    }

    public static void validatePower(double power) {
        if (power <= 0) {
            throw new ValidationException("Мощность должна быть положительной (power > 0)");
        }
    }

    private Validators() {
        throw new UnsupportedOperationException("Класс Validators не может быть инстанцирован");
    }
}

