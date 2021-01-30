package testKS;

import com.h2.constant.Parameters;

import mutiThread.ReconnectToRemoteDisk;

public class ReconnectToRemoteDisk_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] fileStr = {"u:/","t:/","w:/","x:/","y:/"};
		String[] fileStr1 = new String[4];
		ReconnectToRemoteDisk re = new ReconnectToRemoteDisk(3,fileStr);
		for(int i=re.orderNum-1;i>=0;i--) {
			fileStr1 = re.rearrange(i);
			for(int j=0;j<fileStr1.length;j++)
				System.out.println("conse+ "+fileStr1[j]);
			
			System.out.println();
			System.out.println("fileStr1 "+fileStr1.length);
			System.out.println("SensorNum "+Parameters.SensorNum);
		}
		
		for(int i=re.orderNum-1;i>=0;i--) {
			fileStr1 = re.rearrange(i);
			for(int j=0;j<fileStr1.length;j++)
				System.out.println("conse+ "+fileStr1[j]);
			
			System.out.println();
			System.out.println("fileStr1 "+fileStr1.length);
			System.out.println("SensorNum "+Parameters.SensorNum);
		}		
	}
}
