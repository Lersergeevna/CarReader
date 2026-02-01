package sorting;

import collection.MyArrayList;
import java.util.Comparator;

public final class SortContext<T> {
    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия сортировки не может быть null");
        }
        this.strategy = strategy;
    }

    public void sort(MyArrayList<T> data, Comparator<T> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия сортировки не выбрана");
        }
        if (data == null) {
            throw new IllegalArgumentException("Коллекция для сортировки не может быть null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null");
        }

        strategy.sort(data, comparator);
    }

    public String currentName() {
        return strategy == null
                ? "Стратегия не выбрана"
                : strategy.name();
    }
}
