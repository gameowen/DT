package decisionTree;

public class Attributes implements Comparable{
  private String type;
	private String lifeStyle;
	private double vacation;
	private double eCredit;
	private double salary;
	private double propertyValue;
	private String classValue;
	private double distance;
	
	public double getVacation() {
		return vacation;
	}
	public void setVacation(double vacation) {
		this.vacation = vacation;
	}
	public double geteCredit() {
		return eCredit;
	}
	public void seteCredit(double eCredit) {
		this.eCredit = eCredit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLifeStyle() {
		return lifeStyle;
	}
	public void setLifeStyle(String lifeStyle) {
		this.lifeStyle = lifeStyle;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getClassValue() {
		return classValue;
	}
	public void setClassValue(String classValue) {
		this.classValue = classValue;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public int compareTo(Object obj) {
		Attributes attr = (Attributes)obj;
		if(this.distance > attr.getDistance()) {
			return 1;
		} else if (this.distance == attr.getDistance()) {
			return 0;
		} else {
			return -1;
		}
		
	}
	
	

}
