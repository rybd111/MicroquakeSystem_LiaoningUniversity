package testKS;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GetLocation {

	
	public static long getLocation() throws IOException{
	 
	 
	 String fileName = "B:/Users/lemo/Documents/Tencent Files/1159639005/FileRecv/gygc_161126173902.HFMED";
	 RandomAccessFile raf = new RandomAccessFile(fileName, "r");
	 
	 
	 //设置指针的位置
	 long point=0;
	 boolean booleanValue = raf.readBoolean();
	 int intValue = raf.readInt();
	 double doubleValue = raf.readDouble();
	 raf.seek(10000);
	 //raf.seek(0);//设置指针的位置为文件的开始部分
	 byte[] bytes = new byte[12];
	 for (int i=0; i<bytes.length; i++){
	 bytes[i] = raf.readByte();//每次读一个字节，并把它赋值给字节bytes[i]
	 String stringValue = new String(bytes);
	
	 raf.skipBytes(1);//指针跳过一个字节
	 int intValue2 = raf.readInt();
	 System.out.print(bytes[i]+" "+intValue2);
	 }
	 point= raf.getFilePointer();
	 raf.close();
	 
	 return point;
	}
}
