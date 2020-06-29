package com.h2.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.h2.constant.Sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: PSO
 * @Author: CR
 * @Date: 2020/6/11 ����9:40
 * @Version: 1.0
 * @Description:
 */
public class PSO {

    private static double[] gbest;//ȫ������λ��

    private static double gbest_fitness = Double.MAX_VALUE;//ȫ������λ�ö�Ӧ��fitness

    private static int particle_num = 5000;//������

    private static int N = 200;//迭代次数

    private static double c1,c2 = 1.8;
    private static double c3,c4 = 1.2;

    private static double w = 1.4;//��������,���ｫȨ��ϵ����0.9���Լ�С��0.4

    private static List<particle> particles = new ArrayList<particle>();//����Ⱥ

    private static List<Double> fittessList = new ArrayList<>(N);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
//        process();
    }

    /**
     * 
     */
    public static void initialParticles(Sensor[] se) {
        for(int i=0;i<particle_num;i++) {
            particle particle = new particle();
            particle.initialX();
            particle.initialV();
			particle.fitness = particle.calculateFitness(se);
            particles.add(particle);
        }
    }

    /**
     * update gbest
     */
    public static void updateGbest() {
        double fitness = Double.MAX_VALUE;
        int index = 0;
        for(int i=0;i<particle_num;i++) {
            if(particles.get(i).fitness<fitness) {
                index = i;
                fitness = particles.get(i).fitness;
            }
        }
        if(fitness<gbest_fitness) { 
            gbest = particles.get(index).pbest.clone();
            gbest_fitness = fitness;
        }
    }

    /**
     * 跟新每个粒子的速度
     */
    public static void updateV(int n) {
        for(particle particle:particles) {
            for(int i=0;i<particle.dimension;i++) {
            	if(i==0) {
            		double ra = rand();
            		if(ra>=0.5) {
            		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c1*ra*(particle.pbest[i]-particle.X[i])+c2*ra*(gbest[i]-particle.X[i]);
            		
                    if(v>particle.Vmax0) // 判断速度是否超过最大的速度
                        v = particle.Vmax0;
                    else if(v<-particle.Vmax0) // 比最大速度的相反数小
                        v = -particle.Vmax0;
                    particle.V[i] = v;//更新Vi
            		}
            		if(ra<0.5) {
                		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c3*ra*(particle.pbest[i]-particle.X[i])+c4*ra*(gbest[i]-particle.X[i]);
                		
                        if(v>particle.Vmax0) // 判断速度是否超过最大的速度
                            v = particle.Vmax0;
                        else if(v<-particle.Vmax0) // 比最大速度的相反数小
                            v = -particle.Vmax0;
                        particle.V[i] = v;//更新Vi
                		}
            	}
            	if(i==1) {
            		double ra = rand();
            		if(ra>=0.5) {
            		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c1*ra*(particle.pbest[i]-particle.X[i])+c2*ra*(gbest[i]-particle.X[i]);
            		
                    if(v>particle.Vmax1) // 判断速度是否超过最大的速度
                        v = particle.Vmax1;
                    else if(v<-particle.Vmax1) // 比最大速度的相反数小
                        v = -particle.Vmax1;
                    particle.V[i] = v;//更新Vi
            		}
            		if(ra<0.5) {
                		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c3*ra*(particle.pbest[i]-particle.X[i])+c4*ra*(gbest[i]-particle.X[i]);
                		
                        if(v>particle.Vmax1) // 判断速度是否超过最大的速度
                            v = particle.Vmax1;
                        else if(v<-particle.Vmax1) // 比最大速度的相反数小
                            v = -particle.Vmax1;
                        particle.V[i] = v;//更新Vi
                		}
            	}
            	if(i==2) {
            		double ra = rand();
            		if(ra>=0.5) {
            		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c1*ra*(particle.pbest[i]-particle.X[i])+c2*ra*(gbest[i]-particle.X[i]);
            		
                    if(v>particle.Vmax2) // 判断速度是否超过最大的速度
                        v = particle.Vmax2;
                    else if(v<-particle.Vmax2) // 比最大速度的相反数小
                        v = -particle.Vmax2;
                    particle.V[i] = v;//更新Vi
            		}
            		if(ra<0.5) {
                		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c3*ra*(particle.pbest[i]-particle.X[i])+c4*ra*(gbest[i]-particle.X[i]);
                		
                        if(v>particle.Vmax2) // 判断速度是否超过最大的速度
                            v = particle.Vmax2;
                        else if(v<-particle.Vmax2) // 比最大速度的相反数小
                            v = -particle.Vmax2;
                        particle.V[i] = v;//更新Vi
                		}
            	}
            	if(i==particle.dimension-1) {
            		double ra = rand();
            		if(ra>=0.5) {
            		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c1*ra*(particle.pbest[i]-particle.X[i])+c2*ra*(gbest[i]-particle.X[i]);
            		
                    if(v>particle.Vmax3) // 判断速度是否超过最大的速度
                        v = particle.Vmax3;
                    else if(v<-particle.Vmax3) // 比最大速度的相反数小
                        v = -particle.Vmax3;
                    particle.V[i] = v;//更新Vi
            		}
            		if(ra<0.5) {
                		double v =(0.9 - n*(0.9-0.4)/N) * particle.V[i]+c3*ra*(particle.pbest[i]-particle.X[i])+c4*ra*(gbest[i]-particle.X[i]);
                		
                        if(v>particle.Vmax3) // 判断速度是否超过最大的速度
                            v = particle.Vmax3;
                        else if(v<-particle.Vmax3) // 比最大速度的相反数小
                            v = -particle.Vmax3;
                        particle.V[i] = v;//更新Vi
                		}
            	}
            }
        }
    }

    /**
     * 更新每个粒子的位置和pbest
     */
    public static void updateX(Sensor[] se) {
        for(particle particle:particles) {
            /*for(int i=0;i<particle.dimension;i++) {
                particle.X[i] = particle.X[i] + particle.V[i];
            }*/
            for(int i=0;i<particle.dimension;i++) {
                particle.X[i] = particle.X[i] + particle.V[i];
                
                if(i==particle.dimension-4) {
                	if(particle.X[i]>41522401 || particle.X[i]<41513381) {
                		particle.X[i]=particle.X[i] - particle.V[i];
                	}
                }
                if(i==particle.dimension-3) {
                	if(particle.X[i]>4600606 || particle.X[i]<4591010) {
                		particle.X[i]=particle.X[i] - particle.V[i];
                	}
                }
                
                if(i==particle.dimension-2) {
                	if(particle.X[i]>0 || particle.X[i]<-1500) {
                		particle.X[i]=particle.X[i] - particle.V[i];
                	}
                }
                if(i==particle.dimension-1) {
                	if(particle.X[i]>0) {
                		particle.X[i]=particle.X[i] - particle.V[i];
                	}
                }
            }
            double newFitness = particle.calculateFitness(se);//新的适应值
            //如果新的适应值比原来的小则跟新fitness和pbest
            if(newFitness<particle.fitness) {
                particle.pbest = particle.X.clone();
                particle.fitness = newFitness;
            }
        }
    }

    /**
     * �㷨��Ҫ����
     */
    public static Sensor process(Sensor[] se) {
        int n = 0;
        initialParticles(se);
        updateGbest();
        while(n++<N) {
            updateV(n);
            updateX(se);
            updateGbest();
            fittessList.add(gbest_fitness);
//            System.out.println(n+".��ǰgbest:("+gbest[0]+","+gbest[1]+","+gbest[2]+","+gbest[3]+")  fitness="+gbest_fitness);
        }
        Sensor s = new Sensor();
        s.setLatitude(gbest[0]);
        s.setLongtitude(gbest[1]);
        s.setLongtitude(gbest[2]);
        s.setSecTime(gbest[3]);
        System.out.println(gbest[0]+" "+gbest[1]+" "+gbest[2]+" "+gbest[3]);
        return s;
    }
    
    /**
     * 
     * @return
     */
    public static double rand() {
        return new Random().nextDouble();
    }
}
