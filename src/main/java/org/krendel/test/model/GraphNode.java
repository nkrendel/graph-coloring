package org.krendel.test.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a node in the graph with a name and color.
 */
public class GraphNode {

    public enum Color {
        NONE,
        RED,
        GREEN,
        BLUE,
        YELLOW,
        ERR
    }

    String name;
    Color color;

    List<GraphNode> relations;
    Set<Color> possibleColors;

    public GraphNode(String name, Color color) {
        this.name = name;
        this.color = color;
        this.possibleColors = new HashSet<>();
        // initialize possibleColors if color is NONE
        if (color == Color.NONE) {
            possibleColors.add(Color.RED);
            possibleColors.add(Color.GREEN);
            possibleColors.add(Color.BLUE);
            possibleColors.add(Color.YELLOW);
        }
    }

    /**
     * Add a relationship to another node, if it doesn't already exist.
     * @param node the node to connect to this node
     */
    public void addRelation(GraphNode node) {
        if (node != null) {
            boolean found = false;

            for (GraphNode existing : getRelations()) {
                if (existing.getName().equalsIgnoreCase(node.getName())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                getRelations().add(node);
            }
        }
    }

    /**
     * Get the names of related nodes
     * @return list of names
     */
    public List<String> getRelatedNames() {
        return getRelations().stream()
                .map(item -> item.getName())
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<GraphNode> getRelations() {
        if (relations == null) {
            relations = new ArrayList<>();
        }

        return relations;
    }

    public Set<Color> getPossibleColors() {
        return possibleColors;
    }

    public void addPossibleColor(Color color) {
        possibleColors.add(color);
    }

    public void removePossibleColor(Color color) {
        possibleColors.remove(color);
    }
}
