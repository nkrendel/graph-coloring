package org.krendel.test;

import org.junit.Test;
import org.krendel.test.model.Graph;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for graph parser.
 */
public class GraphPrinterTest {
    private final static String SAMPLE_INPUT = "node1: yellow\n" +
            "node2: red\n" +
            "node3:\n" +
            "node4: blue\n\n" +
            "{\n" +
            "\t{node1, node3},\n" +
            "\t{node2, node3},\n" +
            "\t{node3, node4}\n" +
            "}\n";

    /**
     * Test that printing the sample graph results in output that is identical to the input.
     */
    @Test
    public void testPrinter()
    {
        InputStream targetStream = new ByteArrayInputStream(SAMPLE_INPUT.getBytes());
        Graph graph = GraphParser.parse(targetStream);
        String printOut = GraphPrinter.print(graph);

        assertEquals(SAMPLE_INPUT, printOut);
    }
}
