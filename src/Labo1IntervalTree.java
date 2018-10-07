import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Labo1IntervalTree {

    public static void main(String[] args) {
        Random rnd = new Random(31415);


        // create 40 random intervals in range [0,100)
        int nIntervals = 40;
        int maxRange = 100;

        System.out.println("Creating intervals:");
        List<Interval> intervals = new ArrayList<>(nIntervals);
        for(int i = 0; i<nIntervals; i++) {
            int low = rnd.nextInt(maxRange-1);
            int high = low + rnd.nextInt(maxRange-low)+1;

            Interval interval = new Interval(low,high);
            System.out.println(interval);
            intervals.add(interval);
        }
        System.out.println();


        // build interval tree
        IntervalTree tree = new IntervalTree(intervals);

        // print the intervals
        tree.printIntervals();

        // list all intervals overlapping i in range [0,maxRange]
        System.out.printf("Finding overlaps with i in range [%d,%d):\n",0,maxRange);
        for(int i = 0; i<maxRange; i++) {

            List<Interval> overlappingIntervals = tree.findOverlapping(i);

            System.out.println("Overlapping with "+i+": "+overlappingIntervals);

        }

        // list all intervals overlapping a specific interval and return the overlap range
        int a = 15, b = 32;
        Interval ab = new Interval(a,b);

        System.out.printf("Finding overlaps with [%d, %d) and computing intersection:\n",a,b);
        List<Interval> overlappingIntervals = tree.findOverlapping(ab);
        for(Interval interval : overlappingIntervals) {
            System.out.printf("Overlap of [%d, %d) with %s : %s\n",a,b,interval,ab.calculateOverlap(interval));
        }
    }
}
