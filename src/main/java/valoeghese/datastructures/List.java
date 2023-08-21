package valoeghese.datastructures;

/**
 * A variable-size container that contained ordered elements, allows duplicates, and accessing elements.
 * @param <T> the type of element to store in this list.
 */
public interface List<T> extends Iterable<T> {
	/**
	 * Add the given element to this list.
	 * @param element the element to add to the list.
	 */
	void add(T element);

	/**
	 * Prepends the given element to this list.
	 * @param element the element to prepend to the list.
	 */
	void prepend(T element);

	/**
	 * Remove the element at the specified index. The rest of the list will be moved backwards to fill the gap.
	 * @param index the index at which to remove an element.
	 * @return the removed element.
	 * @throws IndexOutOfBoundsException if the requested index is outside the bounds of this list.
	 */
	default T remove(int index) throws IndexOutOfBoundsException {
		return this.remove(index, false);
	}

	/**
	 * Remove the element at the specified index. The rest of the list will be moved to fill the gap.
	 * @param index the index at which to remove an element.
	 * @param shiftForwards determines the direction to resolve the gap. If true, the minimum index will increase;
	 *                   if false, the maximum index will decrease.
	 * @return the removed element.
	 * @throws IndexOutOfBoundsException if the requested index is outside the bounds of this list.
	 */
	T remove(int index, boolean shiftForwards) throws IndexOutOfBoundsException;

	/**
	 * Swap the elements at the given indices.
	 * @param index the first index of the swap.
	 * @param otherIndex the second index of the swap.
	 * @throws IndexOutOfBoundsException if either of the indices provided are outside the bounds of the list.
	 */
	void swap(int index, int otherIndex) throws IndexOutOfBoundsException;

	/**
	 * Get the element at the specified index.
	 * @param index the index from which to retrieve the object.
	 * @return the element at that index in the list.
	 * @throws IndexOutOfBoundsException if the index provided is outside the bounds of this list.
	 */
	T get(int index) throws IndexOutOfBoundsException;

	/**
	 * Check whether this list contains the given element.
	 * @param element the element to search for in this list.
	 */
	default boolean contains(T element) {
		return this.indexOf(element) != this.noElement();
	}

	/**
	 * Get the first index of the list containing this element.
	 * @param element the element to search for in this list.
	 * @return the first index containing the provided element, if it is contained within the list.
	 * Otherwise {@link List#noElement()}.
	 */
	default int indexOf(T element) {
		return this.indexOf(element, 0);
	}

	/**
	 * Get the last index of the list containing this element.
	 * @param element the element to search for in this list.
	 * @return the last index containing the provided element, if it is contained within the list.
	 * Otherwise {@link List#noElement()}.
	 */
	default int lastIndexOf(T element) {
		return this.indexOf(element, -1);
	}

	/**
	 * Get the nth index at which this element is located.
	 * @param element the element to search for in this list.
	 * @param n the number of times to 'skip' this element within the list before checking. For example, in the
	 *             zero-indexed list {@code [0, 4, 6, 9, 4, 3, 4]}, {@code indexOf(4, 0) == 1},
	 *             {@code indexOf(4, 1) == 4}, {@code indexOf(4, 2) == 6}. <br>
	 *          In addition, negative indices should search from the end of the list, where -1 is the last element.
	 *          So {@code indexOf(4, -1) == 6}, etc...
	 * @return the nth index containing the provided element, or {@link List#noElement()} if it is not present in this list.
	 */
	int indexOf(T element, int n);

	/**
	 * Get the size of this list.
	 * @return the number of elements contained in this list.
	 */
	int size();

	/**
	 * Get whether this list is empty.
	 * @return whether this list is empty.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Get the lowest index in this list.
	 * @return the lowest index contained in this list.
	 */
	int minIndex();

	/**
	 * Get the lowest index in this list.
	 * @return the lowest index contained in this list.
	 */
	default int maxIndex() {
		return this.minIndex() + this.size() - 1;
	}

	/**
	 * Value representing the absence of an element in the list. This may change if the minimum index changes.
	 */
	default int noElement() {
		return this.minIndex() - 1;
	}
}
