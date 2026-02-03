package sorting.special;

import collection.MyArrayList;
import sorting.algorithms.MergeSortStrategy;


import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class EvenOnlySorter {
    private EvenOnlySorter() {}

    public static <T> void sortEvenOnly(
            MyArrayList<T> data,
            ToIntFunction<T> numericField,
            Comparator<T> comparator
    ) {
        Objects.requireNonNull(data, "Коллекция не должна быть null");
        Objects.requireNonNull(numericField, "Функция извлечения числового поля не должна быть null");
        Objects.requireNonNull(comparator, "Компаратор не должен быть null");

        int n = data.size();
        if (n < 2) return;

        MyArrayList<T> evens = new MyArrayList<>();

        for (int i = 0; i < n; i++) {
            T item = data.get(i);
            if (numericField.applyAsInt(item) % 2 == 0) {
                evens.add(item);
            }
        }

        if (evens.size() < 2) return;

        // сортируем чётные
        new MergeSortStrategy<T>().sort(evens, comparator);

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

