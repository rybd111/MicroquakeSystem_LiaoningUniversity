package read.yang.readFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import controller.ADMINISTRATOR;

/**
 * 保存数据，并根据vector格式former。
 * @author Chengfeng Liu
 */
public class DataElement {
    
 	private short x1=0 ;
 	private short y1=0 ;
 	private short z1=0 ;
 	
 	private short x2=0 ;
 	private short y2=0 ;
 	private short z2=0 ;
 		
 	private String dataCalendar;
 	public DataElement(){}
 	
 	
	public short getX1() {
		return x1;
	}
	public void setX1(short x1) {
		this.x1 = x1;
	}

	public short getY1() {
		return y1;
	}
	public void setY1(short y1) {
		this.y1 = y1;
	}

	public short getZ1() {
		return z1;
	}
	public void setZ1(short z1) {
		this.z1 = z1;
	}
	
	
	public short getX2() {
		return x2;
	}
	public void setX2(short x2) {
		this.x2 = x2;
	}

	public short getY2() {
		return y2;
	}
	public void setY2(short y2) {
		this.y2 = y2;
	}
	
	public short getZ2() {
		return z2;
	}
	public void setZ2(short z2) {
		this.z2 = z2;
	}	
	
	public void setDataCalendar(String calendar){
		this.dataCalendar = calendar ;
	}
	public String getDataCalendar(){
		return dataCalendar ;
	}
	public double getAmplitude(){
		return Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2) + Math.pow(z2, 2));
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		return ( x1 + " " + y1 + " " + z1 + " "
				+ x2 + " " + y2 + " " + z2 + " "
				+ dataCalendar );
	}
	
}
