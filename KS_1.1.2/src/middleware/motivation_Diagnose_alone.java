/**
 * 
 */
package middleware;

import java.text.ParseException;
import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.tool.CrestorTrough;
import com.h2.tool.relativeStatus;

import mutiThread.MainThread;
import utils.SelectChannel;
import utils.printRunningParameters;

/**
 * @author Hanlin Zhang
 */
public class motivation_Diagnose_alone {
	//determine which chunnel we select, true is the 123 chunnel, or 456 chunnel.
	private boolean channel = false;
	//motivation flag.
	private boolean isMoti = false;
	//sensor motivation position.
	private int motiPos = 0;
	//relative motivation time.
	private double relativeMSTime = 0.0;
	//setAbsoluteTime as milli sec.
	private String absoluteMSTime = "";
	//setAbsoluteTime as sec.
	private String absoluteSTime = "";
	//sensor data.
	private Vector<String> data;
	//startPos。
	private int range = 0;
	//sensorID
	private int th = -1;
	//CrestorTrough temcre
	private CrestorTrough temcre;
	
	public boolean getIsMoti() {
		return isMoti;
	}
	public int getMotiPos() {
		return motiPos;
	}
	public double getRelativeMSTime() {
		return relativeMSTime;
	}
	public String getAbsoluteMSTime() {
		return absoluteMSTime;
	}
	public String getAbsoluteSTime() {
		return absoluteSTime;
	}
	public CrestorTrough getCrestorThrough() {
		return temcre;
	}
	
	/**
	 * 单独调用。
	 * @param data
	 */
	public motivation_Diagnose_alone(Vector<String> data) {
		this.data = data;
	}
	
	/**
	 * 程序内调用。
	 * @param data
	 * @param range
	 * @param th
	 * @throws ParseException
	 */
	public motivation_Diagnose_alone(Vector<String> data, int range, int th) throws ParseException {
		this.data = data;
		this.range = range;
		this.th = th;
	}
	
	/**
	 * 单独判断一个Vector容器内数据是否激发。
	 * 在这10s内激发的传感器,并设置激发传感器的标识和激发时间
	 * 一旦激发就不再继续判断后续的数据，即弃之，采用过早放弃策略。
	 * date并不是整10s，而是加上了前面的一个refineRange的范围数据。
	 * @return
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月18日上午10:32:51
	 */
	public boolean MotivationDiag() throws ParseException {
		int lineSeries = 0;
		boolean flag=false;
		//因为noise计算需要减去afterRange范围，因此，我们将i从afterRange处开始，防止下标越界。
		for(int startPos=Parameters.afterRange; startPos<this.data.size()-Parameters.N-Parameters.refineRange; startPos+=Parameters.INTERVAL)//滑动窗口跳数可以任意设置，但小于50时效率极低，i为窗口的第一条数据开始位置，到最后一个窗口
		{
			//ensure the early abandon strategy.
			if(!this.isMoti)
			{
				lineSeries=getToken(startPos);//激发位置，滑动距离为Parameters.INTERVAL个点，并判断使用哪三个通道
				//if there has a sensor has motivated, it perhaps the last windows.
				if(lineSeries!=0)
				{
					//进入精细判断，即加上各项阈值。
					if(Parameters.motivationDiagnose == 1) {
						flag=getAverage(this.data, lineSeries);
						if(flag==true) {
							//set the flag signal.
							this.isMoti = true;
							//there set the position(series) in now vector, it means the relative position in 10s vector.
							this.motiPos = lineSeries-this.range;
							//The unit is in milliseconds, the frequency of sensor is calculated in 5000Hz.
							this.relativeMSTime = Double.valueOf(lineSeries)/Double.valueOf((Parameters.FREQUENCY+200));
							//Set the absolute time in GPS time as String.
							this.absoluteMSTime = new relativeStatus().PArrivalAbsoluteTime(this.data, this.relativeMSTime);
							//we obtain the time of the motivation time of the now vector. we abandoned this item.
							this.absoluteSTime = this.data.get(lineSeries).split(" ")[6];
						}
					}
					else {
						//set the flag signal.
						this.isMoti = true;
						//there set the position(series) in now vector, it means the relative position in 10s vector.
						this.motiPos = lineSeries-this.range;
						//The unit is in milliseconds, the frequency of sensor is calculated in 5000Hz.
						this.relativeMSTime = Double.valueOf(lineSeries)/Double.valueOf((Parameters.FREQUENCY+200));
						//Set the absolute time in GPS time as String.
						this.absoluteMSTime = new relativeStatus().PArrivalAbsoluteTime(this.data, this.relativeMSTime);
						//we obtain the time of the motivation time of the now vector. we abandoned this item.
						this.absoluteSTime = this.data.get(lineSeries).split(" ")[6];
					}
				}
				//if the 456 chunnel is overflow, then we select the 123 chunnel.
				if(this.channel){
					this.temcre=new CrestorTrough(Double.parseDouble(data.get(lineSeries).split(" ")[0]),
														   Double.parseDouble(data.get(lineSeries).split(" ")[1]),
							                               Double.parseDouble(data.get(lineSeries).split(" ")[2]));
				}else{
					this.temcre=new CrestorTrough(Double.parseDouble(data.get(lineSeries).split(" ")[3]),
							                               Double.parseDouble(data.get(lineSeries).split(" ")[4]),
                                                           Double.parseDouble(data.get(lineSeries).split(" ")[5]));
				}
			}
		}
		
		this.channel = false;
		return flag;
	}
	
	/**
	 * 确定一个传感器在60ms内是否被激发 思想：将一个时窗内的数据(600条数据)全部读取到vector中，
	 * 并在读取的时候判断是否有数据超出阈值，跟据读取的结果给channel赋值。
	 * @param sensor 传感器
	 * @param number 每隔一定点个数就进行一次长短时窗的判断
	 * @return 激发的位置
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	private int getToken(int lineNumber)
	{
		//返回是0，则说明没有激发。
		int line=0;
		
		int inte = -1;//the average value.
		String s;
		Vector<String> container = new Vector<String>();//the current data in window. 
		int count = 0;//loop variable.
		
		//some variable in this function.
		long sumLong = 0;
		long sumShort = 0;
		double aveLong = 0;
		double aveShort = 0;
		container.clear();
		// 读取本时窗的数据，拆分data容器的内容，保存到container容器中，并同时判断每个通道是否超出最大阈值，若超出，则启用另3个通道
		while (count < Parameters.N) {//the all data in total window among short and long.
			s = this.data.get(lineNumber + count);
			String[] str = s.split(" ");
			
			if(SelectChannel.testValue(str[5])) {
				channel = true;
			}
			container.add(s);
			count++;
		}
		//get the average value of the long window.
		if (channel){//read the first three rows.
			for (int i = 0; i < Parameters.N1; i++){
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[2]));
				sumLong += inte;
			}
		} 
		else{
			for (int i = 0; i < Parameters.N1; i++){
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[5]));
				sumLong += inte;
			}
		}
		aveLong = (double) sumLong / Parameters.N1;
		//get the average value of the short window.
		if (channel){//as the same of the before situation.
			for (int i = Parameters.N1; i < Parameters.N; i++){//长时窗最后一个点到短时窗最后一个点
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[2]));
				sumShort += inte;
			}
		} 
		else {
			for (int i = Parameters.N1; i < Parameters.N; i++){//长时窗最后一个点到短时窗最后一个点
				String[] str = container.get(i).split(" ");
				inte = Math.abs(Integer.parseInt(str[5]));
				sumShort += inte;
			}
		}
		aveShort = (double) sumShort / Parameters.N2;
		if(Parameters.Adjust==false){
		//短长视窗比值大于2.5，判定为激发 
			if((aveShort / aveLong) >= Parameters.ShortCompareLong){
				if(channel){
					line=lineNumber+Parameters.N1;
					return line;
				}
				else{
					line=lineNumber+Parameters.N-Parameters.N2;
					return line;
				}
			}
			else return line;
		}
		else{
			if((aveShort / aveLong) >= Parameters.ShortCompareLongAdjust){
				if(channel){
					line=lineNumber+Parameters.N1;
					return line;
				}
				else{
					line=lineNumber+Parameters.N-Parameters.N2;
					return line;
				}
			}
			else return line;
		}
	}
	
	/**
	 * experiment function used to calculate the area of 500 points after motivation position
	   *  此时传进的参数为加上refineRange的数据，总条数为1.2秒，+ 10秒，共计11.2秒的数据。
	 *  lineseries为两个长短时窗平均值比值大于阈值的位置，此时，先判断后面500个点的激发情况，如果达到触发阈值，再判断后面refineRange内的数据是否触发阈值
	   *  若后面refineRange也触发阈值，我们将判断为大能量事件，否则判断为小能量事件，若afterRange不触发，则不是一个有效事件。
	 * @param lineSeries  the absolutely position in now vector. 
	 * @author Hanlin Zhang.
	 */
	@SuppressWarnings("unused")
	private boolean getAverage(Vector<String> data, int lineSeries) {
		double maxA = 0.0;
		double sumA = 0.0;
		double sumB = 0.0;
		double average = 0.0;
		double average_before=0.0;
		
		//计算后面范围和前面范围内的平均值是否满足要求，前面均值保证了前面数据没有较大波动，后期可以改成其他指标。
		for(int i=0;i<Parameters.afterRange;i++) {//the scope of the diagnosing.
			average+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
		}
		
		average = average/Parameters.afterRange;
		//小范围达到最低激发条件，开始判断大范围是否达到最低激发条件。
		if(average>=Parameters.afterRange_ThresholdMin) {
			for(int i=0;i<Parameters.refineRange;i++) {
				sumA+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
			}
			sumA = sumA/Parameters.refineRange;
			//大范围达到最低激发条件，开始判断是否达到大、小能量事件的激发条件。
			if(sumA>=Parameters.refineRange_ThresholdMin) {
				//在afterRange到refineRange区间求绝对值均值，主要判断在afterRange小范围内到大范围期间，波形是否有大幅波动，以此判断是否大能量事件。
				for(int i=Parameters.afterRange;i>0;i--) {
					sumB += Math.abs(Integer.parseInt(data.get(Parameters.refineRange-i+lineSeries).split(" ")[5]));
				}
				sumB = sumB/Parameters.afterRange;
				//达到大能量事件阈值约束？依靠大范围绝对值均值判断大小能量事件。
				if(sumB>Parameters.refineRange_ThresholdMax) {
					//是，则继续判断refineRange内绝对值均值是否达到大能量约束？
					if(sumA>=Parameters.refineRange_ThresholdMax) {
						//use large model diagnosing.使用大能量约束判断。average是小范围绝对值均值，使用小范围绝对值均值判断。
						if(average>=Parameters.afterRange_ThresholdMax) {
							//sumB由（afterRange到refineRange区间求绝对值均值）变为激发位置lineSeries前的小范围绝对值均值，我们视其为底噪（背景噪声）。
							noiseMinus(data, lineSeries, 4);
							//减去底噪后，若还能达到大范围约束，则认为激发。
							if(sumA>=Parameters.refineRange_ThresholdMax) {
								printStatus(sumA, sumB, "large");
								return true;
							}
							//减去底噪后，未达标。
							return false;
						}
						//大能量事件条件达成，但小范围绝对值均值不满足约束，不是大能量事件，直接返回未激发。
						return false;
					}
					//refineRange内绝对值均值未达到大能量约束
					else {
						return false;
					}
				}
				//小能量事件阈值约束，按照小能量事件约束判断。
				else {
					//minus the previous noise.
					sumA = noiseMinus(data, lineSeries, 2);
					
					//减去2倍底噪后的大范围绝对值均值是否达到小能量事件约束？达到则返回真。
					if(sumA>=Parameters.refineRange_ThresholdMin) {
						printStatus(sumA, sumB, "small");
						return true;
					}
					//减去底噪后，未达标。
					return false;
				}
			}
			//大范围未达到小能量事件阈值
			else {
				return false;
			}
		}
		//小范围未达到小能量事件阈值
		else {
			return false;
		}
	}
	
	/**
	 * 减去底噪为了防止提早触发。
	 * @param data
	 * @param lineSeries
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月18日上午8:41:57
	 */
	private double noiseMinus(Vector<String> data, int lineSeries, int magnification) {
		//sumB由（afterRange到refineRange区间求绝对值均值）变为底噪。
		double sumA=0.0;
		double sumB=0.0;
		//get the noise value.
		for(int i=-1*Parameters.afterRange;i<0;i++) {
			sumB+=Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]));
		}
		sumB=sumB/Parameters.afterRange;
		//minus the noise value of each point in the refine range.sumA由refineRange的绝对值均值变为减去2倍底噪后的refineRange绝对值均值。
		sumA=0.0;
		for(int i=0;i<Parameters.refineRange;i++) {
			sumA += Math.abs(Integer.parseInt(data.get(i+lineSeries).split(" ")[5]))-magnification*Math.abs(sumB);
		}
		sumA = sumA/Parameters.refineRange;
		
		return sumA;
	}
	
	private void printStatus(double sumA, double sumB, String kind) {
		String refineRange = printRunningParameters.formToChar(String.valueOf(Parameters.refineRange));
		String sumAF = printRunningParameters.formToChar(String.valueOf(sumA));
		String sumBF = printRunningParameters.formToChar(String.valueOf(sumB));
		
		if(kind.equals("large")) {
			if(this.th == -1) {
				System.out.println("large-"+"当前传感器在"+Parameters.refineRange+"范围内的平均振幅为"+sumA+"noise"+sumB);
			}
			else {
				String fileStr = printRunningParameters.formToChar(MainThread.fileStr[th]);
				String fileParent = printRunningParameters.formToChar(MainThread.fileParentPackage[th]);
				
				if(Parameters.offline==true) {
					System.out.println("large-"+fileParent+"在"+refineRange+"范围内的平均振幅为"+sumAF+"noise"+sumBF);
				}
				else {
					System.out.println("large-"+fileStr+"在"+refineRange+"范围内的平均振幅为"+sumAF+"noise"+sumBF);
				}
			}
		}
		else {
			if(this.th == -1) {
				System.out.println("small-"+"当前传感器在"+Parameters.refineRange+"范围内的平均振幅为"+sumA+"noise"+sumB);
			}
			else {
				String fileStr = printRunningParameters.formToChar(MainThread.fileStr[th]);
				String fileParent = printRunningParameters.formToChar(MainThread.fileParentPackage[th]);
				
				if(Parameters.offline==true) {
					System.out.println("small-"+fileParent+"在"+refineRange+"范围内的平均振幅为"+sumAF+"noise"+sumBF);
				}
				else {
					System.out.println("small-"+fileStr+"在"+refineRange+"范围内的平均振幅为"+sumAF+"noise"+sumBF);
				}
			}
		}
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月18日上午10:02:11
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
