/**
 * ProcessResults.java is used for writing the proceedings and results of an election to an audit file,
 * which is a .txt file with the name <electiontype><date>.txt
 *
 * @author tracy255, cacer019
 */

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * ProcessResults is used in election processing to write election results and proceedings
 * to an audit file.
 *
 * @author everyone
 */
public class ProcessResults {

    /**
     * File type, holds the file being used for the audit during the duration of election processing.
     */
    private File auditFile;

    /**
     * Creates an audit file for election results and proceedings.
     * @param electionType String contained IR or CPL
     * @throws IOException  if IO error occurs when creating the audit file.
     */
    public ProcessResults(String electionType) throws IOException {
        //get today's today to add onto the file title
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = formatter.format(date);

        String fileName = "auditFile-" + electionType + "-" + strDate + ".txt";
        File newFile = new File(fileName);
        auditFile = newFile;
        if(!auditFile.exists()) {
            auditFile.createNewFile();
        }
//        if(!newFile.createNewFile()) {
//            //erase the current contents if this file already exists
//            PrintWriter writer = new PrintWriter(newFile);
//            writer.append("");
//            writer.flush();
//        }
    }


    /**
     * Writes the type of election to the audit file.
     * @param type  the type of the election, IR or CPL, as a String.
     * @throws IOException  if IO error occurs when writing to audit file.
     */
    public void addVotingType(String type) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Election Type: " + type + "\n");
        bw.close();
    }


    /**
     * Writes the number of candidates to the audit file.
     * @param num  the number of candidates in the election as a String.
     * @throws IOException if the audit file cannot be written into
     */
    void addCandidateAmount(String num) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Number of candidates: " + num + "\n");
        bw.close();
    }

    /**
     * Writes a candidate to the audit file.
     * Adds candidate name, political party, and initial number of ballots they received.
     * @param name  the name of the candidate as a String.
     * @param party  the name of the candidate's political party as a String.
     * @param initialBallots  the number of initial ballots the candidate received as an int.
     * @throws IOException  if IO error occurs when writing to the audit file.
     */
    void addCandidate(String name, String party, int initialBallots) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("New Candidate:\n+++Candidate name: " + name + "\n+++Party name: " + party + "\n");
        bw.write("+++Initial ballot count: " + Integer.toString(initialBallots) + "\n");
        bw.close();
    }

    /**
     * Writes a candidate who was eliminated from the election to the audit file.
     * Adds the candidate's name and their final ballot count before elimination.
     * @param name  the name of the eliminated candidate as a String.
     * @param finalBallotCount  the candidate's final ballot count as an int.
     * @throws IOException  if an IO error occurs when writing to the audit file.
     */
    void addLoser(String name, int finalBallotCount) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Removed loser:\n+++Candidate name: " + name + "\n");
        bw.write("+++Final ballot count: " + Integer.toString(finalBallotCount) + "\n");
        bw.close();
    }

    /**
     * Writes that a ballot was removed if all its candidates were eliminated to the audit file.
     * Writes the unique ballot index to the file for identification.
     * @param index  the ballot index of the removed ballot as an int.
     * @throws IOException  if IO error occurs when writing to the audit file.
     */
    void removedBallot( int index) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        //bw.write("Ballot removed:\n+++Final candidate name: " + name + "\n");
        //bw.write("+++Ballot index: " + Integer.toString(index) + "\n");
        bw.write("Ballot removed:\n+++Ballot index: " + Integer.toString(index) + "\n");
        bw.close();
    }

    /**
     * Writes the winner of the election to the audit file.
     * Writes the winner's name and the final number of ballots they received.
     * @param name  the name of the winner as a String.
     * @param finalBallotCount  the winner's final number of ballots as an int.
     * @throws IOException  if IO error occurs when writing to the audit file.
     */
    void addWinner(String name, int finalBallotCount) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("ELECTION WINNER:\n+++Candidate name: " + name + "\n");
        bw.write("+++Final ballot count: " + Integer.toString(finalBallotCount) + "\n");
        bw.write("\n\n\n");
        bw.close();
    }

    /**
     * Writes the Parties in the running for CPL Election.
     * @param party Name of the parties in CPL election.
     * @param candidates String of Candidates for said party.
     * @param partyBallots number of ballots in a party.
     * @throws IOException Throws IOException with null as its error detail message.
     */
    void addParty(String party, ArrayList candidates, int partyBallots) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("New Party:\n+++Party name: " + party + "\n+++Party Candidates: " + candidates.toString() + "\n");
        bw.write("+++Initial Party ballot count: " + Integer.toString(partyBallots) + "\n");
        bw.close();
    }

    /**
     * Writes Party Winner and the candidate to the audit file.
     * @param party Names of the party
     * @param candidate Name of the candidate that won the seat.
     * @param partyBallots The number of ballots voted to respective party.
     * @throws IOException Throws IOException with null as its error detail message.
     */
    void addSeatWinner(String party, String candidate, int partyBallots) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("PARTY WINNER:\n+++ : " + party + "Seat Winner: " + candidate+ "\n");
        bw.write("+++Final Party ballot count: " + Integer.toString(partyBallots) + "\n");
        bw.write("\n\n\n");
        bw.close();
    }

    /**
     * Writes the allocation of seats for a CPL election.
     * @param seatNums Number of seats being allocated.
     * @param party Party recieving allocated seats.
     * @param partyBallots Number of ballots for respective party.
     * @param quota The quota used to determine remainder winner.
     * @throws IOException IOException Throws IOException with null as its error detail message.
     */
    void addSeat(int seatNums, String party, int partyBallots, int quota) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(seatNums +" SEATS ALLOCATED TO:\n+++ : " + party + "\nTOTAL BALLOTS TO PARTY: " + Integer.toString(partyBallots) + "\nLARGEST REMAINDER QUOTA: " + Integer.toString(quota) + "\n");
        bw.write("\n\n\n");
        bw.close();
    }

    /**
     * Writes that a TIE occurs in CPL election. Shows list of tied parties and chosen winner at random.
     * @param tiedParties arrayList of ties parties.
     * @param chosenWinner The winner that was chosen at random.
     * @throws IOException IOException Throws IOException with null as its error detail message.
     */
    void addTie(String tiedParties, String chosenWinner) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("TIED PARTIES:\n+++ " + tiedParties);
        bw.write("\nWINNER OF TIE: " + chosenWinner);
        bw.write("\n\n\n");
        bw.close();
    }

    /**
     * Writes to the audit winner of remaining seats.
     * @param party This is the party that won the remainder.
     * @throws IOException IOException Throws IOException with null as its error detail message.
     */
    void addRemainder(String party) throws IOException {
        FileWriter fw = new FileWriter(auditFile.getName(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("REMAINING SEAT ALLOCATED TO:\n+++ : " + party + " DUE TO HIGHER REMAINDER" + "\n");
        bw.write("\n\n\n");
        bw.close();
    }

}
