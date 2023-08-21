package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

/**
 * Interface for interacting with any tree.
 * @param <T> the type of element to store in the tree.
 */
public interface Tree<T> {
	/**
	 * Add the given element to the binary search tree.
	 * @param elem the element to add.
	 */
	void add(T elem);

	/**
	 * Checks whether the tree contains the given element.
	 * @param element the element to look for in the tree.
	 * @return whether the given element was found in the tree.
	 */
	boolean contains(T element);

	/**
	 * Finds the maximum element in the tree.
	 * @return the maximum element in the tree, or null if the tree is empty.
	 */
	@Nullable
	T findMax();

	/**
	 * Finds the minimum element in the tree.
	 * @return the minimum element in the tree, or null if the tree is empty.
	 */
	@Nullable
	T findMin();

	/**
	 * Get the number of elements in this tree.
	 * @return the number of elements in this tree.
	 */
	int size();

	/**
	 * Get whether this tree is empty.
	 * @return whether this tree is empty.
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}
}
