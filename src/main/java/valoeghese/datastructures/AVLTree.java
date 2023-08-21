package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

/**
 * An AVL self-balancing binary search tree.
 * @param <T> the type of element to store in the tree.
 */
public final class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T, AVLTree<T>.Node> {
	@Override
	protected Node createNode(T elem) {
		return new Node(elem);
	}

	@Override
	public void add(T elem) {
		this.balance(this.addElement(elem));
	}

	/**
	 * Balance the tree, going up from newNode to the root. Will also update the heights.
	 * @param newNode the node to start balancing from.
	 */
	private void balance(Node newNode) {
		Node parent = newNode.parent;

		while (parent != null) {
			// update parent height
			parent.updateHeight();

			// Determine if the parent's children are unbalanced.
			int balance = parent.getBalance();

			// right is heavier to point of needing correction
			if (balance > 1) {
				assert parent.rightChild != null; // this should never not be null if balance > 1

				// if right child has heavier left
				if (parent.rightChild.getBalance() < 0) {
					Node toRotate = parent.rightChild.leftChild;
					assert toRotate != null; // this should never be null if right child unbalanced to left
					this.rotate(toRotate);
					parent = this.rotate(toRotate);
				} else {
					parent = this.rotate(parent.rightChild);
				}
			}
			// left is heavier to point of needing correction
			else if (balance < -1) {
				assert parent.leftChild != null; // this should never not be null if balance < 1

				// if left child has heavier right
				if (parent.leftChild.getBalance() > 0) {
					Node toRotate = parent.leftChild.rightChild;
					assert toRotate != null; // this should never be null if left child unbalanced to right
					this.rotate(toRotate);
					parent = this.rotate(toRotate);
				} else {
					parent = this.rotate(parent.leftChild);
				}
			}
			// otherwise it's balanced enough already.

			// move up once in the tree from the parent
			parent = parent.parent;
		}
	}

	@Override
	protected Node rotate(Node child) {
		Node oldParent = child.parent;
		Node result = super.rotate(child);

		// update heights
		if (result != null) {
			assert oldParent != null; // result will be null if its parent was null
			oldParent.updateHeight();
			result.updateHeight();
		}

		return result;
	}

	class Node extends BinarySearchTree<T, Node>.Node {
		Node(T elem) {
			super(elem);
		}

		int height;

		private int getBalance() {
			return getHeight(this.rightChild) - getHeight(this.leftChild);
		}

		private void updateHeight() {
			this.height = Math.max(getHeight(this.leftChild), getHeight(this.rightChild)) + 1;
		}

		@Override
		public String toString() {
			return super.toString() + " (height: " + this.height + ")";
		}

		private int getHeight(@Nullable Node node) {
			return node == null ? -1 : node.height;
		}
	}
}
