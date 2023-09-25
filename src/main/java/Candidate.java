
/**
 * Candidate.java defines the Candidate class which is used for creating Candidate objects and
 * related methods on them to be used during an IR election.
 * Candidate objects represent candidates in an IR election, and each Candidate has ballots
 * associated with them that represent the ballots that candidate received during the election.
 *
 * @author Caleb Tracy, Ashton Berg.
 */

import java.util.ArrayList;

/**
 * A Candidate represents a candidate in an IR election.
 * Implements IRepresentative, an interface used for outlining a representative in an election.
 */
public class Candidate implements  IRepresentative {

    /**
     * the number of ballots a candidate has as an int
     */
    private int ballotCount;
    /**
     * the name of the political party the candidate belongs to as a String
     */
    private String partyName;
    /**
     * the candidate's name as a String
     */
    private String candidateName;
    /**
     * ArrayList of Ballots that represent the ballots belonging to a candidate
     */
    private ArrayList<Ballot> ballots;

    /**
     * Constructor for a Candidate object, creates a Candidate with specified party name and candidate name.
     * Used to create Candidates when first reading from the election information csv file.
     * @param partyName  the name of the political party the candidate belongs to, Ex: Democrat.
     * @param candidateName  the candidate's name, Ex: Biden.
     */
    public Candidate(String partyName, String candidateName) {
        this.partyName = partyName;
        this.candidateName = candidateName;
        this.ballots = new ArrayList<>();
    }

    /**
     * Removes a Ballot from a Candidate.
     * Decrements ballotCount, the number of Ballots a candidate has.
     * @param index  the index correpsonding to the Ballot to remove
     */
    public void removeBallot(int index) {
        ballots.remove(index);
        this.ballotCount--;
    }


    /**
     * Gives a Ballot to a Candidate.
     * Increments that Candidates ballot count.
     * @param nextBallot  the Ballot to give to the Candidate
     */
    public void addBallot(Ballot nextBallot) {
        this.ballots.add(nextBallot);
        this.ballotCount++;
    }


    /**
     * Gets the number of Ballots a Candidate has.
     * @return  an int representing the number of ballots a Candidate has
     */
    @Override
    public int getBallotCount() { return this.ballotCount; }


    /**
     * Gets the political party the Candidate belongs to.
     * @return  a String representing the name of the Candidate's political party.
     */
    @Override
    public String getParty() {
        return this.partyName;
    }


    /**
     * Gets the Ballots belonging to the Candidate.
     * @return  an ArrayList of Ballots that belong to the Candidate.
     */
    public ArrayList<Ballot> getBallots() { return this.ballots; }


    /**
     * Gets the Candidate's name.
     * @return  a String representing the Candidate's name.
     */
    public String getCandidateName() {
        return this.candidateName;
    }
}
