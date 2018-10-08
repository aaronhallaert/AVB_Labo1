import java.util.ArrayList;
import java.util.Comparator;
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
            Node n= new Node(interval, null, null, null);
            this.addNode(root, n);
        }

        // update max voor volledige boom
        for (Node node : findLeaves()) {
            node.updateMax(node.getMax());
        }
    }

    /**
     * toevoegen van node aan tree
     * @param tempRoot
     * @param node nieuwe node
     */
    public void addNode(Node tempRoot,Node node){

        // nog geen root
        if (root==null){
            root=node;
        }
        else{

            // controle of node rechts of links van tempRoot dient te komen
            int compare = node.getInterval().compareTo(tempRoot.getInterval());

            // node komt rechts van temproot (als rechterkind van temproot null is, anders recursief verdergaan
            // op rechterkind van temproot)
            if(compare ==1){
               if(tempRoot.getRightNode()==null){
                   tempRoot.setRightNode(node);
                   node.setParentNode(tempRoot);
               }
               else{
                   this.addNode(tempRoot.getRightNode(), node);
               }
            }
            else if(compare==0){
                System.out.println("Dit interval behoort al reeds tot de zoekboom");
            }

            // node komt links van temproot (als linkerkind van temproot null is, anders recursief verdergaan
            // op linkerkind van temproot)
            else if(compare==-1){
                if(tempRoot.getLeftNode()==null){
                    tempRoot.setLeftNode(node);
                    node.setParentNode(tempRoot);
                }
                else{
                    this.addNode(tempRoot.getLeftNode(), node);
                }
            }
        }



        // balance check tree after adding node, we krijgen de eerste ongebalanceerde node in onze weg naar boven terug
        Node unbalancedNode=this.checkBalance(node);
        if(unbalancedNode!=null){
            performRotation(unbalancedNode);
        }


    }

    public List<Node> findLeaves(){
        List<Node> result= new ArrayList<>();
        helperLeaves(result, root);
        return result;
    }

    /**
     * helper methode bij het zoeken naar leaves
     * @param result
     * @param node
     */
    public void helperLeaves(List<Node> result, Node node){
        if(node.getRightNode()==null && node.getLeftNode()==null){
            result.add(node);
        }
        else{
            if(node.getRightNode()!=null){
            helperLeaves(result, node.getRightNode());}
            if(node.getLeftNode()!=null) {
                helperLeaves(result, node.getLeftNode());
            }
        }
    }







    public void performRotation(Node unbalancedNode){

        // rechts ongebalanceerd
        if(unbalancedNode.rightDepth()>unbalancedNode.leftDepth()){

            // perform LL rotation
            // b wordt root, c rechterkind van b, a linkerkind van b
            if(unbalancedNode.getRightNode().rightDepth()> unbalancedNode.getRightNode().leftDepth()){
                Node a= unbalancedNode;
                Node b= unbalancedNode.getRightNode();
                Node c= unbalancedNode.getRightNode().getRightNode();

                // b wordt root
                b.setParentNode(a.getParentNode());

                if(a!=root) {
                    if(a.getParentNode().getRightNode()!=null) {
                        if (a.getParentNode().getRightNode() == a) {
                            a.getParentNode().setRightNode(b);
                        } else {
                            a.getParentNode().setLeftNode(b);
                        }
                    }
                }


                a.setRightNode(b.getLeftNode());
                b.setLeftNode(a);
                a.setParentNode(b);
                if(a==root){
                    root=b;
                }
            }

            // perform LR rotation
            else{
                Node a= unbalancedNode;
                Node b= unbalancedNode.getRightNode();
                Node c= unbalancedNode.getRightNode().getLeftNode();

                c.setParentNode(b.getParentNode());
                a.setRightNode(c);
                b.setParentNode(c);
                c.setRightNode(b);
                b.setLeftNode(null);



                c.setParentNode(a.getParentNode());
                if(a!=root) {
                    if(a.getParentNode().getRightNode()!=null) {
                        if (a.getParentNode().getRightNode() == a) {
                            a.getParentNode().setRightNode(c);
                        } else {
                            a.getParentNode().setLeftNode(c);
                        }
                    }
                }
                a.setRightNode(c.getLeftNode());
                c.setLeftNode(a);
                a.setParentNode(c);
                if(a==root){
                    root=c;
                }
            }
        }

        // links ongebalanceerd
        else{
            //perform RR rotation
            // b wordt root, c linkerkind van b, a rechterkind van b
            if(unbalancedNode.getLeftNode().leftDepth()>unbalancedNode.getLeftNode().rightDepth()){
                Node a= unbalancedNode;
                Node b= unbalancedNode.getLeftNode();
                Node c= unbalancedNode.getLeftNode().getLeftNode();

                // b wordt root
                b.setParentNode(a.getParentNode());
                if(a!=root){
                    if(a.getParentNode().getRightNode()!=null) {
                        if (a.getParentNode().getRightNode() == a) {
                            a.getParentNode().setRightNode(b);
                        } else {
                            a.getParentNode().setLeftNode(b);
                        }
                    }
                }

                a.setLeftNode(b.getRightNode());
                b.setRightNode(a);
                a.setParentNode(b);
                if(a==root){
                    root=b;
                }


            }
            //perform RL rotation
            // c wordt "root", b linkerkind van c, a rechterkind van c
            else{
                Node a= unbalancedNode;
                Node b= unbalancedNode.getLeftNode();
                Node c= unbalancedNode.getLeftNode().getRightNode();


                // eerst linkerrotatie op rechtersubtree
                c.setParentNode(b.getParentNode());
                a.setLeftNode(c);
                b.setParentNode(c);
                c.setLeftNode(b);
                b.setRightNode(null);

                // RR rotatie op resulterende boom
                c.setParentNode(a.getParentNode());
                if(a!=root) {
                    if(a.getParentNode().getRightNode()!=null) {
                        if (a.getParentNode().getRightNode() == a) {
                            a.getParentNode().setRightNode(c);
                        } else {
                            a.getParentNode().setLeftNode(c);
                        }
                    }
                }
                a.setLeftNode(c.getRightNode());
                c.setRightNode(a);
                a.setParentNode(c);

                // indien de root van de volledige boom veranderd wordt bij het roteren
                if(a==root){
                    root=c;
                }




            }
        }

    }

    /**
     * balans checken van onder naar boven (na toevoegen van een node)
     * @param node startnode
     * @return ongebalanceerde node
     */
    public Node checkBalance(Node node){

        if(node.getParentNode()==null){
            if(node.isBalanced()){
                return null;
            }
            else{
                return node;
            }
        }
        else if(node.isBalanced()){

            return checkBalance(node.getParentNode());
        }
        else{
            return node;
        }


    }

    /**
     * Opgave 3.2
     */
    public void printIntervals() {

        inOrderPrint(root);

    }

    public void inOrderPrint(Node node){
        if(node!=null){
            inOrderPrint(node.getLeftNode());
            System.out.println(node.getInterval());
            inOrderPrint(node.getRightNode());
        }

    }


    /**
     * Opgave 3.3
     */
    public List<Interval> findOverlapping(int x) {
        List<Interval> overlapIntervallen= new ArrayList<>();
        checkNumberInNode(x, root, overlapIntervallen);

        overlapIntervallen.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.compareTo(o2);
            }
        });
        return overlapIntervallen;
    }

    /**
     * hulpmethode om alle intervallen waartoe x behoort te verzamelen in list overlapintervallen
     * @param x
     * @param node
     * @param overlapIntervallen
     */
    public void checkNumberInNode(int x, Node node, List<Interval> overlapIntervallen) {
        // enkel nuttig verder te zoeken in boom, indien x onder max van huidige node ligt
        if (x < node.getMax()) {

            // altijd links verder zoeken, enkel rechts wanneer x groter is dan ondergrens van huidige node
            if(node.getLeftNode()!=null) {
                checkNumberInNode(x, node.getLeftNode(), overlapIntervallen);
            }
            if(node.getInterval().getLow()<=x){
                if(node.getRightNode()!=null) {
                    checkNumberInNode(x, node.getRightNode(), overlapIntervallen);
                }
            }
        }
        // interval toevoegen indien x behoort tot dat interval
        if (node.getInterval().getHigh() > x && x >= node.getInterval().getLow()) {
            overlapIntervallen.add(node.getInterval());
        }

    }

    /**
     * Opgave 3.4
     */

    public List<Interval> findOverlapping(Interval ab) {
        List<Interval> overlapIntervallen= new ArrayList<>();
        checkIntervalInNode(ab, root, overlapIntervallen);

        overlapIntervallen.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.compareTo(o2);
            }
        });
        return overlapIntervallen;
    }

    /**
     * hulpmethode om alle intervallen waarmee ab overlapt te verzamelen in overlapintervallen
     * @param ab
     * @param node
     * @param overlapIntervallen
     */
    public void checkIntervalInNode(Interval ab, Node node, List<Interval> overlapIntervallen){

        // afkappen indien ondergrens van interval kleiner is dan max van huidige node
        if (ab.getLow() < node.getMax()) {
            if(node.getLeftNode()!=null) {
                checkIntervalInNode(ab, node.getLeftNode(), overlapIntervallen);
            }

            // enkel rechts verderzoeken indien bovengrens van interval >= ondergrens huidige node
            if(ab.getHigh()>=node.getInterval().getLow()) {
                if (node.getRightNode() != null) {
                    checkIntervalInNode(ab, node.getRightNode(), overlapIntervallen);
                }
            }



        }
        if (node.getInterval().calculateOverlap(ab)!=null) {
            overlapIntervallen.add(node.getInterval());
        }
    }
}
