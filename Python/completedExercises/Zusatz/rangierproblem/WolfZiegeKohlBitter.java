

public class WolfZiegeKohlBitter
{
    public static void main(String args[]) {
        ComparableSet<String> all = new ComparableSet<String>();
        all.add("Bauer");
        all.add("Wolf");
        all.add("Ziege");
        all.add("Kohl");
        ComparableSet<ComparableSet<String>> powerAll = all.powerSet();
        ComparableSet<Pair<ComparableSet<String>, ComparableSet<String>>> p = new ComparableSet<Pair<ComparableSet<String>, ComparableSet<String>>>();
        for (ComparableSet<String> s1: powerAll) {
            for (ComparableSet<String> s2: powerAll) {
                ComparableSet<String> u = s1.union(s2);
                ComparableSet<String> d = s1.intersection(s2);
                if (u.compareTo(all) == 0 && d.isEmpty()) {
                    p.add(new Pair<ComparableSet<String>, ComparableSet<String>>(s1, s2));
                }
            }
        }
        System.out.println(p);
		ComparableSet<Pair<Pair<ComparableSet<String>, ComparableSet<String>>, Pair<ComparableSet<String>, ComparableSet<String>>>> r = new ComparableSet<Pair<Pair<ComparableSet<String>, ComparableSet<String>>, Pair<ComparableSet<String>, ComparableSet<String>>>>();
		for (Pair<ComparableSet<String>, ComparableSet<String>> s1s2: p) {
			ComparableSet<String> s1 = s1s2.getFirst();
			ComparableSet<String> s2 = s1s2.getSecond();
			for (ComparableSet<String> b : s1.powerSet()) {
				if (b.member("Bauer") && b.size() <= 2 && !problem(s1.difference(b))) {
					Pair<ComparableSet<String>, ComparableSet<String>> s1bs2b = new Pair<ComparableSet<String>, ComparableSet<String>>(s1.difference(b), s2.union(b));
					r.add(new Pair<Pair<ComparableSet<String>, ComparableSet<String>>, Pair<ComparableSet<String>, ComparableSet<String>>>(s1s2, s1bs2b));
				}
			}
		}
		for (Pair<ComparableSet<String>, ComparableSet<String>> s1s2: p) {
			ComparableSet<String> s1 = s1s2.getFirst();
			ComparableSet<String> s2 = s1s2.getSecond();
			for (ComparableSet<String> b : s2.powerSet()) {
				if (b.member("Bauer") && b.size() <= 2 && !problem(s2.difference(b))) {
					Pair<ComparableSet<String>, ComparableSet<String>> s1bs2b = new Pair<ComparableSet<String>, ComparableSet<String>>(s1.union(b), s2.difference(b));
					r.add(new Pair<Pair<ComparableSet<String>, ComparableSet<String>>, Pair<ComparableSet<String>, ComparableSet<String>>>(s1s2, s1bs2b));
				}
			}
		}
		for (Pair<Pair<ComparableSet<String>, ComparableSet<String>>, Pair<ComparableSet<String>, ComparableSet<String>>> xy : r) {
			System.out.println(xy);
		}
		ComparableSet<String> emptySet = new ComparableSet<String>();
		Pair<ComparableSet<String>, ComparableSet<String>> start = new Pair<ComparableSet<String>, ComparableSet<String>>(all, emptySet);
		Pair<ComparableSet<String>, ComparableSet<String>> goal  = new Pair<ComparableSet<String>, ComparableSet<String>>(emptySet, all);
		Relation<Pair<ComparableSet<String>, ComparableSet<String>>> relation = new Relation<Pair<ComparableSet<String>, ComparableSet<String>>>(r);
		ComparableList<Pair<ComparableSet<String>, ComparableSet<String>>> path = relation.findPath(start, goal);
		System.out.println("\n\nLösung:\n");
		for (Pair<ComparableSet<String>, ComparableSet<String>> pair : path) {
			System.out.println(pair);
		}
    }

	static boolean problem(ComparableSet<String> s) {
		return (s.member("Ziege") && s.member("Kohl")) ||
               (s.member("Wolf") && s.member("Ziege"));
	}
}



