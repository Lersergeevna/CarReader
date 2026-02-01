import validation.Validators;
import java.util.Objects;

public final class Car implements Comparable<Car> {
    private final int power;
    private final String model;
    private final int year;

    private Car(Builder b) {
        this.power = b.power;
        this.model = b.model;
        this.year = b.year;
    }

    public int getPower() { return power; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    @Override
    public int compareTo(Car other) {
        Objects.requireNonNull(other, "Аргумент не должен быть равен null");

        int c1 = this.model.compareToIgnoreCase(other.model);
        if (c1 != 0) return c1;

        int c2 = Integer.compare(this.power, other.power);
        if (c2 != 0) return c2;

        return Integer.compare(this.year, other.year);
    }

    @Override
    public String toString() {
        return "Автомобиль{модель = " + model + ", мощность = '" + power + "', год = " + year + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return power == car.power && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private int power;
        private String model;
        private int year;

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            Validators.validatePower(power);
            Validators.validateModel(model);
            Validators.validateYear(year);
            return new Car(this);
        }
    }
}
