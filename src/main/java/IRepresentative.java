
/**
 * IRepresentative.java defines the IRepresentative interface that is implemented by
 * the Party and Candidate classes.
 * It provides the outline for a representative class.
 *
 * @author tracy255, Caleb Tracy.
 */

/**
 * IRepresentative interface provides the outline for a representative class.
 *
 */
public interface IRepresentative {

    /**
     * Gets the number of ballots, or votes, a representative has.
     * @return  an integer representing the number of ballots belonging to the representative.
     */
    public int getBallotCount();


    /**
     * Gets the party that the representative belongs to.
     * @return  the name of the representative's party as a String.
     */
    public String getParty();

}