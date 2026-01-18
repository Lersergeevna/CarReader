// Сортировка вставками
public class InsertionSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, java.util.Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
            
        }
    }
}