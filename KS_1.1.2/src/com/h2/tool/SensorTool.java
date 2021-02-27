/**
 * @author 韩百硕
 */
package com.h2.tool;

import java.text.ParseException;
import java.util.Vector;

import com.h2.constant.Parameters;

import DataExchange.Sensor;
import controller.ADMINISTRATOR;
import middleware.motivation_Diagnose_alone;
import utils.ArrayMatch;

/**
 * when we initial the sensor, and diagnose the morivated position, then we will use this class.
 * @author 韩百硕, Hanlin Zhang.
 */
public class SensorTool
{
//	public static int baseCoordinate = 0;
	/**
	 * 根据路径返回传感器坐标
	 * @param count 传感器个数
	 * @param Str 传感器路径，离线格式：“Testz” 在线格式：“y:/”
	 * @return Sensor数组
	 * @author Hanlin Zhang.
	 * @date revision 下午5:15:34
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static Sensor[] initSensorInfo(int count,String [] Str, ADMINISTRATOR manager)
	{
		Sensor[] sensors = new Sensor[count];
		//a sequence of correspond places.
		int [] k = new int [Parameters.SensorNum];
		//匹配地区的序号。
		int th = ArrayMatch.match_String(Parameters.station ,Parameters.region);
		k = baseSort(Str, k, th,manager);
		
		for (int i = 0; i < count; i++)
		{
			sensors[i]=new Sensor();
			sensors[i].SetSensorSeries(i);
			//(sensors[i]).setBackupFile(Parameters.EARTHDATAFILE[k[i]]);
			(sensors[i]).setx(Parameters.SENSORINFO1[Parameters.diskNameNum][k[i]][0]-Parameters.SENSORINFO1[Parameters.diskNameNum][k[0]][0]);
//				(sensors[i]).setLatitude(Parameters.SENSORINFO_offline_hongyang[k[i]][0]);
			(sensors[i]).sety(Parameters.SENSORINFO1[Parameters.diskNameNum][k[i]][1]-Parameters.SENSORINFO1[Parameters.diskNameNum][k[0]][1]);
//				(sensors[i]).setLongtitude(Parameters.SENSORINFO_offline_hongyang[k[i]][1]);
			(sensors[i]).setz(Parameters.SENSORINFO1[Parameters.diskNameNum][k[i]][2]-Parameters.SENSORINFO1[Parameters.diskNameNum][k[0]][2]);
//				(sensors[i]).setAltitude(Parameters.SENSORINFO_offline_hongyang[k[i]][2]);
		}
		return sensors;
	}
	
	/**
	 * 确定一个根盘符，然后用其余盘符减去它，求相对坐标。
	 * @param Str 求得路径中的盘符号，与diskName作比较，相等就保存这个序号。
	 * @param k 对应不同的传感器序号
	 * @param region
	 * @param manager
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月21日上午10:12:04
	 */
	public static int[] baseSort(
			String[] Str, 
			int [] k, 
			int region,
			ADMINISTRATOR manager) {
		
		for(int j=0;j<Str.length;j++){
			for(int i=0;i<Parameters.diskName[region].length;i++) {
				String s = null;
				if(Parameters.offline == true) {
					s = Str[j].substring(Str[j].length()-2, Str[j].length()-1);
				}
				else {
					s = Str[j].substring(Str[j].length()-3, Str[j].length()-2);
				}
				if(s.equals(Parameters.diskName[region][i])) {
					k[j]=i;
					break;
				}
			}
		}
		manager.setBaseCoordinate(k[0]);
//		baseCoordinate = k[0];
		Parameters.diskNameNum = region;
		return k;
	}
	
	/**
	 * 在这10s内激发的传感器,并设置激发传感器的标识和激发时间
	 * 一旦激发就不再继续判断后续的数据，即弃之，采用过早放弃策略。
	 * date并不是整10s，而是加上了前面的一个refineRange的范围数据。
	 * @param data
	 * @param sensor
	 * @return
	 * @author Baishuo Han, Hanlin Zhang.
	 */
	public static void motivate(Vector<String> data, Sensor sensor,int th, int range) throws ParseException
	{
		motivation_Diagnose_alone m = new motivation_Diagnose_alone(data, range, th);
		
		if(m.MotivationDiag()) {
			sensor.setSign(m.getIsMoti());
			sensor.setlineSeries(m.getMotiPos());
			sensor.setSecTime(m.getRelativeMSTime());
			sensor.setAbsoluteTime(m.getAbsoluteMSTime());
			sensor.setTime(m.getAbsoluteSTime());
			sensor.setCrestortrough(m.getCrestorThrough());
		}
		
	}	
	public static double Variance(int[] x) { 
		int m=x.length;
		double sum=0;
		for(int i=0;i<m;i++){//求和
			sum+=x[i];
		}
		double dAve=sum/m;//求平均值
		double dVar=0;
		for(int i=0;i<m;i++){//求方差
			dVar+=(x[i]-dAve)*(x[i]-dAve);
		}
		return dVar/m;
	}
}
