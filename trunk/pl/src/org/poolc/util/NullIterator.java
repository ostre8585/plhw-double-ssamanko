package org.poolc.util;

import java.util.Iterator;

public class NullIterator<T> implements Iterable<T> {
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}

			@Override
			public void remove() {
			}
		};
	}
}
