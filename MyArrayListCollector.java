package collection;

import java.util.stream.Collector;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public final class MyArrayListCollector {
    private MyArrayListCollector() {}

    public static <T> Collector<T, MyArrayList<T>, MyArrayList<T>> toMyArrayList() {
        Supplier<MyArrayList<T>> supplier = MyArrayList::new;
        BiConsumer<MyArrayList<T>, T> accumulator = MyArrayList::add;

        BinaryOperator<MyArrayList<T>> combiner = (left, right) -> {
            for (T item : right) left.add(item);
            return left;
        };

        return Collector.of(supplier, accumulator, combiner);
    }
}