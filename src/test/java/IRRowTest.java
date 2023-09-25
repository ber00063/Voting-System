
/**
 * IRRowTest.java is used for testing the methods in the IRRow class.
 *
 * @author tracy255, Caleb Tracy.
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains methods for testing the IRRow class.
 */
class IRRowTest {

    /**
     * Tests if IRRow constructor properly creates an IRRow, normal case
     */
    @Test
    void rowConstructorChecking1() {
        IRRow row1 = new IRRow( "joe biden", "party name");
        assertEquals(row1.getCandParty(),"party name");
        assertEquals(row1.getCandName(),"joe biden");
    }

    /**
     * Tests if IRRow constructor properly creates IRRow, abnormal characters case
     */
    @Test
    void rowConstructorChecking2() {
        IRRow row1 = new IRRow( "a123&", "a123&^/n");
        assertEquals(row1.getCandName(),"a123&");
        assertEquals(row1.getCandParty(),"a123&^/n");
    }

    /**
     * Test arraylist variable, ballot_stats.
     */
    @Test
    void statsChecking() {
        IRRow row2 = new IRRow( "test cand", "test party");
        row2.add_stat(1);
        row2.add_stat(2);
        row2.add_stat(2);
        row2.add_stat(10000000);
        row2.add_stat(-11);

        ArrayList<Integer> stats = row2.get_stats();
        assertEquals(stats.get(0), 1);
        assertEquals(stats.get(1), 2);
        assertEquals(stats.get(2), 2);
        assertEquals(stats.get(3), 10000000);
        assertEquals(stats.get(4), -11);

        assertEquals(stats.size(), row2.get_length());
    }

}
