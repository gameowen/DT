package decisionTree;

import weka.core.Instance;
import weka.core.Instances;


public class NumSplitTest extends SplitModel {
	double oldEntropy;
	
	public NumSplitTest(int attIndex, int minObj, double oldEntropy) {
		super(attIndex, minObj);
		this.oldEntropy = oldEntropy;
	}
	
	public void buildSplit(Instances data, double splitPoint, double[] distribution) {
		this.splitPoint = splitPoint;
		splitedDistribution = new double[2];		
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);
			if (in.value(attIndex) < splitPoint) {
				splitedDistribution[0]++;
			} else {
				splitedDistribution[1]++;
			}
		}		
		calculateInfoGain(data, distribution);
	}
	
	public void calculateInfoGain(Instances data, double[] distribution) {
		double newEntropy = getNewEntropy(data);
		
		this.infoGain = oldEntropy- newEntropy;
	}
	
	public double getNewEntropy(Instances data) {
		double r = 0;
		double numClass = data.classAttribute().numValues();

		double[][] distribution = new double[splitedDistribution.length][(int) numClass];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);			
			
			if (in.value(attIndex) < splitPoint) {
				distribution[0][(int) in.classValue()]++;
			} else {
				distribution[1][(int) in.classValue()]++;
			}
		}
		
		for (int i = 0; i < splitedDistribution.length; i++) {
			for (int j = 0; j < numClass; j++) {
				r += getLog(distribution[i][j]);
			}
			
			r -= getLog(splitedDistribution[i]);
		}
		
		return -r / data.numInstances();
	}
}