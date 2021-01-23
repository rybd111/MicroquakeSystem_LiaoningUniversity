package read.yang.util;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author this class is searching specific byte from given byte array range
 *         from start to end
 * 
 * 
 */
public class FindByte {

	/**
	 * commets : this method requires the length of dest byte array declared
	 * first and then as a parameter in this method as follows : byte[] dest =
	 * new byte[length] ; searchArraySeq(dest , source , 4) ;
	 * 
	 * @param dest
	 *            the destination byte array to store the bytes founded from
	 *            source
	 * @param source
	 *            the source byte array to find
	 * @param start
	 *            the start position to find byte in source byte
	 * @param end
	 *            the end position to find byte in source byte
	 */
	public static void searchByteSeq(byte[] dest, byte[] source, int start) {

		/**
		 * no need to ensure the length of the dest equals (end - start + 1)
		 * because the length of dest equals (end - start + 1), the parameter
		 * end no need
		 */
		for (int i = 0; i < dest.length; i++) {
			dest[i] = source[i + start];
		}
	}
   
	/**
	 * returns an array byte that stores the bytes founded in source array
	 * @param source the source byte array byte array to find 
	 * @param start  the start position to find byte in source byte
	 * @param end    the end position to find byte in source byte
	 * @return byte[] a byte array that stores the bytes founded in source array range from start to end 
	 */
	public static byte[] searchByteSeq(byte[] source, int start, int end) {
        // a temp byte array that stores the byte founded in source array      
		byte[] tempByte = new byte[end - start + 1] ;

		// assigned temp byte
		for(int i = 0 ; i < tempByte.length ; i ++){
			tempByte[i] = source[i + start] ;
		}
		return tempByte ;
	}
	
	//the follow codes are the read method of Mr Ma.
	
	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param 字节数组
	 * @return 十六进制字符串
	 */
	public static String bytesToHexString(byte[] src){       
	   StringBuilder stringBuilder = new StringBuilder();       
	   if (src == null || src.length <= 0) {       
	   	return null;       
	   }       
	   for (int i = 0; i < src.length; i++) {       
	   	int v = src[i] & 0xFF;       
	   	String hv = Integer.toHexString(v);       
	       if (hv.length() < 2) {       
	       	stringBuilder.append(0);       
	       }       
	       stringBuilder.append(hv);       
	   }       
	   return stringBuilder.toString();       
	}
	 
	final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/** 将一个字节转化成十六进制形式的字符串 */
	static String byteToHexString(byte b){
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

   /* 
    * 将转化为long型的时间戳转换为时间
    */
   public static Date longToDate(long lo){
//	   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = new Date(lo*1000L); 
//	   return sd.format(date);
	   return date;
   }

}
