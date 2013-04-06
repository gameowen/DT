import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class Test {
	
	public static void main(String[] args) {		
		DataSource source = null;
		Instances data = null;
		try {
			source = new DataSource("trainProdSelection.arff");
			data = source.getDataSet();
			if (data.classIndex() == -1) data.setClassIndex(data.numAttributes() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DecisionTree dt = new DecisionTree(3);
		dt.buildClassfier(data);
	}
}
