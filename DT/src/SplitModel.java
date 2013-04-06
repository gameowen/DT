import weka.core.Instance;
import weka.core.Instances;


public class SplitModel {
	int attIndex;
	int minObj;
	int numSplits;
	double splitPoint;
	double gainRatio;
	double infoGain;
	int[] splitedDistribution;
	
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
		splitedDistribution = new int[data.attribute(attIndex).numValues()];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instance instance = data.instance(i);
			splitedDistribution[(int) instance.value(attIndex)]++;
		}
		
		// Do we need to check whether the split is valid?
		calculateInfoGain();
		calculateGainRatio();
	}
	
	public void getNumSplitResult(Instances data) {
		
	}
	
	private void calculateInfoGain() {
		
	}
	
	private void calculateGainRatio() {
		
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
