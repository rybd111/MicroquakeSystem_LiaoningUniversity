package read.yang.unity;

import java.util.Arrays;

/**
 * <p>1.文件头实体 ，注意：友军提供的资料字符串也是用字节表示的，
 * 所以将它转换成字符串必然涉及到编码，凑巧的是java里面提供各种
 * string的构造方法</P>
 * 
 * <p>2.<font color= "red">由于文件中的数据都是以字节计数，所以
 * 我们思考的时候也应该面向字节去思考!</font></p>
 * 
 * <p>3.这个类中定义为字节数组的大多数情况下都是“字符串”
 * String s = new String(byte[] , charSetName)
 * 用这种方式去构造，生成字符串</p>
 * 
 * 
 * <p>4.注意：hfmed文件头中的short占两位，但是在用流读出来的两个字节，第一个开始的字节是低位，而第二个字节是高位
 * 解释如下 ： 流的读入顺序： byte1 ： 0x4e  , byte2 : 0x50 ,<font color="red">byte1 是低位，
 * 而byte2是高位</font></p>
 * @author hyena-yang
 *
 */

public class HfmedHead {
     
	
	/** 文件头长度 */
	private short fileHeadLength ;
	
	/** 数据格式版本号 */
	private String formatVer ;
	
	/** 数据文件名称，避免文件名被修改 */
	private String dataFileName ;
	
    /** 操作员 */
	private String operator ;
	
	/** 观测地点名字 */
	private String palaceName ;
	
	/** 系统启动时间 */
	private byte[] sysStartTime ;
	
	/** 系统微秒计数器 
	 *  注意：由于java中long类型占8个字节，所以从一个文件中
	 *  读出来8个字节直接赋值即可
	 * */
	private long sysCounter ;
	
	/** 系统运行频率 */
	private long sysFrequency ;
	
	/** 用户指定的文件特定名称 */
	private String userIdName ;
	
	/** 模数转换频率 */
	private int adFre ;
	
	/** ad分辨率 */
	private short resolution ;
	
	/** 文件记录时间长度  ，注意这是浮点型，4个字节 */
	private float fileDuration  ;
	
	/** 本数据文件所能包含的最多的数据段数 */
	private int segmentNum ;
	
	/** 段头长度字节数 */
	private short segmentHeadLength ;
	
	/** 索引文件段头长度 */
	private short indexSegmentHeadLenght ;
	
	/** 个采样段所包含的记录条数 */
	private int segmentRecNum ;
	
	/** 段记录时间长度 */
	private float segmentDuration ;
	
    /** 特征码 。将在后面的每一个额数据段里面，出现在断头的最后4个字节
     * 这里取为 ：HFME（大写）*/
	private String featureCode ;
	
	/** 本单元所使用的通道数 1 - 7 */
	private short channelOnNum ;
	
	/** 文件编号，从1开始  
	 *  文档上的另一种解释：保留4个字节，留作微调
	 * */
	private int reserve ;
    
	
	/**default construtor */
	public HfmedHead(){
		 //do something
	}


	public HfmedHead(short fileHeadlenght, String formatVer,
			String dataFileName, String operator, String palaceName,
			byte[] sysStartTime, long sysCounter, long sysFrequency,
			String userIdName, int adFre, short resolution, float fileDuration,
			int segmentNum, short segmentHeadLength,
			short indexSegmentHeadLenght, int segmentRecNum,
			float segmentDuration, String featureCode, short channelOnNum,
			int reserve) {
		super();
		this.fileHeadLength = fileHeadlenght;
		this.formatVer = formatVer;
		this.dataFileName = dataFileName;
		this.operator = operator;
		this.palaceName = palaceName;
		this.sysStartTime = sysStartTime;
		this.sysCounter = sysCounter;
		this.sysFrequency = sysFrequency;
		this.userIdName = userIdName;
		this.adFre = adFre;
		this.resolution = resolution;
		this.fileDuration = fileDuration;
		this.segmentNum = segmentNum;
		this.segmentHeadLength = segmentHeadLength;
		this.indexSegmentHeadLenght = indexSegmentHeadLenght;
		this.segmentRecNum = segmentRecNum;
		this.segmentDuration = segmentDuration;
		this.featureCode = featureCode;
		this.channelOnNum = channelOnNum;
		this.reserve = reserve;
	}


	public short getFileHeadlenght() {
		return fileHeadLength;
	}


	public void setFileHeadlenght(short fileHeadlenght) {
		this.fileHeadLength = fileHeadlenght;
	}


	public String getFormatVer() {
		return formatVer;
	}


	public void setFormatVer(String formatVer) {
		this.formatVer = formatVer;
	}


	public String getDataFileName() {
		return dataFileName;
	}


	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getPalaceName() {
		return palaceName;
	}


	public void setPalaceName(String palaceName) {
		this.palaceName = palaceName;
	}


	public byte[] getSysStartTime() {
		return sysStartTime;
	}


	public void setSysStartTime(byte[] sysStartTime) {
		this.sysStartTime = sysStartTime;
	}


	public long getSysCounter() {
		return sysCounter;
	}


	public void setSysCounter(long sysCounter) {
		this.sysCounter = sysCounter;
	}


	public long getSysFrequency() {
		return sysFrequency;
	}


	public void setSysFrequency(long sysFrequency) {
		this.sysFrequency = sysFrequency;
	}


	public String getUserIdName() {
		return userIdName;
	}


	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}


	public int getAdFre() {
		return adFre;
	}


	public void setAdFre(int adFre) {
		this.adFre = adFre;
	}


	public short getResolution() {
		return resolution;
	}


	public void setResolution(short resolution) {
		this.resolution = resolution;
	}


	public float getFileDuration() {
		return fileDuration;
	}


	public void setFileDuration(float fileDuration) {
		this.fileDuration = fileDuration;
	}


	public int getSegmentNum() {
		return segmentNum;
	}


	public void setSegmentNum(int segmentNum) {
		this.segmentNum = segmentNum;
	}


	public short getSegmentHeadLength() {
		return segmentHeadLength;
	}


	public void setSegmentHeadLength(short segmentHeadLength) {
		this.segmentHeadLength = segmentHeadLength;
	}


	public short getIndexSegmentHeadLenght() {
		return indexSegmentHeadLenght;
	}


	public void setIndexSegmentHeadLenght(short indexSegmentHeadLenght) {
		this.indexSegmentHeadLenght = indexSegmentHeadLenght;
	}


	public int getSegmentRecNum() {
		return segmentRecNum;
	}


	public void setSegmentRecNum(int segmentRecNum) {
		this.segmentRecNum = segmentRecNum;
	}


	public float getSegmentDuration() {
		return segmentDuration;
	}


	public void setSegmentDuration(float segmentDuration) {
		this.segmentDuration = segmentDuration;
	}


	public String getFeatureCode() {
		return featureCode;
	}


	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}


	public short getChannelOnNum() {
		return channelOnNum;
	}


	public void setChannelOnNum(short channelOnNum) {
		this.channelOnNum = channelOnNum;
	}


	public int getReserve() {
		return reserve;
	}


	public void setReserve(int reserve) {
		this.reserve = reserve;
	}


	@Override
	public String toString() {
		return "HfmedHead [fileHeadlenght=" + fileHeadLength + ", formatVer="
				+ formatVer + ", dataFileName=" + dataFileName + ", operator="
				+ operator + ", palaceName=" + palaceName + ", sysStartTime="
				+ Arrays.toString(sysStartTime) + ", sysCounter=" + sysCounter
				+ ", sysFrequency=" + sysFrequency + ", userIdName="
				+ userIdName + ", adFre=" + adFre + ", resolution="
				+ resolution + ", fileDuration=" + fileDuration
				+ ", segmentNum=" + segmentNum + ", segmentHeadLength="
				+ segmentHeadLength + ", indexSegmentHeadLenght="
				+ indexSegmentHeadLenght + ", segmentRecNum=" + segmentRecNum
				+ ", segmentDuration=" + segmentDuration + ", featureCode="
				+ featureCode + ", channelOnNum=" + channelOnNum + ", reserve="
				+ reserve + "]";
	}
	
	
	

}
