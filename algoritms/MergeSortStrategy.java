// Сортировка слияниемы
import java.util.Comparator;
public class MergeSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, Comparator<T> comparator) {
        if (list == null || list.size() < 2) {
            return;
        }
        Object[] temp = new Object[list.size()];
        performMergeSort(list, 0, list.size() - 1, comparator, temp);
    }

    private void performMergeSort(MyArrayList<T> list, int left, int right, Comparator<T> comparator, Object[] temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            performMergeSort(list, left, mid, comparator, temp);
            performMergeSort(list, mid + 1, right, comparator, temp);

            merge(list, left, mid, right, comparator, temp);
        }
    }

    @SuppressWarnings("unchecked")
    private void merge(MyArrayList<T> list, int left, int mid, int right, Comparator<T> comparator, Object[] temp) {
        for (int i = left; i <= right; i++) {
            temp[i] = list.get(i);
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (comparator.compare((T) temp[i], (T) temp[j]) <= 0) {
                list.set(k, (T) temp[i]);
                i++;
            }
            else {
                list.set(k, (T) temp[j]);
                j++;
            }
            k++;
        }

        while (i <= mid) {
            list.set(k, (T) temp[i]);
            i++;
            k++;
        }
    }
}
