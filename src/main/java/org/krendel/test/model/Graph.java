package org.krendel.test.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a graph, which is currently just a collection of GraphNodes
 */
public class Graph {
    private final static Logger LOG = Logger.getLogger(Graph.class);

    // save a map of all nodes in the graph
    private Map<String, GraphNode> nodes = new LinkedHashMap<>();

    // save original input relationships (mostly for matching output)
    private List<Pair<String, String>> relationships = new ArrayList<>();

    public void addNode(GraphNode node) {
        if (node == null) {
            return;
        }

        nodes.put(node.getName(), node);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Added node " + node.getName() + " of color " + node.getColor());
        }
    }

    public void addRelationship(String from, String to) {
        Pair<String, String> relation = new ImmutablePair<>(from, to);
        relationships.add(relation);
    }

    public GraphNode getNode(String name) {
        return nodes.get(name);
    }

    public List<String> getNodeNames() {
        return new ArrayList<>(nodes.keySet());
    }

    public List<GraphNode> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Pair<String, String>> getRelationships() {
        return relationships;
    }
}
