package com.yang.search.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class RangeSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String point = request.getParameter("point");
		String place = request.getParameter("place");
		String range = request.getParameter("range");

		int range_Integer = Integer.valueOf(range);
		int point_Integer = Integer.valueOf(point);

		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		ArrayList<Integer> z = new ArrayList<>();

		FileReader fileReader = new FileReader("E:\\kuangshandata\\60.txt");
		BufferedReader buff = new BufferedReader(fileReader);

		// pre-read
		int count = 0;
		for (; count < point_Integer - range_Integer; count++) {
			buff.readLine();
		}

		// 读取真正的数据
		String line = null;
		int[] xyz = new int[3];
		String[] tmp = null;

		for (; count <= point_Integer + range_Integer; count++) {
			line = buff.readLine();
			tmp = line.split(" ");

			// 将数据分别放入x，y，z 中
			x.add(Integer.valueOf(tmp[0]));
			y.add(Integer.valueOf(tmp[1]));
			z.add(Integer.valueOf(tmp[2]));
		}

		// 接下来把数据转换成json
		Map<String, ArrayList> map = new HashMap<>();
		map.put("xdata", x);
		map.put("ydata", y);
		map.put("zdata", z);

		// 下面这两行代码，可以不使用
		 response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
		 response.setCharacterEncoding("UTF-8");

		String json = JSON.toJSONString(map);

		PrintWriter ps = response.getWriter();
		ps.write(json);
		ps.flush();

	}

}
