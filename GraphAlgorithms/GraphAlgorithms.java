import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start can't be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> verlist =
            graph.getAdjacencyList();
        if (!verlist.containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }
        List<Vertex<T>> result = new ArrayList<>();
        Queue<Vertex<T>> myq = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        myq.add(start);
        visited.add(start);
        while (!myq.isEmpty()) {
            Vertex<T> temp = myq.remove();
            result.add(temp);
            for (VertexDistancePair<T> vertex: verlist.get(temp)) {
                if (!visited.contains(vertex.getVertex())) {
                    myq.add(vertex.getVertex());
                    visited.add(vertex.getVertex());
                }
            }
        }
        return result;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }
        if (start == null) {
            throw new IllegalArgumentException("Start can't be null");
        }
        Map<Vertex<T>, List<VertexDistancePair<T>>> verlist =
            graph.getAdjacencyList();
        if (!verlist.containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }
        List<Vertex<T>> result = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        realdepthFirstSearch(start, visited, result, verlist);
        return result;
    }
    /**
    * Helper method for the dfs algorithm
    *
    * @param start the start
    * @param visited set of visited vertices
    * @param result the result output
    * @param vertexList the vertex list
    * @param <T> the data type
    */
    private static <T> void realdepthFirstSearch(Vertex<T>
        start, Set<Vertex<T>> visited,
        List<Vertex<T>> result,
        Map<Vertex<T>, List<VertexDistancePair<T>>> vertexList) {
        result.add(start);
        visited.add(start);
        for (VertexDistancePair<T> vertex: vertexList.get(start)) {
            if (!visited.contains(vertex.getVertex())) {
                realdepthFirstSearch(vertex.getVertex(),
                    visited, result, vertexList);
            }
        }
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph can't be null");
        }
        if (start == null) {
            throw new IllegalArgumentException("start can't be null");
        }
        Map<Vertex<T>, List<VertexDistancePair<T>>> verlist =
            graph.getAdjacencyList();
        if (!verlist.containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }
        Queue<VertexDistancePair<T>> myq = new PriorityQueue<>();
        Map<Vertex<T>, Integer> result = new HashMap<>();
        for (Vertex<T> vertex:verlist.keySet()) {
            if (vertex.equals(start)) {
                result.put(vertex, 0);
            } else {
                result.put(vertex, Integer.MAX_VALUE);
            }
        }
        myq.add(new VertexDistancePair<>(start, 0));
        while (!myq.isEmpty()) {
            VertexDistancePair<T> temp = myq.remove();
            for (VertexDistancePair<T> ver : verlist.get(temp.getVertex())) {
                if (result.get(ver.getVertex())
                    > temp.getDistance() + ver.getDistance()) {
                    result.put(ver.getVertex(), temp.getDistance()
                        + ver.getDistance());
                    myq.add(new VertexDistancePair<T>(ver.getVertex(),
                        temp.getDistance() + ver.getDistance()));
                }
            }
        }
        return result;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be
     * one valid MST that can be formed. In addition, only an undirected graph
     * will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are creating the MST for
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph can't be null");
        }
        if (start == null) {
            throw new IllegalArgumentException("start can't be null");
        }

        Set<Edge<T>> mstpath = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Edge<T>> pq =
            new PriorityQueue<>(graph.getEdgeList().size());

        visited.add(start);

        Map<Vertex<T>, List<VertexDistancePair<T>>> initAdj =
            graph.getAdjacencyList();
        if (!initAdj.containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }

        int count = 0;

        for (Vertex<T> init : initAdj.keySet()) {
            List<VertexDistancePair<T>> pairList = initAdj.get(init);
            for (VertexDistancePair<T> pair : pairList) {
                Vertex<T> dest = pair.getVertex();
                int weight = pair.getDistance();
                if (!visited.contains(dest)) {
                    pq.add(new Edge(init, dest, weight, graph.isDirected()));
                }
            }
        }

        while (!pq.isEmpty()) {
            // find min edge that leads to a new vertex
            Edge<T> minEdge = pq.remove();
            while (!pq.isEmpty() && visited.contains(minEdge.getV())) {
                minEdge = pq.remove();
            }

            if (!visited.contains(minEdge.getV())) {
                // add vertex to mst path, add dest to visited
                mstpath.add(minEdge);
                Vertex<T> v = minEdge.getV();
                visited.add(v);
                // add new edges to pq

                Map<Vertex<T>, List<VertexDistancePair<T>>> adj =
                    graph.getAdjacencyList();
                List<VertexDistancePair<T>> pairList2 = adj.get(v);
                for (VertexDistancePair<T> pair2 : pairList2) {
                    Vertex<T> dest2 = pair2.getVertex();
                    int weight2 = pair2.getDistance();
                    if (!visited.contains(dest2)) {
                        pq.add(new Edge(v, dest2, weight2, graph.isDirected()));
                    }
                }
            }
        }
        if (mstpath.size() == 0) {
            return null;
        }

        return mstpath;
    }

}
