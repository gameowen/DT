package decisionTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class test {

	public static void main(String[] args) {
		DataSource source = null;
		Instances data = null;
		try {
			source = new DataSource("trainProdSelection.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
			
			DecisionTree dt = new DecisionTree(2);
			dt.buildClassifier(data);
			dt.printTree(data);
			CrossValidation cv = new CrossValidation(data, 5);
			System.out.println("Final: " + cv.doCrossValidation(data, dt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
