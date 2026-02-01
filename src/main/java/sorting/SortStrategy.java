package sorting;

import collection.MyArrayList;
import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(MyArrayList<T> data, Comparator<T> comparator);
    String name();
}

