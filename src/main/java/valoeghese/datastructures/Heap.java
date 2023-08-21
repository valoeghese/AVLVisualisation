package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

/**
 * Interface for interacting with a heap. The heap stores items based on some priority, and allows efficient access
 * to the highest priority elements.
 * @param <T> the type of element to store in the heap.
 */
public interface Heap<T extends Comparable<T>> extends Tree<T> {
	/**
	 * Find the element with the highest priority.
	 * @return the element with the highest priority. Null if the heap is empty.
	 */
	@Nullable T top();

	/**
	 * Remove the element with the highest priority.
	 * @return the element which had the highest priority.
	 * @throws NoSuchElementException if the heap is empty.
	 */
	T remove() throws NoSuchElementException;
}
