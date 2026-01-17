// Сортировка вставками
public class InsertionSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(T[] array, java.util.Comparator<T> comparator) {
        for (int i = 1; i < array.length; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}