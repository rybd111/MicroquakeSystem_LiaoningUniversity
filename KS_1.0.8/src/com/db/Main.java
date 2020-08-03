package com.db;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bean.QuackResults;

public class Main {
	public static void main(String[] args) {
		/*DbExcute aDbExcute =new DbExcute();
		aDbExcute.addElement("INSERT INTO mine_quack_results  VALUES (NULL,'15', '45', '45', '2017-10-24 11:36:49', '5')");
	*/
		
		String adate="20"+"170524151342";
	    Date date = new Date();
	    DateFormat formatDate=new SimpleDateFormat("yyyyMMddHHmmss");
	    try {
			date=formatDate.parse(adate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     //format锟侥革拷式锟斤拷锟斤拷锟斤拷锟斤拷
		DateFormat formatDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//三台站：[x=4595388.504 # y=41518099.807 # z=22.776 # Time=2019-11-26 12:38:27 # Grade=-0.22#Parrival0.0--------------------------------------------------------------------------]
		
        String time = formatDateTime.format(date);
        Timestamp timestamp =Timestamp.valueOf(time);
        QuackResults aQuackResults =new QuackResults(15000, 10, 25, "", 5.2, 0.2, " ", 0.0,0.0, " ",0.0," ");
        QuackResults aQuackResults3 =new QuackResults(4595388.504, 41518099.807, 22.776, "", 5.2, 0.2, " ", 0.0,0.0, " ",0.0,"");
        DbExcute aDbExcute =new DbExcute();
//        aDbExcute.addElement(aQuackResults);
        
        aDbExcute.addElement3(aQuackResults3);
        //System.out.println(timestamp.toString());
        //去锟斤拷
//        String sqlstr2 ="delete from mine_quack_results  where id in (select id from (select id from mine_quack_results where quackTime in (select quackTime from mine_quack_results group by quackTime having count(quackTime)>1)"+ 
//        "and id not in(select min(id) from mine_quack_results group by quackTime having count(quackTime)>1)) as tmpresult) ";
//        aDbExcute.addElement(sqlstr2);
	}
}
