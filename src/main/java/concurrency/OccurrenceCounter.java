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

    /**
     * Подсчитывает количество элементов, у которых numericField == target,
     * используя указанное количество потоков.
     *
     * @param data коллекция данных
     * @param numericField функция извлечения числового поля
     * @param target искомое значение
     * @param threads количество потоков
     * @return количество вхождений
     */
    public static <T> int countOccurrencesParallel(
            MyArrayList<T> data,
            ToIntFunction<T> numericField,
            int target,
            int threads
    ) {
        Objects.requireNonNull(data, "Коллекция данных не должна быть null");
        Objects.requireNonNull(numericField, "Функция извлечения поля не должна быть null");

        if (data.size() == 0) {
            return 0;
        }

        if (threads <= 0) {
            threads = 1;
        }

        int size = data.size();
        int actualThreads = Math.min(threads, size);
        int chunkSize = (size + actualThreads - 1) / actualThreads;

        int[] partialResults = new int[actualThreads];
        Thread[] workers = new Thread[actualThreads];

        for (int t = 0; t < actualThreads; t++) {
            final int index = t;
            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, size);

            workers[t] = new Thread(() -> {
                int count = 0;
                for (int i = start; i < end; i++) {
                    if (numericField.applyAsInt(data.get(i)) == target) {
                        count++;
                    }
                }
                partialResults[index] = count;
            });

            workers[t].start();
        }

        // Ожидаем завершения всех потоков
        for (Thread thread : workers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Поток был прерван во время ожидания", e);
            }
        }

        // Суммируем частичные результаты
        int total = 0;
        for (int count : partialResults) {
            total += count;
        }

        return total;
    }
}
