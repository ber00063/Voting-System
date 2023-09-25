
/**
 * Ballot.java defines the Ballot class which is used for creating Ballot objects and
 * related methods on them to be used during an IR election.
 * Ballot objects represent ballots in an IR election and are associated with Candidates.
 *
 * @author Ashton Berg, Caleb Tracy.
 */

import java.util.ArrayList;

/**
 * A Ballot represents a ballot in an IR election with the candidates ranked.
 * Associated with a candidate who is ranked as the ballot's first choice vote.
 */
public class Ballot {

    /**
     * unique identification value for a ballot as an int
     */
    private int ballotIndex;  //starts at 0
    /**
     * the number of candidates ranked on a ballot as an int
     */
    private int numRankings; //will always start with at least 1
    //private rankings
    /**
     * ArrayList of strings that represent the names of the candidates on a ballot
     */
    private ArrayList<String> candidates;

    /**
     * Constructor for a Ballot object, creates a Ballot.
     * Used to create Ballots when first reading from the election information csv file.
     * @param ballotIndex  unique ballot id number for identification, corresponds to its
     *                     order of occurrence in the election information csv file, first
     *                     ballot read and created has ballotIndex=0, second ballot has
     *                     ballotIndex=1, and so on.
     * @param numRankings  current number of candidates on the ballot.
     * @param candidates   list of the candidates on the ballot in order of their vote rank.
     */
    public Ballot(int ballotIndex, int numRankings, ArrayList<String> candidates) {
        this.ballotIndex = ballotIndex;
        this.numRankings = numRankings;
        this.candidates = candidates;
        //TODO: rankings replaced by curCandidate?
    }

    /**
     * Gets the current first vote candidate on a ballot.
     * Since the ArrayList of candidates is in order of the ballot vote rank,
     * the candidate in the first index of candidates is the first vote candidate.
     * Used when a candidate is being eliminated and ballots are being redistributed
     * to find the next candidate in the voting rank order.
     * Simply returns the candidate in the first index of candidates.
     * @return current first vote candidate on a ballot
     */
    public String getNextCandidate() {
        return candidates.get(0);
    }


    /**
     * Gets a ballot's index value.
     * A ballot's index value is a unique id number, used for identifying ballots.
     * @return the ballot's current ballot index value, ballotIndex.
     */
    public int getIndex() {
        return ballotIndex;
    }


    /**
     * Updates a ballot by removing their first choice candidate and decrementing the number
     * of candidates, numRankings, left on the ballot.
     * Used when redistributing ballots from an eliminated candidate.
     * @return  a boolean, true meaning a ballot still has ranked candidates remaining after
     *          being updated, false meaning a ballot no longer has any remaining candidates
     *          after being updated.
     */
    public boolean updateBallot() {

        if(this.numRankings > 1){
            candidates.remove(0);
            this.numRankings--;
            return true;
        }
        else if (this.numRankings == 1) {
            //if the ballot only has 1 ranking, then after removing the current candidate, it
            //won't have any candidates to redistribute to, so it can be discarded.
            candidates.remove(0);
            this.numRankings--;
            return false;
        }
        else {
            return false;
        }

    }


    /**
     * Gets the number of candidates left on a ballot, numRankings.
     * Used to ensure a ballot still has candidates left.
     * @return  the number of candidates left on a ballot, numRankings
     */
    public int getNumRankings() {
        return numRankings;
    }

}
