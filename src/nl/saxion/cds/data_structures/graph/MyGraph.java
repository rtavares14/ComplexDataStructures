package nl.saxion.cds.data_structures.graph;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.trees.heaps.MyHeap;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyGraph<T extends Comparable<T>> implements SaxGraph<T> {
    private final MyHashMap<T, MyArrayList<DirectedEdge<T>>> adjacencyList;

    public MyGraph() {
        this.adjacencyList = new MyHashMap<>();
    }

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
            return new MyArrayList<>();
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
    public SaxGraph<T> shortestPathsDijkstra(T startNode) {
        MyGraph<T> resultGraph = new MyGraph<>();
        MyHeap<SaxGraph.DirectedEdge<T>> openList = new MyHeap<>(true);
        MyHashMap<T, SaxGraph.DirectedEdge<T>> closeList = new MyHashMap<>();

        SaxGraph.DirectedEdge<T> startEdge = new DirectedEdge<>(startNode, startNode, 0);
        openList.enqueue(startEdge);

        while (!openList.isEmpty()) {
            SaxGraph.DirectedEdge<T> currentEdge = openList.dequeue();

            if (!closeList.contains(currentEdge.from())) {
                closeList.add(currentEdge.from(), currentEdge);
                resultGraph.addEdge(currentEdge.from(), currentEdge.to(),
                        currentEdge.weight());

                for (DirectedEdge<T> neighborEdge : getEdges(currentEdge.from())) {
                    if (!closeList.contains(neighborEdge.to())) {
                        SaxGraph.DirectedEdge<T> newEdge = new DirectedEdge<>(
                                neighborEdge.to(), currentEdge.from(),
                                neighborEdge.weight() + currentEdge.weight());
                        openList.enqueue(newEdge);
                    }
                }
            }
        }
        return resultGraph;
    }

    public SaxList<DirectedEdge<T>> shortestPathDijkstraPathPath(T startNode, T endNode) {
        SaxGraph<T> graph = shortestPathsDijkstra(startNode);
        SaxList<DirectedEdge<T>> reconstructedPath = new MyArrayList<>();
        T currentNode = endNode;


        while (!currentNode.equals(startNode)) {
            SaxList<DirectedEdge<T>> edges = graph.getEdges(currentNode);
            if (edges.isEmpty()) {
                return new MyArrayList<>();
            }
            DirectedEdge<T> edge = edges.get(0);
            DirectedEdge<T> reverserd = new DirectedEdge<>(edge.to(), edge.from(),
                    edge.weight());
            reconstructedPath.addFirst(reverserd);
            currentNode = edge.from();
        }
        return reconstructedPath;
    }

    public SaxList<T> dijkstraPathToNodes(T startNode, T endNode) {
        // SaxList<DirectedEdge<T>> edges = getDijkstraPath(startNode, endNode);
        // return convertEdgesToNodes(edges);
        return null;
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
        return new BFSIterator();
    }

    @Override
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    @Override
    public int size() {
        return adjacencyList.size();
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

    private class BFSIterator implements Iterator<T> {
        private final MyHeap<T> queue;
        private final MyHashMap<T, Boolean> visited;

        public BFSIterator() {
            this.queue = new MyHeap<>(true); // Min-heap used as a queue
            this.visited = new MyHashMap<>();

            // Start BFS from the first node if the graph is not empty
            if (!adjacencyList.isEmpty()) {
                T startNode = adjacencyList.getKeys().get(0);
                queue.enqueue(startNode);
                visited.add(startNode, true);  // Mark startNode as visited
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T current = queue.dequeue();

            // Enqueue unvisited neighbors
            for (DirectedEdge<T> edge : getEdges(current)) {
                T neighbor = edge.to();
                if (!visited.contains(neighbor)) {
                    queue.enqueue(neighbor);
                    visited.add(neighbor, true);  // Mark neighbor as visited
                }
            }
            return current;
        }
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
}