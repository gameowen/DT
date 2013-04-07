import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class test {

	public static void main(String[] args) {
		DataSource source = null;
		Instances data = null;
		try {
			source = new DataSource("whether.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1)
				data.setClassIndex(data.numAttributes() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
		
		DecisionTree dt = new DecisionTree(0);
=======

		DecisionTree dt = new DecisionTree(3);
>>>>>>> 3540ce4b2e37a7173f66b64b93cff491b42f30d4
		dt.buildClassfier(data);
	}
}
