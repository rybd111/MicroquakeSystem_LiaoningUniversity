package utils;

import java.util.Vector;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;
import com.h2.locate.Five_Locate;
import com.mathworks.toolbox.javabuilder.MWException;

import bean.QuackResults;
import tensor_pro.Tensor_Pro_Class;


public class Tensor {
	public static  Object moment_tensor(Sensor[] sensors,Sensor[] sensor1,Sensor location_refine) throws MWException{
		double density=2650;//岩石密度
	    double velocity=3850;//波速
		int count=Parameters.SensorNum;
		double [][] a=new double[sensors.length][4];//a为二维数组，包括传感器编号，x，y，z
		double [][] tongdao=new double[sensor1.length][2];
//		Vector<Vector> a=new Vector<Vector>();//a为二维数组，包括传感器编号，x，y，z
//		Vector<Vector> tongdao=new Vector<Vector>();//tongdao为二维数组，包括激发传感器编号以及最大振幅
//		sensors = new Sensor[count];
		for(int i=0;i<sensors.length;i++)
		{		
			
			a[i][0]=sensors[i].getSensorNum();
			for(int j=1;j<4;j++)
			{
				if(j==1)
				{
					a[i][j]=sensors[i].getLatitude();
				}
				if(j==2)
				{
					a[i][j]=sensors[i].getLongtitude();
				}
				if(j==3)
				{
					a[i][j]=sensors[i].getAltitude();
				}
			}
		}
		
		
		for(int i=0;i<sensor1.length;i++)
		{
			tongdao[i][0]=i+1;
			for(int j=1; j<2; j++)
			{
				tongdao[i][j]= sensor1[i].getFudu();
			}
		}
		
//		for(int i=0;i<count;i++)
//		{
//			Vector v1=new Vector ();
//			sensors[i]=new Sensor();
//			v1.add(i+1);//传感器编号
//			v1.add(sensors[i].getLatitude());//维度x
//			v1.add(sensors[i].getLongtitude());//经度y
//			v1.add(sensors[i].getAltitude());//海拔z
//			a.addElement(v1);
//		}
//		for(int i=0;i<sensor1.length;i++)
//		{
//			Vector v2=new Vector();
//			v2.add(sensor1[i]);
//			v2.add(sensor1[i].getFudu());
//			tongdao.addElement(v2);
//		}
		double [] tlineb=new double[4];//存放震源信息，包括x，y，z
		tlineb[0]=1;
		double x=location_refine.getLatitude();
		tlineb[1]=x;
		double y=location_refine.getLongtitude();
		tlineb[2]=y;
		double z=location_refine.getAltitude();
		tlineb[3]=z;
			Tensor_Pro_Class c1=new Tensor_Pro_Class();
		Object[] pso=c1.tensor_pro(1,a,tongdao,tlineb,density,velocity);
		return  pso[0];
		
	}
}
