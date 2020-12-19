package bean;

import java.util.Vector;

public class DataRec {

	public Vector<String> beforeVector;
	public Vector<String> nowVector;
	public Vector<String> afterVector;

	public DataRec(Vector<String> beforeVector, Vector<String> nowVector,
			Vector<String> afterVector) {
		super();
		this.beforeVector = beforeVector;
		this.nowVector = nowVector;
		this.afterVector = afterVector;
	}
	
	//三个缓冲区，用于交换数据
	public void DataSwap(Vector<String> temp ){	
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
