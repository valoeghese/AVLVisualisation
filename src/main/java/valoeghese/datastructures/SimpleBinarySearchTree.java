package valoeghese.datastructures;

/**
 * A simple binary search tree, with no self-balancing properties.
 * @param <T> the type of data to store in the tree.
 */
public final class SimpleBinarySearchTree<T extends Comparable<T>> extends BinarySearchTree<T, SimpleBinarySearchTree<T>.Node> {
	@Override
	protected Node createNode(T elem) {
		return new Node(elem);
	}

	@Override
	public void add(T elem) {
		this.addElement(elem);
	}

	class Node extends BinarySearchTree<T, Node>.Node {
		Node(T elem) {
			super(elem);
		}
	}
}
