package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jakub Kozłowski
 * @author Albert Strzyżewski
 */
public class FileStatsTest {
    private static final String testFileName = "com/asynchronousboiz/pwo_project/FileStatsTest.txt";
    private static String testFilePath;

    public FileStatsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        final InputStream stream = FileStatsTest.class.getClassLoader().getResourceAsStream(testFileName);

        try {
            Path testFile = Files.createTempFile("FileStatsTest", ".txt");
            OutputStream out = Files.newOutputStream(testFile);
            stream.transferTo(out);
            testFilePath = testFile.toAbsolutePath().normalize().toString();
        } catch (IOException ex) {
            assert(false);
        }
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test metody fileSize, z klasy FileStats.
     */
    @Test
    public void testFileSize() throws Exception {
        System.out.println("FileStatsTest: Testowanie metody 'fileSize'");

        long expResult = 2936;
        long result = FileStats.fileSize(testFilePath);
        assertEquals(expResult, result);
    }

    /**
     * Test metody lineCount, z klasy FileStats.
     */
    @Test
    public void testLineCount() throws Exception {
        System.out.println("FileStatsTest: Testowanie metody 'lineCount'");

        long expResult = 9;
        long result = FileStats.lineCount(testFilePath);
        assertEquals(expResult, result);
    }

    /**
     * Test metody characterCount, z klasy FileStats.
     */
    @Test
    public void testCharacterCount() throws Exception {
        System.out.println("FileStatsTest: Testowanie metody 'characterCount'");

        long expResult = 2927;
        long result = FileStats.characterCount(testFilePath);
        assertEquals(expResult, result);
    }

    /**
     * Test metody wordCount, z klasy FileStats.
     */
    @Test
    public void testWordCount() throws Exception {
        System.out.println("FileStatsTest: Testowanie metody 'wordCount'");

        long expResult = 431;
        long result = FileStats.wordCount(testFilePath);
        assertEquals(expResult, result);
    }
}
