package numeric;

import weka.classifiers.trees.j48.Distribution;
import weka.core.Utils;

public class getSplitByInfoGain {
	public double splitValue(Distribution bags) {
		double numerator;
		numerator = oldEnt(bags) - newEnt(bags);
		if (Utils.eq(numerator, 0))
			return Double.MAX_VALUE;
		return bags.total() / numerator;
	}

	public double logFunc(double num) {

		// Constant hard coded for efficiency reasons
		if (num < 1e-6)
			return 0;
		else
			return num * Math.log(num) / Math.log(2);
	}

	public double oldEnt(Distribution bags) {

		double returnValue = 0;
		int j;

		for (j = 0; j < bags.numClasses(); j++)
			returnValue = returnValue + logFunc(bags.perClass(j));
		return logFunc(bags.total()) - returnValue;
	}

	public double newEnt(Distribution bags) {

		double returnValue = 0;
		int i, j;

		for (i = 0; i < bags.numBags(); i++) {
			for (j = 0; j < bags.numClasses(); j++)
				returnValue = returnValue + logFunc(bags.perClassPerBag(i, j));
			returnValue = returnValue - logFunc(bags.perBag(i));
		}
		return -returnValue;
	}

	public double splitEnt(Distribution bags) {

		double returnValue = 0;
		int i;

		for (i = 0; i < bags.numBags(); i++)
			returnValue = returnValue + logFunc(bags.perBag(i));
		return logFunc(bags.total()) - returnValue;
	}

	public double splitValue(Distribution bags, double totalNoInst,
			double oldEnt) {
		double numerator;
		double noUnknown;
		double unknownRate;
		int i;

		noUnknown = totalNoInst - bags.total();
		unknownRate = noUnknown / totalNoInst;
		numerator = (oldEnt - newEnt(bags));
		numerator = (1 - unknownRate) * numerator;

		// Splits with no gain are useless.
		if (Utils.eq(numerator, 0))
			return 0;

		return numerator / bags.total();
	}
}
