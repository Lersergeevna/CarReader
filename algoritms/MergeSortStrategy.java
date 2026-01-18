// Сортировка слияниемы
public class MergeSortStrategy<T> implements SortingStrategy<T> {
    @Override
    public void sort(MyArrayList<T> list, java.util.Comparator<T> comparator) {
        if (list == null || list.size() < 2) {
            return;
        }
        performMergeSort(list, 0, list.size() - 1, comparator);
    }

    private void performMergeSort(MyArrayList<T> list, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            performMargeSort(list, left, mid, comparator);
            performMargeSort(list, mid + 1, right, comparator);

            merge(list, left, mid, right, comparator);
        }
    }

    @SuppressWarnings("unchecked")
    private void merge(MyArrayList<T> list, int left, int mid, int right, Comparator<T> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Object[] leftArray = new Object[n1];
        Object[] rightArray = new Object[n2];

        for (int i = 0;  i < n1; ++i) {
            leftArray[i] = list.get(left + i);
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = list.get(mid + 1 + j);
        }

        int i = 0, j = 0;
        int k = left;

        while(i < n1 && j < n2) {
            if (comparator.compare((T) leftArray[i], (T) rightArray[j]) <= 0) {
                list.set(k, (T) leftArray[i]);
                i++;
            } else {
                list.set(k, (T) rightArray[j]);
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, (T) leftArray[i]);
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, (T) rightArray[j]);
            j++;
            k++;
        }
    }
}
