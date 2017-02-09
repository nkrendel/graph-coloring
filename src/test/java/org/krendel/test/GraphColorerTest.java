package org.krendel.test;

import org.junit.Test;
import org.krendel.test.model.Graph;
import org.krendel.test.model.GraphNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for graph colorer.
 */
public class GraphColorerTest {
    private final static String SAMPLE_INPUT = "node1: yellow\n" +
            "node2: red\n" +
            "node3:\n" +
            "node4: blue\n\n" +
            "{\n" +
            "\t{node1, node3},\n" +
            "\t{node2, node3},\n" +
            "\t{node3, node4}\n" +
            "}\n";

    private final static String AMBIGUOUS_INPUT = "node1: red\n" +
            "node2: blue\n" +
            "node3: yellow\n" +
            "node4: green\n" +
            "node5:\n\n" +
            "{\n" +
            "\t{node1, node5},\n" +
            "\t{node2, node5},\n" +
            "\t{node3, node5},\n" +
            "\t{node4, node5}\n" +
            "}\n";

    @Test
    public void testColoring()
    {
        InputStream targetStream = new ByteArrayInputStream(SAMPLE_INPUT.getBytes());
        Graph graph = GraphParser.parse(targetStream);
        Graph colored = GraphColorer.color(graph);

        // see if node3 was colored
        assertNotNull("colored graph is null!", colored);
        assertNotNull("node3 doesn't exist in graph!", colored.getNode("node3"));
        assertEquals("node3 wasn't colored!", GraphNode.Color.GREEN, colored.getNode("node3").getColor());
    }

    @Test
    public void testErrorNode()
    {
        InputStream targetStream = new ByteArrayInputStream(AMBIGUOUS_INPUT.getBytes());
        Graph graph = GraphParser.parse(targetStream);
        Graph colored = GraphColorer.color(graph);

        // see if node3 was colored
        assertNotNull("colored graph is null!", colored);
        assertEquals("node5 should've been marked as err", GraphNode.Color.ERR, colored.getNode("node5").getColor());
    }
}
