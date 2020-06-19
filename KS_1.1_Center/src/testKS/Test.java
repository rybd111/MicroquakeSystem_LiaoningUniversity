package testKS;

import java.io.IOException;
public class Test {

	public static void main(String[] args) throws IOException {
		long a=GetLocation.getLocation();
		System.out.println("a"+a);
	}
}
