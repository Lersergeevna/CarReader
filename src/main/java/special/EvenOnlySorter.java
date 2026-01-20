package sorting.special;

import collection.MyArrayList;
import sorting.SortStrategy;
import sorting.algorithms.MergeSortStrategy;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public final class EvenOnlySorter {
    private EvenOnlySorter() {}

    public static <T> void sortEvenOnly(
            MyArrayList<T> data,
            ToIntFunction<T> numericField,
            Comparator<T> comparator
    ) {
        int n = data.size();
        MyArrayList<T> evens = new MyArrayList<>();

        for (int i = 0; i < n; i++) {
            T item = data.get(i);
            if (numericField.applyAsInt(item) % 2 == 0) {
                evens.add(item);
            }
        }

        // сортируем чётные O(m log m)
        SortStrategy<T> strategy = new MergeSortStrategy<>();
        strategy.sort(evens, comparator);

        // раскладываем назад
        int idx = 0;
        for (int i = 0; i < n; i++) {
            T item = data.get(i);
            if (numericField.applyAsInt(item) % 2 == 0) {
                data.set(i, evens.get(idx++));
            }
        }
    }
}

