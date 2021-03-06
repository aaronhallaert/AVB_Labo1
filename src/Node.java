public class Node {

    private Interval interval;
    private Node leftNode;
    private Node rightNode;
    private Node parentNode;
    private int max;

    public Node(Interval interval, Node leftNode, Node rightNode, Node parentNode) {
        this.interval = interval;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.parentNode = parentNode;
        this.max = interval.getHigh();
    }

    public Node(){
        this.interval=null;
        this.leftNode=null;
        this.rightNode=null;
        this.parentNode=null;
        this.max=0;
    }

    public boolean isBalanced(){

        if(this.rightDepth()+2<=this.leftDepth() || this.rightDepth()>=this.leftDepth()+2){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     *
     * @return maximale diepte van node (dit kan aan linkerkant of rechterkant zijn)
     */
    public int maxDepth()
    {

            /* berekenen diepte van elke subtree */
            int lDepth =0;
            if(this.getLeftNode()!=null) {
                lDepth = this.getLeftNode().maxDepth();
            }
            int rDepth = 0;
            if(this.getRightNode()!=null){
                rDepth=this.getRightNode().maxDepth();
            }

            /* grootste uitkiezen */
            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);

    }

    public int rightDepth()
    {
        if(this.getRightNode()==null){
            return 0;
        }
        else {
            return this.getRightNode().maxDepth();
        }
    }

    /**
     *
     * @return linkerdiepte van huidige node
     */
    public int leftDepth(){
        if(this.getLeftNode()==null) {
            return 0;
        }
        else{
            return this.getLeftNode().maxDepth();
        }
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    /**
     * update max van huidige node en controleer recursief de parent tot root
     * @param max
     */
    public void updateMax(int max){

        if(max >= this.getMax()) {
            this.setMax(max);
            if(this.getParentNode()!=null) {
                this.getParentNode().updateMax(max);
            }
        }
        else{
            if(this.getParentNode()!=null) {
                this.getParentNode().updateMax(this.getInterval().getHigh());
            }
        }
    }
}
