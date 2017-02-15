import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assign3 {
    private static final int NUM_BEST = 5000;

    public static List<PrecinctVotes> getBestBySort(ArrayList<PrecinctVotes> all, int howmany) {
        all.sort(new PrecinctVotes.Closeness());
        return all.subList(0, howmany);
    }

    public static List<PrecinctVotes> getBestByArray(ArrayList<PrecinctVotes> all, int howmany) {
        return null;
    }
    
    public static List<PrecinctVotes> getBestByTree(ArrayList<PrecinctVotes> all, int howmany) {
        return null;
    }

    public static List<PrecinctVotes> getBestByPQ(ArrayList<PrecinctVotes> all, int howmany) {
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<PrecinctVotes> voteData = new ArrayList<>();
        BufferedReader fin = null;
        try {
            fin = new BufferedReader(new FileReader("vote2012.dat"));
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't open voting data file");
            System.exit(1);
        }

        try {
            String line;
            while ((line=fin.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 5) {
                    System.err.println("Bad input format - not 5 fields per line");
                    System.exit(1);
                }
                
                int dvotes = Integer.parseInt(parts[3]);
                int rvotes = Integer.parseInt(parts[4]);

                voteData.add(new PrecinctVotes(parts[0],parts[1],parts[2],dvotes,rvotes));
            }
        } catch (IOException|NumberFormatException ex) {
            System.err.println("Error reading input file");
            System.exit(1);
        }

        ArrayList<PrecinctVotes> test1 = (ArrayList<PrecinctVotes>)voteData.clone();
        List<PrecinctVotes> best1 = getBestBySort(test1, NUM_BEST);
        
        ArrayList<PrecinctVotes> test2 = (ArrayList<PrecinctVotes>)voteData.clone();
        List<PrecinctVotes> best2 = getBestByArray(test2, NUM_BEST);

        ArrayList<PrecinctVotes> test3 = (ArrayList<PrecinctVotes>)voteData.clone();
        List<PrecinctVotes> best3 = getBestByTree(test3, NUM_BEST);

        ArrayList<PrecinctVotes> test4 = (ArrayList<PrecinctVotes>)voteData.clone();
        List<PrecinctVotes> best4 = getBestByPQ(test4, NUM_BEST);

        // All of these better output NUM_BEST! You'll obviously need more
        // extensive testing procedures than just this, but it's a first step.
        
        System.out.println("best1.size(): "+best1.size());
        System.out.println("best2.size(): "+best2.size());
        System.out.println("best3.size(): "+best3.size());
        System.out.println("best4.size(): "+best4.size());
    }    
}
