package decisionTree;

/*
 * Team13 Prodigy
 * DecisionTree class is a simple classifier that implements decision tree algorithm
 */

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;


public class DecisionTree {
	private TreeNode root;
	private int minObj;
	private Attribute classAttr;

	public DecisionTree(int minObj) {
		this.minObj = minObj;
	}
	
	// Build the classifier based on a given set of training set
	public void buildClassifier(Instances data) {
		if (data.numInstances() < 2 * minObj) {
			throw new RuntimeException("Too few instances!");
		}
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);
			if (in.hasMissingValue()) throw new RuntimeException("Do not support missing values.");
		}	
		
		root = new TreeNode(minObj);
		root.buildTree(data);
		classAttr = data.classAttribute();
	}
	
	public void printTree(Instances data) {
		_printTree(data, root, 0);
	}
	
	// Return the index of the class value given a test instance 
	public double classifyInstance(Instance instance) {
		return root.classifyInstance(instance);				
	}
	
	private void _printTree(Instances data, TreeNode root, int level) {
		if (root == null) return;
		
		for (int i = 0; i < level; i++) {
			System.out.print("	");
		}
		
		if (root.isLeaf) {			
			System.out.println("=> class: " + classAttr.value(root.leafClassIndex));
			return;
		}
		
		Attribute a = data.attribute(root.model.attIndex);
		if (a.isNominal()) {
			System.out.println(a.name() + " (Nominal):");
			for (int i = 0; i < a.numValues(); i++) {
				for (int j = 0; j < level + 1; j++) {
					System.out.print("	");
				}
				System.out.println(a.value(i) + ": ");
				_printTree(data, root.children[i], level + 2);
			}
		} else if (a.isNumeric()) {
			System.out.println(a.name() + " (Numeric):");
			for (int j = 0; j < level + 1; j++) {
				System.out.print("	");
			}
			System.out.println("<" + root.model.splitPoint + ": ");
			_printTree(data, root.children[0], level + 2);
			for (int j = 0; j < level + 1; j++) {
				System.out.print("	");
			}
			System.out.println(">" + root.model.splitPoint + ": ");
			_printTree(data, root.children[1], level + 2);			
		}
		

	}
}

