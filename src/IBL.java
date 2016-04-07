import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class IBL {
	public String kNearestNeighbor(ArrayList<Record> trainingList, ArrayList<Attribute> attributes, int targetAttribute,
			Record instance, int k) {
		String decision = "";
		Queue<Record> ordering = new PriorityQueue<>(10, distComparator);
		for (Record rec : trainingList) {
			Double dist = calcDistance(rec, instance, attributes, targetAttribute);
			rec.setDistance(dist);
			ordering.add(rec);
		}
		ArrayList<Record> kNearestNeighbors = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			kNearestNeighbors.add(ordering.poll());
		}
		int targetDegree = attributes.get(targetAttribute).getDegree();
		int[] countKeeper = Utilities.targetCounter(targetDegree, kNearestNeighbors, targetAttribute, attributes);
		int maxIndex = -1;
		int maxCount = -1;
		for (int t = 0; t < countKeeper.length; t++) {
			if (countKeeper[t] > maxCount) {
				maxCount = countKeeper[t];
				maxIndex = t;
			}
		}
		decision = attributes.get(targetAttribute).getValue(maxIndex);
		return decision;
	}

	public Double calcDistance(Record r, Record instance, ArrayList<Attribute> attributes, int targetAttribute) {
		Double dist = 0.0;
		Double sumOfSquares = 0.0;
		for (int i = 0; i < attributes.size(); i++) {
			if (i != targetAttribute) {
				if (attributes.get(i).isContinuous()) {
					Double diff = Double.parseDouble(r.getValue(i)) - Double.parseDouble(instance.getValue(i));
					sumOfSquares += Math.pow(diff, 2);
				} else {
					if (r.getValue(i).equals(instance.getValue(i))) {
						sumOfSquares += 0.0;
					} else {
						sumOfSquares += 1.0;
					}
				}
			}
		}
		dist = Math.sqrt(sumOfSquares);
		return dist;
	}

	public static Comparator<Record> distComparator = new Comparator<Record>() {
		public int compare(Record r1, Record r2) {
			int i = 0;
			if (r1.getDistance() < r2.getDistance()) {
				i = -1;
			} else if (r1.getDistance() == r2.getDistance()) {
				i = 0;
			} else {
				i = 1;
			}
			return i;
		}
	};
}
