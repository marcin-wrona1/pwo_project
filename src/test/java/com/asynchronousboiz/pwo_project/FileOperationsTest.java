package com.asynchronousboiz.pwo_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
 * @author Krzysztof Zarębski
 * @author Marcin Wrona
 */
public class FileOperationsTest {
    private static final String PACKAGE_PATH = "com/asynchronousboiz/pwo_project";
    private static final String TEST_FILE_RESOURCE = PACKAGE_PATH + "/java.png";

    private static MessageDigest hashAlgo;
    private static byte[] testFileHash;

    private String testDirPath;
    private String testFilePath;

    private String testDirPath2;
    
    public FileOperationsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        final ClassLoader loader = FileOperationsTest.class.getClassLoader();
        final InputStream stream = loader.getResourceAsStream(TEST_FILE_RESOURCE);

        try {
            hashAlgo = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            assert(false);
        }
        try {
            testFileHash = hashAlgo.digest(stream.readAllBytes());
        } catch (IOException ex) {
            assert(false);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        final ClassLoader loader = FileOperationsTest.class.getClassLoader();
        final InputStream stream = loader.getResourceAsStream(TEST_FILE_RESOURCE);

        try {
            Path testDir = Files.createTempDirectory("FileOperationsTest");
            testDirPath = testDir.toAbsolutePath().normalize().toString();
            System.out.println("testDirPath: " + testDirPath);

            Path testFile = Files.createTempFile(testDir, "java", ".png");
            System.out.println("testFilePath: " + testFile.toAbsolutePath().normalize().toString());
            OutputStream out = Files.newOutputStream(testFile);
            stream.transferTo(out);
            testFilePath = testFile.toAbsolutePath().normalize().toString();

            Path testDir2 = Files.createTempDirectory("FileOperationsTest");
            testDirPath2 = testDir2.toAbsolutePath().normalize().toString();

            int i;
            for (i = 0; i < 10; i++) {
                Files.createFile(Paths.get(testDir2 + "/file" + i));
            }
        } catch (IOException ex) {
            assert(false);
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test metody copyFile, z klasy FileOperations.
     */
    @Test
    public void testCopyFile() throws Exception {
        System.out.println("copyFile");

        String from = testFilePath;
        String to = testDirPath + "/java.png";

        FileOperations.copyFile(from, to);

        InputStream copyStream = new FileInputStream(to);
        byte[] copyHash = hashAlgo.digest(copyStream.readAllBytes());
        assertEquals(true, Arrays.equals(testFileHash, copyHash));
    }

    /**
     * Test metody moveFile, z klasy FileOperations.
     */
    @Test
    public void testMoveFile() throws Exception {
        System.out.println("moveFile");

        String from = testFilePath;
        String to = testDirPath + "/java.png";

        FileOperations.moveFile(from, to);

        InputStream copyStream = new FileInputStream(to);
        byte[] copyHash = hashAlgo.digest(copyStream.readAllBytes());
        assertEquals(true, Arrays.equals(testFileHash, copyHash));

        assertEquals(false, new File(testFilePath).exists());
    }
    
    /**
     * Test dla metody 'directoryContent', z klasy 'FileOperations'.
     */
    @Test
    public void directoryContentTest() throws Exception {
        List<Path> files = FileOperations.directoryContent(testDirPath2);

        assertEquals(10, files.size());
        Collections.sort(files);
        int i;
        for (i = 0; i < 10; i++) {
            String targetPath = testDirPath2 + "/file" + i;
            Path file = files.get(i);
            assertNotEquals(null, file);
            String path = file.toAbsolutePath().normalize().toString();
            assertEquals(targetPath, path);
        }
    }
}
