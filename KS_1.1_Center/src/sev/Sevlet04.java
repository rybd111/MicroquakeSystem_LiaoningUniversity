package sev;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mutiThread.MainThread;
import bean.DataRec;

import com.h2.main.EarthQuake;

public class Sevlet04 extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type=request.getParameter("type");
		Vector<String> beforeVector011 = new Vector<String>();
		Vector<String> nowVector012 = new Vector<String>();
		Vector<String> afterVector013 = new Vector<String>();
		DataRec aDataRec =new DataRec(beforeVector011, nowVector012, afterVector013);
		try {
		if(MainThread.aDataRec[3]!=null) {
		aDataRec=MainThread.aDataRec[3];
		
		
		Vector<String> showDataX=new Vector<String>();
		Vector<String> showDataY=new Vector<String>();
		Vector<String> showDataZ=new Vector<String>();
		
		for (int i = 0; i < aDataRec.getAfterVector().size(); i++) {
			/*if(aDataRec.getAfterVector()== null||aDataRec.getAfterVector().size()>=1000||i>=aDataRec.getAfterVector().size()){
				System.out.println("array index out of range------4444444444444444444444");
				
			}
			else{*/
			String[] arrStr=aDataRec.getAfterVector().get(i).split(" ");
			showDataX.add(arrStr[3]);
			showDataY.add(arrStr[4]);
			showDataZ.add(arrStr[5]);
			//System.out.println(arrStr[1]);
		}
		/*for (int i = 0; i < 100; i++) {
			beforeVector011.add(String.valueOf(i));
		}*/
	
		//request.setAttribute("bb",aDataRec.getBeforeVector());
//		System.out.print(request.getAttribute("str"));
		
		String dataStr=EarthQuake.outString;
		
		PrintWriter pw = response.getWriter();
		if(type.equals("1")){
		pw.print(showDataX);
		}else {
			if (type.equals("2")) {
				pw.print(showDataY);
			}
			else {
				pw.print(showDataZ);
			}
		}
		pw.flush();
		pw.close();
		}
		}catch (Exception e) {}
			
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
