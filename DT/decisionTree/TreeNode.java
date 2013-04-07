package decisionTree;
import java.util.Arrays;
import java.util.Enumeration;

import weka.core.Instance;
import weka.core.Instances;

public class TreeNode {
	boolean isLeaf = false;
	TreeNode[] children;
	SplitModel model;
	int minObj;
	double[] distribution;
	int leafClassIndex;

	public TreeNode(int minObj) {
		this.minObj = minObj;
	}

	public void buildTree(Instances data) {
		storeDistribution(data);
		System.out.println(Arrays.toString(distribution));

		if (data.size() < 2 * minObj || sameClass(data.numInstances())) {
			this.isLeaf = true;
			leafClassIndex = getMajClass();
			return;
		}

		getBestModel(data);
		//System.out.println(model);


		Instances[] splitedInstances = model.split(data);

		children = new TreeNode[splitedInstances.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = new TreeNode(this.minObj);
			children[i].buildTree(splitedInstances[i]);
		}
	}

	public int classifyInstance(Instance i) {
		return 0;
	}

	private int getMajClass() {
		int max = 0;

		for (int i = 0; i < distribution.length; i++) {
			if (distribution[i] > distribution[max])
				max = i;
		}

		return max;
	}

	private void getBestModel(Instances data) {
		//double bestGainRatio = 0; use info gain first for tesing
		double bestInfoGain = Double.MIN_VALUE;
		SplitModel bestModel = null;
		SplitModel[] models = new SplitModel[data.numAttributes()];
		// double comparison
//		for (int i = 0; i < data.numAttributes(); i++) {
//			if (i != data.classIndex()) {
//				models[i] = new SplitModel(i, minObj);
//				models[i].buildSplitModel(data, this.distribution);
//				if (models[i].getGainRatio() > bestGainRatio) {
//					bestGainRatio = models[i].getGainRatio();
//					bestModel = models[i];
//				}
//			}
//		}
		
		for (int i = 0; i < data.numAttributes(); i++) {
			if (i != data.classIndex()) {
				models[i] = new SplitModel(i, minObj);
				models[i].buildSplitModel(data, this.distribution);
				double infoG = models[i].getInfoGain();
				System.out.println("infoG: " + infoG);
				if (models[i].getInfoGain() > bestInfoGain) {
					bestInfoGain = models[i].getInfoGain();
					bestModel = models[i];
				}
			}
		}

		this.model = bestModel;
		System.out.println(data.attribute(model.attIndex));
	}

	private boolean sameClass(int totalNumInstances) {
		for (int i = 0; i < distribution.length; i++) {
			if (distribution[i] == totalNumInstances)
				return true;
		}

		return false;
	}

	private void storeDistribution(Instances data) {

		this.distribution = new double[data.classAttribute().numValues()];
		System.out.println("number of class: " + data.classAttribute().numValues());


		@SuppressWarnings("unchecked")
		Enumeration<Instance> instances = data.enumerateInstances();

		while (instances.hasMoreElements()) {
			Instance i = (Instance) instances.nextElement();
			distribution[(int) i.classValue()]++;
		}
	}
}
