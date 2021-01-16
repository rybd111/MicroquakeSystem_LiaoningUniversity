package mutiThread;

public class ReconnectToRemoteDisk_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] fileStr = {"u:/","t:/","w:/","x:/","y:/"};
		String[] fileStr1 = new String[6];
		ReconnectToRemoteDisk re = new ReconnectToRemoteDisk(3,fileStr);
		fileStr1 = re.rearrange(0);
		for(int i=0;i<fileStr1.length;i++)
			System.out.println("conse+ "+fileStr1[i]);
	}
}
