package read.yang.readFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.h2.constant.Parameters;
import read.yang.unity.HfmedHead;
import read.yang.util.FindByte;
import utils.String2Date;

/**
 * the class reads date segment head's time.
 * but only returns the date of the first data segment.
 * @author Xingdong Yang, Hanlin Zhang.
 */
public class ReadDateFromHead {
	
	
	public static String readDataSegmentHead_MrLiu_String(String fileName) throws Exception {
		// 用于承装数据段头的字节
		byte[] dataSegmentHeadByte = new byte[34];
		File file = new File(fileName);
		// 打开流
		HfmedHead hfmedHead = new ReadHfmedHead().readHead(file);//读文件头，文件头内容
		BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file));
		
		if(hfmedHead.getChannelOnNum()==7) {
			Parameters.WenJianTou=284;
			Parameters.TongDaoDiagnose = 1;
		}
		else if(hfmedHead.getChannelOnNum()==4) {
			Parameters.WenJianTou=242;
			Parameters.TongDaoDiagnose = 0;
		}
		buffered.skip(Parameters.WenJianTou);
		buffered.read(dataSegmentHeadByte);
		buffered.close();
		
		byte[] segmentDate = FindByte.searchByteSeq(dataSegmentHeadByte, 8, 17);
		String startDate;
		startDate = "20" + segmentDate[0]+"-";
		startDate = startDate + segmentDate[1]+"-";
		startDate = startDate + segmentDate[2]+" ";
		startDate = startDate + segmentDate[3]+":";
		startDate = startDate + segmentDate[4]+":";
		startDate = startDate + segmentDate[5];
//		System.out.println();
//		System.out.println("当前文件"+fileName+"的起始时间：");
//		
//		for(int i = 0;i<segmentDate.length;i++) {
//			System.out.print(segmentDate[i]+" ");
//		}
//		
//		System.out.println();
		//把78两个字节转换成整型，观察其数值是否符合1000进位。
//		byte [] Abyte = {segmentDate[6], segmentDate[7], segmentDate[8], segmentDate[9]};
//		
//		short[] A = utils.Byte2Short.byteToShort(Abyte);
//		
//		for(int i =0;i<A.length;i++) {
//			System.out.println("转换后的每一字节对应为：" + A[i]);
//		}
		
		return startDate;
	}
	
	public static String readDataSegmentHead_MrMa_String(String fileName) throws Exception {

		// 用于承装数据头(时间戳)的4个字节数组
		byte[] dataSegmentHeadByte = new byte[4];
		File file = new File(fileName);
		// 打开流
		FileInputStream fs = new FileInputStream(file);
		fs.read(dataSegmentHeadByte);
		fs.close();
		
		byte[] xd = new byte[4];
		xd[0]=dataSegmentHeadByte[3];
		xd[1]=dataSegmentHeadByte[2];
		xd[2]=dataSegmentHeadByte[1];
		xd[3]=dataSegmentHeadByte[0];
		
		String st = FindByte.bytesToHexString(xd);//将byte[]转化成16进制字符串		
		long dec_num = Long.parseLong(st, 16); //十六进制数字符串,转换为正的十进制数 
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		String startDate;
		startDate = format2.format(FindByte.longToDate(dec_num));
		return startDate;
	}
   
	public static Date readDataSegmentHead_MrMa_Date(String fileName) throws Exception {
		
		// 用于承装数据头(时间戳)的4个字节数组
		byte[] dataSegmentHeadByte = new byte[4];
		File file = new File(fileName);
		// 打开流
		FileInputStream fs = new FileInputStream(file);
		fs.read(dataSegmentHeadByte);
		fs.close();
		
		byte[] xd = new byte[4];
		xd[0]=dataSegmentHeadByte[3];
		xd[1]=dataSegmentHeadByte[2];
		xd[2]=dataSegmentHeadByte[1];
		xd[3]=dataSegmentHeadByte[0];
		
		String st = FindByte.bytesToHexString(xd);//将byte[]转化成16进制字符串		
		long dec_num = Long.parseLong(st, 16); //十六进制数字符串,转换为正的十进制数 
		
		Date startDate = FindByte.longToDate(dec_num);
		return startDate;
	}
	
	public static Date readDataSegmentHead_MrMa_Date(File file) throws Exception {
		
		// 用于承装数据头(时间戳)的4个字节数组
		byte[] dataSegmentHeadByte = new byte[4];
		// 打开流
		FileInputStream fs = new FileInputStream(file);
		fs.read(dataSegmentHeadByte);
		fs.close();
		
		byte[] xd = new byte[4];
		xd[0]=dataSegmentHeadByte[3];
		xd[1]=dataSegmentHeadByte[2];
		xd[2]=dataSegmentHeadByte[1];
		xd[3]=dataSegmentHeadByte[0];
		
		String st = FindByte.bytesToHexString(xd);//将byte[]转化成16进制字符串		
		long dec_num = Long.parseLong(st, 16); //十六进制数字符串,转换为正的十进制数 
		
		Date startDate = FindByte.longToDate(dec_num);
		return startDate;
	}
	
	/**
	 * returns the string date of the first data segment ,
	 * you can change this string date to any form whatever you want !
	 
	 * change them to the format you desired :
	 * String dateString  = readDateSegmentHead.readDataSegmentHead(fileName) ;
	 *	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
	 *	Date date = simpleDateFormat.parse(dateString) ;
	 * 
	 * @param fileName
	 * @return  String  represents the date of the first data segment
	 * @throws Exception
	 */
     public static Date readDataSegmentHead_MrLiu_Date(File file) throws Exception{
    	
		//用于承装数据段头的字节
    	 
    	byte[] dataSegmentHeadByte = new byte[34] ;
		//打开流
		BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file)) ;
		buffered.skip(Parameters.WenJianTou);
		buffered.read(dataSegmentHeadByte);
		buffered.close();
		byte[] segmentDate = FindByte.searchByteSeq(dataSegmentHeadByte, 8, 17) ;
		String startDate;
		startDate = "20" + segmentDate[0]+"-";
		startDate = startDate + segmentDate[1]+"-";
		startDate = startDate + segmentDate[2]+" ";
		startDate = startDate + segmentDate[3]+":";
		startDate = startDate + segmentDate[4]+":";
		startDate = startDate + segmentDate[5];
		
		Date d = String2Date.str2Date(startDate);
		
		return d;	
     }
}
