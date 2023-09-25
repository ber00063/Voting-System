/**
 * CandidateTest.java is used for testing the methods in the Candidate class.
 *
 * @author tracy255, Caleb Tracy.
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CandidateTest class contains methods for testing the Candidate class.
 */
class CandidateTest {

    /**
     * Tests if Candidate constructor properly creates Candidates, normal case
     */
    @Test
    void candidateTypeChecking1() {
        Candidate cand1 = new Candidate("party name", "joe biden");
        assertEquals(cand1.getParty(),"party name");
        assertEquals(cand1.getCandidateName(),"joe biden");
    }

    /**
     * Tests if Candidate constructor properly creates Candidates, abnormal characters case
     */
    @Test
    void candidateTypeChecking2() {
        Candidate cand2 = new Candidate("a123&", "a123&");
        assertEquals(cand2.getParty(),"a123&");
        assertEquals(cand2.getCandidateName(),"a123&");
    }


    /**
     * Tests the addBallot() and removeBallot() methods in the Candidate class.
     */
    @Test
    void addRemoveBallot() {
        ArrayList<String> cands1 = new ArrayList<String>();
        cands1.add("caleb");
        cands1.add("ashton");
        cands1.add("garrett");
        cands1.add("elias");
        Ballot blt1 = new Ballot(0, 4, cands1);

        //Test what happens on updating a ballot with an empty arraylist
        ArrayList<String> cands2 = new ArrayList<String>();
        Ballot blt2 = new Ballot(1, 0, cands2);

        ArrayList<String> cands3 = new ArrayList<String>();
        cands3.add("caleb");
        Ballot blt3 = new Ballot(2, 1, cands3);

        Candidate cand1 = new Candidate("party name", "cand name");
        cand1.addBallot(blt1);
        assertEquals(cand1.getBallotCount(), 1);
        cand1.removeBallot(0);
        assertEquals(cand1.getBallotCount(), 0);

        cand1.addBallot(blt1);
        cand1.addBallot(blt2);
        cand1.addBallot(blt3);
        cand1.addBallot(blt1);
        assertEquals(cand1.getBallotCount(), 4);
        cand1.removeBallot(3);
        cand1.removeBallot(2);
        assertEquals(cand1.getBallotCount(), 2);
        cand1.removeBallot(1);
        cand1.removeBallot(0);
        assertEquals(cand1.getBallotCount(), 0);
        //assertThrows(cand1.removeBallot(0), exception(e));
    }

    /**
     * Tests the getBallotCount() method in the Candidate class().
     */
    @Test
    void getBallotCount() {

        Candidate countCand = new Candidate("party name", "cand name");

        ArrayList<String> cands1 = new ArrayList<String>();
        cands1.add("caleb");
        cands1.add("ashton");
        cands1.add("garrett");
        cands1.add("elias");
        Ballot blt1 = new Ballot(0, 4, cands1);

        ArrayList<String> cands2 = new ArrayList<String>();
        cands1.add("caleb");
        cands1.add("ashton");
        Ballot blt2 = new Ballot(1, 0, cands2);

        ArrayList<String> cands3 = new ArrayList<String>();
        cands1.add("caleb");
        Ballot blt3 = new Ballot(2, 1, cands3);

        countCand.addBallot(blt1);
        countCand.addBallot(blt2);
        countCand.addBallot(blt3);

        assertEquals(countCand.getBallotCount(), 3);

        countCand.addBallot(blt1);
        countCand.addBallot(blt2);

        assertEquals(countCand.getBallotCount(), 5);

        countCand.removeBallot(0);
        countCand.removeBallot(0);

        assertEquals(countCand.getBallotCount(), 3);

        countCand.removeBallot(0);
        countCand.removeBallot(0);

        assertEquals(countCand.getBallotCount(), 1);

        countCand.removeBallot(0);

        assertEquals(countCand.getBallotCount(), 0);
    }

    /**
     * Tests the getParty() method in the Candidate class.
     */
    @Test
    void getParty() {
        Candidate cand1 = new Candidate("party name", "cand name");
        Candidate cand2 = new Candidate("greenParty", "cand name");
        Candidate cand3 = new Candidate("G", "cand name");
        Candidate cand4 = new Candidate("123450", "cand name");

        assertEquals(cand1.getParty(), "party name");
        assertEquals(cand2.getParty(), "greenParty");
        assertEquals(cand3.getParty(), "G");
        assertEquals(cand4.getParty(), "123450");
    }

    /**
     * Tests the getBallots() method in the Candidate class.
     */
    @Test
    void getBallots() {

        Candidate getBallotsCand = new Candidate("party name", "cand name");

        ArrayList<String> cands1 = new ArrayList<String>();
        cands1.add("caleb");
        cands1.add("ashton");
        cands1.add("garrett");
        cands1.add("elias");
        Ballot blt1 = new Ballot(0, 4, cands1);

        ArrayList<String> cands2 = new ArrayList<String>();
        cands2.add("ashton");
        cands2.add("caleb");
        Ballot blt2 = new Ballot(1, 0, cands2);

        ArrayList<String> cands3 = new ArrayList<String>();
        cands3.add("garrett");
        Ballot blt3 = new Ballot(2, 1, cands3);

        getBallotsCand.addBallot(blt1);
        getBallotsCand.addBallot(blt2);
        getBallotsCand.addBallot(blt3);

        ArrayList<Ballot> ballots = getBallotsCand.getBallots();
        assertEquals(ballots.get(0).getIndex(), 0);
        assertEquals(ballots.get(1).getIndex(), 1);
        assertEquals(ballots.get(2).getIndex(), 2);

        assertEquals(ballots.get(0).getNextCandidate(), "caleb");
        assertEquals(ballots.get(1).getNextCandidate(), "ashton");
        assertEquals(ballots.get(2).getNextCandidate(), "garrett");

    }

    /**
     * Tests the getCandidateName() method in the Candidate class.
     */
    @Test
    void getCandidateName() {
        Candidate cand1 = new Candidate("party name", "cand name");
        Candidate cand2 = new Candidate("party name", "liberals");
        Candidate cand3 = new Candidate("party name", "megaLiberals");
        Candidate cand4 = new Candidate("party name", "**_LIBerAls_**64");

        assertEquals(cand1.getCandidateName(), "cand name");
        assertEquals(cand2.getCandidateName(), "liberals");
        assertEquals(cand3.getCandidateName(), "megaLiberals");
        assertEquals(cand4.getCandidateName(), "**_LIBerAls_**64");
    }

}