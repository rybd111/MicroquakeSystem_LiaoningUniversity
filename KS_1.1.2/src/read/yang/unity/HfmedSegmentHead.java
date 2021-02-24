package read.yang.unity;

import java.util.Arrays;

/**数据段头
 * 注意：每一个数据段有一个数据段头，加下面的好几条数据
 * 
 * 解释如下：
 *  --------------------
 *     数据段头
 *  --------------------
 *      row1 
 *      row2
 *      ...
 *      row3
 *      真正存放数据的地方
 *  --------------------
 * @author  hyena-yang
 *
 */
public class HfmedSegmentHead {
    
	/** 特征码 */
	private byte[] featureCode ;
	
	/** 数据段编号  */
	private int segmentNo ;
	
	/** 数据段头的时间 这个是有用的　*/
	private byte[] sysTime ;
	
	/** usb传输前的系统微妙时钟   */
	private byte[] usbBeforeCurrency ;
	
	/** usb传输后的系统微秒时钟 */
	private byte[] usbAfterCurrency ;

	public HfmedSegmentHead() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HfmedSegmentHead(byte[] featureCode, int segmentNo, byte[] sysTime,
			byte[] usbBeforeCurrency, byte[] usbAfterCurrency) {
		super();
		this.featureCode = featureCode;
		this.segmentNo = segmentNo;
		this.sysTime = sysTime;
		this.usbBeforeCurrency = usbBeforeCurrency;
		this.usbAfterCurrency = usbAfterCurrency;
	}

	public byte[] getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(byte[] featureCode) {
		this.featureCode = featureCode;
	}

	public int getSegmentNo() {
		return segmentNo;
	}

	public void setSegmentNo(int segmentNo) {
		this.segmentNo = segmentNo;
	}

	public byte[] getSysTime() {
		return sysTime;
	}

	public void setSysTime(byte[] sysTime) {
		this.sysTime = sysTime;
	}

	public byte[] getUsbBeforeCurrency() {
		return usbBeforeCurrency;
	}

	public void setUsbBeforeCurrency(byte[] usbBeforeCurrency) {
		this.usbBeforeCurrency = usbBeforeCurrency;
	}

	public byte[] getUsbAfterCurrency() {
		return usbAfterCurrency;
	}

	public void setUsbAfterCurrency(byte[] usbAfterCurrency) {
		this.usbAfterCurrency = usbAfterCurrency;
	}

	@Override
	public String toString() {
		return "HfmedSegmentHead [featureCode=" + Arrays.toString(featureCode)
				+ ", segmentNo=" + segmentNo + ", sysTime="
				+ Arrays.toString(sysTime) + ", usbBeforeCurrency="
				+ Arrays.toString(usbBeforeCurrency) + ", usbAfterCurrency="
				+ Arrays.toString(usbAfterCurrency) + "]";
	}
	
	
	
}
