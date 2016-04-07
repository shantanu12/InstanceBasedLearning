import java.util.ArrayList;

public class Attribute {
	private String attrName;
	private ArrayList<String> values;
	private boolean attrUsed = false;
	private boolean isContinuous;
	private double max;
	private double min;

	public Attribute(String name, ArrayList<String> values, boolean isContinuous) {
		this.attrName = name;
		this.values = new ArrayList<String>();
		for (String v : values) {
			this.values.add(v);
		}
		this.isContinuous = isContinuous;
		this.max = Double.MIN_VALUE;
		this.min = Double.MAX_VALUE;
	}

	public Attribute(Attribute copy) {
		this.attrName = copy.attrName;
		this.values = new ArrayList<String>();
		for (String v : copy.values) {
			this.values.add(v);
		}
		this.isContinuous = copy.isContinuous;
		this.attrUsed = copy.attrUsed;
		this.max = copy.max;
		this.min = copy.min;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public String getValue(int i) {
		return this.values.get(i);
	}

	public int getDegree() {
		return this.values.size();
	}

	public void setAttrUsed() {
		this.attrUsed = true;
	}

	public boolean isUsed() {
		return this.attrUsed;
	}

	public boolean isContinuous() {
		return this.isContinuous;
	}

	public void setMax(Double d) {
		this.max = d;
	}

	public double getMax() {
		return this.max;
	}

	public void setMin(Double d) {
		this.min = d;
	}

	public double getMin() {
		return this.min;
	}
}
