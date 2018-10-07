public class Interval implements Comparable<Interval> {


    private int low;
    private int high;

    public Interval(int lowInclusive, int highExclusive) {
        this.low= lowInclusive;
        this.high=highExclusive;
    }

    /**
     * Geeft de (inclusieve) ondergrens terug
     * @return
     */
    public int getLow() {
        return this.low;
    }

    /**
     * Geeft de (exclusieve) bovengrens terug
     * @return
     */
    public int getHigh() {
        return this.high;
    }


    @Override
    /**
     * Te implementeren!
     * Returned -1 als dit interval een lagere ondergrens heeft, of een gelijke ondergrens en lagere bovengrens heeft dan Interval o
     * Returned 0 als dit interval gelijk is aan Interval o
     * Returnd 1 als dit interval een hogere ondergrens heeft, of een gelijke ondergrens en een hogere bovengrens heeft dan Interval o
     */
    public int compareTo(Interval o) {
        if(this.low <= o.getLow() && this.high<o.getHigh()){
            return -1;
        }
        else if(this.low==o.getLow() && this.high==o.getHigh()){
            return 0;
        }
        else if(this.low>=o.getLow() && this.high>o.getHigh()){
            return 1;
        }

        return 0;
    }

    /**
     * Bereken de overlap met Interval b. Als dit interval niet overlapt met Interval b, dan return je 'null' . Anders
     * return je een nieuw Interval dat de overlap voorstelt.
     * @param b
     * @return overlap
     */
    public Interval calculateOverlap(Interval b) {

        if((this.low<b.getLow() && this.high <b.getHigh()) || (this.low>b.getLow() && this.high> b.getHigh() )){
            return null;
        }
        else {
            // parameters van nieuw interval
            int newLow;
            int newHigh;

            // set nieuwe ondergrens
            if(this.low>=b.getLow()){
                newLow=this.low;
            }
            else{
                newLow=b.getLow();
            }

            // set nieuwe bovengrens
            if(this.high<=b.getHigh()){
                newHigh=this.high;
            }
            else{
                newHigh=b.getHigh();
            }

            // return overlap
            Interval overlap=new Interval(newLow, newHigh);
            return overlap;



        }
    }

    @Override
    public String toString() {
        return String.format("[%d, %d)",getLow(),getHigh());
    }
}
