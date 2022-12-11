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
     * Test metody characterCount, z klasy FileStats.
     */
    @Test
    public void testCharacterCount() throws Exception {
        System.out.println("FileStatsTest: Testowanie metody 'characterCount'");

        long expResult = 2927;
        long result = FileStats.characterCount(testFilePath);
        assertEquals(expResult, result);
    }
}
