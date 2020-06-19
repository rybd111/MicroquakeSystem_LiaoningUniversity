package mutiThread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import testKS.WorkerT;

public class SleepAndWaitThread2 {
	public static void main(String[] args) {
		ReadThread one = new ReadThread();
		CaculateThread two = new CaculateThread();
		one.start();
		two.start();
	}
}

class ReadThread extends Thread {

	@Override
	public void run() {
		synchronized (SleepAndWaitThread2.class) {
			
			/*WorkerT aWorker =new WorkerT("工人1");
			Thread w1 =new Thread(aWorker);
			w1.start();
			try {
				w1.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			while (true) {
				
				System.out.println("1");
				//读10次杨兴东程序
				int num=0;
				while(true)
				{
					num++;
					System.out.print(num);
					if(num==10)
						break;
				}
				System.out.println("");
				
				
				
				try {
					SleepAndWaitThread2.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SleepAndWaitThread2.class.notify();
			}
		}
	}
}

class CaculateThread extends Thread {

	@Override
	public void run() {
		synchronized (SleepAndWaitThread2.class) {
			while (true) {
				System.out.println("2");
				SleepAndWaitThread2.class.notify();
				try {
					SleepAndWaitThread2.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	class WorkerT  implements Runnable{

		private String name;
		
		public WorkerT( String name)
	    {
	        
	        this.name = name;
	    }
		
		
		

		public void run() {
			// TODO Auto-generated method stub
			this.doWork();
	        try
	        {
	            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
	        }
	        catch (InterruptedException ie)
	        {
	        }
	        System.out.println(this.name + "活干完了！");
	        //this.downLatch.countDown();
	        System.out.println(Thread.currentThread().getName() + "结束. 还有" + " 个线程");  
	        return;
		}

		private void doWork()
	    {
			
			
	        System.out.println(this.name + "正在干活...");
	    }

		
	}
	
	
}
