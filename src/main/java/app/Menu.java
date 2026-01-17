package app;

public final class Menu {
    private Menu() {}

    public static void print() {
        System.out.println("\n=== MENU ===");
        System.out.println("1) Create/Load cars");
        System.out.println("2) Print cars");
        System.out.println("3) Choose sorting algorithm");
        System.out.println("4) Sort by all 3 fields");
        System.out.println("5) Special sort: sort EVEN by power (odd stay)");
        System.out.println("6) Append current cars to file");
        System.out.println("7) Count occurrences (multi-thread) by power==N");
        System.out.println("0) Exit");
        System.out.print("Select: ");
    }
}

