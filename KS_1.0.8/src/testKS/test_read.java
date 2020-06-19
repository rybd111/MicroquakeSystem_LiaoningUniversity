package testKS;

import java.io.File;

import com.yang.readFile.FindNewFile;
import com.yang.readFile.ReadData;
import com.yang.unity.DataUnity;

public class test_read {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String f = "z:/";
		//DataUnity unity = new DataUnity();
//		ReadData readData = new ReadData(f,0);
		int n = 1;
		while(true){
			n++;
//			readData.getData(f, 0);
			if(n==60){
				System.out.println("--"+ReadData.dataUnity.getDate());
				n=1;
			}
		}
	}

}
