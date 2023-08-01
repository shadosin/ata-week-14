package com.kenzie.input.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Takes in a file path in the constructor, and provides utility methods for that file.
 */
public class ATAFileReader implements FileReader {

    private final File file;

    /**
     * Constructs a new ATAFileReader object. This object will provide utility methods on the file provided.
     * @param filePath a path to a valid file
     */
    public ATAFileReader(String filePath) {
        file = new File(filePath);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    /**
     * Reads all lines in the instances file, and returns as a list of Strings.
     * @return a list of Strings read from the file. If an error occurs while reading the file,
     *         an empty list will be returned.
     */
    @Override
    public List<String> readLines() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
            return br.lines().collect(Collectors.toList());
            //CHECKSTYLE:OFF:IllegalCatch
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        //CHECKSTYLE:ON:IllegalCatch
    }

    /**
     * Reads a file character by character. Includes tabs and spaces. Excludes new line characters.
     * @return a list of Characters read from the file. If an error occurs while reading the file,
     *        an empty list will be returned.
     */
    @Override
    public List<Character> readCharacters() {
        final List<Character> chars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
            int character;
            while ((character = br.read()) != -1) {
                if (!isLineBreakChar(character)) {
                    chars.add((char) character);
                }
            }
            //CHECKSTYLE:OFF:IllegalCatch
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        //CHECKSTYLE:ON:IllegalCatch
        return chars;
    }

    private boolean isLineBreakChar(int character) {
        char c = (char) character;
        return c == '\n' || c == '\r';
    }
}