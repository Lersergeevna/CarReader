package sorting.algorithms;

import collection.MyArrayList;
import sorting.SortStrategy;

import java.util.Comparator;

public class InsertionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        if (list == null) throw new IllegalArgumentException("Список не заполнен данными");
        if (comparator == null) throw new IllegalArgumentException("Компаратор не получил данные из списка");
        if (list.size() < 2) return;

        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0) {
                T current = list.get(j);
                if (comparator.compare(current, key) <= 0) break;
                list.set(j + 1, current);
                j--;
            }
            list.set(j + 1, key);
        }
    }

    @Override
    public String name() {
        return "INSERTION";
    }
}