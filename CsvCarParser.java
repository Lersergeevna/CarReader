public class CsvCarParser {

    public Car parse(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV-строка не может быть пустой");
        }

        String[] parts = csvLine.trim().split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Ожидалось 3 поля (model,year,power), получено: " + parts.length
            );
        }

        try {
            String model = parts[0];
            int year = Integer.parseInt(parts[1]);
            double power = Double.parseDouble(parts[2]);

            return new Car(model, year, power); // Валидация внутри конструктора
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка преобразования числа: " + e.getMessage(), e);
        }
    }

    public String toCsv(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Объект Car не может быть null");
        }

        return String.format(
                "%s,%d,%f",
                escapeField(car.getModel()),
                car.getYear(),
                car.getPower()
        );
    }

    private String escapeField(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}

