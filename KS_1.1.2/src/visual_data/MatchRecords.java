/**
 * This class is designed to match the records of ten minutes , and matching 
 * results should satisfy the standards of event motivation.
 */
package visual_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Haiyou Yu
 * @version 2.0 
 * @since 2020.12.1 (Version1.0), 2020.12.8 (Version2.0)
 */
public class MatchRecords {

	
	public MatchRecords() {
	}
		
	public static int stationNumber =0;  //matcher函数的输入参数中有几个台站
	public static ArrayList<ArrayList<String>> arrayList = null; //value为符合该时间（+-2s）内的索引记录，即保存所有事件的记录
	public static ArrayList<ArrayList<String>> copy = null;  //保存matcher函数输入参数的副本
	public static int[] indexOfAllStationRecords ; //每个索引段的游标
	public static int[] flag ;       //如果值为1标志该索引段的游标是要加1
    public static int[] lengthOfAllStationRecords; //每个索引段的长度
	public static boolean exit;       //是否完成了匹配
	
	//每一个step就是从这几个索引段中取一条记录然后找出其中日期最小的（即距离现在最远的）那个记录和他对应的台站
	//然后再进行事件评定，如果符合同一事件（激发间隔≤2s且激发个数≥3）则保存这个事件的记录
	public static void step() {
		if(stationNumber<3) {    //因为在step进行循环的过程中，可能有索引段达到末尾了，那么该索引段不再进行比较，所以要再判断一下索引段的个数
			System.out.println("在匹配中发现台站个数小于3，结束匹配");
			exit = true;
			return;             
		}
		long[]  theOldest = new long[stationNumber]; //保存每个索引记录段的一条日期
		for(int i = 0;i<stationNumber;i++) {   //找到所有台站记录中日期 距离现在最远的那个
			//s为一条记录，比如：2020-11-23 07:15:34.93 s D:/data/s 2020-11-23 07-15-34'93.csv 391.5
			String s = copy.get(i).get(indexOfAllStationRecords[i]);
			String[] str = s.split(" ");   
			String time = str[0]+" "+str[1]; //比如：2020-11-23 07:15:34.93
			long longTime = 0;
			//字符串日期转换为long
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = null;
			try {
				date = sf.parse(time);
				longTime = date.getTime();
				theOldest[i] = longTime;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int position = 0;    //保存最远日期在哪一个索引段
		long timeBaseLine = 0;     //将输入参数中所有台站记录的日期 距离现在最远的那个日期的毫秒值作为基准
		timeBaseLine = theOldest[0];  //给一个初值用于比较
		for(int j=0;j<theOldest.length;j++) {   //找出最远日期并将其作为基准
			if(timeBaseLine>theOldest[j]) {
				timeBaseLine = theOldest[j];
				position = j;
			}
		}
		//System.out.println("the oldest time of records:"+timeBaseLine+" and the record segement index is :"+position);
		
		//以最远的日期作为基准，将其他台站的记录数据与该基准比较
		long timeDifference = 0; //时间差
		long range = 2*1000; //即2秒 = 2000ms
		int count = 0 ;    //用于记录在时间差为2秒之内的激发台站个数
		ArrayList<String> save = new ArrayList<String>();  //保存属于同一个事件的记录
		for(int i=0;i<stationNumber;i++) {
			timeDifference = theOldest[i]-timeBaseLine;
			if(timeDifference<=range) { //若存在≥3个时间差≤2s的索引，则将其存入数组，
				count ++ ;
				save.add(copy.get(i).get(indexOfAllStationRecords[i]));
				flag[i] = 1;      //说明这个日期和基准差值小于2s
			}
		}

		if(count<3) {
			save = null;      //如果激发个数不够三个，那么之前保存的记录就不要了
			indexOfAllStationRecords[position] +=1;  //如果激发个数不够三个，那么就将基准所在的索引段的游标加一
			for(int m=0;m<flag.length;m++) {    //然后将所有索引段游标的加1标志重置为0
				flag[m] = 0;
			}
		}else {
			arrayList.add(save);  //如果激发个数够三个，那么之前保存的记录就添加到ArrayList里
			for(int n=0;n<flag.length;n++) {   //如果激发个数够三个，那么将所有加1标志为1的索引段游标都加上1
				if(flag[n]==1) {
					indexOfAllStationRecords[n]+=1;
				}
			}
		}
	}
	
	//判断每个索引段的游标值是否已经达到该索引段的末尾
	public static void end() {
		Vector<Integer> v = new Vector<>();
		for(int i=0;i<stationNumber;i++) {
			if(indexOfAllStationRecords[i]==(lengthOfAllStationRecords[i]-1)) { //说明有个一索引段已经达到末尾了
				v.add(i);
			}
		}
		
		//这里需要将v按照从大到小的顺序排序
		//因为，如果v中的数据是3和5，即对应的索引段是[][][][X][][]和[][][][][][X]
		//如果我们将3对应的索引段删掉，那么就变成[][][][][X],
		//如果我们再将5对应的索引段删掉，那么就会发现越界了，因为上面的索引下标最大为4
		//所以为了解决这个问题，我们可以将v进行从大到小的顺序排序，这样就可以解决这个问题
		Collections.sort(v,Collections.reverseOrder());
		
		//v中存储的都是索引段到达末尾的，遍历v
		for(int k:v) {
			System.out.println("有一个索引段已经匹配到末尾，移除该索引段");
			//索引段到达末尾，那么就需要将该索引段剔除，让其他索引段继续比较
			//首先将其从副本中删除，会有三种情况
			//1、如果该索引段在数组 [][][][X][][] 中间，那么需要将该索引段后面的数据往前移，然后stationNumber减一
			boolean sub = false;   //三个if语句中为了避免出现stationNumber减了一次 却随后又减1的情况，所以添加了一个sub控制变量
			if(k>0 && k<(stationNumber-1) && !sub) {
				for(int j=k;j<(stationNumber-1);j++) {
					copy.get(j).clear();
					for(String s:copy.get(j+1)) {
						copy.get(j).add(s);
					}
				}
				stationNumber -= 1;
				sub = true;
			}
			//2、如果该索引段在数组 [X][][][][][] 开头，那么重新new一个数组保存后面的内容，在重新赋值给copy，然后stationNumber减一
			if(k==0 && !sub) {
				ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
				for(int m=1;m<stationNumber;m++) {
					temp.add(copy.get(m));
				}
				copy = temp;
				stationNumber -= 1;
				sub = true;
			}
			//3、如果该索引段在数组 [][][][][][X] 最后，那么只需要将stationNumber值减一
			if(k==(stationNumber-1) && !sub) {
				stationNumber -= 1;
				sub = true;
			}
			//然后更新每个索引段的游标,更新每个索引段的游标加1标志,更新每个索引段的长度
			int[] index = new int[stationNumber];
			int[] f = new int[stationNumber];
			int[] length = new int[stationNumber];
			System.out.println("台站数为:"+stationNumber);
			int j=-1;
			for(int n=0;n<indexOfAllStationRecords.length;n++) {
				if(n == k) {         //k代表的索引段已经不需要了
					continue;
				}else {
					j++;
				}
				index[j] = indexOfAllStationRecords[n];
				f[j] = flag[n];
				length[j] = lengthOfAllStationRecords[n];
			}
			indexOfAllStationRecords = index;
			flag = f;
			lengthOfAllStationRecords = length;
		}
	}
	
    
	public static ArrayList<ArrayList<String>> matcher(ArrayList<ArrayList<String>> temp){
		exit = false;
		stationNumber = temp.size(); //输入参数中有几个台站
		if(stationNumber>=3) {            //有三个不同台站的记录时，才能做记录匹配
			System.out.println("初始台站个数大于3，开始匹配");
			copy = new ArrayList<>();
			arrayList = new ArrayList<>();
			for(int i=0;i<stationNumber;i++) {
				copy.add(temp.get(i));
			}
		}else {
			System.out.println("初始台站个数小于3，结束匹配");
			return arrayList;
		}
		
		//先初始化每个索引段的游标和标志
		indexOfAllStationRecords = new int[stationNumber]; //每个索引段的游标
		flag = new int[stationNumber];        //如果值为1标志该索引段的游标是要加1
		for(int i=0;i<stationNumber;i++) {
			indexOfAllStationRecords[i] = 0;
			flag[i] = 0;
		}
		
		//获取每个索引段的长度
		lengthOfAllStationRecords = new int[stationNumber];
		for(int i=0;i<stationNumber;i++) {
			lengthOfAllStationRecords[i] = copy.get(i).size();
		}
		
		while(!exit) {
			step();  //开始step
			end();   //判断索引段是否到达末尾
		}
		
		System.out.println("事件个数："+arrayList.size());
		for(int i=0;i<arrayList.size();i++) {
			ArrayList<String> ss = arrayList.get(i);
			for(String sss:ss) {
				System.out.println("事件"+i+" "+sss);
			}
		}
		return arrayList;
	}
	
	//这是测试代码
//	public static void main(String[] args) {
//		ArrayList<ArrayList<String>> list ;
//		list = ReadCSVFile.getLists();
//		System.out.println("台站个数"+list.size());
//		ArrayList<ArrayList<String>> s = matcher(list);
//		System.out.println("事件个数"+s.size());
//		for(int i=0;i<s.size();i++) {
//			ArrayList<String> ss = s.get(i);
//			for(String sss:ss) {
//				System.out.println("事件"+i+" "+sss);
//			}
//		}
//	}
	//测试代码结束
}
