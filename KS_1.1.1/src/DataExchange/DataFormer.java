/**
 * 
 */
package DataExchange;

import java.awt.image.DataBufferByte;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.data.xy.VectorDataItem;

import controller.ADMINISTRATOR;
import mutiThread.obtainHeadTime;
import read.yang.readFile.DataElement;
import read.yang.readFile.DataTypeConversion;

/**
 * @author Hanlin Zhang
 */
public class DataFormer {
	
	private byte[] dataBytes;
	private int sensorID;
	private int channel;
	private int timeCount;
	private Date date;
	private ADMINISTRATOR manager;
	
	public DataFormer(
			byte[] dataBytes,
			int sensorID,
			int channel,
			int timeCount,
			Date date,
			ADMINISTRATOR manager) {
		this.dataBytes = dataBytes;
		this.sensorID = sensorID;
		this.channel = channel;
		this.timeCount = timeCount;
		this.date = date;
		this.manager = manager;
	}
	
	/**
	 * @description
	 * 注意：dataBytes的字节数（下标），以及通道是哪几个，若123通道则必须放在x1，y1，z1中，456通道放在x2，y2，z2中
	 * @param dataBytes
	 * @return
	 * @author Chengfeng Liu, Hanlin Zhang, Rui Cao.
	 */
	@SuppressWarnings("unused")
	public DataElement getDataElementFromDataBytes() {
		DataElement dataElement = new DataElement();
		
		if(channel==456){
			if(manager.isMrMa[sensorID]==true){
				short x2 =dataBytes[0];
				short y2 =dataBytes[1];
				short z2 =dataBytes[2];
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
			else {
				short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
				short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
				short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
				
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
		}
		if(channel==123){
			short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
			short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
			short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		

			dataElement.setX1(x1);
			dataElement.setY1(y1);
			dataElement.setZ1(z1);
		}
		if(channel==123456){
			short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
			short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
			short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
			short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[6], dataBytes[7]);
			short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[8], dataBytes[9]);
			short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[10], dataBytes[11]);
			
			dataElement.setX1(x1);
			dataElement.setY1(y1);
			dataElement.setZ1(z1);
			
			dataElement.setX2(x2);
			dataElement.setY2(y2);
			dataElement.setZ2(z2);
		}
		return dataElement;
	}
	
	public String formerDate() {
		Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
		calendar.setTime(this.date);
		calendar.add(Calendar.SECOND, timeCount);
		Date startDate1 = calendar.getTime();
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		String date1 = format2.format(startDate1);
		return date1;
	}
}
