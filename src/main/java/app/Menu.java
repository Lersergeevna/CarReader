package app;

public final class Menu {
    private Menu() {}

    public static void print() {
        System.out.println("\n=== MENU ===");
        System.out.println("1) Создать/Загрузить машины");
        System.out.println("2) Напечатать машины");
        System.out.println("3) Выбрать алгоритм сортировки");
        System.out.println("4) Сортировать по всем 3 полям");
        System.out.println("5) Специальная сортировка: сортировка по четным числам в степени (нечетные числа остаются)");
        System.out.println("6) Добавить текущие автомобили в файл");
        System.out.println("7) Подсчет количества вхождений (многопоточный режим) по мощности");
        System.out.println("0) Выход");
        System.out.print("Выбрать: ");
    }

