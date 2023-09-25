/**
 * POProcessing.java defines the POProcessing class, which is used to process a PO election
 * and determine its results.
 *
 * @author Ashton Berg.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * POProcessing class used for processing a PO election.
 * Implements IElectionProcessing, an interface outlining election processing.
 *
 */
public class POProcessing implements IElectionProcessing{
    /**
     * an ArrayList of Strings that represent the names of candidates.
     */
    private static ArrayList<String> candidates;
    /**
     * an ArrayList of Strings that represents the political parties of candidates
     * candidateParties.get(i corresponds to candidates.get(i).
     */
    private static ArrayList<String> candidateParties;
    /**
     * an Array of integers that represents the number of votes each candidate received
     * candidatesBallotCounts[i] corresponds to candidates.get(i).
     */
    private static Integer[] candidatesBallotCounts;
    /**
     * an Integer that stores the total number of ballots cast in the election.
     */
    private static int totalNumBallots;
    /**
     * an Integer that stores the number of candidates in the election.
     */
    private static int numCandidates;

    /**
     * Calls the setCandidatesAndParties() and updateBallotCounts() methods to set up the processing of a PO
     * election and then calls processElection() to process the election.
     * @param br  a BufferedReader, buffers the input from a FileReader that is reading
     *            from the election information csv file.
     * @throws RuntimeException  throws a RuntimeException if call to updateBallotCounts() throws
     *                           a RuntimeException or if call to setCandidatesAndParties() throws
     *                           a RuntimeException.
     */
    public POProcessing(BufferedReader br) throws RuntimeException {
        candidates = new ArrayList<String>();
        candidateParties = new ArrayList<String>();
        totalNumBallots = 0;

        try {
            setCandidatesAndParties(br);
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }

        candidatesBallotCounts = new Integer[numCandidates];
        //for some reason candidatesBallotCandidates was getting instantiated with all null values
        //instead of 0, so I just added this for loop
        for(int i = 0; i < numCandidates; i++){
            candidatesBallotCounts[i] = 0;
        }

        try {
            updateBallotCounts(br);
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }

        processElection();
        getVotePercents();
    }

    /**
     * Does the work of actually running a PO election to determine the winner.
     * Prints the name of the winner, and if there was a tie, prints the names of those involved
     * in the tie and who won.
     * @return  a String representing the name of the election winner.
     */
    public String processElection(){
        int max = -1;
        ArrayList<String> WinnerCandidates = new ArrayList<>();

        for(int i = 0; i < numCandidates; i++){
            if(candidatesBallotCounts[i] > max){
                max = candidatesBallotCounts[i];
                WinnerCandidates.clear();
                WinnerCandidates.add(candidates.get(i));
            }
            else if(candidatesBallotCounts[i] == max){
                WinnerCandidates.add(candidates.get(i));
            }
        }

        if(WinnerCandidates.size() == 1){
            //no tie, return winner
            System.out.println(WinnerCandidates.get(0) + " WON THE ELECTION");
            return WinnerCandidates.get(0);
        }
        else{
            //tie for winner, randomly choose
            int randInd = (int)(Math.random()*WinnerCandidates.size());
            String tiedCands = "";
            for(int i = 0; i < WinnerCandidates.size()-1; i++){
                tiedCands += (WinnerCandidates.get(i) + ", ");
            }
            tiedCands += WinnerCandidates.get(WinnerCandidates.size()-1);
            System.out.println("TIE BETWEEN: " + tiedCands);
            System.out.println(WinnerCandidates.get(randInd) + " RANDOMLY SELECTED AS WINNER");
            return WinnerCandidates.get(randInd);
        }
    }

    /**
     * Reads from the election information CSV file to determine the candidates and parties and add
     * them to a String ArrayList candidates and String ArrayList candidateParties.
     * @param br a BufferedReader, buffers the input from a FileReader that is reading
     *           from the election information csv file.
     * @throws RuntimeException  throws a RuntimeException if attempting to read from the
     *                           election information CSV file throws an IOException.
     */
    public void setCandidatesAndParties(BufferedReader br) throws RuntimeException{
        String curLine;
        try {

            //read the number of candidates
            String numCands = br.readLine();
            //set numCandidates
            numCandidates = Integer.parseInt(numCands);
            //read candidate names and parties
            curLine = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //get rid of all [ symbols, ] symbols, and whitespace
        curLine = curLine.replaceAll("\\[", "");
        curLine = curLine.replaceAll("]", "");
        curLine = curLine.replaceAll("\\s", "");

        String[] candsAndParties = curLine.split(",");

        for(int i = 0; i < candsAndParties.length; i+=2){
            candidates.add(candsAndParties[i]);
            candidateParties.add(candsAndParties[i+1]);
        }

    }


    /**
     * Reads from the election information CSV file to distribute votes to candidates by updating
     * the values in Integer[] candidatesBallotCounts.
     * @param br  a BufferedReader, buffers the input from a FileReader that is reading
     *      *           from the election information csv file.
     * @throws RuntimeException  throws RuntimeException if attempting to read from the
     *                           election information CSV file throws an IOException.
     */
    public void updateBallotCounts(BufferedReader br) throws RuntimeException{
        String curLine;
        int ind;
        try {

            //read the number of ballots, convert to int, and add to totalNumBallots
            String numBallotsString = br.readLine();
            int numBallots = Integer.parseInt(numBallotsString);
            totalNumBallots += numBallots;
            for(int i = 0; i < numBallots; i++){
                curLine = br.readLine();
                ind = curLine.indexOf("1");
                candidatesBallotCounts[ind]++;
                //assuming each ballot has a vote, doesn't handle case where a ballot doesn't
                //have a 1 indicating their vote
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used to get the number of votes each candidate received.
     * @return  candidatesBallotCounts, the Integer[] representing the number of votes each
     *          candidate received.
     */
    public Integer[] getCandidatesBallotCounts(){
        return candidatesBallotCounts;
    }

    /**
     * Used to get the names of the candidates in the election, as a String[].
     * @return  a String[] containing the names of the candidates in the election.
     */
    public String[] getCandidates() {
        //loops through candidates and adds their names to a String array
        String[] candStrings = new String[candidates.size()];
        for(int i = 0; i < candidates.size(); i++){
            candStrings[i] = candidates.get(i);
        }
        return candStrings;
    }

    /**
     * Used to get the names of the candidates in the election, as a String ArrayList.
     * @return  candidates, the String ArrayList containing the names of the candidates in the election.
     */
    public ArrayList<String> getCandidatesArrayList(){
        return candidates;
    }

    /**
     * Used to get the parties involved in the election, as a String ArrayList.
     * @return  candidateParties, the String ArrayList containing the names of the parties in the election
     */
    public ArrayList<String> getCandidateParties(){
        return candidateParties;
    }

    /**
     * Used to get the number of ballots cast in the election.
     * @return  totalNumBallots, the Integer representing the number of ballots cast in the election.
     */
    public int getTotalNumBallots(){
        return totalNumBallots;
    }

    /**
     * Used to calculate and display the percentage of votes each candidate received.
     * Prints the name of the candidate, their party, and the percent of votes they received.
     * @return  a String[] containing each candidate, their party, and the percent of votes they received
     *          as a String.
     */
    public String[] getVotePercents(){
        String[] percents = new String[numCandidates];

        for(int i = 0; i < numCandidates; i++){
            float perc = ((float)candidatesBallotCounts[i] / (float)totalNumBallots) * 100;
            percents[i] = candidates.get(i) + ", " + candidateParties.get(i) + ": " + String.format("%.2f", perc) + "%";
            System.out.println(percents[i]);
        }

        return percents;
    }

}
