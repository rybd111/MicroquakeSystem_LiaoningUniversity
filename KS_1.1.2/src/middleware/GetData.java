/**
 * 
 */
package middleware;

/**
 * @author Hanlin Zhang
 */
public class GetData {

	/**
	 * 根据KIND进行读取数据的方式。
	 * @param KIND
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月28日下午1:52:07
	 */
	public static void GetDataA(int KIND) {
		switch (KIND) {
		//远程读取数据
		case 11:
			getDataFromRemote();
			break;
			
		//本地读取数据
		case 12:
			getDataFromLocal();
			break;
		
		default:
			System.out.println("请选择获得数据的方式");
			System.out.println("程序退出。");
			System.exit(0);
			break;
		}
	}
	
	private static void getDataFromRemote() {
		
	}
	
	private static void getDataFromLocal() {
		
	}
	
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年2月28日下午1:44:15
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
