

import java.util.List;

public class Animation {


	public static Move move(ListTripel before, ListTripel after)
	{
		if(before.mFirst.compareTo(after.mFirst) == 0)
		{
			ComparableList<Integer> waggons = findDiff(before.mSecond, after.mSecond);

			// B->C or C->B
			if(before.mSecond.contains(0))
			{
				// B->C
				return new Move(Move.B_C, 0, waggons);
			}
			else
			{
				// C->B
				return new Move(Move.C_B, 0, waggons);
			}
		}
		else if(before.mSecond.compareTo(after.mSecond) == 0)
		{
			ComparableList<Integer> waggons = findDiff(before.mFirst, after.mFirst);

			// A->C or C->A
			if(before.mFirst.contains(0))
			{
				// A->C
				return new Move(Move.A_C, westOrEast(before.mFirst, after.mThird, waggons), waggons);
			}
			else
			{
				// C->A
				return new Move(Move.C_A, westOrEast(after.mThird, before.mFirst, waggons), waggons);
			}
		}
		else
		{
			throw new AssertionError("INVALID RELATION");
		}
	}

	private static int westOrEast(ComparableList<Integer> beforeA, ComparableList<Integer> afterB, ComparableList<Integer> waggons)
	{
		ComparableList<Integer> frontA = new ComparableList<Integer>();
		int toIndex = beforeA.size() < waggons.size() ? beforeA.size() : waggons.size();
		List<Integer> sublist = beforeA.subList(0, toIndex);
		frontA.addAll(sublist);

		ComparableList<Integer> frontB = new ComparableList<Integer>();
		toIndex = afterB.size() < waggons.size() ? afterB.size() : waggons.size();
		sublist = afterB.subList(0, toIndex);
		frontB.addAll(sublist);

		if(frontA.compareTo(waggons) == 0 && frontB.compareTo(waggons.reverse())== 0)
		{
			return Move.WEST;
		}
		else
		{
			return Move.EAST;
		}
	}

	private static ComparableList<Integer> findDiff(ComparableList<Integer> a,
			ComparableList<Integer> b) {
		ComparableList<Integer> l = new ComparableList<Integer>();

		for (Integer i : a) {
			if (!b.contains(i))
				l.add(i);
		}
		for (Integer i : b) {
			if (!a.contains(i))
				l.add(i);
		}
		return l;
	}
}