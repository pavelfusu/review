package us.fusu.ds.trees.binary.iterator;

import java.util.Iterator;

import us.fusu.ds.trees.binary.Node;
import us.fusu.ds.trees.binary.traversals.TraversalType;

public abstract class BinaryTreeIterator<V> implements Iterator<V> {
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	public static <K> Iterator<K> iterator(TraversalType argType, Node<K> argRoot) {
		switch (argType) {
			case IN_ORDER: {
				return new InOrderIterator<K>(argRoot);
			}
			case IN_ORDER_REVERSED: {
              return new InOrderReversedIterator<K>(argRoot);
            }
			case PRE_ORDER: {
				return new PreOrderIterator<K>(argRoot);
			}
			case POST_ORDER: {
				return new PostOrderIterator<K>(argRoot);
			}
			case BREADTH_FIRST: {
				return new BreadthFirstIterator<>(argRoot);
			}
			default: {
				return null;
			}
		}
	}

}
