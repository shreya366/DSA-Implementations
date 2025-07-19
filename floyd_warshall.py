def floyd_warshall(graph):
    """
    Floyd-Warshall algorithm for finding shortest paths between all pairs of vertices.
    :param graph: 2D list representing adjacency matrix of the graph
    :return: 2D list with shortest distances
    """
    num_vertices = len(graph)
    dist = [row[:] for row in graph]  # Make a copy of the graph

    for k in range(num_vertices):
        for i in range(num_vertices):
            for j in range(num_vertices):
                if dist[i][k] + dist[k][j] < dist[i][j]:
                    dist[i][j] = dist[i][k] + dist[k][j]

    return dist


if __name__ == "__main__":
    INF = float('inf')
    graph = [
        [0, 3, INF, 7],
        [8, 0, 2, INF],
        [5, INF, 0, 1],
        [2, INF, INF, 0]
    ]

    shortest_paths = floyd_warshall(graph)

    print("All Pairs Shortest Path Matrix:")
    for row in shortest_paths:
        print(row)
