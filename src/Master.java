import java.util.ArrayList;

public class Master {

	public static void main(String[] args) {
		String dataset = "banknote";
		FileHandler reader = new FileHandler();
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		ArrayList<Record> records = new ArrayList<Record>();
		switch (dataset) {
		case "voting":
			attributes = reader.readAttributes("voting_dataset_attributes.data");
			records = reader.readData("voting_dataset_data.data");
			break;
		case "iris":
			attributes = reader.readAttributes("iris_dataset_attributes.data");
			records = reader.readData("iris_dataset_data.data");
			break;
		case "tictactoe":
			attributes = reader.readAttributes("tictactoe_dataset_attributes.data");
			records = reader.readData("tictactoe_dataset_data.data");
			break;
		case "banknote":
			attributes = reader.readAttributes("banknote_authentication_dataset_attributes.data");
			records = reader.readData("banknote_authentication_dataset_data.data");
			break;
		case "credit":
			attributes = reader.readAttributes("credit_approval_dataset_attributes.data");
			records = reader.readData("credit_approval_dataset_data.data");
			break;
		}
		int split = records.size() / 10;
		for (int i = 0; i < 10; i++) {
			ArrayList<Record> trainingList = new ArrayList<Record>();
			ArrayList<Record> testingList = new ArrayList<Record>();
			if ((i != 0) && (i != 9)) {
				for (int j = 0; j < i * split; j++) {
					trainingList.add(records.get(j));
				}
				for (int j = (i + 1) * split; j < records.size(); j++) {
					trainingList.add(records.get(j));
				}
			} else if (i == 0) {
				for (int j = (i + 1) * split; j < records.size(); j++) {
					trainingList.add(records.get(j));
				}
			} else {
				for (int j = 0; j < i * split; j++) {
					trainingList.add(records.get(j));
				}
				if ((i + 1) * split < records.size()) {
					for (int j = (i + 1) * split; j < records.size(); j++) {
						trainingList.add(records.get(j));
					}
				}
			}
			for (int j = i * split; j < (i + 1) * split; j++) {
				testingList.add(records.get(j));
			}

			IBL knn = new IBL();
			int correct = 0;
			for (Record instance : testingList) {
				String decison = knn.kNearestNeighbor(trainingList, attributes, reader.targetAttributeIndex, instance,
						5);
				if (decison.equals(instance.getValue(reader.targetAttributeIndex))) {
					correct++;
				}
			}
			System.out.println("Correct " + correct);
			System.out.println("Total " + testingList.size());

		}
	}
}
