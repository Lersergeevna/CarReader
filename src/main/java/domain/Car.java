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
        return "Автомобиль{модель = " + model + ", год = " + year + ", мощность = " + power + "}";
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
        return Objects.hash(model, year, power);
    }

    public static Builder builder() {
        return new Builder();
    }

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
