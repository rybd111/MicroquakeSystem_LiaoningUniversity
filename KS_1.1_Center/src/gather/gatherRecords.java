/**
 * 
 */
package gather;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.h2.constant.Parameters;
import com.ibm.icu.util.Calendar;

/**
 * @author Hanlin Zhang
 */
public class gatherRecords {

	private String[] filePath = new String[Parameters.SensorNum];
	private String[] index = new String[Parameters.SensorNum];
	
	
	/**random produce data.*/
	private String []producePath = new String[Parameters.SensorNum];
	
	public gatherRecords() {
		
	}
	
	public void createTest(String[] producePath) throws ParseException, IOException {
		this.producePath = producePath;
		//produce several txt files to test this subprocedure.
		randRecords(this.producePath);
	}
	
	
	public void randRecords(String[] producePath) throws ParseException, IOException {
		this.producePath = producePath;
		String startT = "2020-05-01 00:00:00";
		for(int i=0;i<this.producePath.length;i++) {
			File file[] = new File[this.producePath.length];
			file[i] = new File(this.producePath[i]);
			BufferedWriter out = new BufferedWriter(new FileWriter(file[i],true));
			Date d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(startT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			for(int j=0;j<100;j++) {
				
				int addT = (int)(1000*Math.random());
				cal.add(Calendar.MILLISECOND, addT);
				Date d1 = cal.getTime();
				String returnDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(d1);//加一天后结果
				out.write(returnDateStr+"\t0\r\n");//write to txt file we use to test in procedure next.
				out.flush();
			}
		}	 
	}
	
	public void obtainRecordInSecond(String[] producePath) throws IOException {
		//all files' absolute path.
		this.producePath = producePath;
		//all files we need to read.
		File file[] = new File[this.producePath.length];
		//all time records.
		String [][] timerecords = new String[this.producePath.length][0];
		
		int count = 1;
		
		String content[] = new String[0];
		//obtain all time records for getting ready to compare.
		for(int i=0;i<this.producePath.length;i++) {
			file[i] = new File(this.producePath[i]);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file[i]), "utf-8"));//GBK
			String line = null;
			while((line=reader.readLine())!=null){//when the procedure read the last line in csv file, the length of it will become 1.
	            String item[] = line.split("\t");
	            if(item.length>1) {
	            	timerecords[i] =  Arrays.copyOf(timerecords[i], timerecords[i].length+1);
	            	timerecords[i][timerecords[i].length-1] = item[0];//extract time from each line.
	            }
	            if(count==index[i][count]) {
	            	content =  Arrays.copyOf(content, content.length+1);
	            	content[content.length-1] = line;//extract time from each line.
	            }
	            count++;
	        }
			count++;
		}
//        System.out.println(timerecords.length);
		//compare to these records in timerecords.
		//create index in days.
		//the baseline date.
		
		//index of each file recording in days.
		int [][] index = new int[file.length][0];
		String [][] indexname = new String[file.length][0];
//		boolean flag = true;//the sign.
		
//		for(int i=0;i<timerecords.length;i++) {
//			String baseline[] = timerecords[i][0].split("-");
//			
//			for(int j=0;j<timerecords[i].length;j++) {
//				String item[] = timerecords[i][j].split("-");
//				if(baseline[0].equals(item[0])==false || baseline[1].equals(item[1])==false || baseline[2].substring(0, 2).equals(item[2].substring(0, 2))==false) {//one of the date are different from the front, then we update the index of this file.
//					index[i] = Arrays.copyOf(index[i], index[i].length+1);
//					indexname[i] = Arrays.copyOf(indexname[i], indexname[i].length+1);
//					index[i][index[i].length-1]=j;//update the index.
//					indexname[i][index[i].length-1] = timerecords[i][j];
//					baseline = timerecords[i][j].split("-");//update baseline.
//				}
//			}
//		}
//		System.out.println(index[0].length);
		//count number.
		
		int linenum = 0;
		int countnum = 0;
		for(int i=0;i<timerecords.length;i++) {
			String baseline = timerecords[i][linenum];
			for(int j=0;j<timerecords[i].length;j++) {
				if(baseline.equals(timerecords[i+count][j])) {
					//record the same record's line.
					index[i+count][countnum] = j;
					index[i][countnum] = linenum;
					countnum++;
				}
				//reset the circle variable.
				if(j==timerecords[i].length-1) {
					j=0;
					count++;
				}
			}
			linenum++;
		}
		
		count = 0;
		linenum = 0;
		
		
		
	}
	/**
	 * test our new distribution procedure
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException, IOException {
		String[] filePath = new String[5];
		
		filePath[0] = "D:/data/testRecords/S/1.txt";
		filePath[1] = "D:/data/testRecords/U/2.txt";
		filePath[2] = "D:/data/testRecords/Y/3.txt";
		filePath[3] = "D:/data/testRecords/T/4.txt";
		filePath[4] = "D:/data/testRecords/V/5.txt";
		
		gatherRecords g = new gatherRecords();
//		g.createTest(filePath);
		g.obtainRecordInSecond(filePath);
	}

}
