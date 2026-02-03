package tests;

import domain.Car;
import validation.ValidationException;

public final class CarBuilderTest {
    public static void run() {
        // 1) Успешная сборка
        Car c = Car.builder().model("BMW").year(2020).power(150).build();
        TestUtil.assertEquals("BMW", c.getModel(), "Builder: модель сохраняется");
        TestUtil.assertEquals(2020, c.getYear(), "Builder: год сохраняется");
        TestUtil.assertEquals(150, c.getPower(), "Builder: мощность сохраняется");

        // 2) Валидации (должны падать ValidationException)
        TestUtil.assertThrows(ValidationException.class,
                () -> Car.builder().model("   ").year(2020).power(150).build(),
                "Builder: пустая модель должна валидироваться");

        TestUtil.assertThrows(ValidationException.class,
                () -> Car.builder().model("X").year(1800).power(150).build(),
                "Builder: год ниже допустимого должен валидироваться");

        TestUtil.assertThrows(ValidationException.class,
                () -> Car.builder().model("X").year(2020).power(0).build(),
                "Builder: мощность <= 0 должна валидироваться");

        // 3) equals/compareTo согласованность (важно для сортировок)
        Car a = Car.builder().model("audi").year(2020).power(100).build();
        Car b = Car.builder().model("AUDI").year(2020).power(100).build();
        TestUtil.assertTrue(a.equals(b), "equals: модель сравнивается без регистра");
        TestUtil.assertEquals(0, a.compareTo(b), "compareTo должен быть согласован с equals (0 если equals)");
    }
}