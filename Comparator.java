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
        return state+";"+county+";"+precinct+";"+votes_dem+";"+votes_rep;
    }
    
    public static class Closeness implements Comparator<PrecinctVotes> {
        // Implement your comparator class here (see the assignment)!
    }   
}
