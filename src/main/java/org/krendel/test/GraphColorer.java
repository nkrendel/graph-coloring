package org.krendel.test;

import org.krendel.test.model.Graph;
import org.krendel.test.model.GraphNode;

/**
 * Color a graph
 *
 * Basic flow:
 * 1. loop through nodes until we find a node that doesn't have a color
 * 2. look at its related nodes and remove each possible color
 * 3. if only one possible color is left, set the node color
 * 4. continue looping as long as we've set at least one node color
 * 5. loop again and mark unset nodes as 'err'
 */
public class GraphColorer {
    public static Graph color(Graph graph) {
        boolean colorSet;
        do {
            colorSet = false;   // have we set a color in this iteration
            for (GraphNode node : graph.getNodes()) {
                if (node.getColor() != GraphNode.Color.NONE) {
                    continue;
                }

                // found a node that has no color, look at it's related nodes
                for (GraphNode related : node.getRelations()) {
                    node.getPossibleColors().remove(related.getColor());
                }

                if (node.getPossibleColors().size() == 1) {
                    // if only one color is left, color me!
                    node.setColor(node.getPossibleColors().iterator().next());
                    colorSet = true;    // indicate we set at least one node's color
                } else if (node.getPossibleColors().size() == 0) {
                    // mark node as error
                    node.setColor(GraphNode.Color.ERR);
                }
            }
        } while (colorSet);

        // mark remaining nodes as being in error
        graph.getNodes().stream().filter(node -> node.getColor() == GraphNode.Color.NONE).forEach(node -> node.setColor(GraphNode.Color.ERR));

        return graph;
    }
}
