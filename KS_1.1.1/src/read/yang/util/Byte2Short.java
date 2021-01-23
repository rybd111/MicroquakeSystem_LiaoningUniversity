package read.yang.util;

/**这个类中的方法只适用于长度为2的字节数组
 * 
 * @author hyena
 *
 */
public class Byte2Short {
    
	
	/**
	 * 
	 * @param low  the short of low 8 bits 
	 * @param high the short of high 8 bits
	 * @return
	 */
	public static short byte2Short(byte low , byte high ){
		
		return  (short) ( high <<8 | low & 0xff)  ;
		
	}
	
	public static short byte2Short(byte[] shortByte){
		if( shortByte.length > 2){
			throw new ArrayIndexOutOfBoundsException() ;
		}
		
		byte low = shortByte[0]  ;
		
		byte high = shortByte[1] ;
				
        return byte2Short(low , high) ;
	}
	
}
