package com.yang.search.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class TopKSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//
		String place = request.getParameter("place");
		String time = request.getParameter("time");
		String k = request.getParameter("k");

		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		ArrayList<Integer> z = new ArrayList<>();

		FileReader fileReader = new FileReader("E:\\kuangshandata\\60.txt");
		BufferedReader buff = new BufferedReader(fileReader);
		// --------------------------------------- //

		int point = Integer.valueOf(time) * 5000;
		String line = null;
		String[] tmp = null;

		// read
		int count = 0;
		for (; count < point; count++) {
			line = buff.readLine();
			tmp = line.split(" ");
			x.add(Integer.valueOf(tmp[0]));
			y.add(Integer.valueOf(tmp[1]));
			z.add(Integer.valueOf(tmp[2]));
		}

		// sort arraylist x , y ,z

		int[] xArray = new int[x.size()];
		int[] yArray = new int[y.size()];
		int[] zArray = new int[z.size()];

		// copy array
		for (int i = 0; i < x.size(); i++)
			xArray[i] = x.get(i);

		for (int i = 0; i < y.size(); i++)
			yArray[i] = y.get(i);
		for (int i = 0; i < z.size(); i++)
			zArray[i] = z.get(i);

		// sort
		Arrays.sort(xArray);
		Arrays.sort(yArray);
		Arrays.sort(zArray);

		// get top k

		ArrayList<Integer> xTop = new ArrayList<>();
		ArrayList<Integer> yTop = new ArrayList<>();
		ArrayList<Integer> zTop = new ArrayList<>();

		for (int i = xArray.length - 1; i >= xArray.length - Integer.valueOf(k); i--) {
			xTop.add(xArray[i]);
		}

		for (int i = yArray.length - 1; i >= yArray.length - Integer.valueOf(k); i--) {
			yTop.add(yArray[i]);
		}

		for (int i = zArray.length - 1; i >= zArray.length - Integer.valueOf(k); i--) {
			zTop.add(zArray[i]);
		}

		Map<String, ArrayList> map = new HashMap<>();
		map.put("xdata", xTop);
		map.put("ydata", yTop);
		map.put("zdata", zTop);

		String json = JSON.toJSONString(map);
		response.setContentType("application/json;charset=utf-8");// 指定返回的格式为JSON格式
		response.setCharacterEncoding("UTF-8");
		PrintWriter ps = response.getWriter();
		ps.write(json);
		ps.flush();

	}

}
