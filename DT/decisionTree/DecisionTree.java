package decisionTree;

import java.util.Enumeration;

import weka.core.Instance;
import weka.core.Instances;


public class DecisionTree {
	private TreeNode root;
	private int minObj;

	public DecisionTree(int minObj) {
		this.minObj = minObj;
	}
	
	public void buildClassfier(Instances data) {
		if (data.numInstances() < 2 * minObj) {
			throw new RuntimeException("Too few instances!");
		}
		
		@SuppressWarnings("unchecked")
		Enumeration<Instance> instanceEnum = data.enumerateInstances();
		
		while (instanceEnum.hasMoreElements()) {
			Instance i = (Instance) instanceEnum.nextElement();
			if (i.hasMissingValue()) throw new RuntimeException("Do not support missing values.");
		}
		
		root = new TreeNode(minObj);
		root.buildTree(data);
	}
	

}
