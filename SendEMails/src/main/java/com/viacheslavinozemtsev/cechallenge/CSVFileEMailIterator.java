package com.viacheslavinozemtsev.cechallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class CSVFileEMailIterator implements Iterator<EMail> {
    private final String delimiter;
    private final BufferedReader in;

    private String line;
    private boolean eof;

    public CSVFileEMailIterator(File file, boolean header, String delimiter) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file can't be null!");
        }
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter must be specified!");
        }
        this.delimiter = delimiter;
        this.in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        if (header) {
            in.readLine();
        }
        this.eof = false;
    }

    // for testing purposes
    CSVFileEMailIterator() {
        delimiter = ",";
        in = null;
    }

    public boolean hasNext() {
        if (eof) {
            return false;
        }
        if (line == null) {
            readLine();
        }
        return line != null;
    }

    public EMail next() {
        if (eof) {
            throw new RuntimeException("EOF was reached!");
        }
        if (line == null) {
            readLine();
        }
        if (eof) {
            throw new RuntimeException("EOF was reached!");
        }
        final EMail eMail = parseLine(line);
        if (eMail == null) {
            throw new RuntimeException("Couldn't parse line in the storage file!");
        }
        line = null;
        return eMail;
    }

    private void readLine() {
        try {
            line = in.readLine();
            if (line == null) {
                eof = true;
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IO exception was thrown!", e);
        }
    }

    private EMail parseLine(final String line) {
        if (line == null) {
            return null;
        }
        final String[] fields = line.split(delimiter);
        if (fields.length != 5) {
            return null;
        }
        final String sender = fields[0];
        final String recipients = fields[1];
        final String subject = fields[2];
        final String body = fields[3];
        final boolean active = "1".equals(fields[4]);
        return new EMail(sender, recipients, subject, body, active);
    }
}
