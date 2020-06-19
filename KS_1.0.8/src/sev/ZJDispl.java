package sev;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.h2.main.EarthQuake;

public class ZJDispl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ZJDispl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*Vector<String> aVector =new Vector<String>();
		aVector.add("0");
		aVector.add("0");
		aVector.add("0");
		aVector.add("0");
		aVector.add("0");*/
		String outStr="0 0 0 0 0";
		/*44442576.976 5180224.144 74998.573 170524153319 0.0*/
		if (EarthQuake.outString !="") {
			/*aVector.clear();*/
			outStr =EarthQuake.outString;
			/*String[] resultStr = outStr.split(" ");*/
			/*aVector.add(resultStr[0]);
			aVector.add(resultStr[1]);
			aVector.add(resultStr[2]);
			aVector.add(resultStr[4]);*/
		}
		/*request.getAttribute("ss");
		System.out.println("ddd"+request.getParameter("ss"));
		String outStr = "442051.01994352613 5180480.529752804 0.0 170524153219 0.0";
		outStr = EarthQuake.outString;
		if (outStr !="") {
			String[] resultStr = outStr.split(" ");
			double[] resultDouble = new double[resultStr.length];
			for (int i = 0; i < resultDouble.length; i++) {
				resultDouble[i] = Double.parseDouble(resultStr[i]);
				System.out.println(resultDouble[i]);
			}
			request.setAttribute("x", resultDouble[0]);
			request.setAttribute("y", resultDouble[1]);
			request.setAttribute("zj", resultDouble[4]);
			request.setAttribute("hb", resultDouble[2]);
			request.getRequestDispatcher("/jsp/MyJsp.jsp").forward(request,
					response);
		}*/
		PrintWriter pw= response.getWriter();
		pw.print(outStr);
		pw.flush();
		pw.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
