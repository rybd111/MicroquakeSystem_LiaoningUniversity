package read.yang.readFile;

/**
 * this function used to save one data. 
 *@author Chengfeng Liu.
 */
public class DataTypeConversion {
	public static void main(String[] arg){
		byte a = 1;
		byte b = 1;
		short ab = joint2BytesToShort(a, b);
		System.out.println(ab);
	}

	public static short joint2BytesToShort(byte lowByte, byte highByte){
		//return  (short) ((short)(highByte <<8) + (short)(lowByte));
		return  (short) ( highByte <<8 | lowByte & 0xff);
	}
	
	@SuppressWarnings("unused")
	private DataElement getDataElementFromDataBytes(byte[] dataBytes){

		short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
		short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
		short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
		
		short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[6], dataBytes[7]);
		short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[8], dataBytes[9]);
		short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[10], dataBytes[11]);		

		DataElement dataElement = new DataElement();
		dataElement.setX2(x1);
		dataElement.setY2(y1);
		dataElement.setZ2(z1);
		
		dataElement.setX2(x2);
		dataElement.setY2(y2);
		dataElement.setZ2(z2);
		
		return dataElement;
		
		}

}
