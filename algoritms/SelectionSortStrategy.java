// Сортировка выбором
import java.util.Comparator;
public class SelectionSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        int listSize = list.size();
        for (int i = 0; i < listSize - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < listSize; j++) {
                if (comparator.compare(list.get(j), list.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                T temp = list.get(minIdx);
                list.set(minIdx, list.get(i));
                list.set(i, temp);
            }
            
        }
    }
}
