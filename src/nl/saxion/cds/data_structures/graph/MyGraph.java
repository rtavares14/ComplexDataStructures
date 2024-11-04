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

    /**
     * Create a new graph.
     * This method is used to create a new graph.
     */
    public MyGraph() {
        this.adjacencyList = new MyHashMap<>();
    }

    /**
     * Add a vertex to the graph.
     * This method is used to add a vertex to the graph.
     */
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

    /**
     * Add an edge between two vertices in both directions.
     * This method is used to add an edge between two vertices in both directions.
     */
    @Override
    public void addEdgeBidirectional(T fromValue, T toValue, double weight) {
        addEdge(fromValue, toValue, weight);
        addEdge(toValue, fromValue, weight);
    }

    /**
     * Get the edges for the given vertex.
     * This method is used to get the edges for a given vertex.
     */
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

    /**
     * Get the total weight of the graph.
     * This method is used to get the total weight of the graph.
     */
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

    /**
     * Get the shortest paths between all nodes using Dijkstra's algorithm.
     * This method is used to get the shortest paths between all nodes using Dijkstra's algorithm.
     */
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

    /**
     * Get the shortest path between two nodes using Dijkstra's algorithm.
     * This method is used to get the shortest path between two nodes using Dijkstra's algorithm.
     */
    public SaxList<DirectedEdge<T>> getDijkstraPath(T startNode, T endNode) {
        SaxGraph<T> graph = shortestPathsDijkstra(startNode);
        SaxList<DirectedEdge<T>> reconstructedPath = new MyArrayList<>();
        T currentNode = endNode;


        while (!currentNode.equals(startNode)) {
            SaxList<DirectedEdge<T>> edges = graph.getEdges(currentNode);
            if (edges.isEmpty()) {
                return new MyArrayList<>();
            }
            DirectedEdge<T> edge = edges.get(0);
            DirectedEdge<T> reversed = new DirectedEdge<>(edge.to(), edge.from(),
                    edge.weight());
            reconstructedPath.addFirst(reversed);
            currentNode = edge.to();
        }
        return reconstructedPath;
    }

    /**
     * An estimator for the A* algorithm.
     * Method to estimate the distance between two nodes.
     * This method is used to estimate the distance between two nodes.
     */
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

    /**
     * Reconstruct the path from the start node to the goal node.
     * This method is used to reconstruct the path from the start node to the goal node.
     */
    private SaxList<DirectedEdge<T>> reconstructPath(AStarNode goalNode) {
        MyArrayList<DirectedEdge<T>> path = new MyArrayList<>();
        AStarNode currentNode = goalNode;

        while (currentNode.parent != null) {
            path.addFirst(currentNode.edge);
            currentNode = currentNode.parent;
        }
        return path;
    }

    /**
     * Convert a list of edges to a list of nodes.
     * This method is used to convert the result of shortestPathAStar to a list of nodes.
     * The list of nodes is the path from the start node to the end node.
     */
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
    public SaxGraph<T> minimumCostSpanningTree() {
        //Variation on Dijkstra :
        //- Select any node as root
        //- The priority queue is filled with nodes that are neighbors
        //of this node;
        //the priority is the weight of the connecting edge (low
        //weight is high priority)
        //NOTE: NOT the total cost (like with Dijkstra)
        //- Always remove the front node v from the priority queue
        //and add it to the tree, while update the priority queue with
        //all neighbors w of v,
        //if weight(v,w) < current w.weight

        MyGraph<T> resultGraph = new MyGraph<>();
        MyHeap<SaxGraph.DirectedEdge<T>> openList = new MyHeap<>(true);
        MyHashMap<T, SaxGraph.DirectedEdge<T>> closeList = new MyHashMap<>();

        T startNode = adjacencyList.getKeys().get(0);
        closeList.add(startNode, new DirectedEdge<>(startNode, startNode, 0));
        openList.enqueue(new DirectedEdge<>(startNode, startNode, 0));

        while (!openList.isEmpty()) {
            SaxGraph.DirectedEdge<T> currentEdge = openList.dequeue();
            T currentNode = currentEdge.to();

            if (!closeList.contains(currentNode)) {
                closeList.add(currentNode, currentEdge);
                resultGraph.addEdge(currentEdge.from(), currentEdge.to(),
                        currentEdge.weight());

                for (DirectedEdge<T> neighborEdge : getEdges(currentNode)) {
                    if (!closeList.contains(neighborEdge.to())) {
                        openList.enqueue(neighborEdge);
                    }
                }
            }
        }
        return resultGraph;
    }



    /**
     * Get an iterator that traverses the graph using a breadth-first search.
     */
    @Override
    public Iterator<T> iterator() {
        return new BFSIterator();
    }

    /**
     * Check if the graph is empty.
     */
    @Override
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    /**
     * Get the number of vertices in the graph.
     */
    @Override
    public int size() {
        return adjacencyList.size();
    }

    /**
     * Get a string representation of the graph in GraphViz format.
     */
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

    /**
     * Get the list of edges for the given vertex, or create a new list if it does not exist.
     * This method is used to get the list of edges for a vertex, or create a new list if it does not exist.
     */
    private MyArrayList<DirectedEdge<T>> getOrCreateList(T vertex) {
        if (!adjacencyList.contains(vertex)) {
            adjacencyList.add(vertex, new MyArrayList<>());
        }
        return adjacencyList.get(vertex);
    }

    /**
     * An iterator that traverses the graph using a breadth-first search.
     */
    private class BFSIterator implements Iterator<T> {
        private final MyHeap<T> queue;
        private final MyHashMap<T, Boolean> visited;

        /**
         * Create a new BFSIterator.
         */
        public BFSIterator() {
            this.queue = new MyHeap<>(true);
            this.visited = new MyHashMap<>();

            if (!adjacencyList.isEmpty()) {
                T startNode = adjacencyList.getKeys().get(0);
                queue.enqueue(startNode);
                visited.add(startNode, true);
            }
        }

        /**
         * Check if there are more nodes to visit.
         */
        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        /**
         * Get the next node in the BFS traversal.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T current = queue.dequeue();

            for (DirectedEdge<T> edge : getEdges(current)) {
                T neighbor = edge.to();
                if (!visited.contains(neighbor)) {
                    queue.enqueue(neighbor);
                    visited.add(neighbor, true);
                }
            }
            return current;
        }
    }

    /**
     * A node in the A* algorithm.
     */
    private class AStarNode implements Comparable<AStarNode> {
        DirectedEdge<T> edge;
        double g;
        double h;
        AStarNode parent;

        /**
         * Create a new AStarNode.
         *
         * @param edge   the edge to the node
         * @param g      the cost from the start node to this node
         * @param h      the estimated cost from this node to the goal node
         * @param parent the parent node
         */
        AStarNode(DirectedEdge<T> edge, double g, double h, AStarNode parent) {
            this.edge = edge;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        /**
         * Compare two AStarNodes based on their f = g + h values.
         */
        @Override
        public int compareTo(AStarNode other) {
            return Double.compare(this.g + this.h, other.g + other.h);
        }
    }
}