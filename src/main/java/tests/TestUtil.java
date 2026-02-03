package tests;

public final class TestUtil {
    private TestUtil() {}

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Провал: " + message);
        }
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError("Провал: " + message + " (ожидали " + expected + ", получили " + actual + ")");
        }
    }

    public static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError("Провал: " + message + " (ожидали \"" + expected + "\", получили \"" + actual + "\")");
    }

    public static void assertThrows(Class<? extends Throwable> expected, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new AssertionError(
                    "Провал: " + message + " (ожидали " + expected.getSimpleName()
                            + ", получили " + t.getClass().getSimpleName() + ": " + t.getMessage() + ")",
                    t
            );
        }
        throw new AssertionError("Провал: " + message + " (ожидали исключение " + expected.getSimpleName() + ")");
    }
}