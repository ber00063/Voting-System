/**
 * MainTest.java is used for testing the functionality of main
 *
 * @author tracy255, Caleb Tracy.
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MainTest class contains test methods for the main method.
 */
public class        MainTest {

    /**
     * A test to make sure main can handle the input of multiple files for IR.
     */
    @Test
    void getFilesIR() {
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/IRTesting5.csv", "src/test/java/IRTesting6.csv"}));
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/IRTesting5.csv"}));
    }

    /**
     * A test to make sure main can handle the input of multiple files for CPL.
     */
    @Test
    void getFilesCPL() {
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/CPLTesting3.csv", "src/test/java/CPLTesting4.csv"}));
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/CPLTesting4.csv"}));
    }

    /**
     * A test to make sure main can handle the input a file for PO.
     */
    @Test
    void getFilePO() {
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/POTest3.csv"}));
        assertDoesNotThrow(() -> main.main(new String[]{"src/test/java/POTest4.csv"}));
    }

}
