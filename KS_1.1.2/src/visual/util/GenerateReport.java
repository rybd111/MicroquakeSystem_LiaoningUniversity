package visual.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.h2.constant.Parameters;
import com.mysql.fabric.xmlrpc.base.Param;

import DataExchange.QuackResults;
import visual.model.TableData;

/***
 * 用于生成报表，饿汉式单例模式 2021/3/16
 * 
 * @author Sunny-胡永亮
 *
 */
public class GenerateReport {
	public static boolean isReport = false;

	public void setData(QuackResults data) {
		this.kind = data.getKind();
		String temp[] = data.getFilename_S().split("/");
		String temp1[] = temp[temp.length - 1].split(" ");
		this.filename_S = temp1[1] + temp1[2].split("\\.")[0];
		this.xData = data.getxData();
		this.yData = data.getyData();
		this.zData = data.getzData();
		this.Parrival = data.getParrival();
		this.quackGrade = data.getQuackGrade();
		this.nengliang = data.getNengliang();
		this.eventName = data.getQuackTime();
		this.quackTime = data.getQuackTime();
	}

	private int number = 1;
	// 类型
	private String kind = "PSO";
	// 最早到时
	private String filename_S = "2021-01-0216-59-34`4";// s
	// 定位坐标
	private double xData = 2817.696;
	private double yData = 907.792;
	// 震源深度估计
	private double zData = -344.217;
	// P波到时
	private double Parrival = -3.138732047485;// s
	// 震级：
	private double quackGrade = 1.63;
	// 能量
	private double nengliang = 624040;// J
	// 事件名称
	public String eventName = "2021-01-02 16:59:31.2612679525149346";
	// 发震时刻：
	private String quackTime = "2021-01-02 16:59:31.2612679525149346";

	private XWPFDocument document = null;

	public String generate(String readPath) throws Exception {
		document = new XWPFDocument();
		String SensorinfName = null;
		switch (Parameters.diskNameNum) {
		case 0:
			SensorinfName = "红阳三矿";
			break;
		case 1:
			SensorinfName = "大同";
			break;
		case 2:
			SensorinfName = "平顶山";
			break;
		case 3:
			SensorinfName = "马道头";
			break;
		default:
			break;
		}
		//写入的文件路径
		String filePath = Parameters.prePath + File.separator + "Report"
				+ File.separator + SensorinfName + eventName.split(" ")[0] + ".docx";
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File(filePath));
		// ================添加大标题======================
		XWPFParagraph titleParagraph = document.createParagraph();
		// 设置段落居中
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText(SensorinfName + eventName.split(" ")[0] + "矿震数据初步分析");
		titleParagraphRun.setColor("000000");
		titleParagraphRun.setFontSize(23);
		// ================添加小标题：1.基本参数：======================
		XWPFParagraph title1 = document.createParagraph();
		XWPFRun titleRun1 = title1.createRun();
		titleRun1.setText("1.基本参数：");
		titleRun1.setColor("000000");
		titleRun1.setFontSize(18);
		titleRun1.addCarriageReturn();
		for (int i = 1; i <= number; i++) {
			// ================添加小标题：事件名称======================
			XWPFParagraph title11 = document.createParagraph();
			XWPFRun titleRun11 = title1.createRun();
			titleRun11.setText("事件" + number + ": " + eventName + "  分析结果如下：");
			titleRun11.setColor("000000");
			titleRun11.setFontSize(13);
			// 生成表格
			insertTable();
			// 段落
//			XWPFParagraph firstParagraph = document.createParagraph();
//			XWPFRun run = firstParagraph.createRun();
//			run.setFontSize(10);
//
//			run.setText("类型：" + kind);
//			run.addCarriageReturn();
//
//			run.setText("最早到时：" + filename_S + "s");
//			run.addCarriageReturn();
//
//			run.setText("定位坐标：x: " + xData + " y: " + yData);
//			run.addCarriageReturn();
//
//			run.setText("震源深度估计：z: " + zData);
//			run.addCarriageReturn();
//
//			run.setText("P波到时：" + Parrival + "s");
//			run.addCarriageReturn();
//
//			run.setText("震级： " + quackGrade);
//			run.addCarriageReturn();
//
//			run.setText("能量：" + nengliang + "J");
//			run.addCarriageReturn();
//
//			run.setText("发震时刻：" + quackTime);
//			run.addCarriageReturn();
		}
		// ================添加小标题：2.定位图：======================
		XWPFParagraph title3 = document.createParagraph();
		XWPFRun titleRun3 = title3.createRun();
		titleRun3.setText("2.定位图：");
		titleRun3.setColor("000000");
		titleRun3.setFontSize(18);
		// 插入图片
		
		insertJPG(readPath + "-cad" + ".png");

		// ================添加小标题：3.波形图：======================
		XWPFParagraph title2 = document.createParagraph();
		XWPFRun titleRun2 = title2.createRun();
		titleRun2.setText("3.波形图：");
		titleRun2.setColor("000000");
		titleRun2.setFontSize(18);
		// 插入图片
		insertJPG(readPath + "-wave" + ".png");
		document.write(out);
		out.close();
		// 清理word文档缓存
		document.close();
		document.close();
		System.out.println("生成报表完成！！！！");
		return filePath;
	}

	/**
	 * 生成表格
	 */
	private void insertTable() {

		XWPFTable infoTable = document.createTable();
		// 去表格边框
//        infoTable.getCTTbl().getTblPr().unsetTblBorders();

		// 列宽自动分割
		CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
		infoTableWidth.setType(STTblWidth.DXA);
		infoTableWidth.setW(BigInteger.valueOf(9072));
		// 表格第一行
		XWPFTableRow infoTableRowOne = infoTable.getRow(0);
		infoTableRowOne.getCell(0).setText("类型");
		infoTableRowOne.addNewTableCell().setText(" " + kind);

		// 表格第二行
		XWPFTableRow infoTableRowTwo = infoTable.createRow();
		infoTableRowTwo.getCell(0).setText("最早到时");
		infoTableRowTwo.getCell(1).setText(" " + filename_S + "s");
		// 表格第三行
		XWPFTableRow infoTableRowThree = infoTable.createRow();
		infoTableRowThree.getCell(0).setText("定位坐标");
		infoTableRowThree.getCell(1).setText(" x: " + xData + " y: " + yData);

		// 表格第四行
		XWPFTableRow infoTableRowFour = infoTable.createRow();
		infoTableRowFour.getCell(0).setText("震源深度估计");
		infoTableRowFour.getCell(1).setText(" z: " + zData);

		// 表格第五行
		XWPFTableRow infoTableRowFive = infoTable.createRow();
		infoTableRowFive.getCell(0).setText("P波到时");
		infoTableRowFive.getCell(1).setText(" " + Parrival + "s");
		// 表格第六行
		XWPFTableRow infoTableRowSix = infoTable.createRow();
		infoTableRowSix.getCell(0).setText("震级");
		infoTableRowSix.getCell(1).setText(" " + quackGrade);
		// 表格第七行
		XWPFTableRow infoTableRowSeven = infoTable.createRow();
		infoTableRowSeven.getCell(0).setText("能量");
		infoTableRowSeven.getCell(1).setText(" " + nengliang + "J");
		// 表格第八行
		XWPFTableRow infoTableRowEight = infoTable.createRow();
		infoTableRowEight.getCell(0).setText("发震时刻");
		infoTableRowEight.getCell(1).setText(" " + quackTime);

	}

	/**
	 * 向word文档里插入图片
	 * 
	 * @param path 图片路径
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 */
	private void insertJPG(String path) throws InvalidFormatException, FileNotFoundException, IOException {
		// ================插入图片======================
		XWPFParagraph pictures = document.createParagraph();
		pictures.setIndentationLeft(300);// 前面缩进300
		XWPFRun insertNewRun = pictures.createRun();
		int format = 0;
		String imgFile = path;

		if (imgFile.endsWith(".emf"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFile.endsWith(".wmf"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFile.endsWith(".pict"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFile.endsWith(".png"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFile.endsWith(".dib"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFile.endsWith(".gif"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFile.endsWith(".tiff"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFile.endsWith(".eps"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFile.endsWith(".bmp"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFile.endsWith(".wpg"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			System.err.println(
					"Unsupported picture: " + imgFile + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");

		}
		// 宽 高
		insertNewRun.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(400), Units.toEMU(450)); // 高150，宽350
		// insertNewRun.addBreak(BreakType.PAGE);//图片独占一页
//		insertNewRun.addBreak();// 添加一个回车空行
	}
}
