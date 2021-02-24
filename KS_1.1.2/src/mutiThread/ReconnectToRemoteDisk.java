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
	
	private ArrayList<String[]> arrayList = new ArrayList<>();
	/** 重新排序后的所有情况的个数
	 * 排在前面的是情况较少的根据定义的最少排列数定义，排在后面的是对全盘符排序。
	 * */
	public int orderNum = 0;
	
	/**
	 * 全排列所有路径，通过设置最小排列值，获得全排列的结果
	 * 目的是防止远程测点断线或网络变差时，及时进行断线重连，但重连不能还使用原来的盘符（测点）
	 * 因此设置此函数。
	 * @param minNum 最少排列数，截止到minNum个排列为止。
	 * 比如，x、y、z、p、o、q 6个盘符，当设置minNum为3时，仅排列到3个不同的盘符即停止。
	 * @param fileStr 所给的路径，格式为w:/，返回的排序路径与输入的路径相同。
	 * @param fileStr
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月29日下午10:33:46
	 */
	public ReconnectToRemoteDisk(int minNum, String [] fileStr) {
		for (int i = minNum; i <= fileStr.length; i++) {
            List<String[]> list = MathUtil.combinationSelect(fileStr, i);
            this.arrayList.addAll(list);
            this.orderNum = this.arrayList.size();
        }
	}
	
	/**
	 * 该函数必须在初始化后调用，目的是重新分配盘符，按照不同的顺序，以尝试不同盘符的组合
	 * 避免上次盘符断线后继续使用，也起到对硬件的重置作用。
	 * @param discSymbol　可能性情况的序号，变化该值是选择不同的序号。
	 * @return　新的盘符顺序。
	 * @author Hanlin Zhang.
	 * @date revision 2021年1月29日下午10:33:46
	 */
	public String[] rearrange(int discSymbol){
		String [] fileStr = new String[this.arrayList.get(discSymbol).length];
		for(int i=0;i<this.arrayList.get(discSymbol).length;i++) {
			fileStr[i] = this.arrayList.get(discSymbol)[i];
		}
		//将他们的维度置为一致。
		if(this.arrayList.get(discSymbol).length<Parameters.SensorNum) {
			Parameters.SensorNum = this.arrayList.get(discSymbol).length;
		}
		//当重置盘符时，需要将Parameters.SensorNum置为初始化的值，否则会造成盘符与传感器数量不一致的情况，导致程序出错。
		if(discSymbol==orderNum-1) {
			Parameters.SensorNum = this.arrayList.get(discSymbol).length;
		}
		//Returns the combination of the corresponding sequence number.
		return fileStr;
	}
}