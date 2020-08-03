package bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Store the every parameters will be put into the database.
 * @author Baishuo Han, Hanlin Zhang.
 * */
public class QuackResults {

	private double xData;
	private double yData;
	private double zData;
	private String quackTime;
	private double quackGrade;
	private double Parrival;
	private double duringQuackGrade;
	private String panfu;
	private double nengliang;
	private String filename_S;
	private double tensor;
	private String kind;
	
	public QuackResults(double xData, double yData, double zData,
			String quackTime, double quackGrade, double Parrival, 
			 String panfu, double duringGrade, double nengliang, String filename_S, double tensor, String kind) {
		super();
		this.xData = xData;
		this.yData = yData;
		this.zData = zData;
		this.quackTime = quackTime;
		this.quackGrade = quackGrade;
		this.Parrival = Parrival;
		this.duringQuackGrade = duringGrade;
		this.panfu = panfu;
		this.nengliang = nengliang;
		this.filename_S = filename_S;
		this.kind = kind;
	}

	public QuackResults() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String toString() {
		
		java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
		NF.setGroupingUsed(false);
		//System.out.println("d:="+NF.format(d));
		if(quackGrade < 0){
			return "[x="+NF.format(xData)+" y="+NF.format(yData)+" z="+NF.format(zData)+
					" Time="+quackTime+
					" Grade="+quackGrade+
					" Parrival="+Parrival+
					" duringGrade="+duringQuackGrade+
					" panfu="+panfu+
					" nengliang="+nengliang+
					" filename_S="+filename_S+
					" tensor="+tensor+
					" kind="+kind+
					"--------------------------------------------------------------------------]";
		}
		else{
			return "[x="+NF.format(xData)+" y="+NF.format(yData)+" z="+NF.format(zData)+
					" Time="+quackTime+
					" Grade="+quackGrade+
					" Parrival="+Parrival+
					" duringGrade="+duringQuackGrade+
					" panfu="+panfu+
					" nengliang="+nengliang+
					" filename_S="+filename_S+
					" tensor="+tensor+
					" kind="+kind+
					"]";
		}
	}

	public double getxData() {
		return xData;
	}
	public void setxData(double xData) {
		this.xData = xData;
	}
	
	public double getyData() {
		return yData;
	}
	public void setyData(double yData) {
		this.yData = yData;
	}

	public double getzData() {
		return zData;
	}
	public void setzData(double zData) {
		this.zData = zData;
	}

	public String getQuackTime() {//��Դ����ʱ��
		return quackTime;
	}
	public void setQuackTime(String quackTime) {
		this.quackTime = quackTime;
	}

	public double getQuackGrade() {//���𼶼���
		return quackGrade;
	}
	public void setQuackGrade(double quackGrade) {
		this.quackGrade = quackGrade;
	}
	
	public void setParrival(double Parrival) {//P����ʱ
		this.Parrival = Parrival;
	}
	public double getParrival() {
		return Parrival;
	}
	
	public void setDuringGrade(double duringGrade) {//����ʱ����
		this.duringQuackGrade = duringGrade;
	}
	public double getDuringGrade() {
		return duringQuackGrade;
	}

	public void setPanfu(String panfu) {//����ʱ����
		this.panfu = panfu;
	}
	public String getPanfu() {
		return panfu;
	}
	
	public void setNengliang(double nengliang) {
		this.nengliang = nengliang;
	}
	public double getNengliang() {
		return nengliang;
	}
	
	public void setFilename_S(String filename_S) {
		this.filename_S = filename_S;
	}
	public String getFilename_S() {
		return filename_S;
	}
	
	public void setTensor(double tensor) {
		this.tensor = tensor;
	}
	public double getTensor() {
		return tensor;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getKind() {
		return kind;
	}

}
