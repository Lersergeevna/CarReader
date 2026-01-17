package io;


import java.io.*;

public final class FileCarRepository {
    private FileCarRepository() {}

    public static MyArrayList<Car> readCars(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            MyArrayList<Car> list = new MyArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                list.add(CarCsvParser.parseLine(line));
            }
            return list;
        }
    }
}

