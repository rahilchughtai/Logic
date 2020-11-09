

import java.util.List;


public class Rangierprblem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// All := { 0 .. 3 };
		ComparableSet<Integer> all = ComparableSet.range(0, 3);

		System.out.println("ALL:");
		System.out.println(all+"\n");

		ComparableSet<ComparableSet<Integer>> powerAll = all.powerSet();
		System.out.println("powerAll:");
		System.out.println(powerAll+"\n");

		// Partitions := { [ A, B, C ] : A in pow All, B in pow All, C in pow
		// All |
		// A * B = {} and A * C = {} and B * C = {} and A + B + C = All };
		PartitionSet partitions = new PartitionSet();
		for (ComparableSet<Integer> a : powerAll) {
			for (ComparableSet<Integer> b : powerAll) {
				for (ComparableSet<Integer> c : powerAll) {
					ComparableSet<Integer> iAB = a.intersection(b);
					ComparableSet<Integer> iAC = a.intersection(c);
					ComparableSet<Integer> iBC = b.intersection(c);
					ComparableSet<Integer> u = a.union(b).union(c);
					if (u.compareTo(all) == 0 && iAB.isEmpty() && iAC.isEmpty()
							&& iBC.isEmpty()) {
						partitions.add(new SetTripel(a, b, c));
					}
				}
			}
		}

		System.out.println("partitions: "+partitions.size());
		System.out.println(partitions+"\n");

		// P := { [LA,LB,LC] : [A,B,C] in Partitions,
		// LA in toList(A), LB in toList(B), LC in toList(C) };
		PartitionList P = new PartitionList();
		for (SetTripel t : partitions) {
			ComparableSet<ComparableList<Integer>> LA = t.getFirst().toList();
			ComparableSet<ComparableList<Integer>> LB = t.getSecond().toList();
			ComparableSet<ComparableList<Integer>> LC = t.getThird().toList();

			for(ComparableList<Integer> eLA : LA)
			{
				for(ComparableList<Integer> eLB : LB)
				{
					for(ComparableList<Integer> eLC : LC)
					{
						ListTripel tripel = new ListTripel(eLA, eLB, eLC);
						P.add(tripel);
					}
				}
			}

		}

		System.out.println("P: "+P.size());
		System.out.println(P+"\n");

		// -- RACeast beschreibt Zustands�berg�nge, bei denen die Lokomotive vom
		// Gleis A nach Osten
		// -- zum Gleis C f�hrt.
		// RACeast := { [ [ A, B, C ], [ A(1..k-1), B, C + reverse(A(k..)) ] ]
		// : [ A,B,C ] in P, k in {1 .. #A } | 0 in A(k..)};
		ComparableSet<TripelPair> RACeast = new ComparableSet<TripelPair>();
		for(ListTripel tripel : P)
		{
			ComparableList<Integer> A = tripel.getFirst();
			ComparableList<Integer> B = tripel.getSecond();
			ComparableList<Integer> C = tripel.getThird();

			for(int k = 0; k < A.size(); ++k)
			{
				ComparableList<Integer> Arest = new ComparableList<Integer>();
				List<Integer> list = A.subList(k, A.size());
				Arest.addAll(list);

				if(Arest.contains(0))
				{
					// A(1..k-1)
					ComparableList<Integer> ANew = new ComparableList<Integer>();
					List<Integer> li = A.subList(0, k);
					ANew.addAll(li);
					/* for(int i = 0; i < k-1; ++i)
						ANew.add(A.get(i)); */


					// C + reverse(A(k..))
					ComparableList<Integer> CNew = new ComparableList<Integer>(C);
					CNew.addAll(Arest.reverse());

					ListTripel lt = new ListTripel(ANew,B,CNew);
					RACeast.add(new TripelPair(tripel, lt));
				}
			}
		}

	//	System.out.println("RACeast: "+RACeast.size());
	//	System.out.println(RACeast+"\n");

		// -- RACwest beschreibt Zustands�berg�nge, bei denen die Lokomotive vom
		// Gleis A nach Westen
		// -- zum Gleis C f�hrt.
		// RACwest := { [ [ A, B, C ], [ A(k+1..), B, reverse(A(1..k)) + C ] ]
		// : [ A,B,C ] in P, k in {1 .. #A } | 0 in A(1..k)
		// };

		ComparableSet<TripelPair> RACwest = new ComparableSet<TripelPair>();
		for(ListTripel tripel : P)
		{
			ComparableList<Integer> A = tripel.getFirst();
			ComparableList<Integer> B = tripel.getSecond();
			ComparableList<Integer> C = tripel.getThird();

			for(int k = 0; k < A.size(); ++k)
			{
				ComparableList<Integer> AuntilK = new ComparableList<Integer>();
				List<Integer> list = A.subList(0, k+1);
				AuntilK.addAll(list);

				if(AuntilK.contains(0))
				{
					// A(k+1..)
					ComparableList<Integer> ANew = new ComparableList<Integer>();
					List<Integer> sublist = A.subList(k+1, A.size());
					ANew.addAll(sublist);

					// reverse(A(1..k)) + C
					ComparableList<Integer> CNew = new ComparableList<Integer>();
					CNew.addAll(AuntilK.reverse());
					CNew.addAll(C);

					ListTripel lt = new ListTripel(ANew,B,CNew);
					RACwest.add(new TripelPair(tripel, lt));
				}
			}
		}

	//	System.out.println("RACwest: "+RACwest.size());
	//	System.out.println(RACwest+"\n");

		// RAC := RACeast + RACwest;
		ComparableSet<TripelPair> RAC = RACeast.union(RACwest);

		// RCA := inverse(RAC);
		ComparableSet<TripelPair> RCA = inverse(RAC);

		// -- RBC beschreibt Zustands�berg�nge, bei denen die Lokomotive vom
		// Gleis B
		// -- zum Gleis C f�hrt.
		// RBC := { [ [ A, B, C ], [ A, B(1..k-1), B(k..) + C ] ]
		// : [ A,B,C ] in P, k in {1 .. #B } | 0 in B(k..)
		// };

		ComparableSet<TripelPair> RBC = new ComparableSet<TripelPair>();
		for(ListTripel tripel : P)
		{
			ComparableList<Integer> A = tripel.getFirst();
			ComparableList<Integer> B = tripel.getSecond();
			ComparableList<Integer> C = tripel.getThird();

			for(int k = 0; k < B.size(); ++k)
			{
				ComparableList<Integer> Brest = new ComparableList<Integer>();
				List<Integer> list = B.subList(k, B.size());
				Brest.addAll(list);

				if(Brest.contains(0))
				{
					// B(1..k-1)
					ComparableList<Integer> BNew = new ComparableList<Integer>();
					List<Integer> sublist = B.subList(0,k);
					BNew.addAll(sublist);

					// B(k..) + C
					ComparableList<Integer> CNew = new ComparableList<Integer>(Brest);
					CNew.addAll(C);

					ListTripel lt = new ListTripel(A,BNew,CNew);
					RBC.add(new TripelPair(tripel, lt));
				}
			}
		}

		// RCB := inverse(RBC);
		ComparableSet<TripelPair> RCB = inverse(RBC);

		System.out.println("RAC: "+RAC.size());
		System.out.println(RAC+"\n");

		System.out.println("RCA: "+RCA.size());
		System.out.println(RCA+"\n");

		System.out.println("RBC: "+RBC.size());
		System.out.println(RBC+"\n");

		System.out.println("RCB: "+RCB.size());
		System.out.println(RCB+"\n");

		//-- R ist die Menge aller Verbindungen zwischen Punkten.
		//   R := RAC + RCA + RBC + RCB;
		ComparableSet<TripelPair> Rset = RAC.union(RCA).union(RBC).union(RCB);
		PointRelations R = new PointRelations(); // RAC.union(RCA).union(RBC).union(RCB);

		for(TripelPair pair:Rset)
		{
			R.add(new Pair<ListTripel, ListTripel>(pair.mFirst, pair.mSecond));
		}

		// -- Am Anfang sind die Waggons auf Gleis A, die Lokomotive ist auf Gleis B.
		// start := [ [1,2,3], [0], [] ];
		ComparableList<Integer> emptyList = new ComparableList<Integer>();
		ComparableList<Integer> startA = new ComparableList<Integer>();
		startA.add(1);
		startA.add(2);
		startA.add(3);
		ComparableList<Integer> startB = new ComparableList<Integer>();
		startB.add(0);
		ListTripel start = new ListTripel(startA, startB, emptyList);

		// -- Am Ende sollen die Waggons alle in der Reihenfolge 3,1,2 auf Gleis C
	    // -- stehen und die Lokomotive soll auf Gleis B stehen.
	    // goal := [ [], [0], [3,1,2] ];
		ComparableList<Integer> endB = new ComparableList<Integer>();
		endB.add(3);
		endB.add(1);
		endB.add(2);
		ListTripel goal = new ListTripel(emptyList, startB, endB);

		// -- Berechne alle m�glichen Pfade.
	    // path  := reachable(start, goal, R);
		System.out.println("Calculating Path");
		System.out.println("Start:" + start);
		System.out.println("Goal:"+goal);

		Relation<ListTripel> relation = new Relation<ListTripel>(R);
		System.out.println("R: "+R.size()+"\n"+R);
		System.out.println("\n\nCalculating Path...\n(This will take a while)");

		ComparableList<ListTripel> path = relation.findPath(start, goal);

		System.out.println("Result: "+path.size());
		System.out.println(path);


		RangierFrame f = new RangierFrame(path);

	}

	public static ComparableSet<TripelPair> inverse(ComparableSet<TripelPair> list)
	{
		ComparableSet<TripelPair> result = new ComparableSet<TripelPair>();
		for(TripelPair tripel: list)
		{
			result.add(new TripelPair(tripel.mSecond, tripel.mFirst));
		}
		return result;
	}

}

class PointRelations extends ComparableSet<Pair<ListTripel, ListTripel>>
{
	PointRelations() {
		super();
	}
}

class TripelPair extends Pair<ListTripel, ListTripel> {
	TripelPair(ListTripel a, ListTripel b) {
		super(a, b);
	}
}

class ListTripel
		extends
		Tripel<ComparableList<Integer>, ComparableList<Integer>, ComparableList<Integer>> {
	ListTripel(ComparableList<Integer> a, ComparableList<Integer> b,
			ComparableList<Integer> c) {
		super(a, b, c);
	}
}

class SetTripel
		extends
		Tripel<ComparableSet<Integer>, ComparableSet<Integer>, ComparableSet<Integer>> {
	SetTripel(ComparableSet<Integer> a, ComparableSet<Integer> b,
			ComparableSet<Integer> c) {
		super(a, b, c);
	}
}


class PartitionSet extends ComparableSet<SetTripel> {
	PartitionSet() {
		super();
	}
}

class PartitionList extends ComparableSet<ListTripel> {
	PartitionList() {
		super();
	}
}