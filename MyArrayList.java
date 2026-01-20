import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collector;

public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public static <T> Collector<T, ?, MyArrayList<T>> toMyArrayList() {
        return Collector.of(MyArrayList::new, MyArrayList::add, (l, r) -> {
            r.forEach(l::add);
            return l;
        });
    }

    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    public void add(T item) {
        ensureCapacity();
        elements[size++] = item;
    }
    public T remove(int index) {
        rangeCheck(index);
        T removed = elementAt(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
    }
    public T get(int index) {
        rangeCheck(index);
        return elementAt(index);
    }

    public T set(int index, T item) {
        rangeCheck(index);
        T old =  elementAt(index);
        elements[index] = item;
        return old;
      }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length == 0 ? DEFAULT_CAPACITY : elements.length * 2;
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elementAt(currentIndex++);
            }
        };
    }
    public java.util.stream.Stream<T> stream() {
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Arrays.copyOf(elements, size, Object[].class);
        return java.util.Arrays.stream(arr);
    }
}
