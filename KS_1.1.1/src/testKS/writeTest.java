package testKS;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import com.h2.constant.Parameters;

import DataExchange.Sensor;

public class writeTest {
	/**
	 * a test class to test each class under the package com.h2.backupData.
	 * @param args 
	 * @throws ParseException
	 * @throws IOException
	 * @author Hanlin Zhang.
	 */
	public static void main(String[] args) throws ParseException, IOException {
		String filePath=Parameters.AbsolutePath_CSV3;

//		Vector<String> C = new Vector<String>();
//		C.add("1 2 3 4 5 6 7 8");//1-6 are the data of quack, 7 is the time stamp, 8 is the motivation position.
//		int []line=new int[5];
		Sensor[] s = new Sensor[2];
		s[0] = new Sensor();
		s[1] = new Sensor();
		
		String d="1972-09-01 01:03:10";
//		
		s[0].setAbsoluteTime(d);
//		s[0].setSecTime(0.287);
//		s[0].setSensorNum(4);
//		s[1].setSecTime(0.333);
//		s[1].setSensorNum(2);
//		
//		WriteRecords.Write(s, s[0], "H:/1.csv");
		
		s[0].setSensorNum(3);
		s[1].setSensorNum(1);
		s[0].setSecTime(0.1);
		s[1].setSecTime(0.1);
//		WriteRecords.Write(s, s[0], s[0],"H:/1.csv","1","五台站");
				
//		WriteRecords.Write(s, s[0], "H:/1.csv");
//		WriteMotiData.writeToCSV(C, filePath, line);
	}

}
