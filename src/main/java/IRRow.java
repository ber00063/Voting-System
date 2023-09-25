
/**
 * IRRow.java holds row data that is printed in a single row in the IR table results at the end
 * of an IR election. One row is created for each candidate, and updated during election processing.
 *
 * @author tracy255
 */

import java.util.ArrayList;

/**
 * Class for holding IR table data for each candidate.
 */
public class IRRow {

    /**
     * The candidate's name as a String
     */
    private String CandName;

    /**
     * The name of the political party the candidate belongs to as a String
     */
    private String CandParty;

    /**
     * Every element represents the candidate's number of ballots for a new round.
     */
    private ArrayList<Integer> ballot_stats;

    /**
     * Constructor for an IRRow object.
     * @param candParty  the name of the political party the candidate belongs to, Ex: Democrat.
     * @param candName  the candidate's name, Ex: Biden.
     */
    public IRRow(String candName, String candParty) {
        this.CandName = candName;
        this.CandParty = candParty;
        this.ballot_stats = new ArrayList<>();
    }

    /**
     * Gets the candidate's name.
     * @return row candidate's name as a string
     */
    public String getCandName() {
        return CandName;
    }

    /**
     * Set the candidate's name for the row
     * @param candName String of the candidate's name
     */
    public void setCandName(String candName) {
        CandName = candName;
    }

    /**
     * Gets the candidate's party.
     * @return row candidate's party as a string
     */
    public String getCandParty() {
        return CandParty;
    }

    /**
     * Set the candidate's party for the row
     * @param candParty String of the candidate's party
     */
    public void setCandParty(String candParty) {
        CandParty = candParty;
    }

    /**
     * Get the stats arrayList containing per round ballots
     * @return arrayList containing ints for the candidate's number of ballots per round
     */
    public ArrayList<Integer> get_stats() {
        return ballot_stats;
    }

    /**
     * Add a new round entry for the row's candidate.
     * @param ballot_count Number of ballots the candidate has in the round.
     */
    public void add_stat(int ballot_count) {
        this.ballot_stats.add(ballot_count);
    }

    /**
     * Get the length of the stats arrayList.
     * @return integer for the length of the
     */
    public int get_length() {
        return this.ballot_stats.size();
    }
}
