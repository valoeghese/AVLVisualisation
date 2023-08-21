package valoeghese.datastructures;

/**
 * A splay tree. More frequently accessed elements are closer to the root, making them quick to access again.
 * Over time this should result in a reasonably self-balanced tree.
 */
public final class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T, SplayTree<T>.Node> {
	@Override
	protected Node createNode(T elem) {
		return new Node(elem);
	}

	@Override
	public void add(T elem) {
		this.makeRoot(this.addElement(elem));
	}

	@Override
	public boolean contains(T element) {
		Node node = this.find(element);

		if (node != null) {
			this.makeRoot(node);
			return true;
		}

		return false;
	}

	@Override
	public T findMin() {
		Node minNode = super.findMinNode();
		this.makeRoot(minNode);
		return minNode.elem;
	}

	@Override
	public T findMax() {
		Node maxNode = super.findMaxNode();
		this.makeRoot(maxNode);
		return maxNode.elem;
	}

	private void makeRoot(Node elem) {
		while (elem.parent != null) {
			// if straight line (/ or \) from grandparent, rotate parent first, then child
			if (elem.isZigZig()) {
				this.rotate(elem.parent);
			} else {
				// zig-zag rotation, rotate elem twice
				this.rotate(elem);
			}
		}

		this.rotate(elem);
	}

	class Node extends BinarySearchTree<T, Node>.Node {
		Node(T elem) {
			super(elem);
		}
	}
}
