package tests;

public final class TestRunner {
    public static void main(String[] args) {
        runTest("CarBuilderTest", CarBuilderTest::run);
        runTest("SortingTest", SortingTest::run);
        runTest("EvenOnlySorterTest", EvenOnlySorterTest::run);
        runTest("OccurrenceCounterTest", OccurrenceCounterTest::run);

        System.out.println();
        System.out.println("Все тесты пройдены успешно.");
    }

    private static void runTest(String name, Runnable test) {
        try {
            test.run();
            System.out.println("[OK] " + name);
        } catch (Throwable t) {
            System.out.println("[FAIL] " + name + " — " + t.getClass().getSimpleName() + ": " + t.getMessage());
            throw t; // чтобы сборка/запуск падали явно
        }
    }
}