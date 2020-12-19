package com.yang.readFile;

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
	//锟斤拷锟斤拷锟斤拷byte锟斤拷锟酵碉拷锟斤拷锟斤拷拼锟接筹拷一锟斤拷short锟斤拷锟斤拷锟斤拷锟斤拷
	public static short joint2BytesToShort(byte lowByte, byte highByte){
		//return  (short) ((short)(highByte <<8) + (short)(lowByte));
		return  (short) ( highByte <<8 | lowByte & 0xff);
	}
	private DataElement getDataElementFromDataBytes(byte[] dataBytes){
			//锟斤拷锟斤拷锟斤拷锟斤拷转锟斤拷
			/*注锟解：锟斤拷锟斤拷锟侥硷拷锟叫很讹拷锟斤拷锟斤拷锟斤拷锟酵碉拷锟斤拷锟捷讹拷锟斤拷锟斤拷锟斤拷锟街节ｏ拷通锟斤拷锟街斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷应锟斤拷锟斤拷锟斤拷锟街斤拷时锟斤拷锟饺讹拷锟斤拷锟斤拷锟斤拷锟街斤拷锟角碉拷位锟街节ｏ拷
			 锟斤拷锟斤拷一锟斤拷锟街斤拷锟角革拷位锟街节ｏ拷锟斤拷锟斤拷锟斤拷锟街斤拷锟斤拷锟叫帮拷顺锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷纸冢锟� byte1 = 0x4e, byte2 = 0x50锟斤拷
			 锟斤拷锟斤拷锟斤拷拼锟接筹拷short锟斤拷锟斤拷时byte1锟角碉拷位锟斤拷锟斤拷byte2锟角革拷位*/
		short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
		short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
		short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
		
		short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[6], dataBytes[7]);
		short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[8], dataBytes[9]);
		short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[10], dataBytes[11]);		
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷元锟斤拷
		DataElement dataElement = new DataElement();//锟斤拷锟斤拷元锟斤拷
		dataElement.setX2(x1);
		dataElement.setY2(y1);
		dataElement.setZ2(z1);
		
		dataElement.setX2(x2);
		dataElement.setY2(y2);
		dataElement.setZ2(z2);
		
		return dataElement;
		
		}

}
