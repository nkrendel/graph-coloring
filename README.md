## Coding Question 1: Graph Coloring

Given the attached input file, properly color each graph so that no two connecting nodes have the same color. A node can be colored red, yellow, green, or blue. Some nodes are already assigned in the input file.

In each graph, only assign nodes colors if they can be no other color. If a node can be assigned one of multiple colors, mark it with the word “err”. Also use “err” if there are no available options for that node (see below).

Produce a new file with the remaining nodes assigned a color that works with the rest of the graph. We will diff it with our solution file. For example, with an input file as follows: 

```
node1: yellow
node2: red
node3:
node4: blue  {
    {node1, node3},
    {node2, node3},
    {node3, node4}
}
```

you should produce the following file:  node1: yellow

```
node2: red
node3: green
node4: blue  {
    {node1, node3},
    {node2, node3},
    {node3, node4}
} 
```

#### BUILDING

mvn clean package

#### RUNNING

java -jar target/graph-coloring-1.0-SNAPSHOT.jar < {inputfile}

e.g.:   java -jar target/graph-coloring-1.0-SNAPSHOT.jar < src/main/resources/graph1
