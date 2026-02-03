package sorting.algorithms;

import collection.MyArrayList;
import sorting.SortStrategy;

import java.util.Comparator;
import java.util.Objects;
public class SelectionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "Список не заполнен данными");
        Objects.requireNonNull(comparator, "Компаратор не получил данные из списка");
        if (list.size() < 2) return;

        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            T minVal = list.get(i);

            for (int j = i + 1; j < n; j++) {
                T cur = list.get(j);
                if (comparator.compare(cur, minVal) < 0) {
                    minIdx = j;
                    minVal = cur;
                }
            }

            if (minIdx != i) {
                T tmp = list.get(i);
                list.set(i, minVal);
                list.set(minIdx, tmp);
            }
        }
    }

    @Override
    public String name() {
        return "Сортировка выбором";
    }
}