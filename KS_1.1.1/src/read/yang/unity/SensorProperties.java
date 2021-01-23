package read.yang.unity;

import java.util.Arrays;

/**通道信息，在文件头下面，一共有7个通道信息
 * 
 * @author hyena-yang
 *
 */
public class SensorProperties {
   
	/** 通道号 */
	private short chNo ;
	
	/** 通道采集量名称 --即通道名 */
	private String chName ;
	
	/** 通道标定单位 ，注意每个通道数据算出来的数据还要乘以
	 * 这个单位
	 */
	private String chUnit ;
	
	/** 通道标定数据 */
	private float chCali ;

	public SensorProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SensorProperties(short chNo, String chName, String chUnit, float chCali) {
		super();
		this.chNo = chNo;
		this.chName = chName;
		this.chUnit = chUnit;
		this.chCali = chCali;
	}

	public short getChNo() {
		return chNo;
	}

	public void setChNo(short chNo) {
		this.chNo = chNo;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getChUnit() {
		return chUnit;
	}

	public void setChUnit(String chUnit) {
		this.chUnit = chUnit;
	}

	public float getChCali() {
		return chCali;
	}

	public void setChCali(float chCali) {
		this.chCali = chCali;
	}

	@Override
	public String toString() {
		return "SensorProperties [chNo=" + chNo + ", chName=" + chName
				+ ", chUnit=" + chUnit + ", chCali=" + chCali + "]";
	}

	
	
	
}
