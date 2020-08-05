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


public class Serv1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String startDate=request.getParameter("SD");
		String closeDate=request.getParameter("CD");
		String level=request.getParameter("level");
		String energy=request.getParameter("energy");
		
		String[] paras={startDate,closeDate,level,energy};
		DbExcute aDbExcute =new DbExcute();
		String sqlstr2 ="delete from "+Parameters.DatabaseName3+"  where id in (select id from (select id from "+Parameters.DatabaseName3+" where quackTime in (select quackTime from "+Parameters.DatabaseName3+" group by quackTime having count(quackTime)>1)"+ 
		        "and id not in(select min(id) from "+Parameters.DatabaseName3+" group by quackTime having count(quackTime)>1)) as tmpresult) ";
		aDbExcute.deleteRepate(sqlstr2);
		ArrayList<String> al=aDbExcute.getData(paras);
		
		for(int i=0;i<al.size();i=i+paras.length+1){
			String data=al.get(i)+" "+al.get(i+1)+" "+al.get(i+2)+" "+al.get(i+3)+" "+al.get(i+4);
			out.println(data);
			
		}
		
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
