package read.yang.readFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.h2.constant.Parameters;

import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import read.rqma.history.AlignFile;
import read.yang.readFile.ReadDateFromHead;
import read.yang.readFile.ReadHfmedHead;
import read.yang.readFile.ReadSensorProperties;
import read.yang.readFile.findNew;
import read.yang.unity.HfmedHead;
import read.yang.unity.SensorProperties;
import read.yang.util.Byte2Short;
import read.yang.util.FindByte;
import utils.Date2String;
import utils.DateArrayToIntArray;

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
	
	public ReadData() {
		super();
	}
	/**this is a vector used to store one second data.*/
	private Vector<String> data;//Vector<String>(线程同步数据列表)
	/**when GPS signal has gone, its value become true*/
	public boolean isBroken = false;
	/** 秒数计数器  , 每调用一次getData的时候 ，这个计数器就加一 ，表示加一秒*/
	public int timeCount = 0;
	/**the number of sensor.*/
	private int sensorID = 0;
	/**the name of sensor.*/
	private String sensorName = "";
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
	/**缓冲池大小，10个传感器*频率*10s时间。*/
	private int bufferPoolSize = 10*(Parameters.FREQUENCY+200)*10;

	private int bytenum;
	private int voltstart;
	private int voltend;
	boolean flag1 = false;
	boolean flag2 = false;
	
	/** 第一条数据的日期 */
	private Date date = new Date();
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
	 * 注意 ：fileName的格式：C:/Users/NiuNiu/Desktop/HYJ/
	 * 构造函数，其中添加了manager对象，用于替换掉全局变量netError与newFile。
	 * 根据不同的仪器进行不同的格式读取，并添加了	
	 * @param filePath
	 * @throws Exception
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu, Ruiqiang Ma, Rui Cao.
	 */
	 @SuppressWarnings("unused")
	public ReadData(String path, int th, ADMINISTRATOR manager) throws Exception {
		 this.sensorName = path;
		 this.filePath = path;//路径更新
		 this.sensorID = th;
		 this.manager = manager;
		 
		 if(Parameters.offline==true) {
			 if(manager.isMrMa[th]==false) {//offline Mr. Liu.
				 this.file=new File(this.sensorName);
				 HfmedHead hfmedHead = new ReadHfmedHead().readHead(this.file);//读文件头，文件头内容
				 this.settings(hfmedHead);
				 this.date = ReadDateFromHead.readDataSegmentHead_MrLiu_Date(this.file);// 从第一个数据段头中获得数据文件起始记录时间
				 manager.setLastDate(Date2String.date2str(date));//update the date for the record P arrival txt file's name.
				 SensorProperties[] sensor = new ReadSensorProperties().readSensorProperties(this.file);
				 this.chCahi = sensor[0].getChCali();//由于通道单位都一样，所以用第一个通道单位就可以
				 data = new Vector<String>();//用于存放数据
				 dataByte = new byte[this.bytenum];//改为四个通道，4567
				 this.buffered = new BufferedInputStream(new FileInputStream(file),bufferPoolSize);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
				 buffered.read(new byte[Parameters.WenJianTou]);// 跳过文件头(186)，通道信息(14*7)，他们一共242个字节
			 }
			 else if(manager.isMrMa[th]==true) {//offline Mr. Ma.
				 this.file=new File(this.sensorName);
				 this.channel=123456;
				 this.date = ReadDateFromHead.readDataSegmentHead_MrMa_Date(this.file);// 从第一个数据段头中获得数据文件起始记录时间
				 data = new Vector<String>();//用于存放数据
				 dataByte = new byte[Parameters.Shi];//每次读10个字节的字节数组
				 dataByte1 = new byte[Parameters.ShuJu];//每次跳过840个字节的数据。
				 dataYiMiao=new byte[Parameters.YIMiao];
				 readsan=new byte[Parameters.San];
				 this.newS=null;
				 this.buffered = new BufferedInputStream(new FileInputStream(this.file),bufferPoolSize);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
			 }
		}
		else {//online Mr. Liu.
			if(manager.isMrMa[th]==true) {
				this.file=new File(manager.getNFile1(th));
				this.channel=123456;
				this.date = ReadDateFromHead.readDataSegmentHead_MrMa_Date(manager.getNFile1(th));// 从第一个数据段头中获得数据文件起始记录时间
				data = new Vector<String>();//用于存放数据
				dataByte = new byte[Parameters.Shi];//每次读10个字节的字节数组
				dataByte1 = new byte[Parameters.ShuJu];//每次跳过840个字节的数据。
				dataYiMiao=new byte[Parameters.YIMiao];
				readsan=new byte[Parameters.San];
				this.newS=null;
				this.buffered = new BufferedInputStream(new FileInputStream(manager.getNFile1(th)),bufferPoolSize);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
			}
			else {
				this.file=new File(manager.getNFile1(th));
				HfmedHead hfmedHead = new ReadHfmedHead().readHead(file);//读文件头，文件头内容
				this.settings(hfmedHead);
				this.date = ReadDateFromHead.readDataSegmentHead_MrLiu_Date(file);// 从第一个数据段头中获得数据文件起始记录时间
				manager.setLastDate(Date2String.date2str(date));//update the date for the record P arrival txt file's name.
				SensorProperties[] sensor = new ReadSensorProperties().readSensorProperties(manager.getNFile1(th));
				this.chCahi = sensor[0].getChCali();//由于通道单位都一样，所以用第一个通道单位就可以
				data = new Vector<String>();//用于存放数据
				dataByte = new byte[this.bytenum];//改为四个通道，4567
				this.buffered = new BufferedInputStream(new FileInputStream(manager.getNFile1(th)),bufferPoolSize);//设置缓冲池大小，缓冲池过小可能读不全1s钟的数据，待研究
				buffered.read(new byte[Parameters.WenJianTou]);// 跳过文件头(186)，通道信息(14*7)，他们一共242个字节
			}
		}
	}
	
	/**
	 * 这是在线读取数据的主函数，自动判断是那种仪器的数据，后期可能会扩展。
	 * 每次存储一秒的数据，我们这里作了与离线数据一样的改进，当GPS出现缺失时，会进行补齐。
	 * 不用担心当GPS信号出现问题时数据时间错误或程序无法继续正常运行。
	 * @throws Exception
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu.
	 * @date revision 2021年3月9日上午9:31:17
	 */
	public synchronized void readDataOnline() throws Exception {//读取整秒数据
		if(manager.isMrMa[sensorID] == false) {
			this.readDataOnline_liu();
		}
		else {
			this.readDataOffline_ma();
		}
	}
	
	/**
	 * 在线读取刘老师设备数据，同样每次调用保存1秒。
	 * 此函数经过多次修改，最开始是无法获取实时数据，到达最新数据就告诉主程序产生了新文件，然后又从文件头开始读取数据。
	 * 第二版开始，添加了到达末尾等待的机制，但出现波形毛刺的现象，这是因为没有跳过文件的端头。
	 * 第三版开始，我们添加了文件段头的跳过操作，并使得数据与原数据一致，至此所有刘老师的仪器在线数据读取均完成。
	 * @throws Exception
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu.
	 * @date revision 2021年3月9日上午9:29:38
	 */
	private synchronized void readDataOnline_liu() throws Exception {
		int by = -1;
		boolean fileisOver = false;
		int LoopCount=0;
		
		if(manager.getNewFile() == true) {
			System.out.println("其余台站进入while时，产生新文件");
			return;
		}
		else{
			/**we will clear the data of data variable at each beginning of the readData function.*/
			data.clear();
			/**define two variables to storage date and volt of the 8 channel-GPS volt.*/
			short volt = 0;
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
							//此处读在本地的情况下，会陷入假死状态。
							if(this.tailOnlineProcessing() == true)
								return;
							else
								continue;
						}
					}
					}
					catch(IOException e){
						this.netErrorProcess();
						return;
					}
					LoopCount++;
					//read HFME feature code.
					this.HFMEFeature();
					
		   			DataElement dataElement = this.getDataElementFromDataBytes();
		   			dataElement.setDataCalendar(this.formerDate());//置入数据读取时间
		   			data.add(dataElement.toString());//存储7个通道的数据至data容器
		   			
		   			volt = Byte2Short.byte2Short(dataByte[this.voltstart], dataByte[this.voltend]);//获取电平数据
		   			if(this.voltProcessing(volt, LoopCount) == true) {
		   				break;
		   			}
			}// end while(true)
		}//end if
	}

	/**
	 * 这是离线读取数据的主函数，自动判断是那种仪器的数据，后期可能会扩展。
	 * 每次存储一秒的数据。
	 * when GPS has gone, we can use this function. Of course, this function used to read offline data, but we also can consider use this function after 
	 * amount of testing, the procedure can select this function when GPS signal has gone with reading online. In my view, I can't agree to revise to this
	 * way before the procedure become a more stable version.
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu, rqma.
	 * @throws Exception
	 */
	public synchronized void readDataOffline() throws Exception {//读取整秒数据
		if(this.manager.isMrMa[sensorID] == false) {
			this.readDateOffline_liu();
		}
		else {
			this.readDataOffline_ma();
		}
	}
	
	/**
	 * 离线读取刘老师设备数据，调用它时获取1秒数据。
	 * @throws InterruptedException
	 * @throws IOException
	 * @author Xingdong Yang, Hanlin Zhang, Chengfeng Liu.
	 * @date revision 2021年3月9日上午9:24:27
	 */
	private synchronized void readDateOffline_liu() throws InterruptedException, IOException {
		int by = -1;
		boolean fileisOver = false;//标识程序是否到达末尾
		int LoopCount = 0;//解决文件缺失问题而定义的
		
		/**we will clear the data of data variable at each beginning of the readData function.*/
		data.clear();
		/**define two variables to storage date and volt of the 8 channel-GPS volt.*/
		short volt = 0;
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
							this.tailOfflineProcessing();
							return;
						}
					}
				}
			}
			catch(IOException e){
				this.netErrorProcess();
				return;
			}

			LoopCount++;

			byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
   			if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
   				buffered.skip(this.datahead);//再跳过26字节，就到数据了
   				buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
   			}
   			
   			DataElement dataElement = this.getDataElementFromDataBytes();
   			dataElement.setDataCalendar(this.formerDate());//置入数据读取时间
   			data.add(dataElement.toString());//存储7个通道的数据至data容器

   			volt = Byte2Short.byte2Short(dataByte[this.voltstart], dataByte[this.voltend]);//获取电平数据
			
   			if(this.voltProcessing(volt, LoopCount) == true) {
   				break;
   			}
		}// end while(true)
	}
	
	/**
	 * 离线读取马老师仪器数据，同对齐数据一样，后期需要根据马老师仪器的数据结构进行修改。
	 * @throws Exception
	 * @author Rui Cao, Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:23:36
	 */
	private synchronized void readDataOffline_ma() throws Exception {
		int count=0;//标志位，初始是0
		int count1=0;
		String MaxDateS=null;
		boolean fileisOver = false;//标识程序是否到达末尾
		if(manager.getNewFile() == true) {
			System.out.println("其余台站进入while时，产生新文件");
			return;
		}
		else{
			data.clear();// 每次调用readData方法的时候将data数据区清空
	
			String dateCus;    //保存7个通道的数据
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
//			Date startDate = format1.parse(date);
			//从对齐位置开始，读1秒的数据
			while(true){
				if(manager.getNewFile() == false) {
					//indicate the end of file.
					try{
						if(fileisOver == false) {				
							if(buffered.read(dataByte)==-1) {
								fileisOver=true;
								continue;
							}
						}
						else {
							this.tailOfflineProcessing();
							return;
						}
					}
					catch(IOException e){
						System.out.println(sensorID+"号台站"+sensorName+"产生了网络错误，记录当前错误时间！");
						if(manager.getNetErrID()==-1){manager.setNetErrID(sensorID);System.out.println("!!!!!!!!!"+manager.getNetErrID());}//记录台站号，用于记录发生网络错误的盘符，便于统计结果
						manager.setNetError(true);
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
							MaxDateS = DateArrayToIntArray.FindFourByte(manager.getNFile1(this.sensorID));
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
						//秒数变动
						else {
							//变动后，先将dateYiMiao数据保存到前一秒处。
							for(int i=0;i<count/12;i++) {
								readsan[0]=dataYiMiao[11*i+i];
								readsan[1]=dataYiMiao[11*i+i+1];
								readsan[2]=dataYiMiao[11*i+i+2];
								readsan[3]=dataYiMiao[11*i+i+3];
								readsan[4]=dataYiMiao[11*i+i+4];
								readsan[5]=dataYiMiao[11*i+i+5];
								readsan[6]=dataYiMiao[11*i+i+6];
								readsan[7]=dataYiMiao[11*i+i+7];
								readsan[8]=dataYiMiao[11*i+i+8];
								readsan[9]=dataYiMiao[11*i+i+9];
								readsan[10]=dataYiMiao[11*i+i+10];
								readsan[11]=dataYiMiao[11*i+i+11];
					   			DataElement dataElement = this.getDataElementFromDataBytes();
								
								String date1 = format2.format(date);
					   			dateCus = date1;
					   			dataElement.setDataCalendar(dateCus);
					   			data.add(dataElement.toString());
							}
							//前一秒的时间，单位秒
							newS=Stime;
							timeCount++;
							//前一秒数据的末尾序号。
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
				
				if(count==(Parameters.FREQUENCY+200)*12) {
					for(int i=0;i<((Parameters.FREQUENCY+200)*12-count1)/12;i++) {
						readsan[0]=dataYiMiao[count1+(11*i+i)];
						readsan[1]=dataYiMiao[count1+(11*i+i+1)];
						readsan[2]=dataYiMiao[count1+(11*i+i+2)];
						readsan[3]=dataYiMiao[count1+(11*i+i+3)];
						readsan[4]=dataYiMiao[count1+(11*i+i+4)];
						readsan[5]=dataYiMiao[count1+(11*i+i+5)];
						readsan[6]=dataYiMiao[count1+(11*i+i+6)];
						readsan[7]=dataYiMiao[count1+(11*i+i+7)];
						readsan[8]=dataYiMiao[count1+(11*i+i+8)];
						readsan[9]=dataYiMiao[count1+(11*i+i+9)];
						readsan[10]=dataYiMiao[count1+(11*i+i+10)];
						readsan[11]=dataYiMiao[count1+(11*i+i+11)];
			   			DataElement dataElement = this.getDataElementFromDataBytes();
						
						String date1 = format2.format(date);
			   			dateCus = date1;
			   			dataElement.setDataCalendar(dateCus);//置入数据读取时间
			   			data.add(dataElement.toString());//存储数据至data容器
//			   			date=date1;//更新时间为下一秒数据的开始时间。the date1 should equal to date?
			   			timeCount=0;
					}
					break;
				}
			}//end while.
		}
	}
	
	/**
	 * 这是离线对齐数据的主函数，自动判断是哪种仪器的数据，后期可能会扩展。
	 * 不必一秒一秒读取，直接读取到当前对齐时间记录timeCount 和 对象中buffer中的位置即可。
	 * @return
	 * @throws Exception
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:21:56
	 */
	public int readDataAlignOffline() throws Exception {
		int result = 0;
		if(manager.isMrMa[sensorID] == true) {
			result = this.readDataAlignOffline_ma();
		}
		else {
			result = this.readDataAlignOffline_liu();
		}
		return result;
	}
	
	/**
	 * 刘老师仪器的离线读取对齐已经基本成形，后期不必更改。
	 * @return the same as the readDatadui, but this function can work in the environment GPS signal gone.
	 * @author rqma, Hanlin Zhang.
	 * @throws Exception
	 */
	private int readDataAlignOffline_liu() throws Exception {
        boolean flag1 = false;
        boolean flag2 = false;
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
            //read the HFME feature code.
            this.HFMEFeature();
            
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

                	if ((timeCount-1) == AlignFile.align[this.sensorID]) {
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
                        System.out.println("五个台站中，第"+sensorID + "个台站电压从 \"开始\" 就存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = AlignFile.align[sensorID] - timeCount;
                        isBroken = true;
                    } else if (timeCount != 0 && (LoopCount - firstTimeCount) / timeCount > (Parameters.FREQUENCY+210)) {
                        //经讨论分析得出：
                        //总循环次数 和 到第一个timeCount的循环次数相减，再除以timeCount，如果大于5010，认为文件电压缺失了
                        System.out.println("五个台站中，第"+sensorID + "个台站电压从" + timeCount + "起存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = AlignFile.align[sensorID] - timeCount;
                        isBroken = true;
                    }
                }
            }
            if (isBroken == true && (LoopCount >= remainTimeCount * (Parameters.FREQUENCY+200))) {
                //当总的循环次数等于 >= 剩余次数*5000 时，认为对齐了
                System.out.println("对齐完毕,LoopCount为: " + LoopCount);
                timeCount=AlignFile.align[sensorID];//将时间修改为对齐点时间
                return AlignFile.align[sensorID];//这里直接返回DuiQi.duiqi[ID]，代表对齐成功。
            }
        }// end while(true)
    }

	/**
	 * 离线对齐马老师仪器数据，后期调整根据马老师数据结构进行，不必对逻辑大动干戈。
	 * @return
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:20:09
	 */
	private int readDataAlignOffline_ma() throws IOException {
//		String FindMaxByte = DateArrayToIntArray.FindFourByte(DuiQi.file1[DateArrayToIntArray.j]);
//		String FindMaxByteHM = DateArrayToIntArray.FindTwoByte(DuiQi.file1[DateArrayToIntArray.j]);
		String FindMaxByte = DateArrayToIntArray.FindFourByte(manager.getNFile1(manager.getMaxTimeSeries()));
		String FindMaxByteHM = DateArrayToIntArray.FindTwoByte(manager.getNFile1(manager.getMaxTimeSeries()));
		
		while(true){//从对齐位置开始，读整秒的数据，直到文件末尾	
			//indicate the end of file.
			try{
				if (buffered.read(dataByte) == -1){
					System.out.println("恭喜程序进入死亡地带");
					return -1;
				}
			}
			catch(Exception e){//网络错误,重新分配盘符
				manager.setNetError(true);
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
				//最新的-当前=1帧 14ms，对帧
				if(haomiaoslM-haomiaosl>17920) {
					buffered.read(dataByte1);
					continue;
				}
				//精确
				else {
					//毫秒数不整不一定是14ms，有多少个10B（long 853）
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
	 * 这是在线对齐的主函数，自动判断是那种仪器的数据，后期可能会扩展。
	 * 不必一秒一秒读取，直接读取到当前对齐时间记录timeCount 和 对象中buffer中的位置即可。
	 * @author Hanlin Zhang.
	 */
	public int readDataAlignOnline() throws Exception {
		int result = 0;
		if(manager.isMrMa[sensorID] == true) {
			
		}
		else {
			result = this.readDataAlignOnline_liu();
		}
		return result;
	}
	
	/**
	 * 在线对齐刘老师设备数据。
	 * @return
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:17:03
	 */
	public int readDataAlignOnline_liu() throws IOException {
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
					System.out.println(MainThread.fileStr[sensorID]+"恭喜程序进入死亡地带");
					return -1;
				}
			}
			catch(Exception e){//网络错误,重新分配盘符
				this.netErrorAlign();
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
                	if ((timeCount-1) == manager.getNDuiqi(sensorID)) {
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
                        System.out.println("五个台站中，第"+sensorID + "个台站电压从 \"开始\" 就存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = manager.getNDuiqi(sensorID) - timeCount;
                        isBroken = true;
                    } else if (timeCount != 0 && (LoopCount - firstTimeCount) / timeCount > (Parameters.FREQUENCY+210)) {
                        //经讨论分析得出：
                        //总循环次数 和 到第一个timeCount的循环次数相减，再除以timeCount，如果大于5010，认为文件电压缺失了
                        System.out.println("五个台站中，第"+sensorID  + "个台站电压从" + timeCount + "起存在缺失，所以，停下来吧，少年!!!电压已经不起作用了");
                        remainTimeCount = manager.getNDuiqi(sensorID) - timeCount;
                        isBroken = true;
                    }
                }
            }
            if (isBroken == true && (LoopCount >= remainTimeCount * (Parameters.FREQUENCY+200))) {
                //当总的循环次数等于 >= 剩余次数*5000 时，认为对齐了
                System.out.println(sensorName + "对齐完毕,LoopCount为: " + LoopCount);
                timeCount=manager.getNDuiqi(sensorID);//将时间修改为对齐点时间
                return timeCount;//这里直接返回DuiQi.duiqi[ID]，代表对齐成功。
            }
		}// end while(true)
	}
	
	/**
	 * 在线获取1秒数据，每次调用都获得1秒长度的数据。
	 * 这是对外的数据,给用户一秒的数据
	 * @return
	 * @throws Exception
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:18:49
	 */
	public Vector<String> getOnlineData() throws Exception {
		readDataOnline();
		return data;
	}
	/**
	 * 离线获取1秒数据，每次调用都获得1秒长度的数据。
	 * 这是对外的数据,给用户一秒的数据
	 * @return
	 * @throws Exception
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:18:38
	 */
	public Vector<String> getOfflineData() throws Exception{
		readDataOffline();
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
	
	/**
	 * 通过通道数量设置跳过字节数以及通道判断标志位。
	 * @param hfmedHead
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月21日下午3:34:58
	 */
	private void settings(HfmedHead hfmedHead) {
		 this.segmentNum = hfmedHead.getSegmentNum();//从文件头中获得段的数量
		 this.segmentRecNum = hfmedHead.getSegmentRecNum();// 获得每个段的数据条目数
		 this.channelNum = hfmedHead.getChannelOnNum();
		 
		 if(channelNum==7) {
			 this.channel=123456;
			 this.datahead=20;
			 this.bytenum=14;
			 this.voltstart=12;
			 this.voltend=13;
			 this.manager.mix_flag1 = true;
		 }
		 else if(channelNum==4) {
			 this.channel=456;
			 this.datahead=26;
			 this.bytenum=8;
			 this.voltstart=6;
			 this.voltend=7;
			 this.manager.mix_flag2 = true;
		 }
		 
		 //混合状态下不判断通道溢出。
		 if(manager.mix_flag1 && manager.mix_flag2) {
			 Parameters.TongDaoDiagnose = 0;
		 }
	}
	
	/**
	 * 找到文件目录下的最新文件，并把它赋给file
	 * 相当于刷新目录，并找到当前文件是否还是这个正在读取的文件。
	 * @return
	 * @throws Exception
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:16:18
	 */
	public boolean setState() throws Exception {
		this.nameF1 = manager.getNNameF(sensorID);//上次的文件
		findNew.find(filePath, sensorID, manager);
		if(manager.getNNameF(sensorID).compareTo(nameF1)==0 ){//最新文件还是这个，返回
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * 刘老师仪器使用。
	 * 判断是否为HFMED文件的段头，到了段头就跳过4个字节。
	 * @throws IOException
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:05:43
	 */
	private void HFMEFeature() throws IOException {
		byte[] feature = {dataByte[0] , dataByte[1] , dataByte[2] , dataByte[3]};//特征码是4个字节，其内容为"HFME"
		if(new String(feature).compareTo("HFME") == 0){//读到了数据段头
			buffered.skip(this.datahead);//再跳过26字节，就到数据了
			buffered.read(dataByte);//还是以14字节为单位读，7个通道每个通道占2字节。
		}
	}
	
	/**
	 * 当发生网络错误时，设置网络错误标志位并在控制台提示。
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:06:13
	 */
	private void netErrorProcess() {
		System.out.println(sensorID+"号台站"+this.sensorName+"产生了网络错误，记录当前错误时间！");
		manager.setTimeInterrupt(timeCount);
		System.out.println("##########"+manager.getTimeInterrupt()+"盘号"+sensorID);//保存了网络错误时间，若在接下来等待的时间内未产生新文件，则对齐时加上该时间，避免重复计算前面的数据
		manager.setNetErrID(sensorID);
		System.out.println("!!!!!!!!!"+manager.getNetErrID());//记录台站号，用于记录发生网络错误的盘符，便于统计结果
		manager.setNetError(true);//网络错误，同时记录产生网络错误的盘符及年月日
	}
	
	/**
	 * 设置网络错误标志，并在控制台提示。
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:06:53
	 */
	private void netErrorAlign() {
		manager.setNetError(true);
		System.out.println("对齐过程产生网络错误");
	}
	
	/**
	 * 在线的尾部处理，当读到文件末尾时，设置新文件标志位，并输出控制台。
	 * @return
	 * @throws Exception
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:07:12
	 */
	private boolean tailOnlineProcessing() throws Exception {
		this.countSetState++;
		manager.setNewFile(setState());
		if(manager.getNewFile() == true) {
			System.out.println("有"+this.countSetState+"次读取到了不够8字节的数据");
			System.out.println("第"+sensorID+"号台站"+sensorName+"产生了新文件");
			data.clear();timeCount = 0;
			return true;
		}else{
			return false;//直接等待直到出现数据，写入data容器
		}
	}
	
	/**
	 * 离线的尾部处理，当读到文件末尾时，设置新文件标志位，并输出控制台。
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:11:42
	 */
	private void tailOfflineProcessing() {
		this.countSetState++;
		manager.setNewFile(true);
		System.out.println("有"+this.countSetState+"次读取到了不够8字节的数据");
		System.out.println("第"+sensorID+"号台站"+sensorName+"产生了新文件");
		System.out.println("当前文件的方波个数为："+timeCount);
		data.clear();timeCount = 0;
	}
	
	/**
	 * 刘老师仪器使用。
	 * 压力值处理，当出现高电平向低电平过度时，认为是1秒的结束标志。
	 * @param volt
	 * @param LoopCount
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:12:15
	 */
	private boolean voltProcessing(short volt, int LoopCount) {
		if (isBroken == false) {//之前对齐时电压没缺失，在读一秒时，出现电压缺失
			if (LoopCount > (Parameters.FREQUENCY+210)) {//循环5010(一秒最多5010条数据)次时，还没退出，表明文件电平缺失，
	            isBroken = true;
	            System.out.println(MainThread.fileStr[sensorID]+"出现GPS缺失");
	            timeCount++;//待定！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	            flag1 = flag2 = false;
	            return true;
	        }
            //判断1s是否结束，结束跳出while
            if (Math.abs(volt) < 1000 ) {
//				data.remove(data.size() - 1) ;//保证是正好10s的数据，因为最后一个低电平的数据也被读进来了
                flag2 = true;
            }
            if (Math.abs(volt) > 5000 && flag2) {
            	flag1 = true;
            }
            if (flag1 && flag2) {//高电平结束，说明1s数据结束，跳出while(true)，一个读过程结束
                timeCount++;
                flag1 = flag2 = false;
                return true;
            }
        } else {//在对齐时，就已经出现电压缺失，直接进入这里
            if (LoopCount >= (Parameters.FREQUENCY+200)) {
            	timeCount++; //即使电压缺失了，时间也得跟着走，不然调用calendar.add(Calendar.SECOND, timeCount)是错的
            	flag1 = flag2 = false;
            	return true;
            }
        }
		return false;
	}
	
	/**
	 * 刘老师仪器使用。
	 * 时间的规整函数，当GPS压力位出现跳秒时，我们进行时间+1秒操作。
	 * @return
	 * @author Hanlin Zhang.
	 * @date revision 2021年3月9日上午9:13:05
	 */
	public String formerDate() {
		Calendar calendar = Calendar.getInstance(); //内存溢出的出错位置。~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
		calendar.setTime(this.date);
		calendar.add(Calendar.SECOND, timeCount);
		Date startDate1 = calendar.getTime();
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		String date1 = format2.format(startDate1);
		return date1;
	}
	
	/**
	 * 注意：dataBytes的字节数（下标），以及通道是哪几个，若123通道则必须放在x1，y1，z1中，456通道放在x2，y2，z2中。
	 * 马老师仪器由于更改了6通道，双量程，因此使用channel=123456条件进入。
	 * @param dataBytes
	 * @return
	 * @author Chengfeng Liu, Hanlin Zhang, Rui Cao.
	 * @return 
	 */
	@SuppressWarnings("unused")
	private DataElement getDataElementFromDataBytes() {
		DataElement dataElement = new DataElement();
		
		if(channel==456){
			if(manager.isMrMa[sensorID]==true){
				short x2 = readsan[0];
				short y2 = readsan[1];
				short z2 = readsan[2];
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
			else {
				short x2 = Byte2Short.byte2Short(dataByte[0], dataByte[1]);
				short y2 = Byte2Short.byte2Short(dataByte[2], dataByte[3]);
				short z2 = Byte2Short.byte2Short(dataByte[4], dataByte[5]);		
				
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
		}
		if(channel==123){
			short x1 = Byte2Short.byte2Short(dataByte[0], dataByte[1]);
			short y1 = Byte2Short.byte2Short(dataByte[2], dataByte[3]);
			short z1 = Byte2Short.byte2Short(dataByte[4], dataByte[5]);		

			dataElement.setX1(x1);
			dataElement.setY1(y1);
			dataElement.setZ1(z1);
		}
		if(channel==123456){
			if(manager.isMrMa[sensorID]==true){
				short x1 = Byte2Short.byte2Short(readsan[0], readsan[1]);
				short y1 = Byte2Short.byte2Short(readsan[2], readsan[3]);
				short z1 = Byte2Short.byte2Short(readsan[4], readsan[5]);		
				short x2 = Byte2Short.byte2Short(readsan[6], readsan[7]);
				short y2 = Byte2Short.byte2Short(readsan[8], readsan[9]);
				short z2 = Byte2Short.byte2Short(readsan[10], readsan[11]);
				dataElement.setX1(x1);;
				dataElement.setY1(y1);
				dataElement.setZ1(z1);
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}else {
				short x1 = Byte2Short.byte2Short(dataByte[0], dataByte[1]);
				short y1 = Byte2Short.byte2Short(dataByte[2], dataByte[3]);
				short z1 = Byte2Short.byte2Short(dataByte[4], dataByte[5]);		
				short x2 = Byte2Short.byte2Short(dataByte[6], dataByte[7]);
				short y2 = Byte2Short.byte2Short(dataByte[8], dataByte[9]);
				short z2 = Byte2Short.byte2Short(dataByte[10], dataByte[11]);
				
				dataElement.setX1(x1);
				dataElement.setY1(y1);
				dataElement.setZ1(z1);
				
				dataElement.setX2(x2);
				dataElement.setY2(y2);
				dataElement.setZ2(z2);
			}
		}
		return dataElement;
	}
}
