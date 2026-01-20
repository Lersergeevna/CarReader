import java.util.Set;
import java.util.stream.Collector;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class MyArrayListCollector<T> implements Collector<T, MyArrayList<T>, MyArrayList<T>> {

    @Override
    public Supplier<MyArrayList<T>> supplier() {
        return MyArrayList::new;
    }

    @Override
    public BiConsumer<MyArrayList<T>, T> accumulator() {
        return MyArrayList::add;
    }

    @Override
    public BinaryOperator<MyArrayList<T>> combiner() {
        return (list1, list2) -> {
            for (T item : list2) {
                list1.add(item);
            }
            return list1;
        };
    }

    @Override
    public java.util.function.Function<MyArrayList<T>, MyArrayList<T>> finisher() {
        return list -> list;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    public static <T> MyArrayListCollector<T> toMyArrayList() {
        return new MyArrayListCollector<>();
    }
}