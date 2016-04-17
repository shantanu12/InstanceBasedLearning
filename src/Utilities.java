import java.util.ArrayList;

public class Utilities {
	public static Double[] targetCounter(int targetDegree, ArrayList<Record> records, int targetAttributeIndex,
			ArrayList<Attribute> attributes) {
		Double[] countKeeper = new Double[targetDegree];
		for (int t = 0; t < countKeeper.length; t++) {
			countKeeper[t] = 0.0;
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
