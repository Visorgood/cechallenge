package com.viacheslavinozemtsev.cechallenge;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CSVFileStorage implements Storage {
    private final File file;
    private final boolean header;
    private final String delimiter;

    public CSVFileStorage(String filePath, boolean header, String delimiter) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("filePath must be specified!");
        }
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter must be specified!");
        }
        this.file = new File(filePath);
        this.header = header;
        this.delimiter = delimiter;
    }

    public Iterator<EMail> iterator() {
        try {
            return new CSVFileEMailIterator(file, header, delimiter);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IO exception was thrown!", e);
        }
    }
}
