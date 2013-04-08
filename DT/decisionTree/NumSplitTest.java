package decisionTree;

import weka.core.Instances;


public class NumSplitTest extends SplitModel {
	
	public NumSplitTest(int attIndex, int minObj) {
		super(attIndex, minObj);
	}
	
	public void buildSplit(Instances data, double splitPoint) {
		this.splitPoint = splitPoint;
		splitedDistribution = new double[2];		
		
	}
	
}
