

import java.util.Iterator;
import java.util.LinkedList;

public class ComparableList<T extends Comparable<? super T>>
    extends LinkedList<T>
    implements Comparable<ComparableList<T>>
{
	// default constructor
	public ComparableList() {
		super();
	}

	// copy constructor
	public ComparableList(ComparableList<T> l) {
		super(l);
	}

	public ComparableList<T> reverse() {
		ComparableList<T> result = new ComparableList<T> ();
		for(T l : this)
		{
			result.add(0, l);
		}
		return result;
	}

    // Compare two lists.  Returns -1 if this list is less than the
    // list given as argument, +1 if its greater and 0 if both lists
    // contain the same elements.  Nonempty lists are compared
    // lexicographically.
    public int compareTo(ComparableList<T> comparableList) {
        Iterator<T> iterFirst  = iterator();
        Iterator<T> iterSecond = comparableList.iterator();
        while (iterFirst.hasNext() && iterSecond.hasNext()) {
            T   first  = iterFirst .next();
            T   second = iterSecond.next();
            int cmp    = first.compareTo(second);
            if (cmp == 0) {
                continue;
            }
            return cmp;
        }
        if (iterFirst.hasNext()) {
            return 1;
        }
        if (iterSecond.hasNext()) {
            return -1;
        }
        return 0;
    }
}
