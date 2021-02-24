package read.yang.readFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import read.yang.unity.HfmedHead;
import read.yang.util.Byte2Int;
import read.yang.util.Byte2Short;
import read.yang.util.Byte2String;
import read.yang.util.FindByte;

public class ReadHfmedHead {
    
	
	public HfmedHead readHead(String fileName) throws IOException{
		
		/**
		 * 用于将下面求出的个字段封装起来
		 */
		HfmedHead hfmed = new HfmedHead() ;
		
		//文件地址 
		File file = new File(fileName) ;
		
	    BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file)) ;
		
		//将读到的头文件放入到字符数组缓冲区中
	    byte[] tempByte = new byte[186] ;
	    
	    buffered.read(tempByte) ;
	    
	    buffered.close() ;
	    
	    
	    /**接下来将初始化实体类,接下来要解决的问题就是怎么取到字节数组里面特定的字节 */
	    //数据赋值区
	    
	    byte[] fileHeadLengthByte = FindByte.searchByteSeq(tempByte, 0, 1) ; 
	    
	    byte[] formatVerByte = FindByte.searchByteSeq(tempByte, 2, 5) ;
	    
	    byte[] dataFileNameByte = FindByte.searchByteSeq(tempByte, 6, 85) ;
	    
	    byte[] operatorNameByte = FindByte.searchByteSeq(tempByte, 86, 95) ;
	    
	    byte[] palaceNameByte = FindByte.searchByteSeq(tempByte, 96, 115) ;
	    
	    byte[] startDateByte = FindByte.searchByteSeq(tempByte, 116, 125) ;
	    
	    byte[] sysCounterByte = FindByte.searchByteSeq(tempByte, 126, 133) ;
	    
	    byte[] sysFeqByte = FindByte.searchByteSeq(tempByte, 134, 141) ;
	    
	    byte[] uesrIdNameByte = FindByte.searchByteSeq(tempByte, 142, 149) ;
	    
	    byte[] adFedByte = FindByte.searchByteSeq(tempByte, 150, 153) ;
	    
	    byte[] resolutionByte = FindByte.searchByteSeq(tempByte, 154, 155) ;
	    
	    byte[] fileDurationByte = FindByte.searchByteSeq(tempByte, 156, 159) ;
	    
	    byte[] segmentNumByte = FindByte.searchByteSeq(tempByte, 160, 163) ;
	    
	    byte[] segmentHeadLengthByte = FindByte.searchByteSeq(tempByte, 164, 165) ;
	    
	    byte[] indexSegmentHeadLenghtByte = FindByte.searchByteSeq(tempByte, 166, 167) ;
	    
	    byte[] segmentRecNumByte = FindByte.searchByteSeq(tempByte, 168, 171) ;
	    
	    byte[] segmentDurationByte = FindByte.searchByteSeq(tempByte, 172, 175) ;
	    
	    byte[] featureCodeByte = FindByte.searchByteSeq(tempByte, 176, 179) ;
	    
	    byte[] channelOnNumByte = FindByte.searchByteSeq(tempByte, 180, 181) ;
	    
	    byte[] reserveByte = FindByte.searchByteSeq(tempByte, 182, 185) ;
	   
	    
	    //数据转换区
	    short fileHeadLength = Byte2Short.byte2Short(fileHeadLengthByte) ;
	    String formatVer = Byte2String.byte2String(formatVerByte) ;
	    String dataFileName = Byte2String.byte2String(dataFileNameByte) ;
	    String operator = Byte2String.byte2String(operatorNameByte) ;
	    String palaceName = Byte2String.byte2String(palaceNameByte) ;
	    
	    //时间变成String即可
	    String startDate = Byte2String.byte2String(startDateByte) ;
	    String sysCounter = Byte2String.byte2String(sysCounterByte) ;
	    String sysFeq = Byte2String.byte2String(sysFeqByte) ;
	    //end time transform 
	    //你还需要用simpleDateFormat转换
	    
	    String userIdName = Byte2String.byte2String(uesrIdNameByte);
	    int adFre = Byte2Int.byte2Int(adFedByte) ;
	    short resolution =  Byte2Short.byte2Short(resolutionByte) ; 
	    int segmentNum = Byte2Int.byte2Int(segmentNumByte);
	    short segmentHeadLength = Byte2Short.byte2Short(segmentHeadLengthByte) ;
	    short indexSegmentHeadLength = Byte2Short.byte2Short(indexSegmentHeadLenghtByte);
	    int segmentRecNum = Byte2Int.byte2Int(segmentRecNumByte);
	    String featureCode = Byte2String.byte2String(featureCodeByte) ;
	    short channelOnNum = Byte2Short.byte2Short(channelOnNumByte);
	  
	    //为对象属性赋值
	    hfmed.setAdFre(adFre);
	    hfmed.setChannelOnNum(channelOnNum) ;
	    hfmed.setDataFileName(dataFileName);
	    hfmed.setFeatureCode(featureCode);
	    hfmed.setFileHeadlenght(fileHeadLength);
	    hfmed.setFormatVer(formatVer);
	    hfmed.setIndexSegmentHeadLenght(indexSegmentHeadLength);
	    hfmed.setOperator(operator);
	    hfmed.setPalaceName(palaceName);
	    hfmed.setResolution(resolution);
	    hfmed.setSegmentHeadLength(segmentHeadLength);
	    hfmed.setSegmentNum(segmentNum);
	    hfmed.setSegmentRecNum(segmentRecNum);
	    hfmed.setUserIdName(userIdName);
	    
	    return hfmed ; 
		
		
		
		
		
	}
	
public HfmedHead readHead(File file) throws IOException{
		
		/**
		 * 用于将下面求出的个字段封装起来
		 */
		HfmedHead hfmed = new HfmedHead() ;
		
		//文件地址 
		
	    BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file)) ;
		
		//将读到的头文件放入到字符数组缓冲区中
	    byte[] tempByte = new byte[186] ;
	    
	    buffered.read(tempByte);
	    
	    buffered.close() ;
	    
	   
	    
	    
	    /**接下来将初始化实体类,接下来要解决的问题就是怎么取到字节数组里面特定的字节 */
	    //数据赋值区
	    
	    byte[] fileHeadLengthByte = FindByte.searchByteSeq(tempByte, 0, 1) ; 
	    
	    byte[] formatVerByte = FindByte.searchByteSeq(tempByte, 2, 5) ;
	    
	    byte[] dataFileNameByte = FindByte.searchByteSeq(tempByte, 6, 85) ;
	    
	    byte[] operatorNameByte = FindByte.searchByteSeq(tempByte, 86, 95) ;
	    
	    byte[] palaceNameByte = FindByte.searchByteSeq(tempByte, 96, 115) ;
	    
	    byte[] startDateByte = FindByte.searchByteSeq(tempByte, 116, 125) ;
	    
	    byte[] sysCounterByte = FindByte.searchByteSeq(tempByte, 126, 133) ;
	    
	    byte[] sysFeqByte = FindByte.searchByteSeq(tempByte, 134, 141) ;
	    
	    byte[] uesrIdNameByte = FindByte.searchByteSeq(tempByte, 142, 149) ;
	    
	    byte[] adFedByte = FindByte.searchByteSeq(tempByte, 150, 153) ;
	    
	    byte[] resolutionByte = FindByte.searchByteSeq(tempByte, 154, 155) ;
	    
	    byte[] fileDurationByte = FindByte.searchByteSeq(tempByte, 156, 159) ;
	    
	    byte[] segmentNumByte = FindByte.searchByteSeq(tempByte, 160, 163) ;
	    
	    byte[] segmentHeadLengthByte = FindByte.searchByteSeq(tempByte, 164, 165) ;
	    
	    byte[] indexSegmentHeadLenghtByte = FindByte.searchByteSeq(tempByte, 166, 167) ;
	    
	    byte[] segmentRecNumByte = FindByte.searchByteSeq(tempByte, 168, 171) ;
	    
	    byte[] segmentDurationByte = FindByte.searchByteSeq(tempByte, 172, 175) ;
	    
	    byte[] featureCodeByte = FindByte.searchByteSeq(tempByte, 176, 179) ;
	    
	    byte[] channelOnNumByte = FindByte.searchByteSeq(tempByte, 180, 181) ;
	    
	    byte[] reserveByte = FindByte.searchByteSeq(tempByte, 182, 185) ;
	   
	    
	    //数据转换区
	    short fileHeadLength = Byte2Short.byte2Short(fileHeadLengthByte) ;
	    String formatVer = Byte2String.byte2String(formatVerByte) ;
	    String dataFileName = Byte2String.byte2String(dataFileNameByte) ;
	    String operator = Byte2String.byte2String(operatorNameByte) ;
	    String palaceName = Byte2String.byte2String(palaceNameByte) ;
	    
	    //时间变成String即可
	    String startDate = Byte2String.byte2String(startDateByte) ;
	    String sysCounter = Byte2String.byte2String(sysCounterByte) ;
	    String sysFeq = Byte2String.byte2String(sysFeqByte) ;
	    //end time transform 
	    //你还需要用simpleDateFormat转换
	    
	    String userIdName = Byte2String.byte2String(uesrIdNameByte);
	    int adFre = Byte2Int.byte2Int(adFedByte) ;
	    short resolution =  Byte2Short.byte2Short(resolutionByte) ; 
	    int segmentNum = Byte2Int.byte2Int(segmentNumByte);
	    short segmentHeadLength = Byte2Short.byte2Short(segmentHeadLengthByte) ;
	    short indexSegmentHeadLength = Byte2Short.byte2Short(indexSegmentHeadLenghtByte);
	    int segmentRecNum = Byte2Int.byte2Int(segmentRecNumByte);
	    String featureCode = Byte2String.byte2String(featureCodeByte) ;
	    short channelOnNum = Byte2Short.byte2Short(channelOnNumByte);
	  
	    //为对象属性赋值
	    hfmed.setAdFre(adFre);
	    hfmed.setChannelOnNum(channelOnNum) ;
	    hfmed.setDataFileName(dataFileName);
	    hfmed.setFeatureCode(featureCode);
	    hfmed.setFileHeadlenght(fileHeadLength);
	    hfmed.setFormatVer(formatVer);
	    hfmed.setIndexSegmentHeadLenght(indexSegmentHeadLength);
	    hfmed.setOperator(operator);
	    hfmed.setPalaceName(palaceName);
	    hfmed.setResolution(resolution);
	    hfmed.setSegmentHeadLength(segmentHeadLength);
	    hfmed.setSegmentNum(segmentNum);
	    hfmed.setSegmentRecNum(segmentRecNum);
	    hfmed.setUserIdName(userIdName);
	    return hfmed ; 
		
		
		
		
		
	}
	
	
}
