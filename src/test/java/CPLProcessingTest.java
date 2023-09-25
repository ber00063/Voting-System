/**
 * CPLProcessingTest.java is used for testing the methods in the CPLProcessing class.
 *
 * @author abouz009 and berg00063.
 */

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CPLProcessingTest class contains methods for testing the CPLProcessing class.
 */
class CPLProcessingTest {

    /**
     * Tests the distributeSeats() method in the CPLProcessing class to make sure the
     * correct number of seats are allocated to the correct parties.
     * Also tests with multiple file ballots.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void distributeSeats() throws IOException {
        FileReader csvFile1 = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br1 = new BufferedReader(csvFile1);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br1;
        try {
            br1.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election1 = new CPLProcessing(brs);

        assertEquals(election1.getParties().get(0).getNumSeats(), 1);
        assertEquals(election1.getParties().get(1).getNumSeats(), 1);
        assertEquals(election1.getParties().get(2).getNumSeats(), 0);
        assertEquals(election1.getParties().get(3).getNumSeats(), 1);
        assertEquals(election1.getParties().get(4).getNumSeats(), 0);
        assertEquals(election1.getParties().get(5).getNumSeats(), 0);

        FileReader csvFile2 = new FileReader("src/test/java/CPLTesting2.csv");

        BufferedReader br2 = new BufferedReader(csvFile2);
        brs[0] = br2;
        try {
            br2.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election2 = new CPLProcessing(brs);

        assertEquals(election2.getParties().get(0).getNumSeats(), 1);
        assertEquals(election2.getParties().get(1).getNumSeats(), 0);
        assertEquals(election2.getParties().get(2).getNumSeats(), 0);
        assertEquals(election2.getParties().get(3).getNumSeats(), 1);
        assertEquals(election2.getParties().get(4).getNumSeats(), 0);
        assertEquals(election2.getParties().get(5).getNumSeats(), 1);

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election3 = new CPLProcessing(brs2);
        assertEquals(election3.getParties().get(0).getNumSeats(), 1);
        assertEquals(election3.getParties().get(1).getNumSeats(), 1);
        assertEquals(election3.getParties().get(2).getNumSeats(), 0);
        assertEquals(election3.getParties().get(3).getNumSeats(), 1);
        assertEquals(election3.getParties().get(4).getNumSeats(), 0);
        assertEquals(election3.getParties().get(5).getNumSeats(), 0);

    }

    /**
     * Tests the setParties() method in the CPLProcessing class to make sure
     * a party is created with the correct party name and candidates associated with that party .
     * @throws IOException if an IO error occurs when reading from file
     */
    @Test
    void setParties() throws IOException {
        FileReader csvFile = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election = new CPLProcessing(brs);     //setParties() called

        ArrayList<Party> parts = election.getParties();     //check if parties are all correct
        ArrayList<String> cands = new ArrayList<>();

        cands.add("Foster");
        cands.add("Volz");
        cands.add("Pike");
        assertEquals(parts.get(0).getParty(), "Democratic");
        assertEquals(parts.get(0).getCandidates(), cands);

        cands.clear();
        cands.add("Green");
        cands.add("Xu");
        cands.add("Wang");
        assertEquals(parts.get(1).getParty(), "Republican");
        assertEquals(parts.get(1).getCandidates(), cands);

        cands.clear();
        cands.add("Jacks");
        cands.add("Rosen");
        assertEquals(parts.get(2).getParty(), "New Wave");
        assertEquals(parts.get(2).getCandidates(), cands);

        cands.clear();
        cands.add("McClure");
        cands.add("Berg");
        assertEquals(parts.get(3).getParty(), "Reform");
        assertEquals(parts.get(3).getCandidates(), cands);

        cands.clear();
        cands.add("Zheng");
        cands.add("Melvin");
        assertEquals(parts.get(4).getParty(), "Green");
        assertEquals(parts.get(4).getCandidates(), cands);

        cands.clear();
        cands.add("Peters");
        assertEquals(parts.get(5).getParty(), "Independent");
        assertEquals(parts.get(5).getCandidates(), cands);

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election2 = new CPLProcessing(brs2);
        ArrayList<Party> parts2 = election2.getParties();     //check if parties are all correct
        ArrayList<String> cands2 = new ArrayList<>();
        cands2.add("Foster");
        cands2.add("Volz");
        cands2.add("Pike");
        assertEquals(parts2.get(0).getParty(), "Democratic");
        assertEquals(parts2.get(0).getCandidates(), cands2);

        cands2.clear();
        cands2.add("Green");
        cands2.add("Xu");
        cands2.add("Wang");
        assertEquals(parts2.get(1).getParty(), "Republican");
        assertEquals(parts2.get(1).getCandidates(), cands2);

        cands2.clear();
        cands2.add("Jacks");
        cands2.add("Rosen");
        assertEquals(parts2.get(2).getParty(), "New Wave");
        assertEquals(parts2.get(2).getCandidates(), cands2);

        cands2.clear();
        cands2.add("McClure");
        cands2.add("Berg");
        assertEquals(parts2.get(3).getParty(), "Reform");
        assertEquals(parts2.get(3).getCandidates(), cands2);

        cands2.clear();
        cands2.add("Zheng");
        cands2.add("Melvin");
        assertEquals(parts2.get(4).getParty(), "Green");
        assertEquals(parts2.get(4).getCandidates(), cands2);

        cands2.clear();
        cands2.add("Peters");
        assertEquals(parts2.get(5).getParty(), "Independent");
        assertEquals(parts2.get(5).getCandidates(), cands2);

    }

    /**
     * Tests the distributeBallots() method in the CPLProcessing class to check that the
     * ballots are allocated correctly to their respective parties. This also test distributeBallots()
     * with an election with multiple file ballots.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void distributeBallots() throws IOException {
        FileReader csvFile = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election = new CPLProcessing(brs);

        assertEquals(election.getParties().get(0).getBallotCount(), 3);
        assertEquals(election.getParties().get(1).getBallotCount(), 2);
        assertEquals(election.getParties().get(2).getBallotCount(), 0);
        assertEquals(election.getParties().get(3).getBallotCount(), 2);
        assertEquals(election.getParties().get(4).getBallotCount(), 1);
        assertEquals(election.getParties().get(5).getBallotCount(), 1);

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election2 = new CPLProcessing(brs2);

        assertEquals(election2.getParties().get(0).getBallotCount(), 15);
        assertEquals(election2.getParties().get(1).getBallotCount(), 6);
        assertEquals(election2.getParties().get(2).getBallotCount(), 0);
        assertEquals(election2.getParties().get(3).getBallotCount(), 10);
        assertEquals(election2.getParties().get(4).getBallotCount(), 4);
        assertEquals(election2.getParties().get(5).getBallotCount(), 5);

    }

    /**
     * Tests the processElection() method in the CPLProcessing class to check that
     * the CPL Election results are correct.This also test processElection()
     * with an election with multiple file ballots.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void processElection() throws IOException {
        FileReader csvFile = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election = new CPLProcessing(brs);

        assertEquals("Foster,Green,McClure,", election.processElection());

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election2 = new CPLProcessing(brs2);

        assertEquals("Foster,Green,McClure,", election2.processElection());


    }

    /**
     * Tests the getCandidates() method in the CPLProcessing class to make sure all
     * candidates are extracted and put into a list. Also test with multiple file ballots.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void getCandidates() throws IOException {
        FileReader csvFile = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election = new CPLProcessing(brs);


        String[] candidates = election.getCandidates();

        assertEquals("Foster", candidates[0]);
        assertEquals("Volz", candidates[1]);
        assertEquals("Pike", candidates[2]);
        assertEquals("Green", candidates[3]);
        assertEquals("Xu", candidates[4]);
        assertEquals("Wang", candidates[5]);
        assertEquals("Jacks", candidates[6]);
        assertEquals("Rosen", candidates[7]);
        assertEquals("McClure", candidates[8]);
        assertEquals("Berg", candidates[9]);
        assertEquals("Zheng", candidates[10]);
        assertEquals("Melvin", candidates[11]);
        assertEquals("Peters", candidates[12]);

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election2 = new CPLProcessing(brs2);
        String[] candidates2 = election2.getCandidates();
        assertEquals("Foster", candidates2[0]);
        assertEquals("Volz", candidates2[1]);
        assertEquals("Pike", candidates2[2]);
        assertEquals("Green", candidates2[3]);
        assertEquals("Xu", candidates2[4]);
        assertEquals("Wang", candidates2[5]);
        assertEquals("Jacks", candidates2[6]);
        assertEquals("Rosen", candidates2[7]);
        assertEquals("McClure", candidates2[8]);
        assertEquals("Berg", candidates2[9]);
        assertEquals("Zheng", candidates2[10]);
        assertEquals("Melvin", candidates2[11]);
        assertEquals("Peters", candidates2[12]);

    }

    /**
     * Tests the getParties() method in the CPLProcessing class to make sure all
     * parties are extracted and put into a list. Includes test with multiple file ballots.
     * @throws IOException  if IO exception occurs when reading from csv file.
     */
    @Test
    void getParties() throws IOException {
        FileReader csvFile = new FileReader("src/test/java/CPLTesting1.csv");

        BufferedReader br = new BufferedReader(csvFile);
        BufferedReader[] brs = new BufferedReader[1];
        brs[0] = br;
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CPLProcessing election = new CPLProcessing(brs);

        ArrayList<Party> parties = election.getParties();
        assertEquals("Democratic", parties.get(0).getParty());
        assertEquals("Republican", parties.get(1).getParty());
        assertEquals("New Wave", parties.get(2).getParty());
        assertEquals("Reform", parties.get(3).getParty());
        assertEquals("Green", parties.get(4).getParty());
        assertEquals("Independent", parties.get(5).getParty());

        FileReader csvFile3 = new FileReader("src/test/java/CPLTesting1.csv");
        FileReader csvFile4 = new FileReader("src/test/java/CPLTesting2.csv");
        FileReader csvFile5 = new FileReader("src/test/java/CPLTesting3.csv");
        FileReader csvFile6 = new FileReader("src/test/java/CPLTesting4.csv");

        BufferedReader br3 = new BufferedReader(csvFile3);
        BufferedReader br4 = new BufferedReader(csvFile4);
        BufferedReader br5 = new BufferedReader(csvFile5);
        BufferedReader br6 = new BufferedReader(csvFile6);

        BufferedReader[] brs2 = new BufferedReader[4];
        brs2[0] = br3;
        brs2[1] = br4;
        brs2[2] = br5;
        brs2[3] = br6;

        try {
            br3.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br4.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br5.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br6.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CPLProcessing election2 = new CPLProcessing(brs2);
        ArrayList<Party> parties2 = election2.getParties();
        assertEquals("Democratic", parties2.get(0).getParty());
        assertEquals("Republican", parties2.get(1).getParty());
        assertEquals("New Wave", parties2.get(2).getParty());
        assertEquals("Reform", parties2.get(3).getParty());
        assertEquals("Green", parties2.get(4).getParty());
        assertEquals("Independent", parties2.get(5).getParty());
    }
}