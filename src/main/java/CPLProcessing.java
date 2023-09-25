import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * CPLProcessing.java
 *
 * This file implements a CPLProcessing class that is responsible for handling a CPL election. This class incorporates
 * CPLProcessing object creation, seat distribution, ballot distribution, setting election parties, and processing of
 * the CPL election itself.
 *
 * Attributes to this CPLProcessing class include an ArrayList of the parties in the election,
 * an Integer numParties with the total number of parties in the election, an Integer numAvailableSeats that stores the
 * total number of seats in the running, an Integer numBallots that indicates the total number of ballots that will
 * be processed, and an Integer numCandidates that indicates the number of candidates in the CPL election.
 *
 * @author Elias Josue Caceres
 * @author Garrett Abou-Zeid
 */
public class CPLProcessing implements IElectionProcessing {

    /**
     * ArrayList of Party objects that represent the apolitical parties in the elction
     */
    private ArrayList<Party> parties;
    /**
     * the number of political parties in the election as an int
     */
    private int numParties;
    /**
     * the number of seats up for distribution in the election as an int
     */
    private int numSeats;
    /**
     * the total number of ballots that were cast in the election as an int
     */
    private int numBallots = 0;
    /**
     * the total number of candidates participating in the election
     */
    private int numCandidates;

    /**
     * auditFileOutput object for generating an audit file.
     */
    ProcessResults auditFileOutput;
    /**
     * This is the constructor of a CPLProcessing object. It requires a location to the CPL election file. When this
     * constructor is called the processing of a CPL election as a whole is executed.
     *
     * @param brs Input stream of CPL election ballot files used to read parties in election.
     * @throws IOException Throws IOException with null as its error detail message.
     */
    public CPLProcessing(BufferedReader[] brs) throws IOException {
        auditFileOutput = new ProcessResults("CPL");
        auditFileOutput.addVotingType("Closed-Party-List");
        this.parties = new ArrayList<>();
        //Only need to call on one file to set parties
        setParties(brs[0]);
        try {
            distributeBallots(brs[0], this.parties);
            for (int i = 1; i < brs.length; i++) {
                // Skips first lines containing number of parties, party names, and candidates.
                int numParties = Integer.parseInt(brs[i].readLine());
                for (int j = 0; j < numParties + 1; j++) {
                    brs[i].readLine();
                }
                distributeBallots(brs[i], this.parties);
            }
            distributeSeats();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Party curParty : this.parties) {
            auditFileOutput.addParty(curParty.getParty(), curParty.getCandidates(), curParty.getBallotCount());
        }
        processElection();
    }

    /**
     * The distributeSeats method goes through each party in the election and allocates seats to each party according
     * to the calculated vote quota. This method also handles ties in a fair manner through random number generation.
     * @throws IOException File reading failure
     */
    private void distributeSeats() throws IOException {
        // Calculates quota by dividing number of seats by the number of ballots.

        int quota = getNumBallots() / getNumSeats();
        int numAvailableSeats = getNumSeats();

        ArrayList<Integer> remainders = new ArrayList<>();
        // Allocates seats to parties based on quota and calculates the remainders
        for (Party party : this.parties) {
            int seats = party.getBallotCount() / quota;
            if (seats >= party.getCandidates().size()) {
                party.setNumSeats(party.getCandidates().size());
                numAvailableSeats -= party.getCandidates().size();
                remainders.add(0);
            } else {
                party.setNumSeats(seats);
                numAvailableSeats -= seats;
                remainders.add(party.getBallotCount() % quota);
            }
            auditFileOutput.addSeat(party.getNumSeats(), party.getParty(), party.getBallotCount(), quota);
        }
        // Allocates the rest of the seats based on highest remainder
        while (numAvailableSeats > 0) {
            int highestRemainder = Collections.max(remainders);
            int seatIndex = remainders.indexOf((highestRemainder));
            // Checks to see if there is a tie for remainder
            ArrayList<Integer> tiedIndexes = new ArrayList<>();
            tiedIndexes.add(seatIndex);
            for (int i = 0; i < remainders.size(); i++) {
                if (seatIndex != i) {
                    if (remainders.get(i) == highestRemainder) {
                        tiedIndexes.add(i);
                    }
                }
            }
            // Removes parties where all of their candidates have been allocated to seats
            for (int i = 0; i < tiedIndexes.size(); i++) {
                if (this.parties.get(tiedIndexes.get(i)).getNumSeats() == this.parties.get(tiedIndexes.get(i)).getCandidates().size()) {
                    tiedIndexes.remove(i);
                }
            }
            if (tiedIndexes.size() > 1) {
                // Chooses from select group of parties that tied for highest remainder of ballots
                int randomNum = (int)(Math.random() * tiedIndexes.size());
                int chosenIndex = tiedIndexes.get(randomNum);
                this.parties.get(chosenIndex).setNumSeats(this.parties.get(chosenIndex).getNumSeats() + 1);
                String tiedCandidates = "";
                for(int i = 0; i < tiedIndexes.size(); i++){
                    int partyIndex = tiedIndexes.get(i);
                    tiedCandidates = tiedCandidates + this.parties.get(partyIndex).getParty() + ", ";
                }
                auditFileOutput.addTie(tiedCandidates, this.parties.get(chosenIndex).getParty());
                remainders.set(chosenIndex, 0);
            } else {
                // Adds seat with the highest remainder
                this.parties.get(seatIndex).setNumSeats(this.parties.get(seatIndex).getNumSeats() + 1);
                auditFileOutput.addRemainder(this.parties.get(seatIndex).getParty());
                remainders.set(seatIndex, 0);
            }
            numAvailableSeats--;
        }
    }
    /**
     * This method reads the CPL ballot file to determine the parties in the election. The parties are read and set
     * into a String array. The string array of the parties is then iterated through to create individual party objects
     * and their corresponding candidates.
     *
     * @param br Input stream of CPL election ballot file used to read parties in election.
     * @throws IOException Throws IOException with null as its error detail message.
     */
    private void setParties(BufferedReader br) throws IOException {
        // Gets number if parties (2nd Line)
        int numParties = Integer.parseInt(br.readLine());
        setNumParties(numParties);
        // Gets list of party names (3rd line)
        String curLine = br.readLine();
        String[] partyNames = curLine.split(",");
        // Reads candidates of each party and creates a Party object with respective party name and candidates
        int numCandidates = 0;
        for (int i = 0; i < numParties; i++) {
            curLine = br.readLine();
            ArrayList<String> candidatesOfParty = new ArrayList<>(Arrays.asList(curLine.split(",")));
            // Removes extra spaces at beginning of candidate name
            for (int j = 0; j < candidatesOfParty.size(); j++) {
                candidatesOfParty.set(j, candidatesOfParty.get(j).trim());
            }
            numCandidates += candidatesOfParty.size();
            Party party = new Party(partyNames[i].trim(), candidatesOfParty);
            this.parties.add(party);
        }
        setNumCandidates(numCandidates);
    }
    /**
     * distributeBallots reads through the ballots for CPL election and disburses votes to their according party.
     *
     * @param br Input stream of CPL election ballot file.
     * @param parties Array of the parties in the CPL election.
     * @throws IOException throws IOException with null as its error detail message.
     */
    private void distributeBallots(BufferedReader br, ArrayList<Party> parties) throws IOException {

        // Reads number of available seats
        int numSeats = Integer.parseInt(br.readLine());
        setNumSeats(numSeats);

        // Reads number of ballots that were submitted
        int numBallots = Integer.parseInt(br.readLine());
        setNumBallots(getNumBallots() + numBallots);

        // Iterate through each line and look for ballot vote position
        for(int i = 0; i < numBallots; i++){

            // Go char by char for each ballot make string 1st
            String currentBallot = br.readLine();
            // Convert Ballot string into char array
            char[] ballotArray = currentBallot.toCharArray();

            // Iterate through the char array and distribute ballot
            for(int j = 0; j < ballotArray.length; j++){

                if(ballotArray[j] == ','){
                    continue;
                }

                else if(ballotArray[j] == '1'){
                    // Unsure here if we can do this, maybe we create setter in Party class to set ballot count
                    parties.get(j).setBallotCount(parties.get(j).getBallotCount() + 1);
                }
            }
        }
    }
    /**
     * This method processes the winners of the CPL election and details to their election process. This displays
     * information on the election such as type of election, number of seats and stats for everyone such as number of
     * votes recieved.
     * @return String of winning candidates.
     */
    @Override
    public String processElection() {
        String seatWinners = "";
        System.out.println("CPL Election");
        System.out.println("Number of seats: " + getNumSeats());
        System.out.println("Number of ballots: " + getNumBallots());
        System.out.println("---------- Seats that are allocated ----------");
        for (Party party : parties) {
            for (int i = 0; i < party.getNumSeats(); i++) {
                String candidateWinner = party.getCandidates().get(i);
                seatWinners += candidateWinner + ",";
                System.out.println(candidateWinner + " (" + party.getParty() + ")");
                try {
                    auditFileOutput.addSeatWinner(party.getParty(), party.getCandidates().get(i), party.getBallotCount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return seatWinners;
    }
    /**
     * getNumParties gets the number of parties in the CPL election.
     * @return Number of parties in the CPLProcessing object.
     */
    public int getNumParties() {
        return this.numParties;
    }
    /**
     * setNumParties sets the number of parties in the CPL election.
     * @param numParties Integer input to set the number of parties in a CPLProcessing object.
     */
    public void setNumParties(int numParties) {
        this.numParties = numParties;
    }
    /**
     * getNumSeats gets the number of available seats in the CPL election.
     * @return Number of available seats in the CPLProcessing object.
     */
    public int getNumSeats() {
        return this.numSeats;
    }
    /**
     * setNumSeats sets the number of available seats in the CPL election.
     * @param numSeats Integer input to set the number of available seats in a CPLProcessing object.
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
    /**
     * getNumBallots gets the number of ballots in a CPL election.
     * @return Number of ballots in the CPLProcessing object.
     */
    public int getNumBallots() {
        return this.numBallots;
    }
    /**
     * setNumBallots is used to set the number of ballots in a CPL election.
     * @param numBallots Integer input to set the number of ballots in a CPLProcessing object.
     */
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
    /**
     * getNumCandidates gets the number of candidates in a CPL election.
     * @return Number of candidates in the CPLProcessing object.
     */
    public int getNumCandidates() {
        return this.numCandidates;
    }
    /**
     * setNumCandidates is used to set the number of candidates in a CPL election.
     * @param num Integer input to set the number of candidates in a CPLProcessing object.
     */
    public void setNumCandidates(int num) {
        this.numCandidates = num;
    }

    /**
     * Gets a list of the candidates in the election.
     * @return  a String[] containing the names of the candidates as Strings.
     */
    @Override
    public String[] getCandidates() {
        String[] candidateStrings = new String[getNumCandidates()];
        int index = 0;
        for (int i = 0; i < getNumParties(); i++) {
            ArrayList<String> candidates = parties.get(i).getCandidates();
            for (String candidate : candidates) {
                candidateStrings[index] = candidate;
                index++;
            }
        }
        return candidateStrings;
    }

    /**
     * Gets an ArrayList of Party classes
     * @return  an ArrayList of parties containing Party classes, which contain various information.
     */
    public ArrayList<Party> getParties() {
        return this.parties;
    }
}