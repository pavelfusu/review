package us.fusu.ds.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * An iterator capable of iterating through a list of lists
 * @param <T> the type of the values contained in the lists
 */
public class ListsIterator<T> implements Iterator<T>{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K> Iterator<K> createIterator(List<List<K>> argListOfLists) {
		return new ListsIterator(argListOfLists);
	}
	
	private List<List<T>> lists;
	private Iterator<List<T>> listIt;
	private Iterator<T> currentIt;
	
	public ListsIterator(List<List<T>> argListOfLists) {
		if (argListOfLists == null) {
			throw new NullPointerException();
		}
		lists = Collections.unmodifiableList(argListOfLists);
		listIt = lists.iterator();
	}
	
	@Override
	public boolean hasNext() {
		while ((currentIt == null || !currentIt.hasNext()) && listIt.hasNext()) {
			currentIt = listIt.next().iterator();
		}
		return currentIt != null && currentIt.hasNext();
	}

	@Override
	public T next() {
		return currentIt.next();
	}

	@Override
	public void remove() {
		currentIt.remove();
	}
}
