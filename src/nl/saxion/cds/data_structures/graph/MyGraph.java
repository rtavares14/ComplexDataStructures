package nl.saxion.cds.data_structures.graph;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.trees.heaps.MyHeap;

import java.util.Iterator;

public class MyGraph<T extends Comparable<T>> implements SaxGraph<T> {
    private final MyHashMap<T, MyArrayList<DirectedEdge<T>>> adjacencyList;

    public MyGraph() {
        this.adjacencyList = new MyHashMap<>();
    }

    //to better organized create the getorcreatelist
    @Override
    public void addEdge(T fromValue, T toValue, double weight) {
        SaxList<DirectedEdge<T>> list = getOrCreateList(fromValue);

        for (DirectedEdge<T> edge : list) {
            if (edge.to().equals(toValue)) {
                return;
            }
        }

        DirectedEdge<T> newEdge = new DirectedEdge<>(fromValue, toValue, weight);
        list.addLast(newEdge);
    }

    @Override
    public void addEdgeBidirectional(T fromValue, T toValue, double weight) {
        addEdge(fromValue, toValue, weight);
        addEdge(toValue, fromValue, weight);
    }

    @Override
    public SaxList<DirectedEdge<T>> getEdges(T value) {
        if (!adjacencyList.contains(value)) {
            return new MyArrayList<>(); // Return an empty list instead of null
        }
        MyArrayList<DirectedEdge<T>> edges = adjacencyList.get(value);
        MyArrayList<DirectedEdge<T>> saxEdges = new MyArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            saxEdges.addLast(edges.get(i));
        }
        return saxEdges;
    }

    @Override
    public double getTotalWeight() {
        double totalWeight = 0;
        for (MyArrayList<DirectedEdge<T>> edges : adjacencyList.values()) {
            for (int i = 0; i < edges.size(); i++) {
                totalWeight += edges.get(i).weight();
            }
        }
        return totalWeight;
    }

    @Override
    public SaxGraph shortestPathsDijkstra(T startNode) {
        // Implement Dijkstra's algorithm here
        return null;
    }

    private class AStarNode implements Comparable<AStarNode> {
        DirectedEdge<T> edge;
        double g;
        double h;
        AStarNode parent;

        AStarNode(DirectedEdge<T> edge, double g, double h, AStarNode parent) {
            this.edge = edge;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        @Override
        public int compareTo(AStarNode other) {
            return Double.compare(this.g + this.h, other.g + other.h);
        }
    }
    @Override
    public SaxList<DirectedEdge<T>> shortestPathAStar(T startNode, T endNode, Estimator<T> estimator) {
        MyHeap<AStarNode> openList = new MyHeap<>(true);
        MyHashMap<T, AStarNode> closedList = new MyHashMap<>();

        AStarNode startAStarNode = new AStarNode(new DirectedEdge<>(startNode, startNode, 0), 0, estimator.estimate(startNode, endNode), null);
        openList.enqueue(startAStarNode);

        while (!openList.isEmpty()) {
            AStarNode currentNode = openList.dequeue();

            if (currentNode.edge.to().equals(endNode)) {
                return reconstructPath(currentNode);
            }

            if (!closedList.contains(currentNode.edge.to())) {
                closedList.add(currentNode.edge.to(), currentNode);

                for (DirectedEdge<T> neighborEdge : getEdges(currentNode.edge.to())) {
                    T neighborNode = neighborEdge.to();

                    if (closedList.contains(neighborNode)) continue;

                    double neighborG = currentNode.g + neighborEdge.weight();
                    double neighborH = estimator.estimate(neighborNode, endNode);

                    AStarNode neighborAStarNode = new AStarNode(neighborEdge, neighborG, neighborH, currentNode);

                    openList.enqueue(neighborAStarNode);
                }
            }
        }
        return new MyArrayList<>();
    }

    private SaxList<DirectedEdge<T>> reconstructPath(AStarNode goalNode) {
        MyArrayList<DirectedEdge<T>> path = new MyArrayList<>();
        AStarNode currentNode = goalNode;

        while (currentNode.parent != null) {
            path.addFirst(currentNode.edge);
            currentNode = currentNode.parent;
        }
        return path;
    }

    public SaxList<T> convertEdgesToNodes(SaxList<DirectedEdge<T>> edges) {
        SaxList<T> nodes = new MyArrayList<>();
        for (DirectedEdge<T> edge : edges) {
            if (!nodes.contains(edge.from())) {
                nodes.addLast(edge.from());
            }
            if (!nodes.contains(edge.to())) {
                nodes.addLast(edge.to());
            }
        }
        return nodes;
    }

    @Override
    public SaxGraph minimumCostSpanningTree() {
        // Implement Minimum Cost Spanning Tree algorithm
        return null;
    }

    @Override
    public Iterator<T> iterator() {

        //breath serch here- traverse though nodes and get the lsit of it kinda
        //a list of nodes in a breth serch and then i return the iterator
        //retunt the i
        
        return adjacencyList.getKeys().iterator();
    }

    @Override
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    @Override
    public int size() {
        return adjacencyList.size(); // returns the number of unique vertices
    }

    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph ").append(name).append(" {\n");
        for (T vertex : adjacencyList.getKeys()) {
            for (DirectedEdge<T> edge : adjacencyList.get(vertex)) {
                sb.append("  ").append(vertex).append(" -- ").append(edge.to())
                        .append(" [label=").append(edge.weight()).append("];\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private MyArrayList<DirectedEdge<T>> getOrCreateList(T vertex) {
        if (!adjacencyList.contains(vertex)) {
            adjacencyList.add(vertex, new MyArrayList<>());
        }
        return adjacencyList.get(vertex);
    }
}
