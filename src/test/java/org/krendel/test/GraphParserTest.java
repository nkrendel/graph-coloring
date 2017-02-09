package org.krendel.test;

import org.junit.Test;
import org.krendel.test.model.Graph;
import org.krendel.test.model.GraphNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for graph parser.
 */
public class GraphParserTest {
    private final static String SAMPLE_INPUT = "node1: yellow\n" +
            "node2: red\n" +
            "node3:\n" +
            "node 4: blue\n\n" +
            "{\n" +
            "\t{node1, node3},\n" +
            "\t{node2, node3},\n" +
            "\t{node3, node4}\n" +
            "}\n";

    @Test
    public void testParser()
    {
        InputStream targetStream = new ByteArrayInputStream(SAMPLE_INPUT.getBytes());
        Graph graph = GraphParser.parse(targetStream);

        assertEquals(4, graph.getNodes().size());
        assertEquals(GraphNode.Color.RED, graph.getNode("node2").getColor());
        assertNotNull(graph.getNode("node3"));
    }
}
