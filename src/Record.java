import java.util.ArrayList;

public class Record {
	private ArrayList<String> values;
	private Double distance;

	public Record(ArrayList<String> val) {
		this.values = new ArrayList<String>();
		for (String v : val) {
			this.values.add(v);
		}
	}

	String getValue(int i) {
		return values.get(i);
	}

	void setValue(int i, String s) {
		values.set(i, s);
	}

	Double getDistance() {
		return this.distance;
	}

	void setDistance(Double d) {
		this.distance = d;
	}

	String printRecord() {
		String line = "";
		for (int i = 0; i < values.size(); i++) {
			line += values.get(i) + " ";
		}
		return line;
	}
}
