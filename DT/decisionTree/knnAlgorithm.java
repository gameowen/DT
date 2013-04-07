import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class knnAlgorithm {
  private final int k = 3;
	private List<Attributes> instanceListTraining = new ArrayList<Attributes>();
	private List<Attributes> instanceListTest = new ArrayList<Attributes>();

	private double vacationMax = 0;
	private double vacationMin = 0;
	private double eCreditMax = 0;
	private double eCreditMin = 0;
	private double salaryMax = 0;
	private double salaryMin = 0;
	private double propertyValueMax = 0;
	private double propertyValueMin = 0;

	public void storeData() {
		try {
			BufferedReader trainReader = new BufferedReader(
					new FileReader(
							"C:/Users/vivek/Downloads/task11a_2013(3)/task11a_2013/attachments/trainProdSelection/trainProdSelection.arff"));
			BufferedReader testReader = new BufferedReader(
					new FileReader(
							"C:/Users/vivek/Downloads/task11a_2013(3)/task11a_2013/attachments/testProdSelection/testProdSelection.arff"));

			ArffReader arffTrain = new ArffReader(trainReader);
			Instances data = arffTrain.getData();
			ArffReader arffTest = new ArffReader(testReader);
			Instances testData = arffTest.getData();
			/*
			 * String[] attributes = new String[data.numAttributes()]; String[]
			 * attributesMax = new String[data.numAttributes()]; String[]
			 * attributesMin = new String[data.numAttributes()]; for (int i = 0;
			 * i < data.numAttributes(); i++) { attributes[i] =
			 * data.attribute(i).toString(); attributesMax[i] = "";
			 * attributesMin[i] = ""; }
			 */

			for (int i = 0; i < data.numInstances(); i++) {
				String[] str = new String[data.numAttributes()];
				str = data.instance(i).toString().split(",");
				Attributes attrInstance = new Attributes();
				String type = str[0];
				String lifeStyle = str[1];
				int vacation = Integer.parseInt(str[2]);
				int eCredit = Integer.parseInt(str[3]);
				double salary = Double.parseDouble(str[4]);
				double propertyValue = Double.parseDouble(str[5]);

				if (vacation < vacationMin) {
					vacationMin = vacation;
				}
				if (vacation > vacationMax) {
					vacationMax = vacation;
				}
				if (eCredit < eCreditMin) {
					eCreditMin = eCredit;
				}
				if (eCredit > eCreditMax) {
					eCreditMax = eCredit;
				}
				if (salary < salaryMin) {
					salaryMin = salary;
				}
				if (salary > salaryMax) {
					salaryMax = salary;
				}
				if (propertyValue < propertyValueMin) {
					propertyValueMin = propertyValue;
				}
				if (propertyValue > propertyValueMax) {
					propertyValueMax = propertyValue;
				}
				attrInstance.setType(type);
				attrInstance.setLifeStyle(lifeStyle);
				attrInstance.setVacation(vacation);
				attrInstance.seteCredit(eCredit);
				attrInstance.setSalary(salary);
				attrInstance.setPropertyValue(propertyValue);
				attrInstance.setClassValue(str[6]);
				instanceListTraining.add(attrInstance);
			}

			for (int i = 0; i < testData.numInstances(); i++) {
				String[] str = new String[testData.numAttributes()];
				str = testData.instance(i).toString().split(",");
				Attributes attrInstance = new Attributes();
				String type = str[0];
				String lifeStyle = str[1];
				int vacation = Integer.parseInt(str[2]);
				int eCredit = Integer.parseInt(str[3]);
				double salary = Double.parseDouble(str[4]);
				double propertyValue = Double.parseDouble(str[5]);

				if (vacation < vacationMin) {
					vacationMin = vacation;
				}
				if (vacation > vacationMax) {
					vacationMax = vacation;
				}
				if (eCredit < eCreditMin) {
					eCreditMin = eCredit;
				}
				if (eCredit > eCreditMax) {
					eCreditMax = eCredit;
				}
				if (salary < salaryMin) {
					salaryMin = salary;
				}
				if (salary > salaryMax) {
					salaryMax = salary;
				}
				if (propertyValue < propertyValueMin) {
					propertyValueMin = propertyValue;
				}
				if (propertyValue > propertyValueMax) {
					propertyValueMax = propertyValue;
				}
				attrInstance.setType(type);
				attrInstance.setLifeStyle(lifeStyle);
				attrInstance.setVacation(vacation);
				attrInstance.seteCredit(eCredit);
				attrInstance.setSalary(salary);
				attrInstance.setPropertyValue(propertyValue);
				attrInstance.setClassValue(str[6]);
				instanceListTest.add(attrInstance);
			}

			for (int i = 0; i < data.numInstances(); i++) {
				double[] weights = new double[testData.numAttributes()];
				weights[2] = Math.sqrt(normalize(instanceListTraining.get(i)
						.getVacation(), vacationMax, vacationMin));
				weights[3] = Math.sqrt(normalize(instanceListTraining.get(i)
						.geteCredit(), eCreditMax, eCreditMin));
				weights[4] = Math.sqrt(normalize(instanceListTraining.get(i)
						.getSalary(), salaryMax, salaryMin));
				weights[5] = Math
						.sqrt(normalize(instanceListTraining.get(i)
								.getPropertyValue(), propertyValueMax,
								propertyValueMin));

				instanceListTraining.get(i).setVacation(weights[2]);
				instanceListTraining.get(i).seteCredit(weights[3]);
				instanceListTraining.get(i).setSalary(weights[4]);
				instanceListTraining.get(i).setPropertyValue(weights[5]);

			}

			for (int i = 0; i < testData.numInstances(); i++) {
				double[] weights = new double[testData.numAttributes()];
				weights[2] = Math.sqrt(normalize(instanceListTest.get(i)
						.getVacation(), vacationMax, vacationMin));
				weights[3] = Math.sqrt(normalize(instanceListTest.get(i)
						.geteCredit(), eCreditMax, eCreditMin));
				weights[4] = Math.sqrt(normalize(instanceListTest.get(i)
						.getSalary(), salaryMax, salaryMin));
				weights[5] = Math
						.sqrt(normalize(instanceListTest.get(i)
								.getPropertyValue(), propertyValueMax,
								propertyValueMin));

				instanceListTest.get(i).setVacation(weights[2]);
				instanceListTest.get(i).seteCredit(weights[3]);
				instanceListTest.get(i).setSalary(weights[4]);
				instanceListTest.get(i).setPropertyValue(weights[5]);

			}
			
			for (int i = 0; i < instanceListTest.size(); i++) {
				Map<Double, String> map = new TreeMap<Double, String>();
				for (int j = 0; j < instanceListTraining.size(); j++) {
					double a = euclideanNorm(instanceListTest.get(i)
							.getVacation(), instanceListTraining.get(j)
							.getVacation());
					double b = euclideanNorm(instanceListTest.get(i)
							.geteCredit(), instanceListTraining.get(j)
							.geteCredit());
					double c = euclideanNorm(instanceListTest.get(i)
							.getSalary(), instanceListTraining.get(j)
							.getSalary());
					double d = euclideanNorm(instanceListTest.get(i)
							.getPropertyValue(), instanceListTraining.get(j)
							.getPropertyValue());
					// System.out.println(a);
					double distance = 1 / (Math.sqrt(a + b + c + d));
					map.put(distance, instanceListTraining.get(j)
							.getClassValue());
					
				}
				System.out.println(map.keySet());
				System.out.println(map.values());
				List<String> list = new ArrayList<String>(map.values());
				for(int p =0;p<k;p++) {
					System.out.println("values are: "+list.get(p));
				}
				int [] arr = new int[k];
				String tmp = "";
				int max = 0;
				for(int p =0;p<k;p++) {
					for(int q =0;q<k;q++) {
						if(list.get(p).equals(list.get(q))) {
							arr[p]++;
							//System.out.println(list.get(p) + " inside");
						}
					}
					if(max < arr[p]) {
						max = arr[p];
						tmp = list.get(p);		
					}
					
				}
				
				System.out.println(tmp);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static double normalize(double x, double xMax, double xMin) {
		return Math.pow((x - xMin) / (xMax - xMin), 2);
	}

	private static double euclideanNorm(double x, double y) {
		return Math.pow((x - y), 2);
	}

	public static void main(String[] args) {
		knnAlgorithm knn = new knnAlgorithm();
		knn.storeData();
	}
}
