/**
 * Party.java defines the Party class which is used to create Party objects and related methods on them.
 * Party objects represent political parties in a CPL election.
 *
 * @author abouz009
 */

import java.util.ArrayList;

/**
 * The Party class creates Party objects that represent political parties in a CPL election.
 * It implements IRepresentative, which creates the outline for a representative.
 *
 * @author Garrett
 */
public class Party implements IRepresentative {

    /**
     * Arraylist of strings, name of each candidate in order.
     */
    private ArrayList<String> candidates;
    /**
     * Integer, number of seats available
     */
    private int numSeats;
    /**
     * String for the party's name
     */
    private String partyName;
    /**
     * Integer, number of ballots currently allocated to this party
     */
    private int ballotCount;

    /**
     * Constructor for a Party object, creates a Party and sets its name and its candidates.
     * @param party  the name of the political party as a String.
     * @param candidateList  the names of the candidates that belong to this party as an ArrayList
     *                       of strings.
     */
    public Party(String party, ArrayList<String> candidateList) {
        this.partyName = party;
        this.candidates = candidateList;
    };

    /**
     * Adds a candidate to the list of the Party's candidates.
     * @param candidateName  the name of the candidate to add as a String.
     */
    public void addCandidate(String candidateName) {
        this.candidates.add(candidateName);
    }

    /**
     * Removes a candidate from the Party's list of candidates.
     * @param candidateToRemove  the name of the candidate to remove as a String.
     */
    public void removeCandidate(String candidateToRemove) {
        this.candidates.remove(candidateToRemove);
    }

    /**
     * Gets a list of the names of the candidates that belong to the Party.
     * @return  the names of the Party's candidates as an ArrayList of Strings.
     */
    public ArrayList<String> getCandidates() {
        return this.candidates;
    }

    /**
     * Gets the number of seats allocated to the Party.
     * Number of seats determined by processing the election.
     * @return  the number of seats received by the party as an int.
     */
    public int getNumSeats() {
        return this.numSeats;
    }

    /**
     * Gives the Party seats by setting their number of seats to the number they received.
     * @param seats  the number of seats the Party received as an int.
     */
    public void setNumSeats(int seats) {
        this.numSeats = seats;
    }

    /**
     * Gets the number of ballots the Party received.
     * @return  the number of ballots the Party received as an int.
     */
    @Override
    public int getBallotCount() {
        return this.ballotCount;
    }

    /**
     * Gives the Party its ballots it received by setting its number of ballots.
     * @param numBallots  the number of ballots to set the Party's ballot count to.
     */
    public void setBallotCount(int numBallots) {
        this.ballotCount = numBallots;
    }

    /**
     * Gets the Party's name.
     * @return  the name of the Party as a String.
     */
    @Override
    public String getParty() {
        return this.partyName;
    }
}
