// Сортировка выбором
public class SelectionSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, java.util.Comparator<T> comparator) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(j), list.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            T temp = list.get(minIdx);
            list.set(minIdx, list.get(i));
            list.set(i, temp);
        }
    }
}
