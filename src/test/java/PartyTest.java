/**
 * PartyTest.java is used for testing the methods in the Party class.
 *
 * @author abouz009 and berg00063.
 */

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PartyTest class contains methods for testing the Party class.
 */
class PartyTest {

    /**
     * Tests the addCandidate() method in the Party class
     */
    @Test
    void addCandidate() {
    ArrayList<String> candidates = new ArrayList<>();

    Party party = new Party("party", candidates);
    party.addCandidate("candidate1");
    party.addCandidate("CANDIDATE2");
    party.addCandidate("cAnDiDaTe3");
    party.addCandidate("CANDIdate4");
    party.addCandidate("candiDATE5");

    assertEquals(candidates.get(0), "candidate1");
    assertEquals(candidates.get(1), "CANDIDATE2");
    assertEquals(candidates.get(2), "cAnDiDaTe3");
    assertEquals(candidates.get(3), "CANDIdate4");
    assertEquals(candidates.get(4), "candiDATE5");

    }

    /**
     * Tests the removeCandidate() method in the Party class
     */
    @Test
    void removeCandidate() {
        ArrayList<String> candidates = new ArrayList<>();
        candidates.add("candidate1");
        candidates.add("CANDIDATE2");
        candidates.add("cAnDiDaTe3");

        Party party = new Party("party", candidates);

        party.removeCandidate("CANDIDATE2");
        assertEquals(candidates.get(1), "cAnDiDaTe3");
        party.removeCandidate("candidate1");
        assertEquals(candidates.get(0), "cAnDiDaTe3");
        party.removeCandidate("cAnDiDaTe3");
        assertEquals(candidates.size(), 0);

    }

    /**
     * Tests the getCandidates() method in the Party class
     */
    @Test
    void getCandidates() {
        ArrayList<String> candidates = new ArrayList<>();

        Party party = new Party("party", candidates);
        party.addCandidate("person1");
        party.addCandidate("PERSON2");
        party.addCandidate("pErSoN3");
        party.addCandidate("PERson4");
        party.addCandidate("perSON5");

        assertEquals(party.getCandidates().get(0), "person1");
        assertEquals(party.getCandidates().get(1), "PERSON2");
        assertEquals(party.getCandidates().get(2), "pErSoN3");
        assertEquals(party.getCandidates().get(3), "PERson4");
        assertEquals(party.getCandidates().get(4), "perSON5");
    }

    /**
     * Tests the getNumSeats() and setNumSeats() methods in the Party class
     */
    @Test
    void getSetNumSeats() {
        ArrayList<String> candidates = new ArrayList<>();
        Party party = new Party("party", candidates);


        party.setNumSeats(3);
        assertEquals(party.getNumSeats(), 3);

        party.setNumSeats(6);
        assertEquals(party.getNumSeats(), 6);

        party.setNumSeats(0);
        assertEquals(party.getNumSeats(), 0);

        party.setNumSeats(10);
        assertFalse(party.getNumSeats() == -1);
        assertTrue(party.getNumSeats() == 10);

    }

    /**
     * Tests the getBallotCount() and setBallotCount() methods in the Party class
     */
    @Test
    void getSetBallotCount() {
        ArrayList<String> candidates = new ArrayList<>();
        Party party = new Party("party", candidates);

        party.setBallotCount(15);
        assertEquals(party.getBallotCount(), 15);

        party.setBallotCount(53);
        assertEquals(party.getBallotCount(), 53);

        party.setBallotCount(1);
        assertEquals(party.getBallotCount(), 1);
    }

    /**
     * Tests the getParty() method in the Party class
     */
    @Test
    void getParty() {
        ArrayList<String> candidates = new ArrayList<>();
        Party party1 = new Party("Republican", candidates);
        Party party2 = new Party("democratic", candidates);
        Party party3 = new Party("iNdEpEnDeNt", candidates);
        Party party4 = new Party("REAList", candidates);

        assertEquals(party1.getParty(), "Republican");
        assertEquals(party2.getParty(), "democratic");
        assertEquals(party3.getParty(), "iNdEpEnDeNt");
        assertEquals(party4.getParty(), "REAList");
    }
}