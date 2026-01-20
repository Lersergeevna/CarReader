import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void add(T item) {
        ensureCapacity();
        elements[size++] = item;
    }
    public T remove(int index) {
        rangeCheck(index);
        @SuppressWarnings("unchecked")

        T removed = (T) elements[index];

        System.arraycopy(elements, index + 1, elements, index, size - index - 1);

        // Очищаем последний элемент (во избежание утечек)
        elements[--size] = null;

        return removed;
    }
    public T get(int index) {
        rangeCheck(index);
        @SuppressWarnings("unchecked")
        T result = (T) elements[index];
        return result;
    }

    public T set(int index, T item) {
        rangeCheck(index);
        @SuppressWarnings("unchecked")
        T old = (T) elements[index];
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
                @SuppressWarnings("unchecked")
                T result = (T) elements[currentIndex++];
                return result;
            }
        };
    }
    public java.util.stream.Stream<T> stream() {
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Arrays.copyOf(elements, size, Object[].class);
        return java.util.Arrays.stream(arr);
    }
}
