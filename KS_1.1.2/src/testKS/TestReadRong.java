package testKS;

import java.io.File;
import java.util.Vector;


import controller.ADMINISTRATOR;
import read.yang.readFile.ReadData;
import read.yang.readFile.findNew;

public class TestReadRong {

	public static void main(String[] args) throws Exception {
		ADMINISTRATOR manager=new ADMINISTRATOR();
		String file;
		File f=null;
		f=findNew.find("I:\\矿山\\矿山数据\\新设备数据\\1\\",1,manager);
		System.out.println(f.getAbsolutePath());
		file=f.getAbsolutePath();
		ReadData readData = new ReadData(file,1,manager);
		Vector<String> temVector = new Vector<String>();
		Vector<String> aVector= new Vector<String>();
//		temVector = readData.getData();
		/*aVector.addAll(temVector);
		temVector = readData.getData(file,1);
		aVector.addAll(temVector);
		System.out.println(aVector);*/
		System.out.println(temVector);
	}

}
