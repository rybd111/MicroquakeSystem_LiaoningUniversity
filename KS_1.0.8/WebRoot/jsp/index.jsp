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
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../js/jquery-1.10.2.js"></script></head>
</head>
<body>
    <div class="head">
        <span class="head_name">××矿山地震定位系统</span>
    </div>
<div class="body">
    <div class="body_left">
        <div class="body_left_table">
            <span class="table_word">震中坐标</span>
            <br>
            <span class="table_word">X:</span>
            <span class="table_word" id="x">${requestScope.x}</span>
            <span class="table_word">Y:</span>
            <span class="table_word" id="y">${requestScope.y}</span>
            <br>
            <br>
            <span class="table_word">海拔:</span>
            <span class="table_word" id="hb">${requestScope.hb}</span>
            <br>
            <br>
            <span class="table_word">震级:</span>
            <span class="table_word" id="zj">${requestScope.zj}</span>
            <br>
            <br>
            <input type="button" value="定    位" onclick="DoCmd(0)" id="dw">
        </div>
        <div class="body_left_cad">
            <object classid="clsid:74A777F8-7A8F-4e7c-AF47-7074828086E2" id="MxDrawXCtrl">
                <param name="_Version" value="65536">
                <param name="_ExtentX" value="24262">
                <param name="_ExtentY" value="16219">
                <param name="_StockProps" value="0">
                <param name="DwgFilePath" value="B:/Users/lemo/Workspaces/MyEclipse 10/kkss/WebRoot/image/cad.dwg" >
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
    </div>
    <div class="body_right"></div>
</div>
</body>
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
        /* var getPt = mxOcx.NewComObject("IMxDrawUiPrPoint");
        getPt.message = "点取图片的插入中点";
        if (getPt.go() != 1) {
            return;
        }

        var frstPt = getPt.value();
        if (frstPt == null) {

            return;
        } */

        var sImageFile = "B:/Users/lemo/Workspaces/MyEclipse 10/kkss/WebRoot/image/1234.png";
        var ent = mxOcx.ObjectIdToObject(mxOcx.DrawImageMark(-52745,5182985, 0.5, 0.0, sImageFile, "", true));

        var res = mxOcx.NewResbuf();
        res.AddLong(2147403647);

        ent.SetProp("drawOrder",res);

    }
</script>
</html>