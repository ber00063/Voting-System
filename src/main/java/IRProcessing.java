
/**
 * IRProcessing.java defines the IRProcessing class, which is used to process an IR election
 * and determine its results while documenting the course of the election processing.
 *
 * @author Caleb Tracy, Ashton Berg.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * IRProcessing class used for processing an IR election.
 * Implements IElectionProcessing, an interface outlining election processing.
 *
 * @author tracy255, Caleb Tracy. Ashton Berg.
 */
public class IRProcessing implements IElectionProcessing {

    /**
     * an ArrayList of Candidate objects that represents the candidates in the election
     */
    private static ArrayList<Candidate> candidates;
    /**
     * an integer that keeps track of the total number of ballots that were cast in an election.
     */
    int totalNumBallots = 0;

    /**
     * an integer to track the initial total number of ballots for the output table.
     */
    int initial_total = 0;

    /**
     * instantiates a ProcessResults instance, which is used to write election proceedings to an audit file.
     */
    ProcessResults auditFileOutput;

    /**
     * an arrayList of IRRows, used for each row in the table outputted at the end of an election.
     */
    private ArrayList<IRRow> table;

    /**
     * an arrayList holding the number of lost/deleted ballots for every round.
     */
    private ArrayList<Integer> exhausted_pile;

    /**
     * an int used to keep track of the current ballot index for distributing ballots from multiple files
     */
    int curBallotIndex;

    /**
     * Calls the setCandidates() and distributeBallots() to set up the processing of an IR
     * election and then calls processElection() to process the election.
     * Writes to audit file information about election proceedings.
     * @param brs  a BufferedReader Array, holds buffered readers that each buffer the input from a FileReader
     *             that is reading from an election information csv file
     * @throws IOException  throws an IOException if distributeBallots() throws an IOException
     *                      when called
     */
    public IRProcessing(BufferedReader[] brs) throws IOException {
        curBallotIndex = 0;
        candidates = new ArrayList<>();
        table = new ArrayList<>();
        exhausted_pile = new ArrayList<>();

        //Create ProcessResults objects for sending info to the audit file
        auditFileOutput = new ProcessResults("IR");
        auditFileOutput.addVotingType("Instant-Runoff");

        //Create candidates for candidates class variable
        //Only need to call on one file
        setCandidates(brs[0]);
//
        //Try to distribute the ballots, need to call on all files
        try {
            distributeBallots(brs[0]);
            for(int i = 1; i < brs.length; i++){
                //need to skip first three lines of files that weren't used in setCandidates()
                //brs[i].readLine();
                brs[i].readLine();
                brs[i].readLine();
                distributeBallots(brs[i]);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Add candidate info to the audit file
        for(int i = 0; i < candidates.size(); i++) {
            auditFileOutput.addCandidate(candidates.get(i).getCandidateName(), candidates.get(i).getParty(), candidates.get(i).getBallotCount());
            //Add the name, party, and initial ballot count to the table
            IRRow newRow = new IRRow(candidates.get(i).getCandidateName(), candidates.get(i).getParty());
            newRow.add_stat(candidates.get(i).getBallotCount());
            table.add(newRow);
        }

        //Start processing results
        processElection();
    }


    /**
     * Does the work of actually running an IR election to determine the winner and losers.
     * Writes proceedings to audit file.
     * @return  the name of the election winner as a String, "FAILURE" if error.
     */
    @Override
    public String processElection() {
        //This while loop should never end, returns a winner before all candidates eliminated
        while(candidates.size() > 0) {
            //System.out.println("totalnumballots: " + totalNumBallots);   //debugging
            //Loop through each candidate and check if they are a winner
            for (Candidate curCand : candidates) {
                //check if there is a winner with a majority number of ballots
                //System.out.println("curcandcount -> " + curCand.getCandidateName() + ": " + curCand.getBallotCount());  //debugging
                if (curCand.getBallotCount() > (totalNumBallots * 0.5)) {
                    //winner is candidate
                    try {
                        auditFileOutput.addWinner(curCand.getCandidateName(), curCand.getBallotCount());
                    } catch (IOException e) { throw new RuntimeException(e); }
                    System.out.println("----------WINNER: " + curCand.getCandidateName() + "----------");
                    printTable();  //Print a table to terminal with election stats
                    return curCand.getCandidateName();  //currently return is not used
                }
                //do not need to account for the possibility of a two-way tie, handled in determineLoser
            }
            //Call determineLoser, if only two left check for a tie and break it (look at activity diagram for order)
            Candidate loser = determineLoser();
            try {
                auditFileOutput.addLoser(loser.getCandidateName(), loser.getBallotCount());
            } catch (IOException e) { throw new RuntimeException(e); }
            //redistributeBallots accordingly, removes losing candidate
            redistributeBallots(loser);

            //Update table, some candidates may be removed so check if the candidate still exists.
            for(int i = 0; i < candidates.size(); i++) {
                String candName = candidates.get(i).getCandidateName();
                //Check every row to find the candidate
                for(int x = 0; x < table.size(); x++) {
                    if(table.get(x).getCandName().equals(candName)) {
                        int curNumBallots = candidates.get(i).getBallotCount();
                        table.get(x).add_stat(curNumBallots);
                    }
                }
            }
        }
        return "FAILURE";  //should never reach this point
    }

    /**
     * Gets a list of all the political parties that candidates belong to.
     * @return  a String[] containing the political parties of candidates as Strings
     */
    public String[] getParties() {
        //Used for debugging
        String[] partyStrings = new String[candidates.size()];
        for(int i = 0; i < candidates.size(); i++){
            partyStrings[i] = candidates.get(i).getParty();
        }
        return partyStrings;
    }

    /**
     * Gets a list of the candidates in the election.
     * @return  a String[] containing the names of the candidates as Strings
     */
    @Override
    public String[] getCandidates() {
        //loops through candidates and adds their names to a String array
        String[] candStrings = new String[candidates.size()];
        for(int i = 0; i < candidates.size(); i++){
            candStrings[i] = candidates.get(i).getCandidateName();
        }
        return candStrings;
    }

    /**
     * Gets the list of candidates in the election.
     * @return  an ArrayList of Candidate objects that represent the candidates in the election
     */
    public ArrayList<Candidate> getCandidateArray() {
        return candidates;
    }

    /**
     * Reads from the election information csv file to create the Candidates.
     * @param br  BufferedReader, buffers the input from a FileReader that is reading
     *            from the election information csv file
     */
    private void setCandidates(BufferedReader br) {
        //Get the 2nd line of CSV file
        String curLine;
        try {
            //read the number of candidates, then the names
            String numCands = br.readLine();
            auditFileOutput.addCandidateAmount(numCands);
            curLine = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //need to remove all whitespaces
        curLine = curLine.replaceAll("\\s+","");
        //System.out.println("curline: " + curLine);  //debugging
        String[] candNames = curLine.split(",");
        for (String curCandName : candNames) {
            //Split name from party
            //System.out.println("curCandName: " + curCandName);  //debugging
            String[] candData = curCandName.split("\\(");
            //System.out.println("cand0: " + candData[0]);  //debugging
            //System.out.println("cand1: " + candData[1]);  //debugging
            //Remove parenthesis around party
            String fixedPartyName = candData[1].replace(")", "");
            //Make a new candidate
            //System.out.println("party: " +fixedPartyName);  //debugging
            //System.out.println("name: " + candData[0]);  //debugging
            Candidate newCand = new Candidate(fixedPartyName, candData[0]);
            //Add new Candidate object into class variable candidates
            candidates.add(newCand);
        }
    }

    /**
     * Reads from the election information csv file to distribute Ballots to the Candidates.
     * @param br  BufferedReader, buffers the input from a FileReader that is reading
     *            from the election information csv file.
     * @throws IOException  if IO error occurs when reading file.
     */
    private void distributeBallots(BufferedReader br) throws IOException {
        //Get the 4th line of the CSV file
        String nextLine = br.readLine();
        //System.out.println("NUMBALLOTS: " + nextLine);
        int ballotCount = Integer.parseInt(nextLine);
        //int ballotIndex = 0;
        //System.out.println("Read: " + ballotCount);  //debugging

        //Loop through all the lines to create each individual ballot, O(2) time
        for(int i = 0; i < ballotCount; i++) {
            //Temporary variables for ballot creation
            ArrayList<String> tempRankings = new ArrayList<>();
            int curNumRankings = 0;
            String[] ballotRankings = (br.readLine()).split(",");
            //Go through each ranking and add accordingly
            for(int y = 0; y < ballotRankings.length; y++) {
                //Get candidates name for current position, if ranked add the candidates name
                //into that position in the rankings accordingly
                if(ballotRankings[y] != "") {
                    int curRanking = Integer.parseInt(ballotRankings[y]);
                    String candidateName = candidates.get(curRanking - 1).getCandidateName();
                    //System.out.println("added: " + candidateName);  //debugging
                    tempRankings.add(candidateName);
                    //Increment the num rankings
                    curNumRankings++;
                }
            }
            //Create the new ballot
            //System.out.println("ballotIndex: " + ballotIndex);  //debugging
            //System.out.println("curNumRankings: " + curNumRankings);  //debugging
            Ballot tempBallot = new Ballot(curBallotIndex, curNumRankings, tempRankings);
            //Need to add this Ballot to it's first choice's Ballots
            String candidateToFind = tempBallot.getNextCandidate();
            for (Candidate curCand : candidates) {
                if(curCand.getCandidateName().equals(candidateToFind)) {
                    curCand.addBallot(tempBallot);
                }
            }
            //curCand.get(i).addBallot(tempBallot);
            //Increment to the next ballot number
            curBallotIndex++;
            totalNumBallots++;
            initial_total++;
        }
    }


    /**
     * Determines which candidate to eliminate from the election.
     * Chooses the candidate with the least first choice votes.
     * If there is a tie for candidates with the least first choice votes, a fair coin toss is
     * simulated by randomly choosing a loser.
     * @return  the Candidate that is the loser
     */
    public Candidate determineLoser() {
        int minVotes = Integer.MAX_VALUE;
        int tempVotes;
        ArrayList<Candidate> loserCandidates = new ArrayList<>();

        for (Candidate candidate : candidates) {
            tempVotes = candidate.getBallotCount();
            if(tempVotes < minVotes){       //if candidate has fewer votes than current min, clear list and add them
                minVotes = tempVotes;
                loserCandidates.clear();
                loserCandidates.add(candidate);
            }
            else if(tempVotes == minVotes){     //if candidate has same votes as current min, add them
                loserCandidates.add(candidate);
            }
        }

        //if only one min candidate, return them, else randomly choose loser
        if(loserCandidates.size() == 1){
            return loserCandidates.get(0);
        }
        else {
            //the idea here is that there can be multiple losers tied, but only one is randomly chosen as the loser
            //gets random int from 0 to number of possible losers
            System.out.println("TIE" + loserCandidates.get(0).getCandidateName() + " " + loserCandidates.get(1).getCandidateName());
            int randIndex = (int)(Math.random() * loserCandidates.size());
            return loserCandidates.get(randIndex);
        }

    }


    /**
     * Takes a candidate and redistributes their ballots to other candidates.
     * Gives each ballot to the next ranked candidate on that ballot.
     * Accounts for lost ballots when there are no more rankings.
     * @param cand  the candidate whose votes will be redistributed
     */
    public void redistributeBallots(Candidate cand) {
        //nextCand is used in for each loop
        String nextCand;
        int numDeletedBallots = 0;
        for(Ballot curBallot: cand.getBallots()) {
            System.out.println("ballot " + curBallot.getIndex() + " : " + curBallot.getNumRankings());
            boolean updateBallotResult = curBallot.updateBallot(); //update ballot
            if (!updateBallotResult) {  //if false is returned, delete this ballot (no more rankings)
                //remove this ballot by ignoring it, once this candidate is deleted
                //the ballots are destroyed by the garbage collector
                System.out.println("REMOVED A BALLOT");
                numDeletedBallots++;
                totalNumBallots--;
            }
            else {
                Boolean checkIfCandExists = false;
                outer: while(checkIfCandExists == false) {
                    //If the candidate's name is in candidates, they are still is in the running
                    for (Candidate curCand : candidates) {
                        if (curBallot.getNextCandidate() == curCand.getCandidateName()) {
                            checkIfCandExists = true;
                            break outer;
                        }
                    }
                    updateBallotResult = curBallot.updateBallot();
                    if (!updateBallotResult) {  //if false is returned, delete this ballot (no more rankings)
                        //remove this ballot by ignoring it, once this candidate is deleted
                        //the ballots are destroyed by the garbage collector
                        System.out.println("REMOVED A BALLOT - 2");
                        numDeletedBallots++;
                        try {
                            auditFileOutput.removedBallot(curBallot.getIndex());
                        } catch (IOException e) { throw new RuntimeException(e); }
                        totalNumBallots--;
                        break outer;
                    }
                }

                if(checkIfCandExists == true) {
                    nextCand = curBallot.getNextCandidate();  //get candidate to redistribute to
                    for (Candidate curCand : candidates) {     //find candidate to redistribute to
                        if (curCand.getCandidateName().equals(nextCand)) {
                            //redistribute to candidate; addBallot() updates Candidate BallotCount
                            curCand.addBallot(curBallot);
                        }
                    }
                }
            }
        }
        System.out.println("Removed candidate: " + cand.getCandidateName());
        exhausted_pile.add(numDeletedBallots);
        candidates.remove(cand);
    }

    /**
     * Uses the table instance variable to populate and prints a table
     * containing election information for each round. Takes no parameters
     * and returns nothing.
     */
    private void printTable() {
        int maxCols = 0;  // Number of rounds

        // Record the number of rounds
        for(int i = 0; i < table.size(); i++) {
            IRRow row = table.get(i);
            ArrayList<Integer> stats = row.get_stats();
            if(maxCols < stats.size()) {
                maxCols = stats.size();
            }
        }

        // Format strings for table rows
        String alignment1 = "| %-14s | %-14s |";
        String alignment2 = " %-5d | %-5d |";
        String posAlignment2 = " %-5d | +%-4d |";
        String negAlignment2 = " %-5d | -%-4d |";

        // Print the table headers
        System.out.format("-*--------------+----------------*+");
        for(int i = 0; i < maxCols; i++) {
            System.out.format("---------------+");
        }
        System.out.format("%n|           Candidates            |");
        for(int i = 0; i < maxCols; i++) {
            System.out.format(" round %d       |", (i + 1));
        }
        System.out.format("%n-*--------------+----------------*+");
        for(int i = 0; i < maxCols; i++) {
            System.out.format("---------------+");
        }
        System.out.format("%n| Candidates    | Party           |");
        for(int i = 0; i < maxCols; i++) {
            System.out.format(" Votes |  +/-  |");
        }
        System.out.format("%n-*--------------+----------------*+");
        for(int i = 0; i < maxCols; i++) {
            System.out.format("---------------+");
        }
        System.out.format("%n");

        // Print round information
        for(int i = 0; i < table.size(); i++) {
            IRRow row = table.get(i);
            ArrayList<Integer> temp = row.get_stats();
            System.out.format(alignment1, row.getCandName(), row.getCandParty());
            int prev = 0;
            for(Integer num : temp) {
                int countChange = num - prev;
                if(countChange < 0) {
                    System.out.format(negAlignment2, num, countChange);
                }
                else System.out.format(posAlignment2, num, countChange);
                prev = num;
            }
            System.out.format("%n-*--------------+----------------*+");
            for(int x = 0; x < maxCols; x++) {
                System.out.format("---------------+");
            }
            System.out.format("%n");
        }

        // Print table footers
        System.out.format("| EXHAUSTED PILE |                |");
        int prev = 0;
        for(Integer num : exhausted_pile) {
            System.out.format(posAlignment2, num, num - prev);
            prev = num;
        }
        System.out.format("%n-*--------------+----------------*+");
        for(int x = 0; x < maxCols; x++) {
            System.out.format("---------------+");
        }
        System.out.format("%n| TOTALS         |                |");
        System.out.format(alignment2, initial_total, initial_total);
        System.out.format("%n");
        System.out.format("-*---------------+---------------*+-------+-------+%n");
    }
}
