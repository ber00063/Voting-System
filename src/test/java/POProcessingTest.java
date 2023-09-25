/**
 * POProcessingTest.java is used for testing the methods in the POProcessing class.
 *
 * @author Ashton Berg.
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * POProcessingTest class contains methods for testing the POProcessing class.
 */
public class POProcessingTest {

    /**
     * Tests the setCandidatesAndParties() method in the POProcessing class.
     * @throws IOException  if an IOException occurs when reading from csv file.
     */
    @Test
    void setCandidatesAndParties() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/POTest1.csv");
        BufferedReader br = new BufferedReader(csvFile);

        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        POProcessing election = new POProcessing(br);

        ArrayList<String> cands = election.getCandidatesArrayList();
        ArrayList<String> parties = election.getCandidateParties();

        assertEquals(cands.get(0), "Pike");
        assertEquals(parties.get(0), "D");
        assertEquals(cands.get(1), "Foster");
        assertEquals(parties.get(1), "D");
        assertEquals(cands.get(2), "Deutsch");
        assertEquals(parties.get(2), "R");
        assertEquals(cands.get(3), "Borg");
        assertEquals(parties.get(3), "R");
        assertEquals(cands.get(4), "Jones");
        assertEquals(parties.get(4), "R");
        assertEquals(cands.get(5), "Smith");
        assertEquals(parties.get(5), "I");

    }

    /**
     * Tests the updateBallotCounts() method in the POProcessing class.
     * @throws IOException  if an IOException occurs when reading from csv file.
     */
    @Test
    void updateBallotCounts() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/POTest1.csv");
        BufferedReader br = new BufferedReader(csvFile);

        try {
            br.readLine();
            //br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        POProcessing election = new POProcessing(br);
        //election.updateBallotCounts(brs[0]);

        Integer[] ballotCounts = election.getCandidatesBallotCounts();

        assertEquals(ballotCounts[0], 3);
        assertEquals(ballotCounts[1], 2);
        assertEquals(ballotCounts[2], 0);
        assertEquals(ballotCounts[3], 2);
        assertEquals(ballotCounts[4], 1);
        assertEquals(ballotCounts[5], 1);
    }

    /**
     * Tests the processElection() method in the POProcessing class.
     * @throws IOException  if an IOException occurs when reading from csv file.
     */
    @Test
    void processElection1() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/POTest1.csv");
        BufferedReader br = new BufferedReader(csvFile);

        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        POProcessing election = new POProcessing(br);
        assertEquals(election.processElection(), "Pike");
    }

    /**
     * Tests the processElection() method in the POProcessing class.
     * @throws IOException  if an IOException occurs when reading from csv file.
     */
    @Test
    void processElection2() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/POTest2.csv");
        BufferedReader br = new BufferedReader(csvFile);

        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        POProcessing election = new POProcessing(br);

        ArrayList<String> cands = election.getCandidatesArrayList();
        ArrayList<String> parties = election.getCandidateParties();

        assertEquals(cands.get(0), "Pike");
        assertEquals(parties.get(0), "D");
        assertEquals(cands.get(1), "Foster");
        assertEquals(parties.get(1), "D");
        assertEquals(cands.get(2), "Deutsch");
        assertEquals(parties.get(2), "R");


        Integer[] ballotCounts = election.getCandidatesBallotCounts();

        assertEquals(ballotCounts[0], 2);
        assertEquals(ballotCounts[1], 3);
        assertEquals(ballotCounts[2], 2);

        assertEquals(election.getTotalNumBallots(), 7);

        assertEquals(election.processElection(), "Foster");

    }

    /**
     * Tests the getVotePercents() method in the POProcessing class.
     * @throws IOException  if an IOException occurs when reading from csv file.
     */
    @Test
    void getVotePercents() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/POTest1.csv");
        BufferedReader br = new BufferedReader(csvFile);

        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        POProcessing election = new POProcessing(br);

        String[] percs = election.getVotePercents();

        assertEquals(percs[0], "Pike, D: 33.33%");
        assertEquals(percs[1], "Foster, D: 22.22%");
        assertEquals(percs[2], "Deutsch, R: 0.00%");
        assertEquals(percs[3], "Borg, R: 22.22%");
        assertEquals(percs[4], "Jones, R: 11.11%");
        assertEquals(percs[5], "Smith, I: 11.11%");
    }



}
