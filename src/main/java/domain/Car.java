package domain;

import java.util.Objects;

public final class Car implements Comparable<Car> {
    private final int power;      // Мощность
    private final String model;   // Модель
    private final int year;       // Год производства

    private Car(Builder b) {
        this.power = b.power;
        this.model = b.model;
        this.year = b.year;
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Car other) {
        if (other == null) return 1;
        int c1 = Integer.compare(this.power, other.power);
        if (c1 != 0) return c1;

        int c2 = this.model.compareToIgnoreCase(other.model);
        if (c2 != 0) return c2;

        return Integer.compare(this.year, other.year);
    }

    @Override
    public String toString() {
        return "Car{power=" + power + ", model='" + model + "', year=" + year + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return power == car.power && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public static Builder builder() {
        return new Builder();
    }

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
            if (power <= 0) throw new IllegalArgumentException("power must be > 0");
            if (model == null || model.trim().isEmpty()) throw new IllegalArgumentException("model is blank");
            if (year < 1886 || year > 2100) throw new IllegalArgumentException("year is out of range");
            return new Car(this);
        }
    }
}
