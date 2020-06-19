package sev;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ParseConversionEvent;

import mutiThread.MainThread;
import bean.DataRec;

import com.h2.main.EarthQuake;

public class Sensor extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Sensor() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("type"+request.getParameter("type"));
		Vector<String> beforeVector011 = new Vector<String>();
		Vector<String> nowVector012 = new Vector<String>();
		Vector<String> afterVector013 = new Vector<String>();
		DataRec aDataRec =new DataRec(beforeVector011, nowVector012, afterVector013);
		aDataRec=MainThread.aDataRec[0];
		
		
		Vector<String> showData=new Vector<String>();
		for (int i = 0; i < 1000; i++) {
			String[] arrStr=aDataRec.getAfterVector().get(i).split(" ");
			showData.add(arrStr[1]);
			//System.out.println(arrStr[1]);
		}
		/*for (int i = 0; i < 100; i++) {
			beforeVector011.add(String.valueOf(i));
		}*/
	
		//request.setAttribute("bb",aDataRec.getBeforeVector());
		System.out.print(request.getAttribute("str"));
		
		String dataStr=EarthQuake.outString;
		PrintWriter pw = resp.getWriter();
		//System.out.println(sb.toString());
		pw.print(showData);
		//pw.print("testajax");
		pw.flush();
		pw.close();
		/*request.setAttribute("outStr", dataStr);
		request.getRequestDispatcher("/display.jsp").forward(request,
		response);	*/
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
