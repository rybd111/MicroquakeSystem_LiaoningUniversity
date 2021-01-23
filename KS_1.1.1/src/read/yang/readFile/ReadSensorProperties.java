package read.yang.readFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import read.yang.unity.SensorProperties;
import read.yang.util.Byte2Int;
import read.yang.util.Byte2Short;
import read.yang.util.Byte2String;
import read.yang.util.FindByte;

public class ReadSensorProperties {

	/**
	 * this method read channel properties , but watch out , there should be 7
	 * channels and 1 GPS channel , now the fact is that there only 6 channels
	 * ahead and 1 GPS channel without the 7th channel.
	 * 
	 * @param fileName
	 *            the file name
	 * @return sensor array , sensor[0] ~ sensor[5] represents the sensor
	 *         properties respectively from sensor 1 , to sensor 6 , and
	 *         sensor[6] represents the GPS channel
	 * 
	 * @throws IOException
	 * @author Xingdong Yang.
	 */
	public SensorProperties[] readSensorProperties(String fileName)
			throws IOException {

		/**
		 * 声明7个Sensorproperties指针，这时候并没有为之分配空间 用于将下面求出的个字段封装起来
		 */
		SensorProperties[] sensor = new SensorProperties[7];

		// 为上面的每个引用分配空间
		for (int i = 0; i < 7; i++) {
			sensor[i] = new SensorProperties();
		}

		// 存放头文件字节数组
		byte[] fileHeadByte = new byte[186];

		// 存放通道信息
		byte[][] sensorArray = new byte[8][14];

		File file = new File(fileName);

		BufferedInputStream buffered = new BufferedInputStream(
				new FileInputStream(file));

		// 将读到的头文件放入到字符数组缓冲区中
		buffered.read(fileHeadByte);

		// 将读到的通道信息放到二维的数组中
		for (int i = 0; i < 7; i++) {
			buffered.read(sensorArray[i]);

			// 获取字节序列
			byte[] chNoByte = FindByte.searchByteSeq(sensorArray[i], 0, 1);
			byte[] chNameByte = FindByte.searchByteSeq(sensorArray[i], 2, 5);
			byte[] chUnitByte = FindByte.searchByteSeq(sensorArray[i], 6, 9);
			byte[] chCaliByte = FindByte.searchByteSeq(sensorArray[i], 10, 13);
			byte[] tempFloat ={chCaliByte[3] ,chCaliByte[2] ,chCaliByte[1] ,chCaliByte[0]} ; 
			    
			// 解析字符序列
			short chNo = Byte2Short.byte2Short(chNoByte);
			String chName = Byte2String.byte2String(chNameByte);
			String chUnit = Byte2String.byte2String(chUnitByte);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(tempFloat));
		    float fchCali = dis.readFloat() ;
		    dis.close();

			sensor[i].setChCali(fchCali);
			sensor[i].setChName(chName);
			sensor[i].setChNo(chNo);
			sensor[i].setChUnit(chUnit);
		}

		buffered.close();

		return sensor;

	}

	public SensorProperties[] readSensorProperties(File file) throws Exception {

		SensorProperties[] sensor = new SensorProperties[7];

		// 为上面的每个引用分配空间
		for (int i = 0; i < 7; i++) {
			sensor[i] = new SensorProperties();
		}

		// 存放头文件字节数组
		byte[] fileHeadByte = new byte[186];

		// 存放通道信息
		byte[][] sensorArray = new byte[7][14];

		BufferedInputStream buffered = new BufferedInputStream(
				new FileInputStream(file));

		// 将读到的头文件放入到字符数组缓冲区中
		buffered.read(fileHeadByte);

		// 将读到的通道信息放到二维的数组中
		for (int i = 0; i < 7; i++) {
			buffered.read(sensorArray[i]);

			// 获取字节序列
			byte[] chNoByte = FindByte.searchByteSeq(sensorArray[i], 0, 1);
			byte[] chNameByte = FindByte.searchByteSeq(sensorArray[i], 2, 5);
			byte[] chUnitByte = FindByte.searchByteSeq(sensorArray[i], 6, 9);
			byte[] chCaliByte = FindByte.searchByteSeq(sensorArray[i], 10, 13);

			// 解析字符序列
			short chNo = Byte2Short.byte2Short(chNoByte);
			String chName = Byte2String.byte2String(chNameByte);
			String chUnit = Byte2String.byte2String(chUnitByte);
			byte[] tempFloat ={chCaliByte[3] ,chCaliByte[2] ,chCaliByte[1] ,chCaliByte[0]} ; 
		    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(tempFloat));
		    float f = dis.readFloat() ;
		    dis.close();

			sensor[i].setChCali(f);
			sensor[i].setChName(chName);
			sensor[i].setChNo(chNo);
			sensor[i].setChUnit(chUnit);
		}

		buffered.close();

		return sensor;

	}

}
