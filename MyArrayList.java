package collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements = new Object[DEFAULT_CAPACITY];
    private int size;

    public int size() {
        return size;
    }

    public void add(T item) {
        ensureCapacity(size + 1);
        elements[size++] = item;
    }

    public T remove(int index) {
        rangeCheck(index);
        T removed = elementAt(index);

        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;

        return removed;
    }

    public T get(int index) {
        rangeCheck(index);
        return elementAt(index);
    }

    public T set(int index, T item) {
        rangeCheck(index);
        T old = elementAt(index);
        elements[index] = item;
        return old;
    }

    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    private void ensureCapacity(int needed) {
        if (needed <= elements.length) return;
        elements = Arrays.copyOf(elements, Math.max(needed, elements.length * 2));
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", размер: " + size);
        }
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
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
                if (!hasNext()) throw new NoSuchElementException();
                return elementAt(currentIndex++);
            }
        };
    }
}
