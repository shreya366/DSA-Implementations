import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define a class to represent an edge in a graph
class Edge {
    int source;         // Source vertex of the edge
    int destination;    // Destination vertex of the edge
    double weight;      // Weight or cost associated with the edge

    Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class Edmond {
    private int vertices;        // Number of vertices in the graph
    private List<Edge> edges;    // List to store edges in the graph

    public Edmond(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>(); // Initialize the list of edges
    }

    // Method to add an edge to the graph
    public void addEdge(int source, int destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
    }

    // Initialize distances for Bellman-Ford algorithm
    private double[] initializeDistances() {
        double[] distances = new double[vertices];
        for (int i = 0; i < vertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[0] = 0;
        return distances;
    }

    // Initialize parent array for Bellman-Ford algorithm
    private int[] initializeParents() {
        int[] parents = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parents[i] = -1;
        }
        return parents;
    }

    // Relaxation step in Bellman-Ford algorithm
    private boolean relax(Edge edge, double[] distances, int[] parents) {
        int source = edge.source;
        int destination = edge.destination;
        double weight = edge.weight;
        if (distances[source] != Double.POSITIVE_INFINITY && distances[source] + weight < distances[destination]) {
            distances[destination] = distances[source] + weight;
            parents[destination] = source;
            return true;
        }
        return false;
    }

    // Run the Bellman-Ford algorithm to find shortest paths
    private boolean runBellmanFord(int source, double[] distances, int[] parents) {
        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : edges) {
                relax(edge, distances, parents);
            }
        }

        for (Edge edge : edges) {
            int sourceVertex = edge.source;
            int destinationVertex = edge.destination;
            double weight = edge.weight;
            if (distances[sourceVertex] != Double.POSITIVE_INFINITY && distances[sourceVertex] + weight < distances[destinationVertex]) {
                return false; // Negative weight cycle detected
            }
        }
        return true;
    }

    // Construct the minimum arborescence (a directed tree) from parent array
    private List<Edge> constructArborescence(int[] parents) {
        List<Edge> arborescence = new ArrayList<>();
        for (int i = 1; i < vertices; i++) {
            int source = parents[i];
            int destination = i;
            double weight = getEdgeWeight(source, destination);
            arborescence.add(new Edge(source, destination, weight));
        }
        return arborescence;
    }

    // Get the weight of an edge given source and destination vertices
    private double getEdgeWeight(int source, int destination) {
        for (Edge edge : edges) {
            if (edge.source == source && edge.destination == destination) {
                return edge.weight;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    // Find the minimum arborescence rooted at a designated vertex
    public void findMinimumArborescence(int designatedVertex) {
        double[] distances = initializeDistances();
        int[] parents = initializeParents();

        if (!runBellmanFord(0, distances, parents)) {
            System.out.println("Negative weight cycle detected. Cannot find minimum arborescence.");
            return;
        }

        List<Edge> arborescence = constructArborescence(parents);
        double totalWeight = calculateTotalWeight(arborescence);

        System.out.println("Minimum Arborescence rooted at vertex " + designatedVertex + ":");
        System.out.println();
        for (Edge edge : arborescence) {
            System.out.println("(" + edge.source + ", " + edge.destination + ", " + edge.weight + ")");
        }
        System.out.println();
        System.out.println("Total weight: " + totalWeight);
    }

    // Calculate the total weight of an arborescence
    private double calculateTotalWeight(List<Edge> arborescence) {
        double totalWeight = 0;
        for (Edge edge : arborescence) {
            totalWeight += edge.weight;
        }
        return totalWeight;
    }

    // Main method to run the program
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the input file path as a command-line argument.");
            return;
        }

        String filePath = args[0];
        File file = new File(filePath);

        try {
            Scanner scanner = new Scanner(file);
            boolean readingEdges = false;
            int vertices = 0;
            Edmond edmond = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("** G")) {
                    if (readingEdges) {
                        // Finish processing the previous graph
                        int designatedVertex = 0; // Change this value as per your requirement
                        edmond.findMinimumArborescence(designatedVertex);
                        System.out.println();
                    }

                    vertices = Integer.parseInt(line.split("\\|V\\| = ")[1].trim());
                    edmond = new Edmond(vertices);
                    readingEdges = false;
                } else if (line.contains("(u, v) E = {")) {
                    readingEdges = true;
                } else if (readingEdges && line.contains("(")) {
                    // Parse edge data
                    String[] parts = line.split("\\(|\\)");
                    String[] values = parts[1].split(",");
                    int source = Integer.parseInt(values[0].trim());
                    int destination = Integer.parseInt(values[1].trim());
                    double weight = Double.parseDouble(values[2].trim());
                    edmond.addEdge(source, destination, weight);
                }
            }

            // Finish processing the last graph
            if (edmond != null) {
                int designatedVertex = 0; // Change this value as per your requirement
                edmond.findMinimumArborescence(designatedVertex);
                System.out.println();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }
}