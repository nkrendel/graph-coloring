package org.krendel.test;

import org.apache.commons.lang3.tuple.Pair;
import org.krendel.test.model.Graph;
import org.krendel.test.model.GraphNode;

import java.util.List;

/**
 * Print (display) a graph in a format similar to the required input.
 */
public class GraphPrinter {
    public static String print(Graph graph) {
        List<GraphNode> nodes = graph.getNodes();
        StringBuilder builder = new StringBuilder(nodes.size() * 20);    // estimate initial size

        // addNode node rows
        for (GraphNode node : nodes) {
            if (node.getColor() == GraphNode.Color.NONE) {
                builder.append(String.format("%s:%n", node.getName()));
            } else {
                builder.append(String.format("%s: %s%n", node.getName(), node.getColor().name().toLowerCase()));
            }
        }

        // addNode empty line
        builder.append(String.format("%n"));

        // addNode relationships
        builder.append(String.format("{%n"));

        boolean first = true;
        for (Pair<String, String> relationship : graph.getRelationships()) {
            if (!first) {
                builder.append(String.format(",%n"));
            }
            builder.append(String.format("\t{%s, %s}", relationship.getLeft(), relationship.getRight()));
            first = false;
        }

        builder.append(String.format("%n}%n"));

        return builder.toString();
    }
}
