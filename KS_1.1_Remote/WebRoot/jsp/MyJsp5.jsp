<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%-- <base href="<%=basePath%>"> --%>

<title>煤矿冲击地压震级定位系统</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="../js/echarts.common.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
</head>
<script type="text/javascript">
    
    function countDown(secs){     
	 if(--secs>0){    
			setTimeout("countDown("+secs+")",1000);     
		}else{ 
			init2();
			
		}   
} 
    
$.ajaxSetup({cache:false});
window.setInterval("countDown(0)",1000); 
function init2(){
	$.get("../servlet/ZJDispl",function(data,status){
		
	var eleX=window.document .getElementById("x");
	var eleY=window.document.getElementById("y");
	var eleHB=window.document.getElementById("hb");
	var eleZJ=window.document.getElementById("zj");
	var elenl=window.document.getElementById("nl");
	var data1 =  data.split(" ");
	
	var y1=0.64940742498539*data1[1]+35985600.9610101;
	var y2=-1.53986503030543*data1[1]+44192917.6882761;
	var y3=-1.53983673409655*data1[1]+44185403.6645409;
	var y4=0.649460010261126*data1[1]+35995275.1940533;
	
	
	//if(data1[4]>0 && data1[0]>y1 && data1[0]<y2 && data1[0]>y3 && data1[0]<y4 && data1[1]<3748879.0 && data1[1]>3740986.0){
	
	//if(data1[0]>0 && data1[1]>0 && data1[2]>0){
		eleX.innerHTML =data1[0];//x
		eleY.innerHTML =data1[1];//y
		eleHB.innerHTML =data1[2];//z
		eleZJ.innerHTML =data1[3];//zj
		elenl.innerHTML = data1[4];//nl
	//}
	/* } */
	
});
	}
</script>
<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}

.head {
	width: 100%;
	height: 20%;
	background-image: url("../image/u2.png");
	background-size: 100% 100%;
}

.head_name {
	display: block;
	text-align: center;
	padding-top: 2.5%;
	font-family: 华文行楷;
	font-weight: bold;
	font-size: 48px;
}

.left {
	width: 30%;
	height: 600px;
	float: left;
	margin-top: 60px;
}

.table {
	width: 80%;
	height: 400px;
	margin: 50px auto;
	padding-top: 40px;
	background-image: url("../image/table.png");
	background-size: 100% 100%;
}

.cad {
	width: 70%;
	height: 600px;
	float: left;
	margin-top: 20px;
}

.table_word {
	font-family: 微软雅黑;
	font-size: 32px;
	color: white;
	margin-left: 40px;
}

#dw {
	width: 80%;
	margin-top: 15px;
	margin-left: 10%;
	background-color: rgba(255, 255, 255, 1);
	box-sizing: border-box;
	border-width: 1px;
	border-style: solid;
	border-color: rgba(121, 121, 121, 1);
	border-radius: 44px;
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	box-shadow: none;
	font-family: '微软雅黑 Bold', '微软雅黑';
	font-weight: 700;
	font-style: normal;
	font-size: 36px;
}

#MxDrawXCtrl {
	width: 90%;
	margin: 0 5%;
}
</style>
</head>
<body>
	<div class="head">
		<span class="head_name">煤矿冲击地压震级定位系统</span>
	</div>
	<div class="left">
		<div class="table">
			<span class="table_word">震中坐标</span> 
			<br> <span class="table_word">X:</span> <span class="table_word" id="x"></span>
			<br> <span class="table_word">Y:</span> <span class="table_word" id="y"></span> 
			<br> <span class="table_word">Z:</span> <span class="table_word" id="hb"></span>
			<br> <br> <span class="table_word">震级:</span> <span class="table_word" id="zj"></span> <br> <br>
			<br> <br> <span class="table_word">能量:</span> <span class="table_word" id="nl"></span> <br> <br>
			<input type="button" value="定    位" onclick="DoCmd(0)" id="dw">
		</div>
	</div>
	<div class="cad">
		<object classid="clsid:74A777F8-7A8F-4e7c-AF47-7074828086E2"
			id="MxDrawXCtrl">
			<param name="_Version" value="65536">
			<param name="_ExtentX" value="24262">
			<param name="_ExtentY" value="16219">
			<param name="_StockProps" value="0">
			<param name="DwgFilePath"
				value="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/红阳三矿.dwg">
			<param name="ViewColor" value="16777215">
			<param name="IsRuningAtIE" value="1">
			<param name="EnablePrintCmd" value="1">
			<param2name ="EnableIntelliSelect"  value="0">
			<param name="ShowCommandWindow" value="0">
			<param name="ShowModelBar" value="1">
			<param name="FirstRunPan" value="1">
			<param name="Iniset"
				value="AutoActive=N,EnableGripPoint=N,UseArrowCursor=Y,AutoZoomAll=Y,IintAnimation=Y">
			<param name="ToolBarFiles" value="">
			<param name="IsBrowner" value="1">
			<param name="ShowMenuBar" value="0">
			<param name="EnableUndo" value="0">
		</object>
	</div>
	<div>
		<a href="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/index.jsp">首页</a>
	</div>
	
	
	<script>
		var currentPath="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/";
		var mxOcx = document.getElementById("MxDrawXCtrl");
		var o;
		function DoCmd(iCmd) {
			mxOcx.DoCommand(iCmd);
		}
		function DoCommandEventFunc(iCmd) {
			if (iCmd == 0) {
				InsertImage();
			}
		}
		/* function InitMxDrawX() {
			alert("1111");
			InsertSensor();
			if (mxOcx) {
				if (!mxOcx.IsIniting()) {
					clearInterval(mxtime);
					// 控件初始化完成，需要在启动做的事，在这里做
					
					
					//mxOcx.setCurrentLayout("布局名")

				}
			}
		} */

		document.getElementById("MxDrawXCtrl").ImplementCommandEventFun = DoCommandEventFunc;
		document.getElementById("MxDrawXCtrl").ImplementMouseEventFun = MouseEvent;
		
		function InsertImage() {
			mxOcx.Clear(o);          //清除上一次的定位信息和图标
			var xstr = document.getElementById('x').innerText;
			var ystr = document.getElementById('y').innerText;
			var hbstr = document.getElementById('x').innerText;
			var zjstr = document.getElementById('x').innerText;
			var x = parseFloat(xstr);
			var y = parseFloat(ystr);
			var hb = parseFloat(hbstr);
			var zj = parseFloat(zjstr);
			
			/* var NewX = y-38420000;
			var NewY = x-3744500;
			var angle = 33;
			var radian = angle * Math.PI / 180;
			var y1 = NewY*Math.cos(radian)+NewX*Math.sin(radian);
			var x1 = NewX*Math.cos(radian)-NewY*Math.sin(radian); */
			var sImageFile = currentPath + "123.png";
			var sImageFile1 = currentPath + "456.png";
			o = mxOcx.DrawImageMark(x,y, 0.5, 0.0, sImageFile,sImageFile+","+sImageFile1, false);
			
			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
            var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
            var res = mxOcx.NewResbuf();//新建图层
            res.AddLong(2147403647);//设置图层高度
            ent.SetProp("drawOrder",res);//将图片插入到新图层
            
            
            
		}
		function InsertSensors(){
			var sensorX=new Array();
			var sensorY=new Array();
			var sensorZ=new Array();
			var sImageFile = currentPath+"sensors.png";
			var sImageFile2 = currentPath+"sensors1.png";
			sensorX[0]=41519304.125;sensorY[0]=4595913.485;
			sensorX[1]=41519926.476;sensorY[1]=4597275.978;
			sensorX[2]=41520815.875;sensorY[2]=4597384.576;
			sensorX[3]=41520207.356;sensorY[3]=4597983.404;
			sensorX[4]=41516849.629;sensorY[4]=4598099.366;
			sensorX[5]=41516836.655;sensorY[5]=4596627.472;
			sensorX[6]=41516707.440;sensorY[6]=4593163.619;
			sensorX[7]=41518060.298;sensorY[7]=4594304.927;
			sensorX[8]=41518099.807;sensorY[8]=4595388.504;
		
			for(var i=0;i<sensorX.length;i++){
				
				o = mxOcx.DrawImageMark(sensorX[i],sensorY[i],0.1, 0.0, sImageFile,sImageFile+","+sImageFile2, false);
				mxOcx.TwinkeEnt(o);//开启图片闪烁功能
	            var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
	            var res = mxOcx.NewResbuf();//新建图层
	            res.AddLong(2147403647);//设置图层高度
	            ent.SetProp("drawOrder",res);//将图片插入到新图层
            }
		}
		function MouseEvent(dX,dY,lType)
		{
			if(lType==2)
				InsertSensors();
		}
		
	</script>
	
</body>
</html>
