package com.yang.readFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.backupData.WriteRecords;
import com.h2.constant.Parameters;
import com.rqma.history.AlignFile;
import com.rqma.history.FindHistoryFile;
import com.yang.readFile.findNew;
import com.yang.readFile.ReadDataSegmentHead;
import com.yang.readFile.ReadHfmedHead;
import com.yang.readFile.ReadSensorProperties;
import com.yang.unity.DataUnity;
import com.yang.unity.HfmedHead;
import com.yang.unity.SensorProperties;
import com.yang.util.Byte2Short;
import com.yang.util.FindByte;

import controller.ADMINISTRATOR;
import mutiThread.DuiQi;
import mutiThread.MainThread;
import utils.Date2String;
import utils.DateArrayToIntArray;
import utils.String2Date;

/**
 * 从文件中读数据 ,只需要调用getData()方法即可
 * 我们之前的想法是利用从文件中取到的segmentNum,segmentRecNum进行两次循环，这样的弊端
 * 就是在一个数据段中没有segmentRecNum个数据，这样就会导致之后的数据都会出错
 * 所以转换一种想法：从文件前读到文件后，每次读14个字节，对这个14个字节的前4个字节
 * 进行判断，如果如果这四个字节转换成字符串的是“HFME” ，则说明读到了数据段头，这时候你在跳过
 * 20个字节即可，继续向下读数据
 *
 * 注意：你要的是数据部分，所以你需要跨过每个数据段的 数据段头 14字节
 * @author NiuNiu, Xingdong Yang, Hanlin Zhang, Chengfeng Liu, rqma, Rui Cao.
 * 
 */
public class ReadData {
	public static String NetErDate = " ";
	public static int reSensorID = -1;
	public static boolean newData=false;//表示是否有台站有最新文件
	public static boolean netError = false;//有台站对齐完成就置这个变量为true
//	public static boolean LatestNotAccurate=false;
	
	public static int timeInterrupt = -1;//保存网络错误时的时间，可以直接对齐到该位置，防止重复计算
	public static DataUnity dataUnity = new DataUnity();//一条完整数据（包括7个通道，每个通道一个数据点）
	
	
	public ReadData() {
		super();
	}
	/**this is a vector used to store one second data.*/
	private Vector<String> data;//Vector<String>(线程同步数据列表)
	/** 是否第一次读文件 */
	public boolean isFirst = true;
	/**when GPS signal has gone, its value become true*/
	public boolean isBroken = false;
	/** 统计数据字节数 */
	public int ByteCount_data = 0;
	/** 统计段头字节数 */
	public int ByteCount_start = 0;
	/** 秒数计数器  , 每调用一次getData的时候 ，这个计数器就加一 ，表示加一秒*/
	public int timeCount = 0;
	/**the number of sensor.*/
	private int sensorID = 0;
	/** 调用次数 */
	private int countSetState=0;
	
	/** 上次访问文件名 */
	private String nameF1 = " ";
	/** 数据段总数 */
	private int segmentNum;
	/** 每个数据段中数据的个数 */
	private int segmentRecNum;
	
	/** 通道个数*/
	private int channelNum;
	/** 通道个数字符串用于读取*/
	private int channel;
	/** 数据头、文件头、字节数、电压起始、电压结束*/
	private int datahead;

	private int bytenum;
	private int voltstart;
	private int voltend;
	
	
	/** 第一条数据的日期 */
	private Date date;//
	/** 通道单位大小 */
	private float chCahi;
	/** 最新文件所在的目录路径*/
	private String filePath;
	/** the file to read */
	private File file;
    /** 流的重定向 */
	private BufferedInputStream buffered;
	/** 存放文件的字节 */
	private byte[] dataByte;
	/** 对齐要跳过的字节 */
	private byte[] dataByte1;
	
	/** 存放1秒数据的字节 */
	private byte[] dataYiMiao;
	/** 三个字节进行显示 */
	private byte[] readsan;
	private String newS;
	
	private ADMINISTRATOR manager;
	
	/**
	 * @param filePath
	 *     注意 ：fileName的格式：C:/Users/NiuNiu/Desktop/HYJ/XFJ/
	 * @return
	 * @throws Exception
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu, Ruiqiang Ma, Rui Cao.
	 */
	 
	 @SuppressWarnings("unused")
	public ReadData(String path, int th, ADMINISTRATOR manager) throws Exception {
		 this.manager = manager;
		 if(Parameters.offline==true) {
			 if(manager.isMrMa[th]==false) {//offline Mr. Liu.
				 this.file = FindHistoryFile.getFile(path, th);
				 HfmedHead hfmedHead = new ReadHfmedHead().readHead(file);//读文件头，文件头内容
				 this.segmentNum = hfmedHead.getSegmentNum();//从文件头中获得段的数量
				 this.segmentRecNum = hfmedHead.getSegmentRecNum();// 获得每个段的数据条目数
				 this.channelNum = hfmedHead.getChannelOnNum();
				 if(channelNum==7) {
					 this.channel=123456;
					 this.datahead=20;
					 this.bytenum=14;
					 this.voltstart=12;
					 this.voltend=13;
				 }
				 else if(channelNum==4) {
					 this.channel=456;
					 this.datahead=26;
					 this.bytenum=8;
					 this.voltstart=6;
					 this.voltend=7;
				 }
				 this.date = new ReadDataSegmentHead().readDataSegmentHead(file);// 从第一个数据段头中获得数据文件起始记录时间
				 System.out.print(path+"磁盘创建读对象的时间"+date);System.out.println();
				 WriteRecords.lastDate = Date2String.date2str(date);//update the date for the record P arrival txt file's name.
				 SensorProperties[] sensor = new ReadSensorProperties().readSensorProperties(file);
				 this.chCahi = sensor[0].getChCali();//由于通道单位都一样，所以用第一个通道单位就可以
				 data = new Vector<String>();//用于存放数据
				 dataByte = new byte[this.bytenum];//改为四个通道，4567
				 this.buffered = new BufferedInputStream(new FileInputStream(file),8*5000*8);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
				 buffered.read(new byte[Parameters.WenJianTou]);// 跳过文件头(186)，通道信息(14*7)，他们一共242个字节
			 }
			 else if(manager.isMrMa[th]==true) {//offline Mr. Ma.
				 this.channel=456;
				 this.filePath = path;//路径更新
				 this.file=new File(path);
				 this.date = new ReadDataSegmentHead().readDataSegmentHead_MrMa_Date(DuiQi.file1[th]);// 从第一个数据段头中获得数据文件起始记录时间
				 System.out.println(path+"磁盘创建读对象的时间"+date);
				 data = new Vector<String>();//用于存放数据
				 dataByte = new byte[Parameters.Shi];//每次读10个字节的字节数组
				 dataByte1 = new byte[Parameters.ShuJu];//每次跳过210个字节的数据。
				 dataYiMiao=new byte[Parameters.YIMiao];
				 readsan=new byte[Parameters.San];
				 this.newS=null;
				 this.buffered = new BufferedInputStream(new FileInputStream(DuiQi.file1[th]),10*5000*8);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
			 }
		}
		else {//online Mr. Liu.
			if(manager.isMrMa[th]==true) {
				this.channel=456;
				this.filePath = path;//路径更新
				this.file=new File(path);
				this.date = new ReadDataSegmentHead().readDataSegmentHead_MrMa_Date(DuiQi.file1[th]);// 从第一个数据段头中获得数据文件起始记录时间
				System.out.println(path+"磁盘创建读对象的时间"+date);
				data = new Vector<String>();//用于存放数据
				dataByte = new byte[Parameters.Shi];//每次读10个字节的字节数组
				dataByte1 = new byte[Parameters.ShuJu];//每次跳过210个字节的数据。
				dataYiMiao=new byte[Parameters.YIMiao];
				readsan=new byte[Parameters.San];
				this.newS=null;
				this.buffered = new BufferedInputStream(new FileInputStream(DuiQi.file1[th]),10*5000*8);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
			}
			else {
				this.filePath = path;//路径更新
				HfmedHead hfmedHead = new ReadHfmedHead().readHead(DuiQi.file1[th]);//读文件头，文件头内容
				this.segmentNum = hfmedHead.getSegmentNum();//从文件头中获得段的数量
				this.segmentRecNum = hfmedHead.getSegmentRecNum();// 获得每个段的数据条目数
				this.channelNum = hfmedHead.getChannelOnNum();
				if(channelNum==7) {
					this.channel=123456;
					this.datahead=20;
					this.bytenum=14;
					this.voltstart=12;
					this.voltend=13;
				}
				else if(channelNum==4) {
					this.channel=456;
					this.datahead=26;
					this.bytenum=8;
					this.voltstart=6;
					this.voltend=7;
				}
				this.date = new ReadDataSegmentHead().readDataSegmentHead(DuiQi.file1[th]);// 从第一个数据段头中获得数据文件起始记录时间
				System.out.print(path+"磁盘创建读对象的时间"+date);System.out.println();
				WriteRecords.lastDate = Date2String.date2str(date);//update the date for the record P arrival txt file's name.
				SensorProperties[] sensor = new ReadSensorProperties().readSensorProperties(DuiQi.file1[th]);
				this.chCahi = sensor[0].getChCali();//由于通道单位都一样，所以用第一个通道单位就可以
				data = new Vector<String>();//用于存放数据
				dataByte = new byte[this.bytenum];//改为四个通道，4567
				this.buffered = new BufferedInputStream(new FileInputStream(DuiQi.file1[th]),8*5000*8);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
				buffered.read(new byte[Parameters.WenJianTou]);// 跳过文件头(186)，通道信息(14*7)，他们一共242个字节
			}
		}
	}

	public synchronized void readData(String sName,int sID) throws Exception {//读取整秒数据
		int by = -1;
		boolean fileisOver = false;
		int LoopCount=0;
		if(ReadData.newData == true){
			System.out.println("其余台站进入while时，产生新文件");
			return;
		}
		else{
		/**the number of each sensor, it's the same as the start sequence.*/
		this.sensorID = sID;
		/**the two flag diagnose a second over or not.*/
		boolean flag1 = false;
		boolean flag2 = false;
		/**we will clear the data of data variable at each beginning of the readData function.*/
		data.clear();
		/**define two variables to storage date and volt of the 8 channel-GPS volt.*/
		short volt = 0;
		/**define a date format to storage the date in a self-defined format.*/
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		/**read　one second data until encountering the low volt or the end of the current file.*/
		while(true){
			if(ReadData.newData==false){
			try{
				if(fileisOver == false) {
					if((by = buffered.read(dataByte)) < dataByte.length){//不够8字节读够8字节数据
						fileisOver=true;
						/**wait a little time to make the sensor has time to write data into the file.*/
						Thread.sleep(200);
						continue;
					}
				}
				else {
					/**if by != -1 then the file is not over.*/
					if(by!=-1) {
						if(by>=4) {//diagnose its greater than 4 - is "HFME"
							byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
				   			if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
				   				buffered.skip(this.datahead);//再跳过26字节，就到数据了
				   				buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
				   			}
				   			else {
				   				buffered.skip(this.bytenum-by);//不够8字节，跳过当前这条数据
								fileisOver=false;
								continue;
				   			}
						}
						else {
							byte[] feature = new byte[4];
							for(int i=0;i<by;i++) feature[i] = dataByte[i];							
							int count=0;
							byte[] remain = new byte[4-by];
							buffered.read(remain);
							for(int i=by;i<4;i++) {
								feature[i] = remain[count];
								count++;
							}
							if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
				   				buffered.skip(this.datahead);//再跳过26字节，就到数据了
				   				buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
							}
							else {
								buffered.skip(this.bytenum-4);//不够8字节，跳过当前这条数据
								fileisOver=false;
								continue;
							}
						}
					}
					/**or we read the next dataByte to confirm the file is over.*/
					else{
						if(buffered.read(dataByte)==-1) {//到达末尾
							this.countSetState++;
							ReadData.newData = setState();//判断是否产生了新文件
							if(ReadData.newData == true){//产生了新文件，需重新对齐
								System.out.println("有"+this.countSetState+"次读取到了不够8字节的数据");
								System.out.println("第"+sensorID+"号台站"+sName+"产生了新文件");
								data.clear();timeCount = 0;
								return;
							}else{
//								fileisOver=false;
								continue;//直接等待直到出现数据，写入data容器
							}
						}
					}
				}
				}
				catch(IOException e){
					System.out.println(sensorID+"号台站"+sName+"产生了网络错误，记录当前错误时间！");
					if(timeInterrupt==-1){timeInterrupt = timeCount;System.out.println("##########"+timeInterrupt+"盘号"+sensorID);}//保存了网络错误时间，若在接下来等待的时间内未产生新文件，则对齐时加上该时间，避免重复计算前面的数据
					if(ReadData.reSensorID==-1){ReadData.reSensorID = sensorID;System.out.println("!!!!!!!!!"+ReadData.reSensorID);}//记录台站号，用于记录发生网络错误的盘符，便于统计结果
					ReadData.netError = true;//网络错误，同时记录产生网络错误的盘符及年月日
					return;
				}
				LoopCount++;
				
				byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
	   			if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
	   				buffered.skip(this.datahead);//再跳过26字节，就到数据了
	   				buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
	   			}
				
	   			DataElement dataElement = getDataElementFromDataBytes(dataByte,sensorID);
	   			
	   			Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
	   			calendar.setTime(this.date);
	   			calendar.add(Calendar.SECOND, timeCount);
	   			Date startDate1 = calendar.getTime();
	   			String date1 = format2.format(startDate1);
	   			dataElement.setDataCalendar(date1);//置入数据读取时间
	   			data.add(dataElement.toString());//存储7个通道的数据至data容器
	   			
	   			volt = DataTypeConversion.joint2BytesToShort(dataByte[this.voltstart], dataByte[this.voltend]);//获取电平数据
	   			if (isBroken == false) {//之前对齐时电压没缺失，在读一秒时，出现电压缺失
	                if (LoopCount > (Parameters.FREQUENCY+210)) {//循环5010(一秒最多5010条数据)次时，还没退出，表明文件电平缺失，
	                    isBroken = true;
	                    System.out.println(MainThread.fileStr[sID]+"出现GPS缺失");
	                    break;
	                }
	                
	                //判断1s是否结束，结束跳出while
	                if (Math.abs(volt) < 1000 ) {
//							data.remove(data.size() - 1) ;//保证是正好10s的数据，因为最后一个低电平的数据也被读进来了
	                    flag2 = true;
	                }
	                if (Math.abs(volt) > 5000 && flag2) flag1 = true;
	                if (flag1 && flag2) {//高电平结束，说明1s数据结束，跳出while(true)，一个读过程结束
	                    timeCount++;
	                    break;
	                }
	            } else {//在对齐时，就已经出现电压缺失，直接进入这里
	                if (LoopCount >= (Parameters.FREQUENCY+200)) {
	                	timeCount++; //即使电压缺失了，时间也得跟着走，不然调用calendar.add(Calendar.SECOND, timeCount)是错的
	                    break;//必须读够1秒的数据才能退出(1秒大概5000条数据)
	                }
	            }
			}
			else{
				return;
			}
		}// end while(true)
		}//end if
	}
	
	/**
	 * when we use Mr Ma's sensor, we will use this function to read data as second unit.
	 * @param sName
	 * @param sID
	 * @throws Exception
	 * @author Rui Cao.
	 */
	public synchronized void readData_MrMa(String sName,int sID) throws Exception {//读取整秒数据
		int count=0;//标志位，初始是0
		int count1=0;
		String MaxDateS=null;
		boolean fileisOver = false;//标识程序是否到达末尾
		if(ReadData.newData == true){
			System.out.println("其余台站进入while时，产生新文件");
			return;
		}
		else{
			String sensorName = sName;//台站号，与MainThread中的String fileStr1（盘号）一一对应，用于设置是否读到文件末尾readToNew的标志位
			this.sensorID = sID;
			
			data.clear();// 每次调用readData方法的时候将data数据区清空
	
			String dateCus;    //保存7个通道的数据
			
//			DateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
//			Date startDate = format1.parse(date);
			//从对齐位置开始，读1秒的数据
			while(true){
				if(ReadData.newData==false){
					//indicate the end of file.
					try{
						if(fileisOver == false) {				
							if(buffered.read(dataByte)==-1) {
								fileisOver=true;
								continue;
							}
						}
						else {
								ReadData.newData = setState();//判断是否产生了新文件
								if(ReadData.newData == true){//产生了新文件，需重新对齐
									System.out.println("第"+sensorID+"号台站"+sName+"产生了新文件");
									data.clear();timeCount = 0;
									return;
								}
								else{
									continue;//直接等待直到出现数据，写入data容器
								}
							}
					}
					catch(IOException e){
						System.out.println(sensorID+"号台站"+sName+"产生了网络错误，记录当前错误时间！");
						if(ReadData.reSensorID==-1){ReadData.reSensorID = sensorID;System.out.println("!!!!!!!!!"+ReadData.reSensorID);}//记录台站号，用于记录发生网络错误的盘符，便于统计结果
						ReadData.netError = true;//网络错误，同时记录产生网络错误的盘符及年月日
						return;
					}
					//core of algorithm
					byte[] DATE1 = {dataByte[0]};byte[] DATE2 = {dataByte[1]};byte[] DATE3 = {dataByte[2]};byte[] DATE4 = {dataByte[3]};
					byte[] ID = {dataByte[6]};byte[] zhenhao = {dataByte[7],dataByte[8]};byte[] zengyi = {dataByte[9]};
					String DATEs1 = FindByte.bytesToHexString(DATE1);String DATEs2 = FindByte.bytesToHexString(DATE2);String DATEs3 = FindByte.bytesToHexString(DATE3);String DATEs4 = FindByte.bytesToHexString(DATE4);
					String IDs = FindByte.bytesToHexString(ID);String zhenhaos = FindByte.bytesToHexString(zhenhao);
					String zengyis = FindByte.bytesToHexString(zengyi);//判断是否是帧头
					
					if(		   DATEs1.compareTo("00") !=0 
							&& DATEs2.compareTo("00") !=0 
							&& DATEs3.compareTo("00") !=0 
							&& DATEs4.compareTo("00") !=0 
							&& IDs.compareTo("00") == 0&&zhenhaos.compareTo("0000") != 0&&zengyis.compareTo("00") == 0){
						if(newS==null) {
							MaxDateS = DateArrayToIntArray.FindFourByte(DuiQi.file1[sID]);
						}
						else {
							MaxDateS=newS;
						}
						
						byte[] Time = {dataByte[0],dataByte[1],dataByte[2],dataByte[3]};
						String Stime = FindByte.bytesToHexString(Time);
						
						
						if(Stime.compareTo(MaxDateS)==0) {
							buffered.read(dataByte);
							for(int j=0;j<10;j++) {
								dataYiMiao[count] = dataByte[j];
								count=count+1;
							}		
						}
						else {
							for(int i=0;i<count/3;i++) {
								readsan[0]=dataYiMiao[2*i+i];
								readsan[1]=dataYiMiao[2*i+i+1];
								readsan[2]=dataYiMiao[2*i+i+2];
								DataElement dataElement = getDataElementFromDataBytes(readsan,sID);
								String date1 = format2.format(date);
					   			dateCus = date1;
					   			dataElement.setDataCalendar(dateCus);
					   			data.add(dataElement.toString());
							}
							newS=Stime;
							timeCount++;
							count1 = count;
							Calendar calendar = Calendar.getInstance(); 
				   			calendar.setTime(date);
				   			calendar.add(Calendar.SECOND, timeCount);
//				   			Date startDate1 = calendar.getTime();
				   			date = calendar.getTime();
//				   			String date1= format2.format(startDate1);
//				   			startDate = format2.parse(date1); //this startDate should be the same as startDate1? but if the startDate will change with timeCount, the timeCount is trivial.
				   			buffered.read(dataByte);
				   			for(int j=0;j<10;j++) {
								dataYiMiao[count] = dataByte[j];
								count=count+1;
							}	
//				   			System.out.println("enter into hop");
						}
					}
					else {
						for(int j=0;j<10;j++) {
							dataYiMiao[count] = dataByte[j];
							count=count+1;
						}
					}
				}
				else	
					return;
				
				if(count==(Parameters.FREQUENCY+200)*3) {
					for(int i=0;i<((Parameters.FREQUENCY+200)*3-count1)/3;i++) {
						readsan[0]=dataYiMiao[count1+(2*i+i)];
						readsan[1]=dataYiMiao[count1+(2*i+i+1)];
						readsan[2]=dataYiMiao[count1+(2*i+i+2)];
						DataElement dataElement = getDataElementFromDataBytes(readsan,sID);
						String date1 = format2.format(date);
			   			dateCus = date1;
			   			dataElement.setDataCalendar(dateCus);//置入数据读取时间
			   			data.add(dataElement.toString());//存储数据至data容器
//			   			date=date1;//更新时间为下一秒数据的开始时间。the date1 should equal to date?
			   			timeCount=0;
					}
//					System.out.println("");
					break;
				}
			}//end while.
		}	
	}
	
	/**
	 * @param sName
	 * @param sID
	 * when GPS has gone, we can use this function. Of course, this function used to read offline data, but we also can consider use this function after 
	 * amount of testing, the procedure can select this function when GPS signal has gone with reading online. In my view, I can't agree to revise to this
	 * way before the procedure become a more stable version.
	 * @author rqma
	 * @throws Exception
	 */
	public synchronized void readDataNoGPS_offline(String sName,int sID) throws Exception {//读取整秒数据
		int by = -1;
		boolean fileisOver = false;//标识程序是否到达末尾
		int LoopCount = 0;//解决文件缺失问题而定义的
		
		/**the number of each sensor, it's the same as the start sequence.*/
		this.sensorID = sID;
		/**the two flag diagnose a second over or not.*/
		boolean flag1 = false;
		boolean flag2 = false;
		/**we will clear the data of data variable at each beginning of the readData function.*/
		data.clear();
		/**define two variables to storage date and volt of the 8 channel-GPS volt.*/
		short volt = 0;
		/**define a date format to storage the date in a self-defined format.*/
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		/**read　one second data until encountering the low volt or the end of the current file.*/
		while(true){
			
			try{
				if(fileisOver == false) {
					if((by = buffered.read(dataByte)) < dataByte.length){//不够8字节读够8字节数据
						fileisOver=true;
						/**wait a little time to make the sensor has time to write data into the file.*/
						Thread.sleep(200);
						continue;
					}
				}
				else {
					/**if by != -1 then the file is not over.*/
					if(by!=-1) {
						buffered.skip(this.bytenum-by);//不够8字节，跳过当前这条数据
						fileisOver=false;
						continue;
					}
					/**or we read the next dataByte to confirm the file is over.*/
					else{
						if(buffered.read(dataByte)==-1) {//到达末尾
							this.countSetState++;
							ReadData.newData = true;//判断是否产生了新文件
							System.out.println("有"+this.countSetState+"次读取到了不够8字节的数据");
							System.out.println("第"+sensorID+"号台站"+sName+"产生了新文件");
							System.out.println("当前文件的方波个数为："+timeCount);
							data.clear();timeCount = 0;
							return;
						}
					}
				}
			}
			catch(IOException e){
				System.out.println(sensorID+"号台站"+sName+"产生了网络错误，记录当前错误时间！");
				if(timeInterrupt==-1){timeInterrupt = timeCount;System.out.println("##########"+timeInterrupt+"盘号"+sensorID);}//保存了网络错误时间，若在接下来等待的时间内未产生新文件，则对齐时加上该时间，避免重复计算前面的数据
				if(ReadData.reSensorID==-1){ReadData.reSensorID = sensorID;System.out.println("!!!!!!!!!"+ReadData.reSensorID);}//记录台站号，用于记录发生网络错误的盘符，便于统计结果
				ReadData.netError = true;//网络错误，同时记录产生网络错误的盘符及年月日
				return;
			}
		
			LoopCount++;

			byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
   			if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
   				buffered.skip(this.datahead);//再跳过26字节，就到数据了
   				buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
   			}
   			DataElement dataElement = getDataElementFromDataBytes(dataByte,sensorID);
   			
   			Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
   			calendar.setTime(this.date);
   			calendar.add(Calendar.SECOND, timeCount);
   			Date startDate1 = calendar.getTime();
   			String date1 = format2.format(startDate1);
   			dataElement.setDataCalendar(date1);//置入数据读取时间
   			data.add(dataElement.toString());//存储7个通道的数据至data容器

   			volt = DataTypeConversion.joint2BytesToShort(dataByte[this.voltstart], dataByte[this.voltend]);//获取电平数据
			
   			if (isBroken == false) {//之前对齐时电压没缺失，在读一秒时，出现电压缺失
                if (LoopCount > (Parameters.FREQUENCY+210)) {//循环5010(一秒最多5010条数据)次时，还没退出，表明文件电平缺失，
                    isBroken = true;
                    System.out.println(MainThread.fileStr[sID]+"出现GPS缺失");
                    break;
                }
                
                //判断1s是否结束，结束跳出while
                
                if (Math.abs(volt) < 1000 ) {
                    flag2 = true;
                }
                if (Math.abs(volt) > 5000 && flag2) flag1 = true;
                if (flag1 && flag2) {//高电平结束，说明1s数据结束，跳出while(true)，一个读过程结束
                    timeCount++;
                    break;
                }
            } else {//在对齐时，就已经出现电压缺失，直接进入这里
                if (LoopCount >= (Parameters.FREQUENCY+200)) {
                	timeCount++; //即使电压缺失了，时间也得跟着走，不然调用calendar.add(Calendar.SECOND, timeCount)是错的
                    break;//必须读够1秒的数据才能退出(1秒大概5000条数据)
                }
            }
		}// end while(true)
	}
	
	/**
	 * @param sName
	 * @param sID
	 * @return the same as the readDatadui, but this function can work in the environment GPS signal gone.
	 * @author rqma
	 * @throws Exception
	 */
	public int readDataAlign(String sName, int sID) throws Exception {
        String sensorName = sName;
        int ID = sID;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        short volt = 0;//保存电压值
        int LoopCount = 0;//解决文件电压缺失问题而定义的
        int firstTimeCount = 0;//解决文件电压缺失问题而定义的
        boolean isfirstTimeCount = false;
        int remainTimeCount = 0;
       
        while (true) {//从对齐位置开始，读整秒的数据，直到文件末尾
            try {
                if (buffered.read(dataByte) == -1) {
                    System.out.println("无法对齐");
                    return -1;
                }
            } catch (Exception e) {
                System.out.println("对齐过程产生网络错误");
                return -1;//此时只能再次重启程序，但之前可能已产生网络错误，或产生新文件，因此这里不予修改标志位，防止标志位混乱
            }
            LoopCount++;
            byte[] feature = {dataByte[0], dataByte[1], dataByte[2], dataByte[3]};//特征码是4个字节，其内容为"HFME"
            if (new String(feature).compareTo("HFME") == 0) {//读到了数据段头
                buffered.skip(this.datahead);//再跳过26字节，就到数据了
                buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
            }
            if (isBroken == false) {
                byte[] voltByte = FindByte.searchByteSeq(dataByte, this.voltstart, this.voltend);    //提取电压
                volt = Byte2Short.byte2Short(voltByte); //保存

                if (Math.abs(volt) < 1000 ) flag2 = true;
                if (Math.abs(volt) > 5000 && flag2) flag1 = true;
                if (flag1 && flag2) {//高电平结束，说明1s数据结束，计量timeCount

                	if(timeCount==0 && LoopCount>((Parameters.FREQUENCY+200)/2)-(Parameters.distanceToSquareWave*(Parameters.FREQUENCY+200))) {
                    	timeCount++;
                    	System.out.println(sensorName+"数据头时间距离下一方波处的数据量大于频率的一半"+LoopCount);
                    }

                	if ((timeCount-1) == AlignFile.align[ID]) {
                        System.out.println(sensorName + "对齐完毕,timeCount为：" + timeCount);
                        return timeCount;
                    }
                    
                    timeCount++;
                    flag1 = false;
                    flag2 = false;
                    LoopCount=0;
                }
                if (timeCount == 0 && (!isfirstTimeCount)) {
                    firstTimeCount = LoopCount;
                    isfirstTimeCount = true;
                }
                if (LoopCount >= (Parameters.FREQUENCY+210)) {
                    //注意！！！此处有坑，对于好使的、没有电压丢失的文件，文件开头就有可能发生高低电平转换，导致timeCount 会 ++；
                    if (timeCount == 0) {
                        System.out.println("五个台站中，第"+ID + 1 + "个台站电压从 \"开始\" 就存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = AlignFile.align[ID] - timeCount;
                        isBroken = true;
                    } else if (timeCount != 0 && (LoopCount - firstTimeCount) / timeCount > (Parameters.FREQUENCY+210)) {
                        //经讨论分析得出：
                        //总循环次数 和 到第一个timeCount的循环次数相减，再除以timeCount，如果大于5010，认为文件电压缺失了
                        System.out.println("五个台站中，第"+ID + 1 + "个台站电压从" + timeCount + "起存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = AlignFile.align[ID] - timeCount;
                        isBroken = true;
                    }
                }
            }
            if (isBroken == true && (LoopCount >= remainTimeCount * (Parameters.FREQUENCY+200))) {
                //当总的循环次数等于 >= 剩余次数*5000 时，认为对齐了
                System.out.println("对齐完毕,LoopCount为: " + LoopCount);
                timeCount=AlignFile.align[ID];//将时间修改为对齐点时间
                return AlignFile.align[ID];//这里直接返回DuiQi.duiqi[ID]，代表对齐成功。
            }
        }// end while(true)
    }

	/**
	 * 不必一秒一秒读取
	 * 直接读取到当前对齐时间记录timeCount 和 对象中buffer中的位置即可
	 * 但目前硬件对齐速度较慢，因此我们对每次的读数据时间进行计时，当每次1s对齐的时间超过2s时，重新选择盘符的组合。
	 * @author Hanlin Zhang.
	 */
	public int readDataDui(String sName,int sID) throws Exception {
		String sensorName = sName;
		int ID = sID;long t1=0,t2=0;
		boolean flag1 = false ;
		boolean flag2 = false ;
		short volt = 0;//保存电压值
		int LoopCount = 0;//解决文件电压缺失问题而定义的
        int firstTimeCount = 0;//解决文件电压缺失问题而定义的
        boolean isfirstTimeCount = false;
        int remainTimeCount = 0;
        
		while(true){//从对齐位置开始，读整秒的数据，直到文件末尾	
			
			try{
				if (buffered.read(dataByte) == -1){
					System.out.println(MainThread.fileStr[sID]+"恭喜程序进入死亡地带");
					return -1;
				}
			}
			catch(Exception e){//网络错误,重新分配盘符
				ReadData.netError=true;
				System.out.println("对齐过程产生网络错误");
				return -1;//此时只能再次重启程序，但之前可能已产生网络错误，或产生新文件，因此这里不予修改标志位，防止标志位混乱
			}
			LoopCount++;
			byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
			if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
				buffered.skip(this.datahead);//再跳过26字节，就到数据了
				buffered.read(dataByte) ;//还是以14字节为单位读，7个通道每个通道占2字节。
			}
            if (isBroken == false) {
                byte[] voltByte = FindByte.searchByteSeq(dataByte, this.voltstart, this.voltend);    //提取电压
                volt = Byte2Short.byte2Short(voltByte); //保存

                if (Math.abs(volt) < 1000 ) flag2 = true;
                if (Math.abs(volt) > 5000 && flag2) flag1 = true;
                if (flag1 && flag2) {//高电平结束，说明1s数据结束，计量timeCount

                	if(timeCount==0 && LoopCount>((Parameters.FREQUENCY+200)/2)-(Parameters.distanceToSquareWave*(Parameters.FREQUENCY+200))) {
                    	timeCount++;
                    	System.out.println(sensorName+"数据头时间距离下一方波处的数据量大于频率的一半"+LoopCount);
                    }

                	if ((timeCount-1) == DuiQi.duiqi[sID]) {
                        System.out.println(sensorName + "对齐完毕,timeCount为：" + timeCount);
                        return timeCount;
                    }

                    timeCount++;
                    flag1 = false;
                    flag2 = false;
                    LoopCount=0;
                }
                if (timeCount == 0 && (!isfirstTimeCount)) {
                    //System.out.println(LoopCount);
                    firstTimeCount = LoopCount;
                    isfirstTimeCount = true;
                    //System.exit(0);
                }
                if (LoopCount >= (Parameters.FREQUENCY+210)) {
                    //注意！！！此处有坑，对于好使的、没有电压丢失的文件，文件开头就有可能发生高低电平转换，导致timeCount 会 ++；
                    if (timeCount == 0) {
                        System.out.println("五个台站中，第"+ID + 1 + "个台站电压从 \"开始\" 就存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = DuiQi.duiqi[ID] - timeCount;
                        //System.out.println("剩余对齐时间:" + remainTime);
                        isBroken = true;
                    } else if (timeCount != 0 && (LoopCount - firstTimeCount) / timeCount > (Parameters.FREQUENCY+210)) {
                        //经讨论分析得出：
                        //总循环次数 和 到第一个timeCount的循环次数相减，再除以timeCount，如果大于5010，认为文件电压缺失了
                        System.out.println("五个台站中，第"+ID + 1 + "个台站电压从" + timeCount + "起存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = DuiQi.duiqi[ID] - timeCount;
                        isBroken = true;
                    }
                }
            }
            if (isBroken == true && (LoopCount >= remainTimeCount * (Parameters.FREQUENCY+200))) {
                //当总的循环次数等于 >= 剩余次数*5000 时，认为对齐了
                System.out.println("对齐完毕,LoopCount为: " + LoopCount);
                timeCount=DuiQi.duiqi[ID];//将时间修改为对齐点时间
                return timeCount;//这里直接返回DuiQi.duiqi[ID]，代表对齐成功。
            }
		}// end while(true)
	}
	
	/**
	 * 不必一秒一秒读取
	 * 直接读取到当前对齐时间记录timeCount和对象buffer中的位置即可
	 * 但目前硬件对齐速度较慢，因此我们对每次的读数据时间进行计时，当每次1s对齐的时间超过2s时，重新选择盘符的组合。
	 * @author Rui Cao.
	 */
	public int readDataDui_MrMa(String sName,int sID) throws Exception {
		String sensorName = sName;
				
		String FindMaxByte = DateArrayToIntArray.FindFourByte(DuiQi.file1[DateArrayToIntArray.j]);
		String FindMaxByteHM = DateArrayToIntArray.FindTwoByte(DuiQi.file1[DateArrayToIntArray.j]);
		
		while(true){//从对齐位置开始，读整秒的数据，直到文件末尾	
			//indicate the end of file.
			try{
				if (buffered.read(dataByte) == -1){
					System.out.println("恭喜程序进入死亡地带");
					return -1;
				}
			}
			catch(Exception e){//网络错误,重新分配盘符
				ReadData.netError=true;
				System.out.println("对齐过程产生网络错误");
				return -1;//此时只能再次重启程序，但之前可能已产生网络错误，或产生新文件，因此这里不予修改标志位，防止标志位混乱
			}
			//core of algorithm.
			byte[] bt = {dataByte[0],dataByte[1],dataByte[2],dataByte[3]};
			String st = FindByte.bytesToHexString(bt);
			
			long haomiaoslM = Long.parseLong(FindMaxByteHM, 16);//将对其标准的文件的4 ，5字节转换为long型
			if(st.compareTo(FindMaxByte)==0) {
				byte[] haomiao = {dataByte[4],dataByte[5]};
				String haomiaos = FindByte.bytesToHexString(haomiao);
				long haomiaosl = Long.parseLong(haomiaos, 16);
				if(haomiaoslM-haomiaosl>17920) {
					buffered.read(dataByte1);
					continue;
				}
				else {
					long hm = (haomiaoslM-haomiaosl)/853;
					for(int i=0;i<hm;i++) {
						buffered.read(dataByte);
					}
					System.out.println(sensorName+"对齐完毕");
				    break;
				}
			}
			else {
				buffered.read(dataByte1);
			}
	   }
		return 1;
	}
	
	/**
	 * @description
	 * 注意：dataBytes的字节数（下标），以及通道是哪几个，若123通道则必须放在x1，y1，z1中，456通道放在x2，y2，z2中
	 * @param dataBytes
	 * @return
	 * @author Chengfeng Liu, Hanlin Zhang, Rui Cao.
	 */
	@SuppressWarnings("unused")
	private DataElement getDataElementFromDataBytes(byte[] dataBytes, int th){
		//数据类型转换
		/*注意：数据文件中很多数字类型的数据都是两个字节，通过字节流读出来相应的两个字节时，先读出来的字节是低位字节，
		 而后一个字节是高位字节，例如在字节流中按顺序读出两个字节： byte1 = 0x4e, byte2 = 0x50，
		 把他们拼接成short类型时byte1是低位，而byte2是高位*/
		DataElement dataElement = new DataElement();
		if(this.channel==456){
			if(manager.isMrMa[th]==true){
				short x2 =dataBytes[0];
				short y2 =dataBytes[1];
				short z2 =dataBytes[2];
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
			else {
				short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
				short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
				short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
				
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
		}
		if(this.channel==123){
			short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
			short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
			short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		

			dataElement.setX1(x1);
			dataElement.setY1(y1);
			dataElement.setZ1(z1);
		}
		if(this.channel==123456){
			short x1 = DataTypeConversion.joint2BytesToShort(dataBytes[0], dataBytes[1]);
			short y1 = DataTypeConversion.joint2BytesToShort(dataBytes[2], dataBytes[3]);
			short z1 = DataTypeConversion.joint2BytesToShort(dataBytes[4], dataBytes[5]);		
			short x2 = DataTypeConversion.joint2BytesToShort(dataBytes[6], dataBytes[7]);
			short y2 = DataTypeConversion.joint2BytesToShort(dataBytes[8], dataBytes[9]);
			short z2 = DataTypeConversion.joint2BytesToShort(dataBytes[10], dataBytes[11]);
			
			dataElement.setX1(x1);
			dataElement.setY1(y1);
			dataElement.setZ1(z1);
			
			dataElement.setX2(x2);
			dataElement.setY2(y2);
			dataElement.setZ2(z2);
		}
		return dataElement;
	}
	
	/** 这是对外的数据  ,给用户一秒的数据 
	 * @throws Exception */
	public Vector<String> getData(String sName,int sID) throws Exception {
		//this.isFirst = true;
		if(manager.isMrMa[sID]==true) {
			readData_MrMa(sName,sID);
		}
		else {
			readData(sName,sID);
//			readDataNoGPS_offline(sName,sID);
		}
		
		return data;
	}
	public Vector<String> getOfflineData(String sName,int sID) throws Exception{
		readDataNoGPS_offline(sName,sID);
		return data;
	}
	public int getSegmentNum() {
		return segmentNum;
	}
	public void setSegmentNum(int segmentNum) {
		this.segmentNum = segmentNum;
	}
	public int getSegmentRecNum() {
		return segmentRecNum;
	}
	public void setSegmentRecNum(int segmentRecNum) {
		this.segmentRecNum = segmentRecNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getChCahi() {
		return chCahi;
	}
	public void setChCahi(float chCahi) {
		this.chCahi = chCahi;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public boolean setState() throws Exception {
		// 找到文件目录下的最新文件，并把它赋给file
		this.nameF1 = findNew.nameF[sensorID];//上次的文件
		findNew.find(filePath, sensorID, manager);
		if(findNew.nameF[sensorID].compareTo(nameF1)==0 ){//最新文件还是这个，返回
			return false;
		}
		else{//读新文件信息
			return true;
		}
	}
}
