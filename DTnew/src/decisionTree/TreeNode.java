package decisionTree;

import weka.core.Instance;
import weka.core.Instances;

public class TreeNode {
	boolean isLeaf = false;
	TreeNode[] children;
	Split model;
	int minObj;
	double[] classification;
	int leafClassIndex;

	public TreeNode(int minObj) {
		this.minObj = minObj;
	}

	public void buildTree(Instances data) {
		storeDistribution(data);
		//System.out.println(Arrays.toString(distribution));

		if (data.size() <= 2 * minObj || sameClass(data.numInstances())) {
			this.isLeaf = true;
			leafClassIndex = getMajClass();
			return;
		}

		//System.out.println(model);

		if (!getBestModel(data)) return;
		
		Instances[] splitedInstances = model.split(data);

		children = new TreeNode[splitedInstances.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = new TreeNode(this.minObj);
			children[i].buildTree(splitedInstances[i]);
		}
	}

	public int classifyInstance(Instance i) {
		if (this.isLeaf) return this.leafClassIndex;
		TreeNode child = children[this.model.classify(i)];
		return child.classifyInstance(i);
	}

	private int getMajClass() {
		int max = 0;

		for (int i = 0; i < classification.length; i++) {
			if (classification[i] > classification[max])
				max = i;
		}

		return max;
	}

	private boolean getBestModel(Instances data) {
		//double bestGainRatio = 0; use info gain first for tesing
		double bestInfoGain = Double.MIN_VALUE;
		Split bestModel = null;
		Split[] models = new Split[data.numAttributes()];
		
		for (int i = 0; i < data.numAttributes(); i++) {
			if (i != data.classIndex()) {
				models[i] = new Split(i, minObj);
				models[i].buildSplitModel(data, this.classification);
//				double infoG = models[i].getInfoGain();
//				System.out.println(data.attribute(i).name() + " infoG: " + infoG);
//				System.out.println("-------------------------------------------");
				if (models[i].isValid() && models[i].getInfoGain() > bestInfoGain) {
					bestInfoGain = models[i].getInfoGain();
					bestModel = models[i];
				}
			}
		}
		
		if (bestModel == null) {
			this.isLeaf = true;
			leafClassIndex = getMajClass();
			return false;
		}

		this.model = bestModel;
		return true;
		//System.out.println(data.attribute(model.attIndex));
	}

	private boolean sameClass(int totalNumInstances) {
		for (int i = 0; i < classification.length; i++) {
			if (classification[i] == totalNumInstances)
				return true;
		}

		return false;
	}

	private void storeDistribution(Instances data) {

		this.classification = new double[data.classAttribute().numValues()];
		//System.out.println("number of class: " + data.classAttribute().numValues());

		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);
			classification[(int) in.classValue()]++;
		}
	}
}
