/**
 * 
 */
package read.rqma.history;

import com.h2.constant.Parameters;
import controller.ADMINISTRATOR;
import read.yang.readFile.ReadData;

/**
 * @author Rq Ma
 */
public class GetReadArray {
	private ADMINISTRATOR manager;
	//对齐时间，从改时间往后查找文件。
	//对齐文件对象。
	private AlignFile alignFile = null;
	//定义 ReadData
    private ReadData[] readDataArray = new ReadData[Parameters.SensorNum];
	
	public GetReadArray(AlignFile alignFile, ADMINISTRATOR manager) {
		this.manager = manager;
		this.alignFile = alignFile;
	}
	
	/**
     * 该函数体里的内容，会在程序里出现两次，为了避免代码的冗余，故放在一个函数体了，在两个地方调用函数即可
     *
     * @param alignFile
     * @param timeStr
     * @return Parameters.sensorNum个 ReadData
	 * @throws Exception 
     */
    public ReadData[] getDataArray() throws Exception {
        
        if(StopperFindFile() == true) {
        	return null;
        }
        
        for (int i = 0; i < Parameters.SensorNum; i++) {
        	readDataArray[i] = new ReadData(alignFile.paths_original[i], i, manager);
        }
        
        //计时。
		manager.setStartInstance(System.currentTimeMillis());
		
        if(StopperMoveBuffer() == true) {
        	return null;
        }
        
        //计时
		manager.setEndInstance(System.currentTimeMillis());
        System.out.println("对齐时间花费：" + (manager.getEndInstance()-manager.getStartInstance())/Parameters.TEMP + "秒");
        
        return readDataArray;
    }
    
    /**
     * 文件对齐阻拦器。
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月18日下午3:57:40
     */
    private boolean StopperFindFile() {
    	//获取对齐数组，单位秒。
    	try {AlignFile.align = alignFile.getAlign();} 
    	catch (Exception e1) {e1.printStackTrace();}
    	//对齐数组出现异常，则阻拦器生效，返回空。
    	for (int i = 0; i < Parameters.SensorNum; i++) {
            if (AlignFile.align[i] == -1) {
                System.out.println("对齐文件时出错");
                return true;
            }
        }
    	return false;
    }
    
    /**
     * 文件移动阻拦器。
     * @return
     * @author Hanlin Zhang.
     * @date revision 2021年2月24日下午2:02:10
     */
    private boolean StopperMoveBuffer() {
    	/**保存对齐后的timeCount变量*/
        int kuai[] = new int[Parameters.SensorNum];
        
    	for (int i = 0; i < Parameters.SensorNum; i++) {
            try {
                kuai[i] = readDataArray[i].readDataAlignOffline();
                
                if (kuai[i] == -1) {
                    System.out.println("该组数据无法对齐，开始对齐下一组---------");
                    readDataArray=null;
                    return true;
                }
            } catch (Exception e1) {e1.printStackTrace();}
        }
    	return false;
    }
    
}
