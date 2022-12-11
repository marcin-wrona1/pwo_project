package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Marcin Wrona
 */
public class FileOperationsTest {
    private String testDirPath;
    
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
        try {
            Path testDir = Files.createTempDirectory("FileOperationsTest");
            testDirPath = testDir.toAbsolutePath().normalize().toString();

            int i;
            for (i = 0; i < 10; i++) {
                Files.createFile(Paths.get(testDir + "/file" + i));
            }
        } catch (IOException ex) {
            assert(false);
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test dla metody 'directoryContent', z klasy 'FileOperations'.
     */
    @Test
    public void directoryContentTest() throws Exception {
        List<Path> files = FileOperations.directoryContent(testDirPath);

        assertEquals(10, files.size());
        Collections.sort(files);
        int i;
        for (i = 0; i < 10; i++) {
            String targetPath = testDirPath + "/file" + i;
            Path file = files.get(i);
            assertNotEquals(null, file);
            String path = file.toAbsolutePath().normalize().toString();
            assertEquals(targetPath, path);
        }
    }
}
