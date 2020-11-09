

public class WolfZiegeKohl
{
    public static void main(String args[]) {
		// all := { "Bauer", "Wolf", "Ziege", "Kohl" };
        ComparableSet<String> all = new ComparableSet<String>();
        all.add("Bauer");
        all.add("Wolf");
        all.add("Ziege");
        all.add("Kohl");
        ComparableSet<ComparableSet<String>> powerAll = all.powerSet();
		// p := { [ s1, s2 ] : s1 in pow all, s2 in pow all
        //        | s1 + s2 = all and s1 * s2 = {} };
        PointSet p = new PointSet();
        for (ComparableSet<String> s1: powerAll) {
            for (ComparableSet<String> s2: powerAll) {
                ComparableSet<String> u = s1.union(s2);
                ComparableSet<String> d = s1.intersection(s2);
                if (u.compareTo(all) == 0 && d.isEmpty()) {
                    p.add(new SetPair(s1, s2));
                }
            }
        }
        System.out.println(p);
		PointRelation r = new PointRelation();
		// R1 := { [ [s1, s2], [s1 - b, s2 + b] ] : [s1, s2] in p, b in pow s1 |
		//           "Bauer" in b and #b <= 2 and not problem(s1 - b) };
		for (SetPair s1s2: p) {
			ComparableSet<String> s1 = s1s2.getFirst();
			ComparableSet<String> s2 = s1s2.getSecond();
			for (ComparableSet<String> b : s1.powerSet()) {
				if ( b.member("Bauer")          &&
                     b.size() <= 2              &&
                     !problem(s1.difference(b))
                    )
                {
					SetPair s1bs2b = new SetPair(s1.difference(b), s2.union(b));
					r.add(new Pair<SetPair, SetPair>(s1s2, s1bs2b));
				}
			}
		}
		// R2 := { [ [s1, s2], [s1 + b, s2 - b] ] : [s1, s2] in p, b in pow s2 |
		//           "Bauer" in b and #b <= 2 and not problem(s2 - b) };
		for (SetPair s1s2: p) {
			ComparableSet<String> s1 = s1s2.getFirst();
			ComparableSet<String> s2 = s1s2.getSecond();
			for (ComparableSet<String> b : s2.powerSet()) {
				if ( b.member("Bauer")          &&
					 b.size() <= 2              &&
                     !problem(s2.difference(b))
                   )
                {
					SetPair s1bs2b = new SetPair(s1.union(b), s2.difference(b));
					r.add(new Pair<SetPair, SetPair>(s1s2, s1bs2b));
				}
			}
		}
		for (Pair<SetPair, SetPair> xy : r) {
			System.out.println(xy);
		}
		ComparableSet<String> emptySet = new ComparableSet<String>();
		SetPair start = new SetPair(all, emptySet);
		SetPair goal  = new SetPair(emptySet, all);
		Relation<SetPair> relation = new Relation<SetPair>(r);
		ComparableList<SetPair> path = relation.findPath(start, goal);
		System.out.println("\n\nLösung:\n");
		for (SetPair pair : path) {
			System.out.println(pair);
		}
    }

	static boolean problem(ComparableSet<String> s) {
		return (s.member("Ziege") && s.member("Kohl")) ||
               (s.member("Wolf") && s.member("Ziege"));
	}
}

// Alias-Klassen

class SetPair extends Pair<ComparableSet<String>, ComparableSet<String>>
{
	SetPair(ComparableSet<String> a, ComparableSet<String> b) {
		super(a, b);
	}
}

class PointSet extends ComparableSet<SetPair>
{
    PointSet() {
        super();
    }
}

class PointRelation extends ComparableSet<Pair<SetPair, SetPair>>
{
	PointRelation() {
		super();
	}
}


