/**
 * Implement the four algorithms to provide m amount of smallest items with the 
 * narrowest margin of victory.
 * Complier: Using JVM
 * @author Thien Le
 */
package assign3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator; //added
import java.util.List;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Assign3 {

    private static final int NUM_BEST = 100;

    /**
     * Sorting all the data first. This algorithm sorts all n items, and then
     * copies the smallest m items into a new list. The running will be Θ(n log
     * n).
     *
     * @param all the array list that contain n amount of items.
     * @param howmany the m amount of items.
     * @return the sorted ArrayList
     */
    public static List<PrecinctVotes> getBestBySort(ArrayList<PrecinctVotes> all, int howmany) {
        all.sort(new PrecinctVotes.Closeness());
        return all.subList(0, howmany);
    }

    /**
     * Keep the m amount of the smallest items in an array. This algorithm will
     * takes Θ(nm) time, compare one item at a time by keeping track of the m
     * smallest items in an unsorted array.
     *
     * @param all the array list that contain n amount of items.
     * @param howmany the m amount of items.
     * @return the sorted ArrayList
     */
    public static List<PrecinctVotes> getBestByArray(ArrayList<PrecinctVotes> all, int howmany) {

        Comparator comparator = new PrecinctVotes.Closeness();
        List<PrecinctVotes> myArrayList = new ArrayList<PrecinctVotes>();
        myArrayList = all.subList(0, howmany);
//        myArrayList.sort(Collections.reverseOrder(comparator));
//            
//      for (int i = 0; i < howmany; i++) {
//            myArrayList.add(all.get(i));
//      }
        myArrayList.sort(comparator);
        for (int i = howmany; i < all.size(); i++) {
            PrecinctVotes max = Collections.max(myArrayList, comparator);
            if (comparator.compare(all.get(i), max) < 0) {
                myArrayList.add(all.get(i));
                myArrayList.remove(max);
            }
        }
        return myArrayList;
    }

    /**
     * Keeping the smallest m items in a red-black tree. Using a TreeSet to keep 
     * all items in a red-black tree. The first m items are simply added to the 
     * tree, and then once there are m items in the tree the
     * algorithm can check in O(log m) time whether a new item should be
     * inserted into the tree (removing the largest item so the size remains m)
     * to find the largest item in the tree (for comparison) you should use
     * the last() method, and to remove the largest use pollLast(). Takes Θ(n
     * log m) time.
     *
     * @param all the array list that contain n amount of items.
     * @param howmany the m amount of items.
     * @return the sorted ArrayList
     */
    public static List<PrecinctVotes> getBestByTree(ArrayList<PrecinctVotes> all, int howmany) {

        Comparator comparator = new PrecinctVotes.Closeness();
        TreeSet<PrecinctVotes> myTreeSet = new TreeSet<PrecinctVotes>(comparator);
        ArrayList<PrecinctVotes> myArrayList = new ArrayList<PrecinctVotes>();

        //Adding m amount of elements into the TreeSet.
        for (int i = 0; i < howmany; i++) {
            myTreeSet.add(all.get(i));
        }

        //Compare one element from all arrayList to the max of Treesort.
        for (int i = howmany; i < all.size(); i++) {
            if (comparator.compare(all.get(i), myTreeSet.last()) < 0) {
                myTreeSet.pollLast();
                myTreeSet.add(all.get(i));
            }
        }
        //A copy version of TreeSet into an arrayList.
        for (PrecinctVotes i : myTreeSet) {
            myArrayList.add(i);
        }
        return myArrayList;
    }

    /**
     * Keeping the smallest m items in a binary heap. Locate the maximum item
     * out of the m values. Use the standard Java PriorityQueue class, but the
     * Comparator should make a max heap! Once m items are put into a max heap,
     * you can quickly locate the largest item (using method peek()) and remove
     * it if necessary (using method poll(). This algorithm takes Θ(n log m)
     * time.
     *
     * @param all the array list that contain n amount of items.
     * @param howmany the m amount of items.
     * @return the sorted ArrayList
     */
    public static List<PrecinctVotes> getBestByPQ(ArrayList<PrecinctVotes> all, int howmany) {
        Comparator comparator = new PrecinctVotes.Closeness();
        PriorityQueue<PrecinctVotes> myMaxHeap
                = new PriorityQueue<PrecinctVotes>(howmany, Collections.reverseOrder(comparator));
        ArrayList<PrecinctVotes> myArrayList = new ArrayList<PrecinctVotes>();

        for (int i = 0; i < howmany; i++) {
            myMaxHeap.add(all.get(i));
        }

        for (int i = howmany; i < all.size(); i++) {
            if (comparator.compare(all.get(i), myMaxHeap.peek()) < 0) {
                myMaxHeap.add(all.get(i));
                myMaxHeap.poll();
            }
        }

        for (PrecinctVotes i : myMaxHeap) {
            myArrayList.add(i);
        }
        myArrayList.sort(comparator);
        return myArrayList;
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
            while ((line = fin.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 5) {
                    System.err.println("Bad input format - not 5 fields per line");
                    System.exit(1);
                }

                int dvotes = Integer.parseInt(parts[3]);
                int rvotes = Integer.parseInt(parts[4]);

                voteData.add(new PrecinctVotes(parts[0], parts[1], parts[2], dvotes, rvotes));
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error reading input file");
            System.exit(1);
        }

        ArrayList<PrecinctVotes> test1 = (ArrayList<PrecinctVotes>) voteData.clone();
        List<PrecinctVotes> best1 = getBestBySort(test1, NUM_BEST);

        ArrayList<PrecinctVotes> test2 = (ArrayList<PrecinctVotes>) voteData.clone();
        List<PrecinctVotes> best2 = getBestByArray(test2, NUM_BEST);

        ArrayList<PrecinctVotes> test3 = (ArrayList<PrecinctVotes>) voteData.clone();
        List<PrecinctVotes> best3 = getBestByTree(test3, NUM_BEST);

        ArrayList<PrecinctVotes> test4 = (ArrayList<PrecinctVotes>) voteData.clone();
        List<PrecinctVotes> best4 = getBestByPQ(test4, NUM_BEST);

        // All of these better output NUM_BEST! You'll obviously need more
        //extensive testing procedures than just this, but it's a first step.
        System.out.println("=======================TEST 1=======================");
        System.out.println("\t\t   best1.size(): " + best1.size() + "\n");
        int i = 1;
        for (PrecinctVotes s : best1) {
            System.out.println(i + "-->" + s);
            i++;
        }
        System.out.println("=======================TEST 2=======================");
        System.out.println("\t\t   best2.size(): " + best2.size() + "\n");
        int j = 1;
        for (PrecinctVotes s : best2) {
            System.out.println(j + "-->" + s);
            j++;
        }
        //System.out.println("best3.size(): "+best3.size());
        System.out.println("=======================TEST 3=======================");
        System.out.println("\t\t   best3.size(): " + best3.size() + "\n");
//        int k = 1;
//        for (PrecinctVotes s : best3) {
//            System.out.println(k + "-->" + s);
//            k++;
//        }
        System.out.println("=======================TEST 4=======================");
        System.out.println("\t\t   best4.size(): " + best4.size() + "\n");
//        int c = 1;
//        for (PrecinctVotes s : best4) {
//            System.out.println(c + "-->" + s);
//            c++;
//        }
//        System.out.println("====================================================");
        //System.out.println("best4.size(): "+best4.size());
    }
}
