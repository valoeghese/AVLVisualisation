package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

/**
 * A skeleton of a binary search tree implementation.
 * @param <T> the type of element to store in the tree.
 * @param <N> the class of the nodes in the tree.
 */
public abstract class BinarySearchTree<T extends Comparable<T>, N extends BinarySearchTree<T, N>.Node> implements Tree<T> {
	// Fields //

	@Nullable protected N root;
	private int elementCount;

	// Abstract Methods //

	/**
	 * Construct a new node with the given element.
	 * @param elem the node to create.
	 * @return the new node.
	 */
	protected abstract N createNode(T elem);

	// Useful Methods for Subclasses //

	/**
	 * Add the given element as a node in the binary search tree, and retrieve the new node.
	 * @param elem the element to add.
	 * @return the node created.
	 */
	protected N addElement(T elem) {
		this.elementCount++;

		if (this.root == null) {
			return this.root = this.createNode(elem);
		}

		N parent = null;
		N child = this.root;

		// find the location to insert
		while (child != null) {
			// move left if our element is less, right otherwise
			if (elem.compareTo(child.elem) < 0) {
				parent = child;
				child = child.leftChild;
			} else {
				parent = child;
				child = child.rightChild;
			}
		}

		// parent now contains the node to insert as a child of.
		N newNode = this.createNode(elem);
		newNode.parent = parent;

		// determine direction to insert in
		if (elem.compareTo(parent.elem) < 0) {
			parent.leftChild = newNode;
		} else {
			parent.rightChild = newNode;
		}

		return newNode;
	}

	/**
	 * Rotates the given child node around its parent node.
	 * @param child the child node.
	 * @return the new parent.
	 */
	protected @Nullable N rotate(N child) {
		// cannot rotate the root node with its parent
		if (child.parent == null) return null;

		N oldParent = child.parent;

		if (oldParent.parent != null) {
			N grandParent = oldParent.parent;

			// make child the child of its grandparent
			grandParent.replace(oldParent, child);
		} else {
			// child is now root
			child.parent = null;
			this.root = child;
		}

		// swap parent and child
		// in any case the old parent's parent is now the child
		oldParent.parent = child;

		// next behaviour depends on whether the child was a left child or right
		// bring parent up, bring child down

		boolean isLeft = oldParent.leftChild == child;

		if (isLeft) {
			oldParent.leftChild = child.rightChild;
			if (oldParent.leftChild != null)
				oldParent.leftChild.parent = oldParent;

			child.rightChild = oldParent;
		} else {
			oldParent.rightChild = child.leftChild;
			if (oldParent.rightChild != null)
				oldParent.rightChild.parent = oldParent;

			child.leftChild = oldParent;
		}

		return child;
	}

	// Overridden Methods //

	@Override
	public boolean contains(T element) {
		return this.find(element) != null;
	}

	/**
	 * Find the node containing a given element.
	 * @param element the element to find in the tree.
	 * @return the node containing the element. Null if it is not found in the tree.
	 */
	protected @Nullable N find(T element) {
		N node = this.root;

		if (node == null) return null;

		// binary search for the element
		while (node != null) {
			int comparison = element.compareTo(node.elem);

			if (comparison == 0) {
				return node; // the element has been found
			} else if (comparison < 0) {
				node = node.leftChild;
			} else {
				node = node.rightChild;
			}
		}

		return null;
	}

	@Override
	public T findMax() {
		return this.findMaxNode().elem;
	}

	@Override
	public T findMin() {
		return this.findMinNode().elem;
	}

	protected N findMaxNode() {
		N node = this.root;

		if (node == null) return null;

		// keep going right (larger number) until you cannot go any further
		while (node.rightChild != null)
			node = node.rightChild;

		return node;
	}

	protected N findMinNode() {
		N node = this.root;

		if (node == null) return null;

		// keep going left (smaller number) until you cannot go any further
		while (node.leftChild != null)
			node = node.leftChild;

		return node;
	}

	@Override
	public int size() {
		return this.elementCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Node.appendStringRepr(builder, this.root, 0);
		return builder.toString();
	}

	// Inner Classes //

	/**
	 * A node within the tree.
	 */
	protected abstract class Node {
		Node(T elem) {
			this.elem = elem;
		}

		final T elem;
		@Nullable N parent;
		@Nullable N leftChild;
		@Nullable N rightChild;

		/**
		 * Replace the given child with the new child, and updates the new child's parent.
		 * Please note the old child's parent will <b>not</b> be updated.
		 * @param child the child of this node to replace.
		 * @param newChild the node to replace the given child with.
		 */
		protected void replace(N child, N newChild) {
			if (this.leftChild == child) {
				this.leftChild = newChild;
			} else if (this.rightChild == child) {
				this.rightChild = newChild;
			} else {
				// if the child isn't a child of this node don't do anything.
				return;
			}

			newChild.parent = (N) this;
		}

		protected boolean isZigZig() {
			if (this.parent == null) return false;
			if (this.parent.parent == null) return false;

			N grandParent = this.parent.parent;

			boolean leftLine = this.parent.leftChild == this && grandParent.leftChild == this.parent;
			boolean rightLine = this.parent.rightChild == this && grandParent.rightChild == this.parent;
			return rightLine || leftLine;
		}

		@Override
		public String toString() {
			return this.elem + (this.parent == null ? "":(" p: " + this.parent.elem));
		}

		private static <T extends Comparable<T>, N extends BinarySearchTree<T, N>.Node> void appendStringRepr(StringBuilder builder, BinarySearchTree<T, N>.Node node, int indent) {
			// draw | and - connecting elements in the tree
			for (int i = 0; i < indent - 1; i++) {
				builder.append("| ");
			}

			if (indent > 0) {
				builder.append("|-");
			}

			if (node == null) {
				builder.append("nil");
			} else {
				builder.append(node).append("\n");
				appendStringRepr(builder, node.leftChild, indent + 1);
				builder.append("\n");
				appendStringRepr(builder, node.rightChild, indent + 1);
			}
		}
	}
}
