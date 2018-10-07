import java.util.List;

public class IntervalTree {

    Node root;
    /**
     * Constructor
     *
     * Opgave 3.1
     * Bouw in de constructor de gebalanceerde boom op adhv de ongesorteerde lijst van intervals
     * @param intervals
     */
    public IntervalTree(List<Interval> intervals) {
        for (Interval interval : intervals) {
            Node n= new Node(interval, null, null, null, 0);
            this.addNode(root, n);
        }
        //throw new UnsupportedOperationException("Nog te implementeren!");

    }

    /**
     * toevoegen van node en balanceren
     */
    public void addNode(Node tempRoot,Node node){

        // nog geen root
        if (root==null){
            root=node;
            System.out.println("root wordt "+ node.getInterval());
        }
        else{

            int compare =node.getInterval().compareTo(tempRoot.getInterval());
            if(compare ==1){
               if(tempRoot.getRightNode()==null){
                   tempRoot.setRightNode(node);
                   System.out.println("rechterkind van "+tempRoot.getInterval()+" wordt "+ node.getInterval());
               }
               else{
                   this.addNode(tempRoot.getRightNode(), node);
               }
            }
            else if(compare==0){
                System.out.println("Dit interval behoort al reeds tot de zoekboom");
            }
            else if(compare==-1){
                if(tempRoot.getLeftNode()==null){
                    tempRoot.setLeftNode(node);
                    System.out.println("linkerkind van "+tempRoot.getInterval()+" wordt "+ node.getInterval());
                }
                else{
                    this.addNode(tempRoot.getLeftNode(), node);
                }
            }
        }
    }



    /**
     * Opgave 3.2
     */
    public void printIntervals() {
        throw new UnsupportedOperationException("Nog te implementeren!");
    }


    /**
     * Opgave 3.3
     */
    public List<Interval> findOverlapping(int x) {
        throw new UnsupportedOperationException("Nog te implementeren!");
    }

    /**
     * Opgave 3.4
     */

    public List<Interval> findOverlapping(Interval ab) {
        throw new UnsupportedOperationException("Nog te implementeren!");
    }
}
