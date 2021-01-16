package com.db;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbExcute aDbExcute =new DbExcute();
		String startDate="2017-01-01";
		String closeDate="2017-07-08";
		String level="0";
		String sql="INSERT INTO `mine_quack_results` (`xData`, `yData`, `zData`, `quackTime`, `quackGrade`) VALUES ('123456', '78674729384.1', '1344985.642', '2018-07-05 14:32:59', '2.45')";
		aDbExcute.update(sql);
		String[] paras={startDate,closeDate,level};
		System.out.println(aDbExcute.getData(paras).toString());
		
	}

}
