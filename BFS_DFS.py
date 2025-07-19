from collections import deque

class Graph:
    def __init__(self, vertices):
        self.graph = {i: [] for i in range(vertices)}

    def add_edge(self, u, v):
        self.graph[u].append(v)
        self.graph[v].append(u)  # For undirected graph

    def bfs(self, start):
        visited = set()
        queue = deque([start])
        order = []

        while queue:
            vertex = queue.popleft()
            if vertex not in visited:
                visited.add(vertex)
                order.append(vertex)
                queue.extend(n for n in self.graph[vertex] if n not in visited)
        return order

    def dfs(self, start):
        visited = set()
        stack = [start]
        order = []

        while stack:
            vertex = stack.pop()
            if vertex not in visited:
                visited.add(vertex)
                order.append(vertex)
                stack.extend(n for n in self.graph[vertex] if n not in visited)
        return order

# Example usage
if __name__ == "__main__":
    g = Graph(5)
    edges = [(0, 1), (0, 2), (1, 3), (1, 4)]
    for u, v in edges:
        g.add_edge(u, v)
    print("BFS:", g.bfs(0))
    print("DFS:", g.dfs(0))
