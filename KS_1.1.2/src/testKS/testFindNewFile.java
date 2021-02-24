package testKS;
import java.io.*;

import read.yang.readFile.*;
public class testFindNewFile {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FindNewFile find = new FindNewFile();
		String path = "w:/";
		//String path = "D:/环境+项目/测试查找最新文件";
		int th = 0;
		File file1;
		//while(true){
			file1 = find.researchFile(path, th);
			System.out.println(file1.getAbsolutePath()+file1.lastModified());
			Thread.sleep(2000);
		//}
	}

}
