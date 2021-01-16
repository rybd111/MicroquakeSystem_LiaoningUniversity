package visual_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jfree.data.time.TimeSeries;

import com.h2.constant.Parameters;
/**
 * Read the specific format as csv file.
 * Return the Z direction's consequence of the CSV.
 * Z direction includes 6 or 3 channels in red box, and includes 3 channels in the device of the teacher Mr.Ma.
 * @author Hanlin Zhang
 */
public class readCSV {

	protected File file = null;
	protected int senNum = 0;
	
	//the row number denote the number of sensors in using.
	//the line number denote the number of each sensor's file have.
	protected String SenChannel[][] = new String[(Parameters.startTime+Parameters.endTime)*5000][];
	protected String SenChannelNum[][] = new String[(Parameters.startTime+Parameters.endTime)*5000][];
	protected String Time[]=new String[(Parameters.startTime+Parameters.endTime)*5000];
	
	//the motivation position.
	protected int motiPos[];
	
	
	
	public readCSV() {
		System.out.println("You need specify a specific absolutely filePath on your disk.");
	}
	
	/**
	 * filePath must be a absolute path on a disk.
	 * moti
	 * @param filePath
	 * @author Hanlin Zhang.
	 */
	public readCSV(String filePath) {
		
		this.file = new File(filePath);
		//determine the number of sensor.
		String fileS[] = filePath.split("/");
		String fileSS = fileS[fileS.length-1].split(" ")[0];
		this.senNum = fileSS.length();
		motiPos = new int[senNum];//the motivated position of each sensor.
		SenChannel = new String[(Parameters.startTime+Parameters.endTime)*(Parameters.FREQUENCY+200)][this.senNum];//the channel of all sensors' z axis data.
		SenChannelNum = new String[(Parameters.startTime+Parameters.endTime)*(Parameters.FREQUENCY+200)][this.senNum];
	}
	
	/**
	 * read the csv file's specific contents in coal mine files.
	 * we need to know csv file must have different format, so this function only adapt for our current csv file.
	 * @return TimeSeries in jfreeChart type.
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void readContents() throws NumberFormatException, IOException {
		int count=0;
        //(文件完整路径),编码格式
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));//GBK
        
//            reader.readLine();//显示标题行,没有则注释掉
//            System.out.println(reader.readLine());
        
        String line = null;
        while((line=reader.readLine())!=null){//when the procedure read the last line in csv file, the length of it will become 1.
            String item[] = line.split(",");//CSV格式文件时候的分割符,我使用的是,号
            if(item.length>1) {
	            Time[count] = item[0];
	            //save different data from csv file.
	            for(int i=0;i<this.senNum;i++) {
	            	SenChannel[count][i] = item[6+i*8];
	            	SenChannelNum[count][i] = item[8+i*8];
	            	motiPos[i] = Integer.parseInt(item[7+i*8]);
	            }
	            count++;
            }
        }
//        return null;
	}
	
	/**
	 * For testing this class.
	 * @param args
	 * @author Hanlin Zhang.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		readCSV r = new readCSV("D:/data/ConstructionData/5moti/124637 2020-05-20 13-52-55`13.csv");
		r.readContents();
		System.out.println(" ");
	}

}
