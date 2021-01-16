package utils;

/**
 * 这个方式适用于转换4个字节的字节型数组
 * 
 * @author hyena
 *
 */
public class Byte2Int {
	
	public static int byte2Int(byte[] temp){
		
		int a = ( temp[3] & 0xff) <<24 ;
		
		int b = (temp[2] & 0xff) << 16 ;
		
		int c = (temp[1] & 0xff) << 8 ;
		
		int d = (temp[0] & 0xff) ;
		
		return a + b + c + d ;
 		
		
	}

}
