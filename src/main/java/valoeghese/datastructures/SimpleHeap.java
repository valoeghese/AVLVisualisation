package valoeghese.datastructures;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Basic implementation of a generic data structure. Stores elements in a tree where each node has two children,
 * and orders elements top to bottom based on a simple comparison.
 */
public class SimpleHeap<T extends Comparable<T>> implements Heap<T> {
	protected final DynamicArray<T> elements = new DynamicArray<>(16);

	public SimpleHeap(Comparator<T> priorityComparator) {
		this.priorityComparator = priorityComparator;
	}

	private Comparator<T> priorityComparator;

	@Override
	public void add(T elem) {
		this.elements.add(elem);
		this.heapify(this.size() - 1);
	}

	/**
	 * Heapify the heap from the given start index.
	 * @param startIndex the index to start heapifying from.
	 */
	private void heapify(int startIndex) {
		while (startIndex > 0) {
			int parent = startIndex/2;

			// if parent>child we are done
			if (this.priorityComparator.compare(this.elements.get(parent), this.elements.get(startIndex)) > 0) {
				break;
			}

			this.elements.swap(parent, startIndex);
			startIndex = parent;
		}
	}

	@Override
	public @Nullable T top() {
		return this.elements.isEmpty() ? null : this.elements.get(0);
	}

	@Override
	public T remove() throws NoSuchElementException {
		try {
			return this.elements.remove(0);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException("Cannot remove element from heap as heap is empty.", e);
		}
	}

	// nothing is assumed about the heap in this implementation. Blind search the array.

	@Override
	public @Nullable T findMin() {
		T smallest = null;

		for (T t : this.elements) {
			if (smallest == null || t.compareTo(smallest) < 0) {
				smallest = t;
			}
		}

		return smallest;
	}

	@Override
	public @Nullable T findMax() {
		T largest = null;

		for (T t : this.elements) {
			if (largest == null || t.compareTo(largest) > 0) {
				largest = t;
			}
		}

		return largest;
	}

	@Override
	public boolean contains(T element) {
		return this.elements.contains(element);
	}

	@Override
	public int size() {
		return this.elements.size();
	}

	/**
	 * Create a new min heap.
	 * @param <T> the type of data to store in the heap.
	 */
	public static <T extends Comparable<T>> SimpleHeap<T> newMinHeap() {
		return new MinHeap<>();
	}

	/**
	 * Create a new max heap.
	 * @param <T> the type of data to store in the heap.
	 */
	public static <T extends Comparable<T>> SimpleHeap<T> newMaxHeap() {
		return new MaxHeap<>();
	}

	private static int bits(int num) {
		int result = 0;

		while (num > 0) {
			num >>= 1;
			result++;
		}

		return result;
	}

	private static class MinHeap<T extends Comparable<T>> extends SimpleHeap<T> {
		public MinHeap() {
			super(Comparator.reverseOrder());
		}

		@Override
		public @Nullable T findMax() {
			SkippingIterator<T> iterator = this.elements.iterator();

			int layers = SimpleHeap.bits(this.size() - 1);

			if (layers > 1) {
				iterator.skip((1 << (layers - 1)));
			}

			T largest = null;

			while (iterator.hasNext()) {
				T next = iterator.next();

				if (largest == null || (next.compareTo(largest) > 0)) {
					largest = next;
				}
			}

			return largest;
		}

		@Override
		public @Nullable T findMin() {
			return this.top();
		}
	}

	private static class MaxHeap<T extends Comparable<T>> extends SimpleHeap<T> {
		public MaxHeap() {
			super(Comparator.naturalOrder());
		}

		@Override
		public @Nullable T findMax() {
			return this.remove();
		}

		@Override
		public @Nullable T findMin() {
			SkippingIterator<T> iterator = this.elements.iterator();

			int layers = SimpleHeap.bits(this.size() - 1);

			if (layers > 1) {
				iterator.skip((1 << (layers - 1)));
			}

			T smallest = null;

			while (iterator.hasNext()) {
				T next = iterator.next();

				if (smallest == null || (next.compareTo(smallest) < 0)) {
					smallest = next;
				}
			}

			return smallest;
		}
	}
}
