/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign3;

 /**
 * Implement a Comparator class based on the requirements in the assignment.
 * Complier: Using JVM
 * @author Thien Le
 */
import java.util.Comparator;

public class PrecinctVotes {

    private final String state;
    private final String county;
    private final String precinct;
    private final int votes_dem;
    private final int votes_rep;

    public PrecinctVotes(String state, String county, String precinct,
            int votes_dem, int votes_rep) {
        this.state = state;
        this.county = county;
        this.precinct = precinct;
        this.votes_dem = votes_dem;
        this.votes_rep = votes_rep;
    }

    @Override
    public String toString() {
        return state + ";" + county + ";" + precinct + ";" + votes_dem + ";" + votes_rep;
    }

    public static class Closeness implements Comparator<PrecinctVotes> {

        // Implement your comparator class here (see the assignment)!
        /* You should implement this comparator in such a way that PrecinctVotes 
        object a is less than b if the absolute value of the vote difference is 
        smaller, or if the vote difference is the same and the state, county, 
        and precinct (in that order) is smaller. Note that for one of the 
        implementations, you’ll actually need the reverse of this comparator — 
        see the reversed() method in the Comparator class for an easy solution!*/
        /**
         * Implement an override comparator method. Compare the absolute value of
         * the different between object a and b, return the boolean value. If both
         * differences are the same then compare the state/county/precinct in that
         * order.
         * @param a the first object to compare
         * @param b the second object to compare
         * @return -1 if the the first element is smaller, 1 if the second element
         * is bigger, other return precintADiif.compareTo(precintBDiff).
         */
        @Override
        public int compare(PrecinctVotes a, PrecinctVotes b) {
            
            Integer precintADiff = Math.abs(a.votes_dem - a.votes_rep);
            Integer precintBDiff = Math.abs(b.votes_dem - b.votes_rep);
            
            if (precintADiff.equals(precintBDiff)) {
                if (a.state.compareTo(b.state) < 0) {
                    return -1;
                } else if (a.state.equals(b.state) && a.county.compareTo(b.county) < 0) {
                    return -1;
                } else if (a.county.equals(b.county) && a.precinct.compareTo(b.precinct) < 0) {
                    return -1;
                } 
            }else{
                return precintADiff.compareTo(precintBDiff);
            }
            return 1;
        }
    }
}
