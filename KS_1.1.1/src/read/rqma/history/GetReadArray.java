/**
 * 
 */
package read.rqma.history;

import com.h2.constant.Parameters;

import controller.ADMINISTRATOR;
import mutiThread.MainThread;
import read.yang.readFile.ReadData;

/**
 * @author Rq Ma
 */
public class GetReadArray {
	/**
     * 该函数体里的内容，会在程序里出现两次，为了避免代码的冗余，故放在一个函数体了，在两个地方调用函数即可
     *
     * @param alignFile
     * @param timeStr
     * @return Parameters.sensorNum个 ReadData
     */
    public static ReadData[] getDataArray(AlignFile alignFile, String timeStr, ADMINISTRATOR manager) {
        /**保存对齐后的timeCount变量*/
        int kuai[] = new int[Parameters.SensorNum];
        /**统计对齐时间开销*/

        /**
         * 定义 ReadData
         */
        ReadData[] readDataArray = new ReadData[Parameters.SensorNum];
        long m1 = 0;
        long m2 = 0;
        try {
            AlignFile.align = alignFile.getAlign(MainThread.fileStr, timeStr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < Parameters.SensorNum; i++) {
            if (AlignFile.align[i] == -1) {
                System.out.println("对齐文件时出错");
                return null;
            }
        }
        
        String file[] = alignFile.paths_original;//文件路径
        for (int i = 0; i < Parameters.SensorNum; i++) {
            try {
                readDataArray[i] = new ReadData(file[i], i, manager);
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
       
        m1 = System.currentTimeMillis();
        for (int i = 0; i < Parameters.SensorNum; i++) {
            try {
                readDataArray[i].timeCount = 0;
                kuai[i] = readDataArray[i].readDataAlign(MainThread.fileStr[i], i);
                if (kuai[i] == -1) {
                    System.out.println("该组数据无法对齐，开始对齐下一组---------");
                    readDataArray=null;
                    return null;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        m2 = System.currentTimeMillis();
        System.out.println("对齐时间花费：" + (m2 - m1) + "ms");

        return readDataArray;
    }
}
