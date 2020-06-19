package mutiThread;

import java.util.ArrayList;
import java.util.List;

import com.h2.constant.Parameters;

import cn.hutool.core.math.MathUtil;

/**
 * rearrange the sequence of the all disk, but there is an other problem we need consider.
 * when some sensors are break down absolutely, we need reset the MainThread.fileStr array.
 * @author Hanlin Zhang.
 */
public class ReconnectToRemoteDisk {
	
	public ArrayList<String[]> arrayList = new ArrayList<>();
	
	public ReconnectToRemoteDisk(int minNum,String [] fileStr) {
		
		for (int i = minNum; i <= fileStr.length; i++) {
            List<String[]> list = MathUtil.combinationSelect(fileStr, i);
            this.arrayList.addAll(list);
        }
//        for(int j=0;j<this.arrayList.size();j++) {
//        	for(int k=0;k<this.arrayList.get(j).length;k++) {
//        		System.out.print(this.arrayList.get(j)[k]+" ");
//        	}
//        	System.out.println();
//        }
//        System.out.println(arrayList.size());//print the rows of the arrayList.
	}
	
	public String[] rearrange(int discSymbol){
		String [] fileStr = new String[Parameters.SensorNum];
		for(int i=0;i<this.arrayList.get(discSymbol).length;i++) {
			fileStr[i] = this.arrayList.get(discSymbol)[i];
		}
		if(this.arrayList.get(discSymbol).length<Parameters.SensorNum) {
			for(int i=this.arrayList.get(discSymbol).length;i<Parameters.SensorNum;i++) {
				fileStr[i] = this.arrayList.get(discSymbol)[this.arrayList.get(discSymbol).length-1];
			}
		}
		return fileStr;//Returns the combination of the corresponding sequence number.
	}
}