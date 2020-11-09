

public class Tripel<S extends Comparable<? super S>, T extends Comparable<? super T>, K extends Comparable<? super K>>
		implements Comparable<Tripel<S, T, K>> {
	S mFirst;
	T mSecond;
	K mThird;

	public Tripel(S first, T second, K third) {
		mFirst = first;
		mSecond = second;
		mThird = third;
	}

	public int compareTo(Tripel<S, T, K> tripel) {
		// a < x
		// v (a = x & b < y)
		// v (a = x & b = y & c < z)
		if ((tripel.mFirst.compareTo(mFirst) == -1)
				|| (tripel.mFirst.compareTo(mFirst) == 0 && tripel.mSecond
						.compareTo(mSecond) == -1)
				|| (tripel.mFirst.compareTo(mFirst) == 0
						&& tripel.mSecond.compareTo(mSecond) == 0 && tripel.mThird
						.compareTo(mThird) == -1)) {
			return -1;
		} else if (tripel.mFirst.compareTo(mFirst) == 0
				&& tripel.mSecond.compareTo(mSecond) == 0
				&& tripel.mThird.compareTo(mThird) == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public String toString() {
		return "<" + mFirst + ", " + mSecond + ", " + mThird + ">";
	}

	public S getFirst() {
		return mFirst;
	}

	public T getSecond() {
		return mSecond;
	}

	public K getThird() {
		return mThird;
	}

	public void setFirst(S first) {
		mFirst = first;
	}

	public void setSecond(T second) {
		mSecond = second;
	}

	public void setThird(K third) {
		mThird = third;
	}
}