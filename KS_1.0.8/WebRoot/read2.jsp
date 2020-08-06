<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>煤矿冲击地压震级统计</title>
	<link rel="stylesheet" type="text/css" href="css/head.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">	
	<link rel="stylesheet" type="text/css" href="css/index2.css">
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>	
  </head>
  <body>
  	<div class="head">
        <a href="index.jsp">首页<img src="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/152569275640874.png"></a>
        <a href="jsp/MyJsp5.jsp">实时数据</a>
        <a href="read2.jsp">微震统计</a>
       <!--  <a href="search.jsp">特征查询</a> -->
        <a href="jsp/MyJsp4.jsp">煤矿微震波形</a>
    </div>
    <div class="searchBox">
        <div class="radio">

        </div>
        <div class="test">
            <label>
            	<span>查询日期:</span>
               	<input id="sdate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'%y-%M-%d'})"/>&nbsp;-&nbsp;<input id="edate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'sdate\')}',maxDate:'%y-%M-%d'})"/>
            </label>
            <label>
				震级在<input type="text" id="n1" value="0">级以上<br/>
				<span></span>能量在<input type="text" id="n2" value="0">焦耳以上
            </label>
        </div>
        <div class="searchbtn">
            <button type="button" class="btn btn-success" onclick="DoCmd(0)">查找</button>
            <button type="button" class="btn btn-success" onclick="DoCmd(1)">清除</button>            
        </div>
    </div>
    <div class="cad">
    	<object height="85%" align="left" id="MxDrawXCtrl" classid="clsid:74A777F8-7A8F-4e7c-AF47-7074828086E2" id = "MxDrawXCtrl">
    	<PARAM NAME="_Version" VALUE="65536">
    	<PARAM NAME="_ExtentX" VALUE="24262">
    	<PARAM NAME="_ExtentY" VALUE="20241">
    	<PARAM NAME="_StockProps" VALUE="0">
    	<PARAM NAME="DwgFilePath" VALUE="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/红阳三矿.dwg">
    	<PARAM NAME="IsRuningAtIE" VALUE="1">
    	<PARAM NAME="EnablePrintCmd" VALUE="1">
    	<PARAM NAME="EnableOpenCmd" VALUE="1">
    	<PARAM NAME="ViewColor" VALUE="16777215">
    	<PARAM NAME="HightQualityDraw" VALUE="0">
    	<PARAM NAME="FirstRunPan" VALUE="0">
    	<PARAM NAME="ShowStatusBar" VALUE="0">
    	<PARAM NAME="IsBrowner" VALUE="0">
    	<PARAM NAME="IsDrawCoord" VALUE="0">
    	<PARAM NAME="LineWidth" VALUE="0">
    	<PARAM NAME="LineType" VALUE="">
    	<PARAM NAME="LayerName" VALUE="">
    	<PARAM NAME="TextStyle" VALUE="">
    	<PARAM NAME="PatternDefinition" VALUE="">
    	<PARAM NAME="UserName" VALUE="">
    	<PARAM NAME="UserSoftwareName" VALUE="">
    	<PARAM NAME="UserPhone" VALUE="">
    	<PARAM NAME="UserData" VALUE="">
    	<PARAM NAME="EnableIntelliSelect" VALUE="1">
    	<PARAM NAME="CommandMessgaeModify" VALUE="1">
    	<PARAM NAME="ToolBarFiles" VALUE="">
    	<PARAM NAME="DimStyle" VALUE="">
    	<PARAM NAME="ShowRulerWindow" VALUE="0">
    	<PARAM NAME="CursorWidth" VALUE="6">
    	<PARAM NAME="CursorLong" VALUE="30">
    	<PARAM NAME="EnableClipboard" VALUE="0">
    	<PARAM NAME="EnableDeleteKey" VALUE="0">
    	<PARAM NAME="AutoZoomAll" VALUE="1">
    	<PARAM NAME="UseArrowCursor" VALUE="0">
    	<PARAM NAME="EnableMouseMoveView" VALUE="1">
    	<PARAM NAME="AutoActive" VALUE="1">
    	<PARAM NAME="ResPath" VALUE="">
    	<PARAM NAME="Theme" VALUE="0">
    	<PARAM NAME="EnableOleShow" VALUE="0">
    	<PARAM NAME="DynToolTipTime" VALUE="1000">
    	<PARAM NAME="SingleSelection" VALUE="0">
    	<PARAM NAME="Watermark" VALUE="">
    	<PARAM NAME="EnableDrawOrder" VALUE="0">
    	<PARAM NAME="EditGripPoint" VALUE="0">
    	<PARAM NAME="InitUrl" VALUE="">
    	<PARAM NAME="DynToolTip" VALUE="0"> 
    	<param name="_StockProps" value="0">
    	<param name="IsRuningAtIE" value="1">
    	<param name="EnablePrintCmd" value="1">  
    	<param name="ShowCommandWindow" value="0">   
    	<param name="ShowToolBars" value="1">  
    	<param name="ShowModelBar" value="0">
    	<param name="Iniset" value="">
    	<param name="ToolBarFiles" value="">
    	<param name="ShowMenuBar" value="0">
    	<param name="EnableUndo" value="1">
    	<param name="ShowPropertyWindow" value="0">
    	<span style="color: red;">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。请点击<a href="http://www.mxdraw.com/MxDrawX52.msi">安装控件</a></span></object>
		<button type="button" onclick="catSensor()">查看传感器位置</button>
		
    </div>
    
	<script>
	$.ajaxSetup({cache:false});
	$.ajaxSetup({async:false});
	var mxOcx = document.getElementById("MxDrawXCtrl");
	var currentPath="Z:/研究生阶段/矿山/更新程序/KS_1.0.8/WebRoot/image/";
	var res;
	
	function catSensor(){
		InsertSensors();
	}
	
	function DoCmd(iCmd) {
		mxOcx.DoCommand(iCmd);
	}
	function DoCommandEventFunc(iCmd) {
		if (iCmd == 0) {
			InsertImage();
		}
		if (iCmd == 1) {
			DeleteImage();
		}
	}
	function InitMxDrawX() {
		if (mxOcx) {
			if (!mxOcx.IsIniting()) {
				clearInterval(mxtime);
				// 控件初始化完成，需要在启动做的事，在这里做

				//mxOcx.setCurrentLayout("布局名")

			}
		}
	}

	document.getElementById("MxDrawXCtrl").ImplementCommandEventFun = DoCommandEventFunc;
	document.getElementById("MxDrawXCtrl").ImplementMouseEventFun = MouseEvent;
	document.getElementById("MxDrawXCtrl").ImpInputPointToolTipFun = DoInputPointToolTipFun;
	//插入振动点图片
	function InsertImage() {
		 DeleteImage();
		 var sImageFileR = currentPath+"pic31.png";
		 var sImageFileR1 = currentPath+"pic32.png";
		 var sImageFileB = currentPath+"pic311.png";
		 var sImageFileB1 = currentPath+"pic322.png";
		 var sImageFileG = currentPath+"pic3111.png";
		 var sImageFileG1 = currentPath+"pic3222.png";
		 
		 var t1=document.getElementById("sdate").value;
         var t2=document.getElementById("edate").value;
         var quacklevel=document.getElementById("n1").value;
         var nengliang=document.getElementById("n2").value;
         if(t1=="" || t2==""){
         	alert("搜索框不能为空");
           	return false;
         }
         var x=new Array();
         var y=new Array();
         var nearGrade=new Array();
         var levelEnergy=new Array();
         var flag=new Array();
         $.get("Serv1",{SD:t1,CD:t2,level:quacklevel,energy:nengliang},function(data,status){
        	if(data==""){
        		alert("该时间段没有记录");
        		return false;
        	}
        	
        	var data1=data.split("\n");
        	for(var i=0;i<data1.length-1;i++){
		 		var data2=data1[i].split(" ");
		 		x.push(data2[1]);
	      		y.push(data2[2]);
	      		nearGrade.push(data2[3]);
	      		levelEnergy.push(data2[4]);
	      		flag.push(data2[0].toString());
	      		//alert(flag[i]+" "+x[i]+" "+y[i]);
 			}
         });                      

         for(var i=0;i<x.length;i++){
        	var m=0.15*nearGrade[i]+0.2;
        	alert(x.length+" "+y.length+" "+flag.length);
        	//alert(flag[i].toString()=="five");
	 		if(flag[i].toString()=="three"){
		 		var o= mxOcx.DrawImageMark(x[i],y[i],m, 0.0, sImageFileR, sImageFileR+","+sImageFileR1, false);
		 	    mxOcx.TwinkeEnt(o);//开启图片闪烁功能
		 	    var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
		 	    res = mxOcx.NewResbuf();//新建图层
		 	    //writeToEnt(ent,x[i],y[i],nearGrade,levelEnergy,i,flag[i]);//扩展数据
		 	    res.AddLong(2147403647);//设置图层高度
		 	    ent.SetProp("drawOrder",res);//将图片插入到新图层
	 		}
	 		if(flag[i].toString()=="PSO"){
	 			var o= mxOcx.DrawImageMark(x[i],y[i],m, 0.0, sImageFileB, sImageFileB+","+sImageFileB1, false);
	 			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
		 	    var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
		 	    res = mxOcx.NewResbuf();//新建图层
		 	    //writeToEnt(ent,x[i],y[i],nearGrade,levelEnergy,i,flag[i]);//扩展数据
		 	    res.AddLong(2147403647);//设置图层高度
		 	    ent.SetProp("drawOrder",res);//将图片插入到新图层
	 		}
	 		if(flag[i].toString()=="five") {
	 			var o= mxOcx.DrawImageMark(x[i],y[i],m, 0.0, sImageFileG, sImageFileG+","+sImageFileG1, false);
	 			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
		 	    var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
		 	    res = mxOcx.NewResbuf();//新建图层
		 	    //writeToEnt(ent,x[i],y[i],nearGrade,levelEnergy,i,flag[i]);//扩展数据
		 	    res.AddLong(2147403647);//设置图层高度
		 	    ent.SetProp("drawOrder",res);//将图片插入到新图层
	 		}
	 		
 	 	 }
	}
	//删除全部图片
	function DeleteImage(){
		var ssGet = mxOcx.NewSelectionSet();
        var filter = mxOcx.NewResbuf();
        filter.AddStringEx("MxImageMark", 5020);
        ssGet.AllSelect(filter);

        for (var i = 0; i < ssGet.Count; i++)
        {
            ssGet.Item(i).Erase();
        }
	}
	
	//插入传感器
	function InsertSensors(){
		//传感器全局坐标
		var sensorX=new Array();
		var sensorY=new Array();
		var sensorZ=new Array();
		
		sensorX[0]=41519304.125;sensorY[0]=4595913.485;
		sensorX[1]=41519926.476;sensorY[1]=4597275.978;
		sensorX[2]=41520815.875;sensorY[2]=4597384.576;
		sensorX[3]=41520207.356;sensorY[3]=4597983.404;
		sensorX[4]=41516849.629;sensorY[4]=4598099.366;
		sensorX[5]=41516836.655;sensorY[5]=4596627.472;
		sensorX[6]=41516707.440;sensorY[6]=4593163.619;
		sensorX[7]=41518060.298;sensorY[7]=4594304.927;
		sensorX[8]=41518099.807;sensorY[8]=4595388.504;
		var sImageFile = currentPath+"sensor.png";
		var sImageFile2 = currentPath+"sensor1.png";
		
		for(var i=0;i<sensorX.length;i++){
			o = mxOcx.DrawImageMark(sensorX[i],sensorY[i],0.1, 0.0, sImageFile,sImageFile+","+sImageFile2, false);
			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
            var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
            res = mxOcx.NewResbuf();//新建图层
            res.AddLong(2147403647);//设置图层高度
            ent.SetProp("drawOrder",res);//将图片插入到新图层
            writeToEntS(ent,sensorX[i],sensorY[i],sensorZ[i],i);
        }
	}
	//鼠标响应事件
    function MouseEvent(dX,dY,lType,sensorX,sensorY,sensorZ)
	{
		if(lType == 3)
		{
			ModelSpaceEntity(sensorX,sensorY,sensorZ);
		}
	    //事件类型,1鼠标移动，2是鼠标左键按下，3是鼠标右键按下，
	    // 4是鼠标左键双击 5是鼠标左键释放 6是鼠标右键释放
	    // 7是鼠标中键按下 8是鼠标中键释放 9是鼠标中键双击 10是鼠标中键滚动
	    if(lType == 2)
	    {
	        var ss = mxOcx.Call("Mx_NewSelectionSet","");
	        var pt = mxOcx.Call("Mx_NewPoint","");
	        var fil = mxOcx.Call("Mx_NewResbuf","");
	        pt.x = dX;
	        pt.y = dY;
	        ss.SelectAtPoint(pt,fil);
	        if(ss.Count > 0)
	        {
	        	var ent = ss.Item(0);
	        	if(ent.ObjectName=="McDbMxImageMark"){
	        		if(ent == null){
	        	        return;
	        	    }
	        	    var ex_x = ent.GetXData("x");
	        	    var ex_y = ent.GetXData("y");
	        	    
	        	   	
	        	    openwin();
	        	    
	        	   // alert(ent.ObjectName);
	        	    
	        	    
	        	    
	        	}
	        	
	        }
	        // 取消后面的命令。
	        mxOcx.SendStringToExecute("");
	        return 1;
	    }
		return 0;
	}
    function openwin(){
    	window.open ("dateshow.jsp", "newwindow", "height=800, width=1000, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no")
    }
	//写入振动点扩展数据
	function writeToEnt(ent,x,y,nearGrade,nengliang,i,flag) {
		if(ent == null){
	        return;
	    }
	    var exData = mxOcx.Call("Mx_NewResbuf","");
	    exData.AddStringEx("x",1001);
	    exData.AddStringEx(x,1000);
	    exData.AddStringEx("y",1001);
	    exData.AddStringEx(y,1000);
	    exData.AddStringEx("NearGrade",1001);
	    exData.AddStringEx(NearGrade,1000);
	    exData.AddStringEx("levelEnergy",1001);
	    exData.AddStringEx(nengliang,1000);
	    
	    exData.AddStringEx("series",1001);
	    exData.AddStringEx(i,1000);
	    
	    exData.AddStringEx("PosAlgorithm",1001);
	    exData.AddStringEx(flag,1000);
	    ent.SetXData(exData);
	}
	//写入传感器点振动数据
	function writeToEntS(ent,x,y,z,i) {
		if(ent == null){
	        return;
	    }
		var exData = mxOcx.Call("Mx_NewResbuf","");
	    exData.AddStringEx("x",1001);
		exData.AddStringEx(x,1000);
		exData.AddStringEx("y",1001);
	    exData.AddStringEx(y,1000);
	    exData.AddStringEx("z",1001);
	    exData.AddStringEx(z,1000);
	    exData.AddStringEx("i",1001);
	    exData.AddStringEx(i,1000);
	    ent.SetXData(exData);
	}
	
	function DoInputPointToolTipFun(ent) {
		alert("1");
	    var sHyperlinks = ent.ObjectName;
		if(sHyperlinks.length != 0)
		{
			var sClassName = ent.ObjectName;
	  
	        var tip = "<b><ct=0x0000FF><al_c>"+sClassName+
	           "</b><br><ct=0x00AA00><hr=100%></ct><br><a=\"link\">" + ent.type + "</a>";
	  
	        mxOcx.SetEventRetString(tip);
	    }
	    ent = null;
	    CollectGarbage();
	}
	</script>
	<script>
	$(function(){
        $("#nums").click(function(){
            $("#n1").attr("disabled",false);
            $("#edate").attr("disabled",true);
            $("#sdate").attr("disabled",true);
        })
        $("#dates").click(function(){
            $("#n1").attr("disabled",true);
            $("#edate").attr("disabled",false);
            $("#sdate").attr("disabled",false);
        })
    })
</script>
  </body>
</html>
