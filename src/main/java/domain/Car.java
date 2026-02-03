package domain;

import validation.Validators;

import java.util.Locale;
import java.util.Objects;

/**
 * Естественный порядок автомобилей:
 * 1) модель (без учёта регистра)
 * 2) год выпуска
 * 3) мощность
 */

public final class Car implements Comparable<Car> {
    private final String model;
    private final int year;
    private final int power;

    private Car(Builder b) {
        this.model = b.model;
        this.year = b.year;
        this.power = b.power;
    }

    public String getModel() { return model; }
    public int getYear() { return year; }
    public int getPower() { return power; }

    @Override
    public int compareTo(Car other) {
        Objects.requireNonNull(other, "Аргумент не должен быть равен null");

        int c1 = this.model.compareToIgnoreCase(other.model);
        if (c1 != 0) return c1;

        int c2 = Integer.compare(this.year, other.year);
        if (c2 != 0) return c2;

        return Integer.compare(this.power, other.power);
    }

    @Override
    public String toString() {
        return "Автомобиль{модель = " + model + ", год = " + year + ", мощность = " + power + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return year == car.year
                && power == car.power
                && model.equalsIgnoreCase(car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model.toLowerCase(Locale.ROOT), year, power);
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String model;
        private int year;
        private int power;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Car build() {
            Validators.validateModel(model);
            Validators.validateYear(year);
            Validators.validatePower(power);
            return new Car(this);
        }
    }
}
