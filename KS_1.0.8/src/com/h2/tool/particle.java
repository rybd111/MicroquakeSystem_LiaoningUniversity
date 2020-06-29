package com.h2.tool;

import java.util.Random;

import com.h2.constant.Parameters;
import com.h2.constant.Sensor;

/**
 * @ClassName: particle
 * @Author: CR
 * @Date: 2020/6/11 ����9:40
 * @Description:
 */
public class particle {

    //ά��
    public  int dimension = 4;

    //���ӵ�λ��
    public double[] X = new double[dimension];

    //�ֲ����λ��
    public double[] pbest = new double[dimension];

    //���ӵ��ٶ�
    public double[] V = new double[dimension];

    //����ٶ�
    public double Vmax0 = 4152240;
    public double Vmax1 = 460060;
    public double Vmax2 = 150;
    public double Vmax3 = 0.2;

    //��Ӧֵ
    public double fitness;
    /*public double x1=41518099.807,y1=4595388.504,z1=22.776,t1=0.9;
    public double x2=41518060.298,y2=4594304.927,z2=21.926,t2=0;
    public double x3=41520207.356,y3=4597983.404,z3=22.661,t3=0.1;
    public double x4=41520815.875,y4=4597384.576,z4=25.468,t4=0.04;
    public double x5=41516707.440,y5=4593163.619,z5=22.564,t5=0.06;*/
    public double v = Parameters.C;
    /**
     * ���ݵ�ǰλ�ü�����Ӧֵ
     * @return newFitness
     */
    public double calculateFitness(Sensor[] se) {//��ʱ��Ĭ����5̨վ��
    	double[] x = new double[se.length];
    	double[] y = new double[se.length];
    	double[] z = new double[se.length];
    	double[] t = new double[se.length];
    	for(int i=0;i<se.length;i++) {
    		if(Parameters.offline==false) {
	    		x[i]=se[i].getLatitude()+Parameters.SENSORINFO[SensorTool.baseCoordinate][0];
	    		y[i]=se[i].getLongtitude()+Parameters.SENSORINFO[SensorTool.baseCoordinate][1];
	    		z[i]=se[i].getAltitude()+Parameters.SENSORINFO[SensorTool.baseCoordinate][2];
    		}
    		if(Parameters.region_offline=="hongyang") {
    			x[i]=se[i].getLatitude()+ Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][0];
        		y[i]=se[i].getLongtitude()+Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][1];
        		z[i]=se[i].getAltitude()+Parameters.SENSORINFO_offline_hongyang[SensorTool.baseCoordinate][2];
    		}
    		if(Parameters.region_offline=="datong") {
    			x[i]=se[i].getLatitude()+ Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][0];
        		y[i]=se[i].getLongtitude()+Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][1];
        		z[i]=se[i].getAltitude()+Parameters.SENSORINFO_offline_datong[SensorTool.baseCoordinate][2];
    		}
    		if(Parameters.region_offline=="pingdingshan") {
    			x[i]=se[i].getLatitude()+ Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][0];
        		y[i]=se[i].getLongtitude()+Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][1];
        		z[i]=se[i].getAltitude()+Parameters.SENSORINFO_offline_pingdingshan[SensorTool.baseCoordinate][2];
    		}
    		t[i]=se[i].getSecTime();
    	} 
    	double Sum=0;
    	
    	for(int i=0;i<se.length;i++) {
    		double sum = Math.abs(Math.sqrt(Math.pow(X[0]-x[i], 2)+Math.pow(X[1]-y[i], 2)+Math.pow(X[2]-z[i], 2))-v*v*Math.pow(t[i]-X[3], 2));
    		Sum+=sum; 
    	}
    	double newFitness = Sum;
    	return newFitness;
        
    }


    /**
     * ��ʼ���Լ���λ�ú�pbest
     */
    public void initialX() {
        for(int i=0;i<dimension;i++) {
        	if(i==0) {
            	X[i] = new Random().nextInt(9021)+41513381;
                pbest[i] = X[i];
            }
        	if(i==1) {
            	X[i] = new Random().nextInt(9597)+4591010;
                pbest[i] = X[i];
            }
        	if(i==2) {
            	X[i] = 0-(new Random().nextInt(1500));
                pbest[i] = X[i];
            }
        	if(i==dimension-1) {
            	X[i] =0-(new Random().nextDouble()*2);
                pbest[i] = X[i];
            }
        }
    }
    /**
     * ��ʼ���Լ����ٶ�
     */
    public void initialV() {
        for(int i=0;i<dimension;i++) {
        	if(i==0) {
            	double tmp2 = new Random().nextDouble();//�������һ��0~1�����С��
                V[i] = tmp2*1804+(-902);
            }
        	if(i==1) {
            	double tmp2 = new Random().nextDouble();//�������һ��0~1�����С��
                V[i] = tmp2*1918+(-959);
            }
        	if(i==2) {
            	double tmp2 = new Random().nextDouble();//�������һ��0~1�����С��
                V[i] = tmp2*300+(-150);
            }
        	if(i==dimension-1) {
            	double tmp2 = new Random().nextDouble();//�������һ��0~1�����С��
                /*V[i] = tmp2*0.4+(-0.2);*/
            	V[i] = 0-(new Random().nextDouble()*0.2);
            }
        }
    }
}
