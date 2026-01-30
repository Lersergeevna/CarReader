package app;

public final class Menu {
    private Menu() {}

    public static void print() {
        System.out.println();
        System.out.println("===== МЕНЮ =====");
        System.out.println("1) Создать/загрузить массив автомобилей");
        System.out.println("2) Показать текущий массив");
        System.out.println("3) Выбрать алгоритм сортировки (Strategy)");
        System.out.println("4) Отсортировать (базово по всем 3 полям)");
        System.out.println("5) Спец. сортировка: сортировать только чётные значения поля (нечётные остаются на местах)");
        System.out.println("6) Записать текущий массив в файл (добавлением)");
        System.out.println("7) Многопоточный подсчёт вхождений N по числовому полю");
        System.out.println("0) Выход");
        System.out.print("Выберите пункт: ");
    }
}
