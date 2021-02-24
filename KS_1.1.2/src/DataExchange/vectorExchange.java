package DataExchange;

import java.util.Vector;

import com.h2.constant.Parameters;

/**
 * The vector exchange class using in procedure.
 * @author Yilong Zhang, Hanlin Zhang.
 */
public class vectorExchange {
	
	public Vector<String> beforeVector = new Vector<String>();
	public Vector<String> nowVector = new Vector<String>();
	public Vector<String> afterVector = new Vector<String>();
	
	public vectorExchange() {
		super();
	}
	
	/**Three buffers to swap data.*/
	public void DataSwap(Vector<String> temp){	
		beforeVector=nowVector;
		nowVector=afterVector;
		afterVector=temp;
	}
	
	public Vector<String> getBeforeVector() {
		return beforeVector;
	}
	public void setBeforeVector(Vector<String> beforeVector) {
		this.beforeVector = beforeVector;
	}
	public Vector<String> getNowVector() {
		return nowVector;
	}
	public void setNowVector(Vector<String> nowVector) {
		this.nowVector = nowVector;
	}
	public Vector<String> getAfterVector() {
		return afterVector;
	}
	public void setAfterVector(Vector<String> afterVector) {
		this.afterVector = afterVector;
	}
	
}
