package valoeghese.datastructures;

import java.util.Iterator;

/**
 * Iterator that allows you to skip elements.
 * @param <T> the type of data to iterate over.
 */
public interface SkippingIterator<T> extends Iterator<T> {
	/**
	 * SKip forward this amount. You can go over the end of the array.
	 * @param amount the number of elements to skip.
	 */
	void skip(int amount);
}
