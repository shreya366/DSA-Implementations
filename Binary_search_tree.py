class Node:
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None

class BinarySearchTree:
    def __init__(self):
        self.root = None

    def insert(self, key):
        self.root = self._insert_recursive(self.root, key)

    def _insert_recursive(self, node, key):
        if node is None:
            return Node(key)
        if key < node.key:
            node.left = self._insert_recursive(node.left, key)
        else:
            node.right = self._insert_recursive(node.right, key)
        return node

    def search(self, key):
        return self._search_recursive(self.root, key)

    def _search_recursive(self, node, key):
        if node is None or node.key == key:
            return node is not None
        if key < node.key:
            return self._search_recursive(node.left, key)
        else:
            return self._search_recursive(node.right, key)

    def inorder_traversal(self):
        result = []
        self._inorder_recursive(self.root, result)
        return result

    def _inorder_recursive(self, node, result):
        if node:
            self._inorder_recursive(node.left, result)
            result.append(node.key)
            self._inorder_recursive(node.right, result)

if __name__ == "__main__":
    bst = BinarySearchTree()
    nodes = [50, 30, 70, 20, 40, 60, 80]

    for n in nodes:
        bst.insert(n)

    print("Inorder Traversal of BST:", bst.inorder_traversal())

    search_key = 40
    if bst.search(search_key):
        print(f"Node {search_key} found in the BST!")
    else:
        print(f"Node {search_key} not found in the BST.")
