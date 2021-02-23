/**
 * 
 */
package com.h2.locate;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.db.DbExcute;
import com.h2.backupData.WriteRecords;
import com.h2.constant.Parameters;
import com.h2.strength.calStrength;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.QuackResults;
import DataExchange.Sensor;
import b_pro.BClass;
import controller.ADMINISTRATOR;
import utils.Tensor;
import utils.TimeDifferent;
import utils.one_dim_array_max_min;

/**
 * calculation process.
 * @author Hanlin Zhang
 */
public class SaveInfo {

	private Sensor[] allsensors;
	private String earlistTime;
	private Sensor[] MotisensorsAfterCut;
	private Sensor location_refine;
	private QuackResults aQuackResults;
	private DbExcute aDbExcute;
	
	
	/**
	 * 保存数据库记录通用函数
	 * @param aQuackResults
	 * @param aDbExcute
	 * @param kind
	 * @param location_refine
	 * @param sensors1
	 * @param quakeString
	 * @param finalEnergy
	 * @param tensor_c
	 * @param b_value
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:16:34
	 */
	public void saveInfo(
			String kind, 
			String quakeString, 
			double finalEnergy, 
			double tensor_c, 
			double b_value,
			ADMINISTRATOR manager) {
		location_refine.toString(manager);
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		aQuackResults.setKind(kind);
		aQuackResults.setxData(Double.parseDouble(nf.format(location_refine.getLatitude())));
		aQuackResults.setyData(Double.parseDouble(nf.format(location_refine.getLongtitude())));
		aQuackResults.setzData(Double.parseDouble(nf.format(location_refine.getAltitude())));
		aQuackResults.setQuackTime(location_refine.getquackTime());
		aQuackResults.setQuackGrade(Double.parseDouble(quakeString));//近震震级
		aQuackResults.setDuringGrade(0);//持续时间震级
		aQuackResults.setParrival(location_refine.getSecTime());//P波到时，精确到毫秒
		aQuackResults.setPanfu(MotisensorsAfterCut[0].getpanfu());//盘符
		aQuackResults.setNengliang(finalEnergy);//能量
		aQuackResults.setFilename_S(MotisensorsAfterCut[0].getFilename());//文件名，当前第一个台站的文件名，其他台站需要进一步改变第一个字符为其他台站，则为其他台站的文件名。
		aQuackResults.setTensor(tensor_c);//矩张量
		aQuackResults.setbvalue(b_value);//b值
		
		//output the five locate consequence.
		System.out.println(kind+"："+aQuackResults.toString());//在控制台输出计算结果
		
		//diagnose is not open the function of storing into the database.
		if(Parameters.isStorageDatabase == 1) {
			try {
				aDbExcute.addElement(aQuackResults);
			} catch (Exception e) {
				System.out.println("add to database error");
			}
		}
	}
	
	/**
	 * 保存数据库记录通用函数。
	 * @param intequackTime
	 * @param sensors1
	 * @param sensors
	 * @param location_refine
	 * @param quakeString
	 * @param finalEnergy
	 * @param tensor_c
	 * @param b_value
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:14:43
	 */
	public void saveEventRecord(
			String intequackTime, 
			String quakeString, 
			double finalEnergy, 
			double tensor_c, 
			double b_value,
			ADMINISTRATOR manager) 
					throws ParseException {
//		int dif = TimeDifferent.DateDifferent(intequackTime, manager.getLastDate());
		//cut the quack time to the day.
		String dateInFileName = intequackTime.substring(0, 10);
		//If the difference between the current calculated time and the last time is more than 1 day, the storage file is changed to a new.
		new WriteRecords().WriteCalculationResults(
				MotisensorsAfterCut,
				earlistTime,
				location_refine,
				Parameters.AbsolutePath5_record+dateInFileName+"_QuakeRecords.csv",
				quakeString, 
				finalEnergy, 
				"粒子群",
				tensor_c,
				b_value);
	}
	
	/**
	 * 计算近震震级通用函数。
	 * @param sensors1
	 * @param location_refine
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:15:55
	 */
	public float calEarthQuake() {
		
		calStrength sensorThread3[] = new calStrength[Parameters.SensorNum];
		
		//Compute the near quake magnitude which data only must from the motivated sensors we selected, so the sensors1 is used.
		ExecutorService executor_cal = Executors.newFixedThreadPool(Parameters.SensorNum);
		final CountDownLatch threadSignal_cal = new CountDownLatch(MotisensorsAfterCut.length);
		//calculate the near earthquake magnitude et al.
		for(int i=0;i<MotisensorsAfterCut.length;i++) {
			sensorThread3[i] = new calStrength(MotisensorsAfterCut[i], location_refine, i, threadSignal_cal);
			executor_cal.execute(sensorThread3[i]);//计算单个传感器的近震震级
		}
		try {threadSignal_cal.await();}
        catch (InterruptedException e1) {e1.printStackTrace();}
		executor_cal.shutdown();
		
		//We integrate every sensors quake magnitude to compute the last quake magnitude.
		float earthQuakeFinal = 0;
		for (Sensor sen : MotisensorsAfterCut)	earthQuakeFinal += sen.getEarthClassFinal();
		earthQuakeFinal /= MotisensorsAfterCut.length;//For this method is only support 5 sensors, so we must divide 5 to calculate the last quake magnitude.
		if(Parameters.MinusAFixedOnMagtitude==true)
			earthQuakeFinal = (float) (earthQuakeFinal-Parameters.MinusAFixedValue);// We discuss the consequen to minus 0.7 to reduce the final quake magnitude at datong coal mine.
		
		return earthQuakeFinal;
	}
	
	/**
	 * 计算能量通用函数。
	 * @param sensors1
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:22:11
	 */
	public double calEnergy() {
		//We compute the minimum energy of all sensors as the final energy.
		double finalEnergy = 0.0;double []energy = new double[MotisensorsAfterCut.length];
		for (int i=0;i<MotisensorsAfterCut.length;i++) {
			energy[i] = MotisensorsAfterCut[i].getEnergy();
		}
		finalEnergy = one_dim_array_max_min.mindouble(energy);
		
		return finalEnergy;
	}
	
	/**
	 * 计算b值。
	 * @param earthQuakeFinal
	 * @return
	 * @throws MWException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:33:44
	 */
	public double bValue(float earthQuakeFinal) throws MWException {
		Object [] earthQuakeFinal1=new Object[1];
		earthQuakeFinal1[0]=earthQuakeFinal;	//事件的震级
		double zjmax=4.5;//最大震级
		double zjmin=0.1;//最小震级
		BClass bclass=new BClass();
		Object[] bb=bclass.b_pro(1,earthQuakeFinal1,zjmax,zjmin);
		
		return Double.parseDouble(bb[0].toString());//b值
	}
	
	/**
	 * 计算发震时刻。
	 * @param sensors
	 * @param location_refine
	 * @throws ParseException
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午6:36:47
	 */
	public void quackTime() throws ParseException {
		//we calculate the real quake time by reduce the first receiving quake signal sensor and the quake time in second.
		String intequackTime = TimeDifferent.TimeDistance(earlistTime, location_refine.getSecTime()); //the time of refine quake time;
		//compute the quake time and quake coordination, the two sensors' locate is also adapt to the three location.
		location_refine.setquackTime(intequackTime);
	}
	
	/**
	 * 通用定位流程。
	 * 
	 * @author Hanlin Zhang.
	 * @throws ParseException 
	 * @throws MWException 
	 * @date revision 2021年2月12日下午6:41:56
	 */
	public void generalProcess(String kind, ADMINISTRATOR manager) throws ParseException, MWException {
		//发震时刻
		quackTime();
		//近震震级
		float earthQuakeFinal = calEarthQuake();
		//能量
		double finalEnergy = calEnergy();
		//矩张量计算
		double tensor_c = Tensor.moment_tensor(allsensors, MotisensorsAfterCut, location_refine);
		//b值
		double b_value = bValue(earthQuakeFinal);
		//we will set 0 when the consequence appears NAN value.
		String quakeString = (Float.compare(Float.NaN, earthQuakeFinal) == 0) ? "0"	: String.format("%.2f", earthQuakeFinal);//修改震级，保留两位小数
		//save to database.
		saveInfo(kind, quakeString, finalEnergy, tensor_c, b_value, manager);
		//we also record the quake location in a '.csv' file for manually computation.
		if(Parameters.isStorageEventRecord==1) {
			saveEventRecord(location_refine.getquackTime(), quakeString, finalEnergy, tensor_c, b_value, manager);
		}
	}
	
	//get all variables.
	public void setQuackResults(QuackResults quakeresults) {
		this.aQuackResults = quakeresults;
	}
	public void setDbexcute(DbExcute db) {
		this.aDbExcute = db;
	}
	public void setAllsensors(Sensor[] all) {
		this.allsensors = all;
	}
	public void setEarlistTime(String earlistTime) {
		this.earlistTime = earlistTime;
	}
	public void setMotisensorsAfterCut(Sensor[] MotisensorsAfterCut) {
		this.MotisensorsAfterCut = MotisensorsAfterCut;
	}
	public void setlocation_refine(Sensor refine) {
		this.location_refine = refine;
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月12日下午5:20:13
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
