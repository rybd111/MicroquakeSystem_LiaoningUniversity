/**
 * 
 */
package utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import utils.GDI32Extra;
import utils.User32Extra;
import utils.WinGDIExtra;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HBITMAP;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinGDI.BITMAPINFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;

/**
 * @author Hanlin Zhang
 */
@SuppressWarnings("serial")
public class CutPaint extends JFrame {
	BufferedImage image;
	boolean writeFlag = false;
	/**
	 * @param args
	 * @author Hanlin Zhang.
	 * @date revision 2021年4月8日上午11:37:43
	 */
	public static void main(String[] args) {
		int[] cutSize = {50, 100};
		
		new CutPaint("lib", "E:/1.jpg", cutSize);
	}

	public BufferedImage capture(HWND hWnd, int[] cutSize) {

		HDC hdcWindow = User32.INSTANCE.GetDC(hWnd);
		HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

		RECT bounds = new RECT();
		User32Extra.INSTANCE.GetClientRect(hWnd, bounds);

//		int width = bounds.right - bounds.left;
		int width = cutSize[0];
		
//		int height = bounds.bottom - bounds.top;
		int height = cutSize[1];
		
		HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);

		HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
		GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0, 0, WinGDIExtra.SRCCOPY);

		GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
		GDI32.INSTANCE.DeleteDC(hdcMemDC);

		BITMAPINFO bmi = new BITMAPINFO();
		bmi.bmiHeader.biWidth = width;
		bmi.bmiHeader.biHeight = -height;
		bmi.bmiHeader.biPlanes = 1;
		bmi.bmiHeader.biBitCount = 32;
		bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

		Memory buffer = new Memory(width * height * 4);
		GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

		GDI32.INSTANCE.DeleteObject(hBitmap);
		User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);

		return image;
	}

	public CutPaint(String windowName, String writePath, int[] cutSize) {
		HWND hWnd = User32.INSTANCE.FindWindow(null, windowName);
		this.image = capture(hWnd, cutSize);
		
		try {
			boolean writeFlag = ImageIO.write(this.image, "jpg", new FileOutputStream(writePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.writeFlag = writeFlag;
		//显示截图。
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		pack();
//		setExtendedState(MAXIMIZED_BOTH);
//		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 20, 40, null);
	}
	
	/**获取截图*/
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**获取写入状态*/
	public boolean getWriteStatus() {
		return this.writeFlag;
	}
}
