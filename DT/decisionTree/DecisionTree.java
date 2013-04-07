package decisionTree;

import java.util.Enumeration;

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
		classAttr = data.classAttribute();
	}
	
	public void printTree(Instances data) {
		_printTree(data, root, 0);
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
		} else if (a.isNumeric()) {
			System.out.println(a.name() + " (Numeric):");
		}
		

		for (int i = 0; i < a.numValues(); i++) {
			for (int j = 0; j < level + 1; j++) {
				System.out.print("	");
			}
			System.out.println(a.value(i) + ": ");
			_printTree(data, root.children[i], level + 2);
		}
	}
}

