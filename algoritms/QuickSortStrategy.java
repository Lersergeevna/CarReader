// Быстрая сортировка
import java.util.Comparator;

public class QuickSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private int partition(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        int mid = low + (high - low) / 2;

        if (comparator.compare(list.get(mid), list.get(low)) < 0) swap(list, low, mid);
        if (comparator.compare(list.get(high), list.get(low)) < 0) swap(list, low, high);
        if (comparator.compare(list.get(high), list.get(mid)) < 0) swap(list, mid, high);
    
    
        swap(list, mid, high);

        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) < 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(MyArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
