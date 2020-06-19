/**
 * 
 */
package sev;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DbExcute;
import com.h2.constant.Parameters;

/**
 * @author Hanlin Zhang
 */
public class dataShow extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String x1=request.getParameter("x1");
		String y1=request.getParameter("y1");
		String x2=request.getParameter("x2");
		String y2=request.getParameter("y2");
		
		String[] paras={x1,y1,x2,y2};
//		
//		ArrayList<String> al=aDbExcute.getData(paras);
//		
//		for(int i=0;i<al.size();i=i+paras.length+1){
//			String data=al.get(i)+" "+al.get(i+1)+" "+al.get(i+2)+" "+al.get(i+3)+" "+al.get(i+4);
//			out.println(data);
//		}
//		
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
