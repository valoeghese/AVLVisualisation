package valoeghese.datastructures;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Dynamic Array data structure. Implements the java.util.List interface. Automatically resizes if it gets too large
 * or small.
 */
public final class DynamicArray<T> implements List<T> {
	public DynamicArray(int initialSize) {
		this(0, initialSize);
	}

	public DynamicArray(int minIndex, int initialSize) {
		// we have to do Object unless we take a generator, as Java doesn't allow initialising generic type arrays.
		this.array = new Object[initialSize];
		this.min = minIndex;
	}

	private int min;
	private Object[] array;
	private int size;

	@Override
	public void add(T element) {
		if (this.size == this.array.length - 1) {
			// double array size to fit more data
			this.array = Arrays.copyOf(this.array, this.array.length * 2);
		}

		this.array[this.size++] = element;
	}

	@Override
	public void prepend(T element) {
		if (this.size == this.array.length - 1) {
			// double array size to fit more data
			this.array = Arrays.copyOf(this.array, this.array.length * 2);
		}

		// shift list over by 1
		System.arraycopy(this.array, 0, this.array, 1, this.size);
		this.array[0] = element;
		this.min++;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T remove(int index, boolean shiftForwards) throws IndexOutOfBoundsException {
		this.checkIndex(index);

		index -= this.min;
		T result = (T) this.array[index];
		System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
		this.size--;

		// adjust array size if array size has quartered
		if (this.size <= this.array.length / 4) {
			this.array = Arrays.copyOf(this.array, this.array.length / 4);
		}

		if (shiftForwards) {
			this.min++;
		}

		return result;
	}

	@Override
	public void swap(int index, int otherIndex) throws IndexOutOfBoundsException {
		this.checkIndex(index);
		this.checkIndex(otherIndex);

		Object temp = this.array[index - this.min];
		this.array[index - this.min] = this.array[otherIndex - this.min];
		this.array[otherIndex - this.min] = temp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int index) throws IndexOutOfBoundsException {
		this.checkIndex(index);
		return (T) this.array[index - this.min];
	}

	@Override
	public int indexOf(T element, int n) {
		if (n < 0) {
			// search from last index
			// convert from n to number of skips
			n = -n - 1;

			for (int i = this.size - 1; i >= 0; i--) {
				if (Objects.equals(this.array[i], element)) {
					if (n == 0) {
						return i + this.min;
					}

					n--;
				}
			}
		} else {
			// search from first index
			for (int i = 0; i < this.size; i++) {
				if (Objects.equals(this.array[i], element)) {
					if (n == 0) {
						return i + this.min;
					}

					n--;
				}
			}
		}

		return this.noElement();
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int minIndex() {
		return this.min;
	}

	@NotNull
	@Override
	public SkippingIterator<T> iterator() {
		return new DynamicArrayIterator();
	}

	/**
	 * Test whether the given index is within the bounds of this list. Throws an {@link IndexOutOfBoundsException}
	 * if it breaks the bounds.
	 * This is more useful than delegating to natural {@link ArrayIndexOutOfBoundsException} as it provides the actual
	 * list bounds.
	 * @param index the index to test.
	 * @throws IndexOutOfBoundsException if the index is not within the bounds of this list.
	 */
	private void checkIndex(int index) throws IndexOutOfBoundsException {
		if (index < this.min || index > this.maxIndex()) {
			throw new IndexOutOfBoundsException("Index " + index + "outside of list bounds ("
					+ this.min + ":" + this.maxIndex() + ")");
		}
	}

	private class DynamicArrayIterator implements SkippingIterator<T> {
		private int arrayIndex = 0;

		@Override
		public boolean hasNext() {
			return this.arrayIndex < DynamicArray.this.size;
		}

		@Override
		public void skip(int amount) {
			this.
		}

		@Override
		@SuppressWarnings("unchecked")
		public T next() throws NoSuchElementException {
			if (!this.hasNext()) {
				throw new NoSuchElementException("No element at index " + (this.arrayIndex + DynamicArray.this.minIndex()));
			}

			return (T) DynamicArray.this.array[this.arrayIndex++];
		}
	}
}
