package DataExchange;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Store the every parameters will be put into the database.
 * @author Baishuo Han, Hanlin Zhang and Gang Zhang.
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
	private double b;
	
	public QuackResults(double xData, double yData, double zData,
			String quackTime, double quackGrade, double Parrival, 
			 String panfu, double duringGrade, double nengliang, String filename_S, double tensor, String kind, double b) {
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
		this.b = b;
	}

	public QuackResults() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String toString() {
		
		java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
		NF.setGroupingUsed(false);
		
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
					" b_value="+b+
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
					" b_value="+b+
					" kind="+kind+
					"]";
		}
	}
	/**x */
	public double getxData() {
		return xData;
	}
	public void setxData(double xData) {
		this.xData = xData;
	}
	/**y */
	public double getyData() {
		return yData;
	}
	public void setyData(double yData) {
		this.yData = yData;
	}
	/**z */
	public double getzData() {
		return zData;
	}
	public void setzData(double zData) {
		this.zData = zData;
	}
	/**quake time- the consequence for calculating from location algorithm.*/
	public String getQuackTime() {
		return quackTime;
	}
	public void setQuackTime(String quackTime) {
		this.quackTime = quackTime;
	}
	/**quacke grade- the consequence for calculating from quake grade algorithm.*/
	public double getQuackGrade() {
		return quackGrade;
	}
	public void setQuackGrade(double quackGrade) {
		this.quackGrade = quackGrade;
	}
	/**P arrival as second unit.*/	
	public void setParrival(double Parrival) {
		this.Parrival = Parrival;
	}
	public double getParrival() {
		return Parrival;
	}
	/**the during grade decided with the background noise.*/	
	public void setDuringGrade(double duringGrade) {
		this.duringQuackGrade = duringGrade;
	}
	public double getDuringGrade() {
		return duringQuackGrade;
	}
	/**the motivation disk with different sensors.*/	
	public void setPanfu(String panfu) {
		this.panfu = panfu;
	}
	public String getPanfu() {
		return panfu;
	}
	/**the energy of one micro-quake event.*/	
	public void setNengliang(double nengliang) {
		this.nengliang = nengliang;
	}
	public double getNengliang() {
		return nengliang;
	}
	/**we store the wave on the disk.*/	
	public void setFilename_S(String filename_S) {
		this.filename_S = filename_S;
	}
	public String getFilename_S() {
		return filename_S;
	}
	/**Tensor value calculating by the algorithm matlab.*/	
	public void setTensor(double tensor) {
		this.tensor = tensor;
	}
	public double getTensor() {
		return tensor;
	}
	/**which locating algorithm use.*/
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getKind() {
		return kind;
	}
	/**b value calculating by matlab.*/
	public void setbvalue(double b) {
		this.b = b;
	}
	public double getbvalue() {
		return b;
	}

}
