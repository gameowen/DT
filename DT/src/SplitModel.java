import weka.core.Instance;
import weka.core.Instances;


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
}
