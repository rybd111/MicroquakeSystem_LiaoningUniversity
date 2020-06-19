package com.h2.tool;

import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
/**
 *  
 * @author 张翰林（HanLin Zhang）
 * @data 传进来的时任意长度的数据，均可以进行激发判断，该函数返回传入的vector数据中的激发位置
 */
public class motiDiag {
	/**
	 * 判断阈值大于1.4的激发位置
	 * @param data
	 * @return
	 */
	
	public int[] motiD(Vector<String> data){//此时传进的data是1分钟数据，number是第几个时窗
		int[] iStr = new int[6]; // 存储记录切割值，因为后边用到分割s的值
		boolean channel = false;// 确定读取那三列数据，f表示读取345列，如果为t则读取123列
		int inte = -1;// 存储平均值，三个通道的融合数据
		int[] motiPos = new int[1000];//存储激发位置
		int countMoti = 0;//对激发位置进行计数
		String s;// 存储中间的字符串
		Vector<String> container = new Vector<String>();// 存储当前时窗的数据
//		System.out.println("时窗的个数："+data.size()/Parameters.N);
		int windowNum = (int) Math.floor(data.size()/Parameters.N);//计算时窗的个数
		int count = 0;//一个时窗内的数据，个数取决于Parameters.N的大小
		long sumLong = 0;
		long sumShort = 0;
		double aveLong = 0;
		double aveShort = 0;
		for(int win=0;win<windowNum;win++){//在时窗中循环，计算每个长短时窗的比值及激发情况
			int lineNumber = win * Parameters.N;//从长短时窗开始的位置读取，与数据长短无关
			container.clear();count=0;sumLong=0;sumShort=0;
			// 读取本时窗的数据
			while (count < Parameters.N)//提取长短时窗内的所有数据
			{
				s = data.get(lineNumber + count);
				String[] str = s.split(" ");
				for (int i = 3; i < 6; i++)// 共7列数据，最后一列为时间
					iStr[i] = Integer.parseInt(str[i]);
				if (!TestThreshold(iStr[3]) && !TestThreshold(iStr[4]) && !TestThreshold(iStr[5]))
					container.add(s);
				else{
					channel = true;
					container.add(s);
				}
				count++;
			}
//			System.out.println("输出临时容器的大小"+container.size());//输出临时容器的大小
			if (channel)// 读取前三列
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])),	Integer.parseInt(str[2]));
					sumLong += inte;
				}
			else
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])),	Integer.parseInt(str[5]));
					sumLong += inte;
				}
			aveLong = (double) sumLong / Parameters.N1;//计算长时窗平均值
			if (channel)// 读取前三列
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])), Integer.parseInt(str[2]));
					sumShort += inte;
				}
			} else
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])), Integer.parseInt(str[5]));
					sumShort += inte;
				}
			}
			aveShort = (double) sumShort / Parameters.N2;//计算短时窗平均值
			double yuzhi=aveShort/aveLong;
			if(yuzhi>=1.4)//大于等于1.4激发
				countMoti++;
				motiPos[countMoti] = lineNumber+Parameters.N;//激发后记录激发位置，在长短时窗的末尾
		}
//		for(int i = 0;i<motiPos.length;i++)
//			System.out.println("激发位置数组："+motiPos[i]);
		return motiPos;
	}
	/**
	 * 判断阈值大于1.4并且上下阈值大于500的数据的激发位置
	 * @param data 1分钟长度的数据
	 * @return
	 */
	public int[] motiV(Vector<String> data){//此时传进的data是1分钟数据，number是第几个时窗
		int[] iStr = new int[6]; // 存储记录切割值，因为后边用到分割s的值
		boolean channel = false;// 确定读取那三列数据，f表示读取345列，如果为t则读取123列
		int inte = -1;// 存储平均值，三个通道的融合数据
		int[] motiPos = new int[5000];//存储激发位置
		int countMoti = 0;//对激发位置进行计数
		String s;// 存储中间的字符串
		Vector<String> container = new Vector<String>();// 存储当前时窗的数据
//		System.out.println("时窗的个数："+data.size()/Parameters.N);
//		int windowNum = (int) Math.floor(data.size()/Parameters.N);//计算时窗的个数
		int count = 0;//一个时窗内的数据，个数取决于Parameters.N的大小
		long sumLong = 0;
		long sumShort = 0;
		double aveLong = 0;
		double aveShort = 0;
		for(int win=0;win<data.size()-2*Parameters.N;win+=100){//在时窗中循环，计算每个长短时窗的比值及激发情况
			int lineNumber = win + Parameters.N;//从长短时窗开始的位置读取，与数据长短无关
			container.clear();count=0;sumLong=0;sumShort=0;
			// 读取本时窗的数据
			while (count < Parameters.N)//提取长短时窗内的所有数据
			{
				s = data.get(lineNumber + count);
				String[] str = s.split(" ");
				for (int i = 3; i < 6; i++)// 共7列数据，最后一列为时间
					iStr[i] = Integer.parseInt(str[i]);
				if (!TestThreshold(iStr[3]) && !TestThreshold(iStr[4]) && !TestThreshold(iStr[5]))
					container.add(s);
				else{
					channel = true;
					container.add(s);
				}
				count++;
			}
//			System.out.println("输出临时容器的大小"+container.size());//输出临时容器的大小
			if (channel)// 读取前三列
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])),	Integer.parseInt(str[2]));
					sumLong += inte;
				}
			else
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])),	Integer.parseInt(str[5]));
					sumLong += inte;
				}
			aveLong = (double) sumLong / Parameters.N1;//计算长时窗平均值
			if (channel)// 读取前三列
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])), Integer.parseInt(str[2]));
					sumShort += inte;
				}
			} else
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])), Integer.parseInt(str[5]));
					sumShort += inte;
				}
			}
			aveShort = (double) sumShort / Parameters.N2;//计算短时窗平均值
			double yuzhi=aveShort/aveLong;
//			if(yuzhi>=1.4) {//大于等于1.4激发
//				countMoti++;
//				motiPos[countMoti] = lineNumber+Parameters.N;//激发后记录激发位置，在长短时窗的末尾
//			}
			if(yuzhi>=1.4 && Math.abs(Integer.parseInt(data.get(lineNumber+Parameters.N).split(" ")[3]))>500){//大于等于1.4激发 且x方向大于500
				countMoti++;
				motiPos[countMoti] = lineNumber+Parameters.N;//激发后记录激发位置，在长短时窗的末尾
			}
			
				
		}
//		for(int i = 0;i<motiPos.length;i++)
//			System.out.println("激发位置数组："+motiPos[i]);
		return motiPos;
	}
	/**
	 * 判断阈值大于10的激发位置
	 * @param data
	 * @return
	 */
	public static int motiExceed10(Vector<String> data){//此时传进的data是1分钟数据，number是第几个时窗
		int[] iStr = new int[6]; // 存储记录切割值，因为后边用到分割s的值
		boolean channel = false;// 确定读取那三列数据，f表示读取345列，如果为t则读取123列
		int inte = -1;// 存储平均值，三个通道的融合数据
		int motiPos = 0;//存储激发位置
		int countMoti = 0;//对激发位置进行计数
		String s;// 存储中间的字符串
		Vector<String> container = new Vector<String>();// 存储当前时窗的数据
//		System.out.println("时窗的个数："+data.size()/Parameters.N);
//		int windowNum = (int) Math.floor(data.size()/Parameters.N);//计算时窗的个数
		int count = 0;//一个时窗内的数据，个数取决于Parameters.N的大小
		long sumLong = 0;
		long sumShort = 0;
		double aveLong = 0;
		double aveShort = 0;
		for(int win=0;win<data.size()-Parameters.N;win+=100){//在时窗中循环，计算每个长短时窗的比值及激发情况
			int lineNumber = win;//从长短时窗开始的位置读取，与数据长短无关
			container.clear();count=0;sumLong=0;sumShort=0;
			// 读取本时窗的数据
			while (count < Parameters.N)//提取长短时窗内的所有数据
			{
				s = data.get(lineNumber + count);
				String[] str = s.split(" ");
				for (int i = 3; i < 6; i++)// 共7列数据，最后一列为时间
					iStr[i] = Integer.parseInt(str[i]);
				if (!TestThreshold(iStr[3]) && !TestThreshold(iStr[4]) && !TestThreshold(iStr[5]))
					container.add(s);
				else{
					channel = true;
					container.add(s);
				}
				count++;
			}
			
//			System.out.println("输出临时容器的大小"+container.size());//输出临时容器的大小
			if (channel)// 读取前三列
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])),	Integer.parseInt(str[2]));
					sumLong += inte;
				}
			else
				for (int i = 0; i < Parameters.N1; i++)
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])),	Integer.parseInt(str[5]));
					sumLong += inte;
				}
			aveLong = (double) sumLong / Parameters.N1;//计算长时窗平均值
			if (channel)// 读取前三列
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[0]), Integer.parseInt(str[1])), Integer.parseInt(str[2]));
					sumShort += inte;
				}
			} else
			{
				for (int i = Parameters.N1; i < Parameters.N; i++)//长时窗最后一个点到短时窗最后一个点
				{
					String[] str = container.get(i).split(" ");
					inte = (int) Math.hypot(Math.hypot(Integer.parseInt(str[3]), Integer.parseInt(str[4])), Integer.parseInt(str[5]));
					sumShort += inte;
				}
			}
			aveShort = (double) sumShort / Parameters.N2;//计算短时窗平均值
			double yuzhi=aveShort/aveLong;
			if(yuzhi>=10){//大于等于10激发
				countMoti++;//激发次数统计
				motiPos = lineNumber+Parameters.N1;//激发后记录激发位置，在长短时窗的中间
			}
		}
//		for(int i = 0;i<motiPos.length;i++)
//			System.out.println("激发位置数组："+motiPos[i]);
		return motiPos;//返回该位置的前一个位置
	}
	/**
	 * 计算通道转换
	 * 
	 * @param num
	 *            传进来的值
	 * @return 通道是否转换
	 */
	private static boolean TestThreshold(int num)
	{
		if (num > Parameters.HEAD || num < Parameters.TAIL)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
