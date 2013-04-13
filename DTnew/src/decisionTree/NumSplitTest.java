package decisionTree;

import weka.core.Instance;
import weka.core.Instances;


public class NumSplitTest extends Split {
	double oldEntropy;
	
	public NumSplitTest(int attIndex, int minObj, double oldEntropy) {
		super(attIndex, minObj);
		this.oldEntropy = oldEntropy;
	}
	
	public void buildSplit(Instances data, double splitPoint, double[] classification) {
		this.splitPoint = splitPoint;
		splitedbranches = new double[2];		
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);
			if (in.value(attIndex) < splitPoint) {
				splitedbranches[0]++;
			} else {
				splitedbranches[1]++;
			}
		}		
		calculateInfoGain(data, classification);
	}
	
	public void calculateInfoGain(Instances data, double[] distribution) {
		double newEntropy = getNewEntropy(data);
		
		this.infoGain = oldEntropy- newEntropy;
	}
	
	public double getNewEntropy(Instances data) {
		double r = 0;
		double numClass = data.classAttribute().numValues();

		double[][] distribution = new double[splitedbranches.length][(int) numClass];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);			
			
			if (in.value(attIndex) < splitPoint) {
				distribution[0][(int) in.classValue()]++;
			} else {
				distribution[1][(int) in.classValue()]++;
			}
		}
		
		for (int i = 0; i < splitedbranches.length; i++) {
			for (int j = 0; j < numClass; j++) {
				r += getLog(distribution[i][j]);
			}
			
			r -= getLog(splitedbranches[i]);
		}
		
		return -r / data.numInstances();
	}
}
