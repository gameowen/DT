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
	
	public SplitModel(int attIndex, int minObj) {
		this.attIndex = attIndex;
		this.minObj = minObj;
	}
	
	public void buildSplitModel(Instances data) {
		if (data.attribute(attIndex).isNominal()) {
			numSplits = data.attribute(attIndex).numValues();
			getNomSplitResult(data);
		} else {
			numSplits = 2;
			getNumSplitResult(data);
		}
	}
	
	public void getNomSplitResult(Instances data) {
		int[] splitedDistribution = new int[data.attribute(attIndex).numValues()];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance instance = data.instance(i);
			splitedDistribution[(int) instance.value(attIndex)]++;
		}
		
		
	}
	
	public void getNumSplitResult(Instances data) {
		
	}
	
	public double getGainRatio() {
		return this.gainRatio;
	}
	
	public double getInfoGain() {
		return this.infoGain;
	}
	
	public Instances[] split(Instances data) {
		Instances[] instances = new Instances[numSplits];
		
		return instances;
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
