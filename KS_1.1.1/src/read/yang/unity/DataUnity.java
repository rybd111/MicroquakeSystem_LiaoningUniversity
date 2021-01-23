package read.yang.unity;

public class DataUnity {
    
	/** low x */
 	private short x1 ;
	
    /** low y */
 	private short y1 ;
 	
 	/** low z  */ 
 	private short z1 ;
 	
 	/** high x */
 	private short x2 ;
 	
 	/** high y */
 	private short y2 ;
 	
 	/** high z */
 	private short z2 ;
 	
 	/** 电位  */
 	private short volt ;
 	
 	/** 日期  */
 	private String date ;
 	
 	
 	
 	public DataUnity(){
 		//do something
 	}

	public DataUnity(short x1, short y1, short z1, short x2, short y2, short z2 , short volt , String date) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.volt = volt ;
		this.date = date ;
		
	}

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
	
	public void setVolt(short volt){
		this.volt = volt ;
	}
	
	public short getVolt(){
		return volt ;
	}
	
	public void setDate(String date){
		this.date = date ;
	}
	
	public String getDate(){
		return this.date ;
	}
	

	@Override
	public String toString() {
		return ( x1 + " " + y1 + " " + z1 + " "	+ x2 + " " + y2 + " " + z2 + " " + date);
	}

	
 	
 	
 	
	
	
	
}
