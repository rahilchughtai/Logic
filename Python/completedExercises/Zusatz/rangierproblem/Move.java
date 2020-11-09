


public class Move {
	public static int EAST = 0;
	public static int WEST = 1;

	public static int B_C = 2;
	public static int C_B = 3;
	public static int A_C = 4;
	public static int C_A = 5;

	ComparableList<Integer> waggons;
	int fromTo;
	int westEast;

	public Move(int fromTo,int westEast, ComparableList<Integer> waggons)
	{
		this.fromTo = fromTo;
		this.westEast = westEast;
		this.waggons = waggons;
	}
}
