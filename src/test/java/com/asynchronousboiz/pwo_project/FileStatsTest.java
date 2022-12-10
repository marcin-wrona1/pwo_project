package com.asynchronousboiz.pwo_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jakub Kozłowski
 */
public class FileStatsTest {

    public FileStatsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
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
     * Test of fileSize method, of class FileStats.
     */
    @Test
    public void testFileSize() throws Exception {
        System.out.println("fileSize");
        String filepath = "";
        long expResult = 0L;
        long result = FileStats.fileSize(filepath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
