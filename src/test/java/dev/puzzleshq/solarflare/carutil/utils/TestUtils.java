package dev.puzzleshq.solarflare.carutil.utils;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestUtils {
    public static void assertDirectoriesEqual(File expected, File actual) throws IOException {
        if (!expected.isDirectory() || !actual.isDirectory()){
            Assertions.fail("Both must be directories, but one or both are not:\n" +
                    "expected: " + expected.getPath() + "\nactual: " + actual.getPath() + "\n");
        }

        File[] expectedFiles = expected.listFiles();
        File[] actualFiles = actual.listFiles();

        Assertions.assertNotNull(expectedFiles, "Expected directory has no files: " + expected.getAbsolutePath());
        Assertions.assertNotNull(actualFiles, "Actual directory has no files: " + actual.getAbsolutePath());


        Map<String, File> expectedFileMap = new HashMap<>();
        Map<String, File> actualFileMap = new HashMap<>();

        for (File f : expectedFiles) expectedFileMap.put(f.getName(), f);
        for (File f: actualFiles) actualFileMap.put(f.getName(), f);

        if (expectedFileMap.size() != actualFileMap.size())
            Assertions.fail("Files/Directories do not match");

        for (String fileName : expectedFileMap.keySet()) {
            File fileA = expectedFileMap.get(fileName);
            File fileB = actualFileMap.get(fileName);

            byte[] bytesA = new byte[(int) fileA.length()];
            byte[] bytesB = new byte[(int) fileB.length()];

            FileInputStream fileStreamA = new FileInputStream(fileA);
            fileStreamA.read(bytesA, 0, bytesA.length);
            fileStreamA.close();

            FileInputStream fileStreamB = new FileInputStream(fileB);
            fileStreamB.read(bytesB, 0, bytesB.length);
            fileStreamB.close();

            Assertions.assertArrayEquals(bytesA, bytesB);
        }

    }

    public static void deleteDir(File dir){
        if (!dir.exists()) return;
        File[] files = dir.listFiles();
        if (files.length == 0) dir.delete();

        for (File file : files){
            file.delete();
        }
        dir.delete();
    }

    public static byte[] readFileBytes(File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];

        FileInputStream fileStream = new FileInputStream(file);
        fileStream.read(bytes, 0, bytes.length);
        fileStream.close();
        return bytes;
    }
}
