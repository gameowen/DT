package decisionTree;

/*
 * Team13 Prodigy
 * This is a simple test class that builds the decision tree, print the tree and the accuracy,
 * and print out the predicted results of the given test set.
 */

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Test {

	public static void main(String[] args) {
		DataSource source = null;
		Instances data = null;
		try {
			System.out.println("Part a: product selection");
			source = new DataSource("trainProdSelection.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
			
			DecisionTree dt = new DecisionTree(2);
			dt.buildClassifier(data);
			System.out.println("Tree model:");
			dt.printTree(data);
			CrossValidation cv = new CrossValidation(data, 5);
			System.out.println("Final accuracy: " + cv.doCrossValidation(data, dt));
			
			source = new DataSource("testProdSelection.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
			for (int i = 0; i < data.numInstances(); i++) {
				System.out.println("Test Instance " + i + ": " + dt.classifyInstance(data.instance(i)));			
			}
			
		    System.out.println("--------------------------------------");
			System.out.println("Part b: product indroduction (binary)");
			source = new DataSource("trainProdIntro.binary.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
			
			dt = new DecisionTree(2);
			dt.buildClassifier(data);
			System.out.println("Tree model:");
			dt.printTree(data);
			cv = new CrossValidation(data, 5);
			System.out.println("Final accuracy: " + cv.doCrossValidation(data, dt));
			
			source = new DataSource("testProdIntro.binary.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
			for (int i = 0; i < data.numInstances(); i++) {
				System.out.println("Test Instance " + i + ": " + dt.classifyInstance(data.instance(i)));			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
