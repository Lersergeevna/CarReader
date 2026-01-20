// Сортировка вставками
import java.util.Comparator;
public class InsertionSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;
            int cellJ = list.get(j);

            while (j >= 0 && comparator.compare(cellJ, key) > 0) {
                list.set(j + 1, cellJ);
                j--;
            }
            list.set(j + 1, key);
            
        }
    }
}