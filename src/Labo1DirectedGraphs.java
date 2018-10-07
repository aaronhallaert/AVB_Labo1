import com.google.common.graph.*;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Labo1DirectedGraphs {

    public static void main(String[] args) throws IOException {

        // gerichte graaf inlezen (je mag er van uit gaan dat dit een gerichte graaf is, ie. graph.isDirected() == true
        // bestanden: tinyDG.txt, tinyDAG.txt, mediumDG.txt, mediumDAG.txt onder de folder data
        Graph<String> graph = Util.loadDiGraphFromFile(new File("data-lab1\\mediumDAG.txt"));


        boolean hasCycle = hasCycle(graph);


        if(!hasCycle) {
            System.out.println("Graaf heeft geen gerichte lus.");

            long startTime = System.nanoTime();
            System.out.printf("Ordening van nodes volgens edges: %s\n",determinePrecedenceFeasibleOrdering(graph));
            long endTime = System.nanoTime();

            long finalTime= (endTime-startTime);
            System.out.println("gesorteerd in "+ finalTime+" ns");
        } else {
            System.out.println("Graaf heeft een gerichte lus!");
        }

    }


    public static void isTopological(List<String> list, Graph graph){
        Map<String, Integer> map= new HashMap<>();

        int i=0;
        for (String node : list) {
            map.put(node, i);
            i++;
        }

        for (String node : list) {
            for (Object child : graph.successors(node)) {
                if(map.get(child)<map.get(node)){
                    System.out.println("FOUT: successor "+ child+" ligt links van "+node );
                }
            }
        }


    }
    // Implementeer hier opgave 1
    public static boolean hasCycle(Graph<String> graph) {
        Set<String> visited= new HashSet<String>();
        Set<String> stack= new HashSet<String>();

        for (String node : graph.nodes()) {
            if(!visited.contains(node)) {
                if (recCycle(node, visited, stack, graph)) {
                    return true;
                }
            }

        }

        return false;
        //throw new UnsupportedOperationException("Nog te implementeren");
    }

    public static boolean recCycle( String currentElement, Set<String> visited, Set<String> stack, Graph<String> graph){


        if(stack.contains(currentElement)){
            return true;
        }

        visited.add(currentElement);
        stack.add(currentElement);

        for (String child : graph.successors(currentElement)) {
            stack.add(currentElement);
            if(recCycle(child, visited, stack, graph)){
                return true;
            }
        }

        stack.clear();
        return false;

    }


    // Implementeer hier opgave 2
    public static List<String> determinePrecedenceFeasibleOrdering(Graph<String> graph) {


        LinkedList<String> rij= new LinkedList<String>();
        while(rij.size()!=graph.nodes().size()) {
            for (String node : graph.nodes()) {
                if (!rij.contains(node)) {
                    if (rij.containsAll(graph.successors(node))) {
                        rij.addFirst(node);
                    }
                }
            }
        }
        //isTopological(rij, graph);
        return rij;
        //throw new UnsupportedOperationException("Nog te implementeren");
    }

}
