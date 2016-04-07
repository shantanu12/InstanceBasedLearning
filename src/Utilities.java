import java.util.ArrayList;

public class Utilities {
	public static int[] targetCounter(int targetDegree, ArrayList<Record> records, int targetAttributeIndex,
			ArrayList<Attribute> attributes) {
		int[] countKeeper = new int[targetDegree];
		for (int t = 0; t < countKeeper.length; t++) {
			countKeeper[t] = 0;
		}
		for (Record rec : records) {
			for (int i = 0; i < targetDegree; i++) {
				if (rec.getValue(targetAttributeIndex).equals(attributes.get(targetAttributeIndex).getValue(i))) {
					countKeeper[i]++;
				}
			}
		}
		return countKeeper;
	}
}
