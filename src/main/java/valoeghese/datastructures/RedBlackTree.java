package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

/**
 * Red-Black self-balancing tree implementation.
 * @param <T> the type of data to store in the tree.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T, RedBlackTree<T>.Node> {

	@Override
	protected Node createNode(T elem) {
		return new Node(elem);
	}

	@Override
	public void add(T elem) {
		this.balance(this.addElement(elem));
	}

	/**
	 * Balance the node to maintain black-height properties.
	 * @param node the node to balance.
	 */
	private void balance(Node node) {
		// root is black.
		if (node.parent == null) {
			node.black = true;
			return;
		}

		// if parent is black properties are not violated.
		if (node.parent.black) {
			return;
		}

		// parent is red, grandparent is black
		// check uncle
		Node grandParent = node.parent.parent;
		assert grandParent != null : "Parent of a red node is never null, as the root is black.";

		@Nullable Node uncle = grandParent.getBrother(node.parent);

		// uncle is black
		if (Node.isBlack(uncle)) {
			Node parent = node.parent;

			// Are we in a zig-zag or zig-zig pattern?
			if (!node.isZigZig()) {
				// transform into zig-zig pattern
				parent = this.rotate(node);
				assert parent != null; // don't warn, it won't happen.
			}

			// rotate parent to grandparent
			this.rotate(parent);

			// new grandparent is black, old grandparent is red.
			parent.black = true;
			grandParent.black = false;
		}
		// uncle is red. Move blackness down from grandparent
		else {
			grandParent.black = false; // note: this is not the only violatable property, so cannot inline. grandParent.parent == null;
			uncle.black = true;
			node.parent.black = true;

			// check grandparent
			this.balance(grandParent);
		}
	}

	class Node extends BinarySearchTree<T, Node>.Node {
		Node(T elem) {
			super(elem);
		}

		boolean black;

		/**
		 * Get the brother of the given child.
		 * @param child the child node to get the brother of.
		 * @return the child's brother, if it is a child of this node. Else null.
		 */
		@Nullable
		Node getBrother(Node child) {
			if (this.rightChild == child) {
				return this.leftChild;
			}

			if (this.leftChild == child) {
				return this.rightChild;
			}

			return null;
		}

		static <T extends Comparable<T>> boolean isBlack(@Nullable RedBlackTree<T>.Node node) {
			return node == null || node.black;
		}
	}
}
