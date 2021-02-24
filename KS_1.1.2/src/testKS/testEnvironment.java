/**
 * @revision 2020年10月26日
 * @author Hanlin Zhang
 */
package testKS;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.DbExcute;
import com.mathworks.toolbox.javabuilder.MWException;

import DataExchange.QuackResults;
import testEnvironment.SUM;

/**
 * 
 * @author Hanlin Zhang
 * @date revision 2020年10月26日
 */
public class testEnvironment {

	/**
	 * Test environment of matlab, db.
	 * @param args
	 * @author Hanlin Zhang
	 * @throws MWException 
	 * @date revision 2020年10月26日
	 */
	public static void main(String[] args) throws MWException {
		//test matlab environment.
		int a = 1,b = 1;
		SUM s = new SUM();
		Object[] conse = s.testEnvironment(1, a, b);
		System.out.println("consequence:" + conse[0].toString());
		if(conse[0].toString().equals("2")) {
			System.out.println("MATLAB环境配置正确");
		}
		else {
			System.out.println("MATLAB配置错误，参见配置文档。");
		}
		//test database environment.
		String adate="20"+"170524151342";
	    Date date = new Date();
	    DateFormat formatDate=new SimpleDateFormat("yyyyMMddHHmmss");
	    try {
			date=formatDate.parse(adate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        QuackResults aQuackResults =new QuackResults(15000, 10, 25, "", 5.2, 0.2, " ", 0.0,0.0, " ",0.0," ",0.0);
        DbExcute aDbExcute =new DbExcute();
        if(aDbExcute.addElement(aQuackResults)) {
        	System.out.println("数据库环境配置正确");
        }
        else {
			System.out.println("数据库配置错误，参见配置文档。");
		}
	}

}
