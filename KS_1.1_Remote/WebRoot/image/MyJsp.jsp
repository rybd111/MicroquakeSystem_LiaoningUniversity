<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    
     <script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
     <script type="text/javascript" src="../js/echarts.common.min.js"></script></head>
    <style>
        *{
            margin: 0px;
            padding: 0px;
        }
        .head{
            width: 100%;
            height: 20%;
            background-image: url("../image/u2.png");
            background-size: 100% 100%;
        }
        .head_name{
            display: block;
            text-align: center;
            padding-top:2.5%;
            font-family: 华文行楷;
            font-weight: bold;
            font-size: 48px;
        }
        .left{
            width: 30%;
            height: 600px;
            float: left;
            margin-top: 60px;
        }
        .table{
            width: 80%;
            height:400px;
            margin: 50px auto;
            padding-top: 40px;
            background-image:url("../image/table.png");
            background-size: 100% 100%;
        }
        .cad{
            width: 70%;
            height: 600px;
            float: left;
            margin-top: 20px;
        }
        .table_word{
            font-family: 微软雅黑;
            font-size: 32px;
            color: white;
            margin-left: 40px;
        }
        #dw{
            width: 80%;
            margin-top: 15px;
            margin-left:10%;
            background-color:rgba(255, 255, 255, 1);
            box-sizing:border-box;
            border-width:1px;
            border-style:solid;
            border-color:rgba(121, 121, 121, 1);
            border-radius:44px;
            -moz-box-shadow:none;
            -webkit-box-shadow:none;
            box-shadow:none;
            font-family:'微软雅黑 Bold', '微软雅黑';
            font-weight:700;
            font-style:normal;
            font-size:36px;
        }
        #MxDrawXCtrl{
            width: 90%;
            margin: 0 5%;
        }
        .wave{
            width: 100%;
            height: 400px;
            margin-bottom: 30px;
        }
        #first_wave{
            margin-top: 620px;
        }
        .wave_in{
            width: 30%;
            height: 300px;
            background-color: gainsboro;
            float: left;
            margin-left: 2.5%;
        }
        .wave img{
            width: 95%;
            height: 70px;
            margin-left: 2%
        }
    </style>
    <script type="text/javascript">
		$.get("../servlet/ZJDispl",function(data,status){
		var ele=window.document .getElementById ("x"); 
		var ele2=window.document .getElementById ("y");
        ele.innerHTML = data[0];
        ele2.innerHTML = "ss";	
		/* alert(data);
		var data =  point.split(" ");
		var textId=$("#ss");
		//textId.val("sssttttt");
  		$("#x").text("sdsdsd");
  		$('#y').html(data[0]);
  		$('#hb').html(data[0]);
  		$('#zj').html(data[0]);
  		$('#ss').val("ccc");
		// 基于准备好的dom，初始化echarts实例
    	document.getElementById('hb').innerText="span的文本"; */
        });

    $("dw").click(function(){
    $("#ss").val("Hello World");
   });
   
   
   function tt(){
   alert("sss");
    $("#ss").val("Hello World2");
  document.getElementById('hb').innerText="span的文本";
   $('#hb').html("ssdsd");
   }
    
    </script>
</head>
<body>
    <div class="head">
        <span class="head_name">××矿山地震定位系统</span>
    </div>
    <div class="left">
        <div class="table">
            <span class="table_word">震中坐标</span>
            <br>
            <span class="table_word">X:</span>
            <span class="table_word" id="x"></span>
            <input id="ss">
            <br>
            <span class="table_word">Y:</span>
            <span class="table_word" id="y"></span>
            <br>
            <br>
            <span class="table_word">海拔:</span>
            <span class="table_word" id="hb"></span>
            <br>
            <br>
            <span class="table_word">震级:</span>
            <span class="table_word" id="zj">${requestScope.zj}</span>
            <br>
            <br>
            <input type="button" value="定    位" onclick="DoCmd(0)" id="dw">
        </div>
    </div>
    <div class="cad">
        <object classid="clsid:74A777F8-7A8F-4e7c-AF47-7074828086E2" id="MxDrawXCtrl">
            <param name="_Version" value="65536">
            <param name="_ExtentX" value="24262">
            <param name="_ExtentY" value="16219">
            <param name="_StockProps" value="0">
            <param name="DwgFilePath" value="B:/Users/lemo/Workspaces/MyEclipse 10/kkss/WebRoot/image/cad(1).dwg" >
            <param name="IsRuningAtIE" value="1">
            <param name="EnablePrintCmd" value="1">
            <param name="EnableIntelliSelect"  value="0">
            <param name="ShowCommandWindow" value="0">
            <param name="ShowModelBar" value="1">
            <param name="FirstRunPan" value="1">
            <param name="Iniset" value="AutoActive=N,EnableGripPoint=N,UseArrowCursor=Y,AutoZoomAll=Y,IintAnimation=Y">
            <param name="ToolBarFiles" value="">
            <param name="IsBrowner" value="1">
            <param name="ShowMenuBar" value="0">
            <param name="EnableUndo" value="0">
        </object>
    </div>
    <%-- <div class="wave" id="first_wave">
        <img src="../image/head1.png">
        <div class="wave_in">
        <%@include file="sensor01.jsp"%>
        </div>
        <div class="wave_in"> <%@include file="sensor02.jsp"%></div>
        <div class="wave_in"> <%@include file="sensor03.jsp"%></div>
    </div>
    <div class="wave">
        <img src="../image/head2.png">
        <div class="wave_in"> <%@include file="sensor11.jsp"%></div>
        <div class="wave_in"> <%@include file="sensor12.jsp"%></div>
        <div class="wave_in"> <%@include file="sensor13.jsp"%></div>
    </div>
    <div class="wave">
        <img src="../image/head3.png">
        <div class="wave_in"><%@include file="sensor21.jsp"%></div>
        <div class="wave_in"><%@include file="sensor22.jsp"%></div>
        <div class="wave_in"><%@include file="sensor23.jsp"%></div>
    </div>
    <div class="wave">
        <img src="../image/head4.png">
        <div class="wave_in"><%@include file="sensor31.jsp"%></div>
        <div class="wave_in"><%@include file="sensor32.jsp"%></div>
        <div class="wave_in"><%@include file="sensor33.jsp"%></div>
    </div>
    <div class="wave">
        <img src="../image/head5.png">
        <div class="wave_in"><%@include file="sensor41.jsp"%></div>
        <div class="wave_in"><%@include file="sensor42.jsp"%></div>
        <div class="wave_in"><%@include file="sensor43.jsp"%></div>
    </div> --%>
    <script>
        var mxOcx = document.getElementById("MxDrawXCtrl");
        function DoCmd(iCmd) {
            mxOcx.DoCommand(iCmd);
        }
        function DoCommandEventFunc(iCmd)
        {
            if (iCmd == 0) {
                InsertImage();
            }
        }
        function InitMxDrawX() {
            if (mxOcx) {
                if (!mxOcx.IsIniting())
                {
                    clearInterval(mxtime);
                    // 控件初始化完成，需要在启动做的事，在这里做

                    //mxOcx.setCurrentLayout("布局名")

                }
            }
        }



        mxtime = setInterval(InitMxDrawX, 100);

        document.getElementById("MxDrawXCtrl").ImplementCommandEventFun = DoCommandEventFunc;
        document.getElementById("MxDrawXCtrl").ImpDynWorldDrawFun = DoDynWorldDrawFun;
        document.getElementById("MxDrawXCtrl").ImpExplodeFun = ExplodeFun;
        document.getElementById("MxDrawXCtrl").ImpGetGripPointsFun = GetGripPointsFun;
        document.getElementById("MxDrawXCtrl").ImpMoveGripPointsAtFun = MoveGripPointsFun;
        document.getElementById("MxDrawXCtrl").ImpTransformByFun = TransformByFun;
        document.getElementById("MxDrawXCtrl").ImpGetGeomExtentsFun = GetGeomExtentsFun;
        document.getElementById("MxDrawXCtrl").ImpInputPointToolTipFun = DoInputPointToolTipFun;
        document.getElementById("MxDrawXCtrl").ImpHyperlinkClickFun = DoHyperlinkClickFun;
        document.getElementById("MxDrawXCtrl").ImplementMouseEventFun = MouseEvent;
        document.getElementById("MxDrawXCtrl").ImpGetOsnapPointsFun = GetOsnapPointsFun;
        document.getElementById("MxDrawXCtrl").ImplementCustomEvent = DoCustomEventEventFun;


        function InsertImage() {
        var xstr=document.getElementById('x').innerText;
		var ystr=document.getElementById('y').innerText;
		var hbstr=document.getElementById('hb').innerText;
		var zjstr=document.getElementById('zj').innerText;
		var x=parseFloat(xstr);
		var y=parseFloat(ystr);
		var hb=parseFloat(hbstr);
		var zj=parseFloat(zjstr);
		alert(x);
            var sImageFile = "B:/Users/lemo/Workspaces/MyEclipse 10/kkss/WebRoot/image/1234.png";
            var ent = mxOcx.ObjectIdToObject(mxOcx.DrawImageMark(x, y, 0.5, 0.0, sImageFile, "", true));

            var res = mxOcx.NewResbuf();
            res.AddLong(2147403647);

            ent.SetProp("drawOrder",res);

        }
    </script>
</body>
</html>