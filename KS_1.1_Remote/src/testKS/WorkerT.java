package testKS;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WorkerT  implements Runnable{

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
