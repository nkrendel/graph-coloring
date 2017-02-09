package org.krendel.test;

import org.krendel.test.model.Graph;

/**
 * Given the attached input file, properly color each graph so that no two connecting nodes have the same color.
 * A node can be colored red, yellow, green, or blue. Some nodes are already assigned in the input file.
 */
public class App 
{
    public static void main(String[] args) {
        // this class currently reads only from standard input
        Graph graph = GraphParser.parse(System.in);

        Graph colored = GraphColorer.color(graph);

        System.out.println("OUTPUT: ");
        System.out.print(GraphPrinter.print(colored));
    }
}
