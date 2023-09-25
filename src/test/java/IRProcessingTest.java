/**
 * IRProcessingTest.java is used for testing the methods in the IRProcessing class.
 *
 * @author tracy255, Caleb Tracy.
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IRProcessingTest class contains methods for testing the IRProcessing class.
 */
class IRProcessingTest {

    /**
     * Tests the processElection(), getCandidates(), setCandidates(), and
     * getCandidateArray() methods in the IRProcessing class.
     * The IRProcessing constructor calls setCandidate(), so we test that the candidates
     * in the IRProcessing instance were created properly, and therefore test setCandidate().
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void processElection1() throws IOException {
        //Simulate main giving a br to IRProcessing
        FileReader csvFile = new FileReader("src/test/java/IRTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            brs[0].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);

        String[] cands = election.getCandidates();
        assertEquals("Rosen", cands[0]);
        assertEquals("Kleinberg", cands[1]);
        assertEquals("Chou", cands[2]);
        assertEquals("Royce", cands[3]);

        ArrayList<Candidate> curCandsArray = election.getCandidateArray();
        assertEquals(curCandsArray.get(0).getCandidateName(), "Rosen");
        assertEquals(curCandsArray.get(2).getCandidateName(), "Chou");
        assertEquals(curCandsArray.get(0).getBallotCount(), 5);
        assertEquals(curCandsArray.get(2).getBallotCount(), 1);

        assertEquals("Rosen", election.processElection());

    }

    /**
     * Tests the processElection() method in the IRProcessing class.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void processElection2() throws IOException {
        //Simulate main giving a br to IRProcessing
        FileReader csvFile = new FileReader("src/test/java/IRTesting2.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            brs[0].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);

        String[] cands = election.getCandidates();
        assertEquals("Rosen", cands[0]);
        assertEquals("Kleinberg", cands[1]);

        ArrayList<Candidate> curCandsArray = election.getCandidateArray();
        assertEquals("Rosen", curCandsArray.get(0).getCandidateName());
        assertEquals("Kleinberg", curCandsArray.get(1).getCandidateName());
        assertEquals(3, curCandsArray.get(0).getBallotCount());
        assertEquals(4, curCandsArray.get(1).getBallotCount());

        //The tie in the first round can go royce or joe, but klein still wins
        assertEquals("Kleinberg", election.processElection());

    }

    /**
     * Tests the determineLoser() method in IRProcessing
     * @throws IOException if IO error occurs when reading from file
     */
    @Test
    void determineLoser() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/IRTesting4.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            brs[0].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);

        Candidate loser;
        loser = election.determineLoser();
        assertEquals(loser.getCandidateName(),"caleb");
        election.redistributeBallots(loser);

        loser = election.determineLoser();
        assertEquals(loser.getCandidateName(),"ashton");
        //election.redistributeBallots(loser);

    }

    /**
     * Tests the redistributeBallots() method in the IRProcessing class.
     * @throws IOException if IO error occurs when reading from file
     */
    @Test
    void redistributeBallots() throws IOException{
        FileReader csvFile = new FileReader("src/test/java/IRTesting3.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            brs[0].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);

        ArrayList<Candidate> cands1 = election.getCandidateArray();
        assertEquals(cands1.get(0).getBallotCount(), 5);
        assertEquals(cands1.get(1).getBallotCount(), 4);

        election.redistributeBallots(election.determineLoser());
        ArrayList<Candidate> cands2 = election.getCandidateArray();
        assertEquals(cands2.get(0).getBallotCount(), 9);
        //assertEquals(cands2.get(1).getBallotCount(), 4);
    }

    /**
     * Tests that setCandidates(), distributeBallots(), and processElection() work with multiple files
     * Uses IRTesting3.csv and IRTesting5.csv
     * @throws IOException if IO error occurs when reading from file
     */
    @Test
    void processElectionMultipleFiles() throws IOException{
        FileReader csvFile1 = new FileReader("src/test/java/IRTesting3.csv");
        FileReader csvFile2 = new FileReader("src/test/java/IRTesting5.csv");

        BufferedReader br1 = new BufferedReader(csvFile1);
        BufferedReader br2 = new BufferedReader(csvFile2);
        BufferedReader[] brs = new BufferedReader[2];
        brs[0] = br1;
        brs[1] = br2;

        try {
            brs[0].readLine();
            brs[1].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);


        String[] cands = election.getCandidates();
        for(int i = 0; i < cands.length; i++){
            System.out.println(cands[i]);
        }
        assertEquals("ashton", cands[0]);
        assertEquals("caleb", cands[1]);

        ArrayList<Candidate> curCandsArray = election.getCandidateArray();
        assertEquals(curCandsArray.get(0).getCandidateName(), "ashton");
        assertEquals(curCandsArray.get(1).getCandidateName(), "caleb");
        assertEquals(curCandsArray.get(0).getBallotCount(), 8);
        assertEquals(curCandsArray.get(1).getBallotCount(), 5);

        assertEquals("ashton", election.processElection());
    }


    /**
     * Tests that setCandidates(), distributeBallots(), and processElection() work with multiple files
     * Uses IRTesting3.csv and IRTesting6.csv
     * @throws IOException if IO error occurs when reading from file
     */
    @Test
    void processElectionMultipleFiles2() throws IOException{
        FileReader csvFile1 = new FileReader("src/test/java/IRTesting3.csv");
        FileReader csvFile2 = new FileReader("src/test/java/IRTesting6.csv");

        BufferedReader br1 = new BufferedReader(csvFile1);
        BufferedReader br2 = new BufferedReader(csvFile2);
        BufferedReader[] brs = new BufferedReader[2];
        brs[0] = br1;
        brs[1] = br2;

        try {
            brs[0].readLine();
            brs[1].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);


        String[] cands = election.getCandidates();
        assertEquals("ashton", cands[0]);
        assertEquals("caleb", cands[1]);
        assertEquals("garrett", cands[2]);

        ArrayList<Candidate> curCandsArray = election.getCandidateArray();
        assertEquals(curCandsArray.get(0).getBallotCount(), 4);
        assertEquals(curCandsArray.get(1).getBallotCount(), 3);
        assertEquals(curCandsArray.get(2).getBallotCount(), 8);
        assertEquals("garrett", election.processElection());

    }

    /**
     * Tests that setCandidates(), distributeBallots(), and processElection() work with multiple files
     * Uses IRTesting3.csv, IRTesting5.csv, and IRTesting6.csv
     * @throws IOException if IO error occurs when reading from file
     */
    @Test
    void processElectionMultipleFiles3() throws IOException{
        FileReader csvFile1 = new FileReader("src/test/java/IRTesting3.csv");
        FileReader csvFile2 = new FileReader("src/test/java/IRTesting5.csv");
        FileReader csvFile3 = new FileReader("src/test/java/IRTesting6.csv");

        BufferedReader br1 = new BufferedReader(csvFile1);
        BufferedReader br2 = new BufferedReader(csvFile2);
        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader[] brs = new BufferedReader[3];
        brs[0] = br1;
        brs[1] = br2;
        brs[2] = br3;

        try {
            brs[0].readLine();
            brs[1].readLine();
            brs[2].readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IRProcessing election = new IRProcessing(brs);


        ArrayList<Candidate> curCandsArray = election.getCandidateArray();
        assertEquals(curCandsArray.get(0).getCandidateName(), "ashton");
        assertEquals(curCandsArray.get(0).getBallotCount(), 7);
        assertEquals(curCandsArray.get(1).getCandidateName(), "garrett");
        assertEquals(curCandsArray.get(1).getBallotCount(), 12);
        assertEquals("garrett", election.processElection());

    }
}