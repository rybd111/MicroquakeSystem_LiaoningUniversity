package testKS;

import java.util.concurrent.CountDownLatch;

import bean.Location;

public class Boss implements Runnable
{
    private CountDownLatch downLatch;
    private boolean isDown=false;
    
    private Location aLocation;
    
    public Boss(CountDownLatch downLatch, Location aLocation) {
		super();
		this.downLatch = downLatch;
		this.isDown = isDown;
		this.aLocation = aLocation;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public Boss(CountDownLatch downLatch, Boolean isDown) {
		super();
		this.downLatch = downLatch;
		this.isDown = isDown;
	}

	public Boss(CountDownLatch downLatch)
    {
        this.downLatch = downLatch;
    }

    
    public void run()
    {
        System.out.println("老板正在等所有的工人干完活......");
        try
        {
            this.downLatch.await();
        }
        catch (InterruptedException e)
        {
        }
        System.out.println("工人活都干完了，老板开始检查了！");
        this.isDown=true;
        aLocation.getSite();
        System.out.println("location 位置"+aLocation.getSite());
        if (isDown) {

			//lock.notify();

			final Thread clientThread = new Thread(new Runnable() {
				public void run() {
					 //new ThreadClient().chat();
					System.out.print("chart");
				}
			});
			clientThread.start();

			System.out.println("已经发出了通知");
		}

        
        
        this.isDown=true;
    }


}
