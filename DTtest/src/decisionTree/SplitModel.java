package decisionTree;

import java.util.Enumeration;

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
	double[] splitedDistribution;
	boolean valid = true;

	
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
			getNumSplitResult(data, distribution);
		}
	}
	

	public void getNomSplitResult(Instances data, double[] distribution) {
		splitedDistribution = new double[data.attribute(attIndex).numValues()];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance instance = data.instance(i);
			splitedDistribution[(int) instance.value(attIndex)] += 1;
		}

		//System.out.println(Arrays.toString(splitedDistribution));
		
		// Do we need to check whether the split is valid? Whether minObj in split model?
		calculateInfoGain(data, distribution);
		checkValid();
		//calculateGainRatio(data, distribution);
	}
	
	private void checkValid() {
		int count = 0;
		for (int i = 0; i < this.splitedDistribution.length; i++) {
			if (splitedDistribution[i] < minObj) count++; 
			if (count >= 2) {
				valid = false;
				return;
			}
		}
		valid = true;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void getNumSplitResult(Instances data, double[] distribution) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_NORMAL;
		
		for (int i = 0; i < data.size(); i++) {		
			double n = data.instance(i).value(attIndex);
			if (n < min) min = n;
			if (n > max) max = n;
		}
		
		double range = max - min;
		
		double k = 10;
		NumSplitTest[] tests = new NumSplitTest[(int) k];
		
		double oldEntropy = getOldEntropy(distribution, data.numInstances());
//		System.out.println("Old entropy: " + oldEntropy);
		for (int i = 0; i < (int) k; i++) {
			tests[i] = new NumSplitTest(attIndex, minObj, oldEntropy);
			tests[i].buildSplit(data, min + range * (1/k + i * 1/k), distribution);
		}		
		
		int maxIndex = 0;
		
//		System.out.println("Attribute: " + data.attribute(attIndex).name());
		for (int i = 0; i < (int) k; i++) {
			double infoG = tests[i].getInfoGain();
//			System.out.println("Test: " + infoG);
//			System.out.println("SplitPoint: " + tests[i].splitPoint);
			if (tests[i].getInfoGain() > tests[maxIndex].getInfoGain()) {
				maxIndex = i;
			}
		}
		
		NumSplitTest test = tests[maxIndex];
		
		this.infoGain = test.getInfoGain();
		this.splitPoint = test.splitPoint;
		
		this.splitedDistribution = new double[2];
		
	}
	
	public void calculateInfoGain(Instances data, double[] classDist) {
		double oldEntropy = getOldEntropy(classDist, data.numInstances());
		double newEntropy = getNewEntropy(data);
		
//		System.out.println("oldEntropy: " + oldEntropy);
//		System.out.println("newEntropy: " + newEntropy);
		
		this.infoGain = oldEntropy - newEntropy;
	}
	
	public double getNewEntropy(Instances data) {
		double r = 0;
		double numClass = data.classAttribute().numValues();

		double[][] distribution = new double[splitedDistribution.length][(int) numClass];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance in = data.instance(i);			
			distribution[(int) in.value(attIndex)][(int) in.classValue()]++;
		}
		
		for (int i = 0; i < splitedDistribution.length; i++) {
			for (int j = 0; j < numClass; j++) {
				r += getLog(distribution[i][j]);
			}
			
			r -= getLog(splitedDistribution[i]);
		}
		
		return -r / data.numInstances();
	}
	 
	public double getLog(double num) {
		if (num == 0) return 0;
		
		return num * (Math.log(num) / Math.log(2));
	}
	
	private double getOldEntropy(double[] classDist, double numOfInstances) {
		// double question again
		double r = 0;
		//System.out.println("	num of class:" + classDist.length);
		for (int i = 0; i < classDist.length; i++) {
			//System.out.println("	percent: " + (classDist[i] / numOfInstances));
			//System.out.println("	log percent: " + (Math.log((classDist[i] / numOfInstances))));
			if (classDist[i] != 0) {
				r += getLog(classDist[i]);
				//System.out.println("r: " + r);
			}			
		}
		
		r -= getLog(numOfInstances);
		
		return -r;
	}
	
	private void calculateGainRatio(Instances data, double[] classDist) {
		
		
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
