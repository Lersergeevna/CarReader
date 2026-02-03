package concurrency;

import collection.MyArrayList;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * Многопоточный подсчёт количества вхождений значения N
 * в числовом поле элементов кастомной коллекции.
 */
public final class OccurrenceCounter {

    private OccurrenceCounter() {}

    public static <T> int countOccurrencesParallel(
            MyArrayList<T> data,
            ToIntFunction<T> numericField,
            int target,
            int threads
    ) {
        Objects.requireNonNull(data, "Коллекция данных не должна быть null");
        Objects.requireNonNull(numericField, "Функция извлечения поля не должна быть null");

        int size = data.size();
        if (size == 0) return 0;

        if (threads <= 1) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (numericField.applyAsInt(data.get(i)) == target) count++;
            }
            return count;
        }

        int actualThreads = Math.min(threads, size);
        int chunkSize = (size + actualThreads - 1) / actualThreads;

        int[] partial = new int[actualThreads];
        Thread[] workers = new Thread[actualThreads];

        for (int t = 0; t < actualThreads; t++) {
            final int index = t;
            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, size);

            Thread worker = new Thread(() -> {
                int count = 0;
                for (int i = start; i < end; i++) {
                    if (numericField.applyAsInt(data.get(i)) == target) count++;
                }
                partial[index] = count;
            }, "поток-подсчёта-вхождений-" + t);

            workers[t] = worker;
            worker.start();
        }

        for (Thread thread : workers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Поток был прерван во время ожидания", e);
            }
        }

        int total = 0;
        for (int x : partial) total += x;
        return total;
    }
}
