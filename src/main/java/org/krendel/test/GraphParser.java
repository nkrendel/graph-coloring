package org.krendel.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.krendel.test.exception.GraphParsingException;
import org.krendel.test.model.Graph;
import org.krendel.test.model.GraphNode;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is able to create a graph from lines from input from an InputStream
 *
 * node1: yellow
 * node2: red
 * node3:
 * node4: blue
 *
 * {
 *     {node1, node3},
 *     {node2, node3},
 *     {node3, node4}
 * }
 *
 */
public class GraphParser {
    private final static Logger LOG = Logger.getLogger(GraphParser.class);
    private final static Pattern NODE_PATTERN = Pattern.compile("(\\w+\\s?\\w*):\\s*(\\w*)");
    private final static Pattern RELATIONSHIP_PATTERN = Pattern.compile("\\{(\\w+), (\\w+)},?");

    public static Graph parse(InputStream inputStream) {
        Graph graph = new Graph();

        boolean nodesDone = false;
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.length() == 0) {
                nodesDone = true;   // recognize empty line separator between nodes and relationships
                continue;
            }

            if (!nodesDone) {
                // line represents a node
                parseNode(graph, line);
            } else {
                // line represents either '{', '}', or a node relationship
                parseRelationship(graph, line);
            }
        }

        return graph;
    }

    /**
     * Take a line in the form "name: color" and create a graph node named 'name' with color 'color'.
     *
     * @param graph the graph we're adding nodes to
     * @param line the input line we're parsing
     */
    private static void parseNode(Graph graph, String line) {
        Matcher matcher = NODE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new GraphParsingException(String.format("Input line [%s] doesn't represent a valid node", line));
        }

        String nodeName = matcher.group(1);
        String color = matcher.group(2);

        // remove possible spaces in nodeName to take care of potentially bad input files (hint: graph2)
        nodeName = nodeName.replaceAll("\\s", "");

        GraphNode.Color nodeColor;
        if (StringUtils.isEmpty(color)) {
            // node has no color assigned
            nodeColor = GraphNode.Color.NONE;
        } else {
            nodeColor = GraphNode.Color.valueOf(color.toUpperCase());
        }

        // create the new graph node and addNode to the graph
        GraphNode node = new GraphNode(nodeName, nodeColor);
        graph.addNode(node);
    }

    /**
     * Take a line in the form "{node1, node2}" and create a relationship in the graph, as well as a
     * bi-directional relationship between the two actual graph nodes represented by node1 and node2.
     *
     * @param graph the graph we're adding relationships to
     * @param line the input line we're parsing
     */
    private static void parseRelationship(Graph graph, String line) {
        if (!"{".equals(line) && !"}".equals(line)) {
            Matcher matcher = RELATIONSHIP_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new GraphParsingException(String.format("Input line [%s] doesn't represent a valid relationship", line));
            }
            String first = matcher.group(1);
            String second = matcher.group(2);

            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Found relationship between %s and %s!", first, second));
            }

            // lookup the nodes in the relationship
            GraphNode firstNode = graph.getNode(first);
            GraphNode secondNode = graph.getNode(second);

            if (firstNode == null || secondNode == null) {
                throw new GraphParsingException(String.format("Unable to find one of the related nodes (%s:%s) (%s:%s)",
                        first, firstNode, second, secondNode));
            }

            // add fixed (input) relationship to graph
            graph.addRelationship(first, second);

            // create bi-directional relationship
            firstNode.addRelation(secondNode);
            secondNode.addRelation(firstNode);
        }
    }

}
