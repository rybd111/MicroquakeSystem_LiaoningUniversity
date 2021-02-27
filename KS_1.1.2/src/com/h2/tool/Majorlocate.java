package com.h2.tool;

import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import com.h2.constant.Parameters;

import DataExchange.Sensor;

/*主地震定位方法………… t主走时 = t待走时 + coex xx + coey yy + coez zz  （coe表示系数，t待走时=t接收到时-t发震时刻）
 * 已知量：主事件走时、待定事件到时、coe系数通过计算迭代更新。
 * 未知量：xx、yy、zz三方向相对于主事件偏移量、发震时刻
 * 四个未知数所以需要最少四个传感器，一个传感器形成一个方程，本程序只实现四个传感器定位，还需改进*/

public class Majorlocate {
	//主地震位置
	final static double x=100;//待定
	final static double y=100;
	final static double z=100;
	
	//有效距离，所以主事件位置需要随着采掘进度进行更新
	final static double Rx=100;
	final static double Ry=100;
	final static double Rz=100;
	
	/*算法核心：解方程组形成的矩阵  AB=C
	 * A为系数矩阵、B为未知数矩阵、C为已知量矩阵
	 * 形参：到时时间数组，为相对时间
	 * 运算后得到发震时刻，也为相对时间，暂时无法存到sensor中*/
	public static Sensor majorLocate(double[] arrivaltime) {
		//与主地震相对位置
		double xx=0.0,yy=0.0,zz=0.0;
		//发震时刻，为相对于基准位置的时间
		double t0=0.0;
		int count=10;//计数器，控制迭代次数，暂时只有这一个控制参量，后续还需要改进
		while(true) {
			DoubleMatrix B = DoubleMatrix.zeros(4, 1);//4行1列,未知数
			DoubleMatrix A = getA(xx,yy,zz);//系数矩阵
			DoubleMatrix C = getC(arrivaltime);//常数项矩阵，4行1列
			B = Solve.pinv(A).mmul(C);//求解
			t0=B.get(0);
			xx=B.get(1);
			yy=B.get(2);
			zz=B.get(3);
			if(xx>Rx|xx<-Rx|yy>Ry|yy<-Ry|zz>Rz|zz<-Rz) {//判断是否超出有效计算距离
				System.out.println("超出有效距离，无法计算");
				break;
			}
			System.out.println("xx:"+xx+" yy:"+yy+" zz:"+zz);
			count--;
			if(count==0) {
				break;
			}
		}	
		//只传入定位结果，未传入发震时刻，需要后续计算
		Sensor sensor=new Sensor();
		sensor.setx(x+xx);
		sensor.sety(y+yy);
		sensor.setz(z+zz);
//		System.out.println("定位点x="+sensor.getLatitude()+" y="+sensor.getLongtitude()+" z="+sensor.getAltitude());
		return sensor;
	}
	//得到方程系数 ………… t主走时 = t待走时 + coex xx + coey yy + coez zz
	private static double[] getCoefficient(int i,double xx,double yy,double zz) {//i表示传感器坐标矩阵中的位置
		//传感器坐标，震源与每个传感器的直线距离是不同的
		double xi=Parameters.SENSORINFO1[Parameters.diskNameNum][i][0];
		double yi=Parameters.SENSORINFO1[Parameters.diskNameNum][i][1];
		double zi=Parameters.SENSORINFO1[Parameters.diskNameNum][i][2];
		double R =Math.sqrt(Math.pow(xi-x+xx,2)+Math.pow(yi-y+yy,2)+Math.pow(zi-z+zz,2));		
		//方程系数求取方式，第一次带入xx、yy、zz为0，随着迭代，偏移量变化，系数变化，方程组结果变化即偏移量变化，循环求解
		double coex=(xi-x+xx)/(Parameters.C*R);
		double coey=(yi-y+yy)/(Parameters.C*R);
		double coez=(zi-z+zz)/(Parameters.C*R);
		double[] Coefficient= {coex,coey,coez};
		return Coefficient;
	}
	//得到系数矩阵A
	private static DoubleMatrix getA(double xx,double yy,double zz) {
		DoubleMatrix A=DoubleMatrix.zeros(4, 4);
		DoubleMatrix temp=DoubleMatrix.zeros(1,4);
		for(int i=0;i<4;i++) {//得到各传感器形成方程的参数,传感器数量也要根据实际给定，暂定4个
			double[] array= getCoefficient(i, xx, yy, zz);	
			temp.put(0, -1);//发震时刻系数为-1
			temp.put(1, array[0]);//xx、yy、zz的系数
			temp.put(2, array[1]);
			temp.put(3, array[2]);
			A.putRow(i, temp);
		}
		return A;
	}
	//得到常数项矩阵，主事件走时-传感器接收到时（应该是同一个能量级的）
	private static DoubleMatrix getC(double[] arrivaltime) {
		//主地震到各传感器的走时
		double t0=Math.sqrt(Math.pow(x-Parameters.SENSORINFO1[Parameters.diskNameNum][0][0], 2)+Math.pow(y-Parameters.SENSORINFO1[Parameters.diskNameNum][0][1], 2)+Math.pow(z-Parameters.SENSORINFO1[Parameters.diskNameNum][0][2], 2))/Parameters.C;
		double t1=Math.sqrt(Math.pow(x-Parameters.SENSORINFO1[Parameters.diskNameNum][1][0], 2)+Math.pow(y-Parameters.SENSORINFO1[Parameters.diskNameNum][1][1], 2)+Math.pow(z-Parameters.SENSORINFO1[Parameters.diskNameNum][1][2], 2))/Parameters.C;
		double t2=Math.sqrt(Math.pow(x-Parameters.SENSORINFO1[Parameters.diskNameNum][2][0], 2)+Math.pow(y-Parameters.SENSORINFO1[Parameters.diskNameNum][2][1], 2)+Math.pow(z-Parameters.SENSORINFO1[Parameters.diskNameNum][2][2], 2))/Parameters.C;
		double t3=Math.sqrt(Math.pow(x-Parameters.SENSORINFO1[Parameters.diskNameNum][3][0], 2)+Math.pow(y-Parameters.SENSORINFO1[Parameters.diskNameNum][3][1], 2)+Math.pow(z-Parameters.SENSORINFO1[Parameters.diskNameNum][3][2], 2))/Parameters.C;
		//double t4=Math.sqrt(Math.pow(x-Parameters.SENSORINFO[4][0], 2)+Math.pow(y-Parameters.SENSORINFO[4][1], 2)+Math.pow(z-Parameters.SENSORINFO[4][2], 2))/Parameters.C;
		//4行1列二维数组
		DoubleMatrix C=DoubleMatrix.zeros(4, 1);
		//对常数矩阵赋值，需要对应传感器，暂时不知道哪几个传感器好用，参数待定
		C.put(0, t0-arrivaltime[0]);
		C.put(1, t1-arrivaltime[1]);
		C.put(2, t2-arrivaltime[2]);
		C.put(3, t3-arrivaltime[3]);
		return C;
	}
	
}
