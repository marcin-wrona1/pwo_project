package com.asynchronousboiz.pwo_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Krzysztof Zarębski
 */
public class FileOperationsTest {
    
    public FileOperationsTest() {
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
     * Test of copyFile method, of class FileOperations.
     */
    @Test
    public void testCopyFile() throws Exception {
        System.out.println("copyFile");
        String from = "";
        String to = "";
        FileOperations.copyFile(from, to);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveFile method, of class FileOperations.
     */
    @Test
    public void testMoveFile() throws Exception {
        System.out.println("moveFile");
        String from = "";
        String to = "";
        FileOperations.moveFile(from, to);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
