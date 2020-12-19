package com.yang.readFile;

import java.io.File;

public class FindTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FindNewFile aFile =new FindNewFile();
		File str=aFile.researchFile("F:/",0);
		System.out.println(str.getAbsolutePath());
//		String aString =str.getAbsolutePath();
//		System.out.println(aFile.researchFile("D:/kuangshandata/HYJ/GYGC", 0));
	}

}
