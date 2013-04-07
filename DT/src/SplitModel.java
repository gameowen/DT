<<<<<<< HEAD
import java.util.Arrays;
=======
import java.util.Enumeration;
>>>>>>> 3540ce4b2e37a7173f66b64b93cff491b42f30d4

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

//
public class SplitModel {
	int attIndex;
	int minObj;
	int numSplits;
	double splitPoint;
	double gainRatio;
	double infoGain;
<<<<<<< HEAD
	double[] splitedDistribution;
=======
>>>>>>> 3540ce4b2e37a7173f66b64b93cff491b42f30d4
	
	public SplitModel(int attIndex, int minObj) {
		this.attIndex = attIndex;
		this.minObj = minObj;
	}
	
	public void buildSplitModel(Instances data, double[] distribution) {
		if (data.attribute(attIndex).isNominal()) {
			numSplits = data.attribute(attIndex).numValues();
			getNomSplitResult(data, distribution);
		} else {
			numSplits = 2;
			getNumSplitResult(data);
		}
	}
	
<<<<<<< HEAD
	public void getNomSplitResult(Instances data, double[] distribution) {
		splitedDistribution = new double[data.attribute(attIndex).numValues()];
=======
	public void getNomSplitResult(Instances data) {
		int[] splitedDistribution = new int[data.attribute(attIndex).numValues()];
>>>>>>> 3540ce4b2e37a7173f66b64b93cff491b42f30d4
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance instance = data.instance(i);
			splitedDistribution[(int) instance.value(attIndex)] += 1;
		}
		
<<<<<<< HEAD
		System.out.println(Arrays.toString(splitedDistribution));
		
		// Do we need to check whether the split is valid? Whether minObj in split model?
		calculateInfoGain(data, distribution);
		calculateGainRatio(data, distribution);
	}
	
	public void getNumSplitResult(Instances data) {
		
	}
	
	private void calculateInfoGain(Instances data, double[] classDist) {
		double oldEntropy = getEntropy(classDist, data.numInstances());
		double newEntropy = getEntropy(splitedDistribution, data.numInstances());
		
		System.out.println("oldEntropy: " + oldEntropy);
		System.out.println("newEntropy: " + newEntropy);
		
		this.infoGain = newEntropy - oldEntropy;
	}
	
	private double getEntropy(double[] classDist, double numOfInstances) {
		// double question again
		double r = 0;
		System.out.println("	num of class:" + classDist.length);
		for (int i = 0; i < classDist.length; i++) {
			System.out.println("	percent: " + (classDist[i] / numOfInstances));
			//System.out.println("	log percent: " + (Math.log((classDist[i] / numOfInstances))));
			if (classDist[i] != 0) {
				r -= (Math.log((classDist[i] / numOfInstances)) / Math.log(2)) * (classDist[i] / numOfInstances);
				System.out.println("r: " + r);
			}			
		}
		
		
		return r;
	}
	
	private void calculateGainRatio(Instances data, double[] classDist) {
=======
		
	}
	
	public void getNumSplitResult(Instances data) {
>>>>>>> 3540ce4b2e37a7173f66b64b93cff491b42f30d4
		
	}
	
	public double getGainRatio() {
		return this.gainRatio;
	}
	
	public double getInfoGain() {
		return this.infoGain;
	}
	
	public Instances[] split(Instances data) {
		Instances[] splitedInstances = new Instances[numSplits];
		
		for (int i = 0; i < numSplits; i++) {
			splitedInstances[i] = new Instances(data, data.numInstances());
		}
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);
			splitedInstances[classify(in)].add(in);
		}
		
		return splitedInstances;
	}
	
	public int classify(Instance in) {
		if (in.attribute(attIndex).isNominal()) {
			return (int) in.value(attIndex);
		} else if (in.attribute(attIndex).isNumeric()) {
			if (in.value(attIndex) < splitPoint) return 0;
			return 1;
		}
	
	
		return 0;		
	}
	private double computeEntropy(Instances data) throws Exception {

	    double [] classCounts = new double[data.numClasses()];
	    Enumeration instEnum = data.enumerateInstances();
	    while (instEnum.hasMoreElements()) {
	      Instance inst = (Instance) instEnum.nextElement();
	      classCounts[(int) inst.classValue()]++;
	    }
	    double entropy = 0;
	    for (int j = 0; j < data.numClasses(); j++) {
	      if (classCounts[j] > 0) {
	        entropy -= classCounts[j] * Utils.log2(classCounts[j]);
	      }
	    }
	    entropy /= (double) data.numInstances();
	    return entropy + Utils.log2(data.numInstances());
	  }
}
