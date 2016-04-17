import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class IBL {
	public ArrayList<Record> kNearestNeighbor(ArrayList<Record> trainingList, ArrayList<Attribute> attributes,
			int targetAttribute, Record instance, int k) {
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
		return kNearestNeighbors;
	}

	public Double calcDistance(Record r, Record instance, ArrayList<Attribute> attributes, int targetAttribute) {
		Double dist = 0.0;
		Double sumOfSquares = 0.0;
		for (int i = 0; i < attributes.size(); i++) {
			if (i != targetAttribute && attributes.get(i).isRelevant()) {
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

	public String getDecision(ArrayList<Attribute> attributes, int targetAttribute,
			ArrayList<Record> kNearestNeighbors) {
		String decision = "";
		int targetDegree = attributes.get(targetAttribute).getDegree();
		Double[] countKeeper = Utilities.targetCounter(targetDegree, kNearestNeighbors, targetAttribute, attributes);
		int maxIndex = -1;
		Double maxCount = -1.0;
		for (int t = 0; t < countKeeper.length; t++) {
			if (countKeeper[t] > maxCount) {
				maxCount = countKeeper[t];
				maxIndex = t;
			}
		}
		decision = attributes.get(targetAttribute).getValue(maxIndex);
		return decision;
	}

	public void relief(ArrayList<Record> trainingList, ArrayList<Attribute> attributes, int targetAttribute,
			double tau) {
		double weights[] = new double[attributes.size()];
		for (Record rec : trainingList) {
			ArrayList<Record> nearHit = new ArrayList<>();
			ArrayList<Record> nearMiss = new ArrayList<>();
			String decision = rec.getValue(targetAttribute);
			ArrayList<Record> positiveInstances = new ArrayList<>();
			ArrayList<Record> negativeInstances = new ArrayList<>();
			for (Record data : trainingList) {
				if (!rec.equals(data)) {
					if (data.getValue(targetAttribute).equals(decision)) {
						positiveInstances.add(data);
					} else {
						negativeInstances.add(data);
					}
				}
			}
			nearHit = kNearestNeighbor(positiveInstances, attributes, targetAttribute, rec, 1);
			nearMiss = kNearestNeighbor(negativeInstances, attributes, targetAttribute, rec, 1);
			weights = updateWeight(weights, rec, nearHit.get(0), nearMiss.get(0), attributes, targetAttribute);
		}
		for (int i = 0; i < weights.length; i++) {
			weights[i] /= trainingList.size();
		}
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] < tau) {
				attributes.get(i).setAttrRelevance(false);
			}
		}
	}

	public double[] updateWeight(double[] weights, Record x, Record nearHit, Record nearMiss,
			ArrayList<Attribute> attributes, int targetAttribute) {
		for (int i = 0; i < attributes.size(); i++) {
			if (i != targetAttribute) {
				double nearHitError = 0.0;
				double nearMissError = 0.0;
				if (!attributes.get(i).isContinuous()) {
					String value = x.getValue(i);
					if (nearHit.getValue(i).equals(value)) {
						nearHitError = 0;
					} else {
						nearHitError = 1;
					}
					if (nearMiss.getValue(i).equals(value)) {
						nearMissError = 0;
					} else {
						nearMissError = 1;
					}
				} else {
					double value = Double.parseDouble(x.getValue(i));
					nearHitError = Math.pow((value - Double.parseDouble(nearHit.getValue(i))), 2);
					nearMissError = Math.pow((value - Double.parseDouble(nearMiss.getValue(i))), 2);
				}
				weights[i] = weights[i] - nearHitError + nearMissError;
			}
		}
		return weights;
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
