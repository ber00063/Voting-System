/**
 * BallotTest.java is used for testing the methods in the Ballot class.
 *
 * @author tracy255, Caleb Tracy.
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BallotTest class contains test methods for the Ballot class.
 */
class BallotTest {

    /**
     * Tests the getNextCandidate(), updateBallot(), and getNumRankings() methods in the Ballot class.
     */
    @Test
    void getNextCandidateAndUpdate() {
        ArrayList<String> cands1 = new ArrayList<String>();
        cands1.add("caleb");
        cands1.add("ashton");
        Ballot blt1 = new Ballot(0, 2, cands1);
        assertEquals(blt1.getNextCandidate(),"caleb");
        assertEquals(blt1.updateBallot(), true);
        assertEquals(blt1.getNextCandidate(),"ashton");
        assertEquals(blt1.updateBallot(), false);
        assertEquals(blt1.getNumRankings(), 0);

        ArrayList<String> cands2 = new ArrayList<String>();
        cands2.add("caleb");
        cands2.add("ashton");
        cands2.add("abc");
        cands2.add("abcd 123");
        Ballot blt2 = new Ballot(0, 4, cands2);
        assertEquals(blt2.getNextCandidate(),"caleb");
        assertEquals(blt2.updateBallot(), true);
        assertEquals(blt2.getNextCandidate(),"ashton");
        assertEquals(blt2.updateBallot(), true);
        assertEquals(blt2.getNextCandidate(),"abc");
        assertEquals(blt2.updateBallot(), true);
        assertEquals(blt2.getNextCandidate(),"abcd 123");
//        assertEquals(blt2.updateBallot(), false);
    }

    /**
     * Tests the getIndex() method in the Ballot class, and that ballots are created with the
     * correct ballotIndex.
     */
    @Test
    void getIndex() {
        ArrayList<String> cands = new ArrayList<String>();
        Ballot blt1 = new Ballot(0, 2, cands);
        Ballot blt2 = new Ballot(1, 2, cands);
        Ballot blt3 = new Ballot(2, 2, cands);
        Ballot blt4 = new Ballot(3, 2, cands);
        Ballot blt5 = new Ballot(999, 2, cands);
        assertEquals(blt1.getIndex(), 0);
        assertEquals(blt2.getIndex(), 1);
        assertEquals(blt3.getIndex(), 2);
        assertEquals(blt4.getIndex(), 3);
        assertEquals(blt5.getIndex(), 999);
    }

    /**
     * Tests to ensure the ballot bug was corrected.
     */
    @Test
    void updateBallotBugFixTest() {
        ArrayList<String> cands = new ArrayList<String>();
        cands.add("caleb");
        cands.add("ashton");
        Ballot blt1 = new Ballot(0, 2, cands);
        assertEquals(blt1.getNumRankings(), 2);
        assertEquals(blt1.updateBallot(), true);
        assertEquals(blt1.getNumRankings(), 1);
        assertEquals(blt1.updateBallot(), false);

        ArrayList<String> cands2 = new ArrayList<String>();
        cands2.add("ashton");
        Ballot blt2 = new Ballot(1, 1, cands2);
        assertEquals(blt2.updateBallot(), false);

        ArrayList<String> cands3 = new ArrayList<String>();
        Ballot blt3 = new Ballot(2, 0, cands3);
        assertEquals(blt2.updateBallot(), false);
    }

}