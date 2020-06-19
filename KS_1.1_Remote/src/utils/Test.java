package utils;

import java.util.Date;

import com.h2.backupData.WriteRecords;


public class Test {

	/**
	 * 对齐测试工具类
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String date1Str = "20161126173901";
//		String date2Str = "20150417130003";
//		String date3Str = "20150417130005";
//		String date4Str = "20150417130000";
//		//String date5Str = "20150417130007";
//	
//		String fileName1 = "B:/Users/lemo/Documents/Tencent Files/1159639005/FileRecv/gygc_161126173902.HFMED";
//		ReadDataSegmentHead readDateSegmentHead1 = new ReadDataSegmentHead();
//		String date5Str = readDateSegmentHead1.readDataSegmentHead(fileName1);
//		System.out.println(date5Str.toString());
//		
//		
//		String[] aStrings =new String[5];
//		
//		aStrings[0]=date1Str;
//		aStrings[1]=date2Str;
//		aStrings[2]=date3Str;
//		aStrings[3]=date4Str;
//		aStrings[4]=date5Str;
//		
//		
//		
//		Date date1 = String2Date.str2Date(date1Str);
//		Date date2 = String2Date.str2Date(date2Str);
//		Date date3 = String2Date.str2Date(date3Str);
//		Date date4 = String2Date.str2Date(date4Str);
//		Date date5 = String2Date.str2Date(date5Str);
//
//		/*Date[] aDates = new Date[] { date1, date2, date3, date4, date5 };
//		Date DateMax = FindMaxDate(aDates);
//*/
//		int[] DateDifferntInt = new int[5];
//		DateArrayToIntArray aDateArrayToIntArray =new DateArrayToIntArray();
//		DateDifferntInt =aDateArrayToIntArray.IntArray(aStrings);
//		/*for (int i = 0; i < DateDifferntInt.length; i++) {
//			DateDifferntInt[i] = Math.abs((int) ((aDates[i].getTime() - DateMax
//					.getTime()) / 1000));
//		}*/
//
//		for (int i = 0; i < DateDifferntInt.length; i++) {
//			System.out.println(DateDifferntInt[i]);
//		}
//		//System.out.println(DateMax.toString());
//		System.out.println(TimeDifferent.DateDifferent(date1Str, date2Str));
		
//		double d1 = -1.3;
//		double d2 = -0.6;
//		
		
		String d1 = "2019-1-15 09:10:01.339";
//		double d2 = 43841.0;
//		d1 = d1.substring(0,9);
		
		int con = 0;
//		con=TimeDifferent.DateDifferent(d1, WriteRecords.lastDate);
		System.out.println(d1.substring(0, 9));
//		System.out.println(d2/5000.0);
		
		
		
	}

	private static Date FindMaxDate(Date[] aDates) {
		// TODO Auto-generated method stub
		Date MaxDate = aDates[0];

		for (int i = 0; i < aDates.length; i++) {

			int cha = (int) ((MaxDate.getTime() - aDates[i].getTime()) / 1000);

			if (cha <= 0) {
				MaxDate = aDates[i];
			}

		}

		return MaxDate;
	}

}
