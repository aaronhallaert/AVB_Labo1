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

        System.out.print("Deze tree is ");
        if(root.isBalanced()){
            System.out.println("gebalanceerd");
        }
        else{
            System.out.println("niet gebalanceerd");
        }
        //throw new UnsupportedOperationException("Nog te implementeren!");

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
            System.out.println("root wordt "+ node.getInterval());
        }
        else{

            int compare =node.getInterval().compareTo(tempRoot.getInterval());
            System.out.println(node.getInterval()+" wordt vergeleken met " +tempRoot.getInterval());
            if(compare ==1){
               if(tempRoot.getRightNode()==null){
                   tempRoot.setRightNode(node);
                   node.setParentNode(tempRoot);
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
                    node.setParentNode(tempRoot);
                    System.out.println("linkerkind van "+tempRoot.getInterval()+" wordt "+ node.getInterval());

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


        updateMax(node.getInterval().getHigh(), node);

    }

    /**
     * update max boven nieuwe node
     * @param max
     * @param node
     */
    public void updateMax(int max, Node node){
        if(max > node.getMax()) {
            node.setMax(max);
            updateMax(max, node.getParentNode());
        }
    }

    public void performRotation(Node unbalancedNode){
        System.out.println("Er gebeurt een rotatie met als ongebalanceerde node " + unbalancedNode.getInterval());
        // doe rotatie met node "unbalancedNode" als root


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

                // update max
                a.setMax(a.getInterval().getHigh());
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

                a.setMax(a.getInterval().getHigh());
                updateMax(a.getInterval().getHigh(), a);

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


                b.setParentNode(a.getParentNode());
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

                // update max
                a.setMax(a.getInterval().getHigh());
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

                a.setMax(a.getInterval().getHigh());
                updateMax(a.getInterval().getHigh(), a);

            }
        }

    }

    /**
     * balans checken na toevoegen van een node
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
            //System.out.println(node.getInterval()+" is gebalanceerd");
            return checkBalance(node.getParentNode());
        }
        else{
            //System.out.println(node.getInterval()+" is niet gebalanceerd");
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

        throw new UnsupportedOperationException("Nog te implementeren!");
    }

    /**
     * Opgave 3.4
     */

    public List<Interval> findOverlapping(Interval ab) {
        throw new UnsupportedOperationException("Nog te implementeren!");
    }
}
