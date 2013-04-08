import java.util.Enumeration;

import weka.classifiers.trees.j48.Distribution;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.core.Utils;

public class NumericHandler {
	private int attIndex;
	private int minObj;
	private int subsets;
	private double splitPoint;
	private double infoGain;
	private double gainRatio;
	private double sumOfWeights;
	private getSplitByInfoGain split_ig = new getSplitByInfoGain();
	private getSplitByGainRatio split_gr = new getSplitByGainRatio();

	public void buildClassifier(Instances instances) throws Exception {
		subsets = 0;
		splitPoint = Double.MAX_VALUE;
		infoGain = 0;
		gainRatio = 0;
		if (instances.attribute(attIndex).isNumeric()) {
			instances.sort(instances.attribute(attIndex));
			splitNumericAttribute(instances);
		} else {
			// Nominal method
		}

	}

	private void splitNumericAttribute(Instances instances) throws Exception {
		int firstMiss;
		int next = 1;
		int last = 0;
		int index = 0;
		int splitIndex = -1;
		double newInfoGain;
		double defaultEnt;
		double minSplit;
		Instance instance = null;
		int i;

		Distribution distribution = new Distribution(2, instances.numClasses());
		Enumeration enu = instances.enumerateInstances();
		i = 0;
		while (enu.hasMoreElements()) {
			instance = (Instance) enu.nextElement();
			if (instance.isMissing(attIndex))
				break;
			distribution.add(1, instance);
			i++;
		}
		firstMiss = i;

		minSplit = 0.1 * (distribution.total())
				/ ((double) instance.numClasses());
		if (Utils.smOrEq(minSplit, minObj))
			minSplit = minObj;
		else if (Utils.gr(minSplit, 25))
			minSplit = 25;
		if (Utils.sm((double) firstMiss, 2 * minSplit))
			return;
		defaultEnt = split_ig.oldEnt(distribution);
		while (next < firstMiss) {
			if (instances.instance(next).value(attIndex) + 1e-5 < instances
					.instance(next).value(attIndex)) {
				distribution.shiftRange(1, 0, instances, last, next);
				if (Utils.grOrEq(distribution.perBag(0), minSplit)
						&& Utils.grOrEq(distribution.perBag(1), minSplit)) {
					newInfoGain = split_ig.splitValue(distribution,
							sumOfWeights, defaultEnt);
					if (Utils.gr(newInfoGain, infoGain)) {
						infoGain = newInfoGain;
						splitIndex = next - 1;

					}
					index++;
				}
				last = next;
			}
			next++;
		}
		if (index == 0)
			return;
		infoGain = infoGain - (Utils.log2(index) / sumOfWeights);
		if (Utils.smOrEq(infoGain, 0))
			return;
		subsets = 2;
		splitPoint = (instances.instance(splitIndex + 1).value(attIndex) + instances
				.instance(splitIndex).value(attIndex)) / 2;
		if (splitPoint == instances.instance(splitIndex + 1).value(attIndex)) {
			splitPoint = instances.instance(splitIndex).value(attIndex);
		}
		distribution = new Distribution(2, instances.numClasses());
		distribution.addRange(0, instances, 0, splitIndex + 1);
		distribution.addRange(1, instances, splitIndex + 1, firstMiss);
	}

	public double infoGain() {

		return infoGain;
	}

	public double spiltPoint() {

		return splitPoint;
	}
}
