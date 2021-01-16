<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%-- <base href="<%=basePath%>"> --%>

<title>My JSP 'MyJsp3.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="../js/echarts.common.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
</head>
<style>
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

.wave {
	width: 100%;
	height: 400px;
	margin-bottom: 30px;
}

#first_wave {
	margin-top: 620px;
}

.wave_in {
	width: 30%;
	height: 300px;
	background-color: gainsboro;
	float: left;
	margin-left: 2.5%;
}

.wave img {
	width: 95%;
	height: 70px;
	margin-left: 2%
}
</style>
</head>
<body>
	<div class="head">
		<span class="head_name">××矿山地震定位系统</span>
	</div>
	<div class="left">
		<div class="table">
			<span class="table_word">震中坐标</span> <br> <span
				class="table_word">X:</span> <span class="table_word" id="x">${requestScope.x}</span>
			<br> <span class="table_word">Y:</span> <span class="table_word"
				id="y">${requestScope.y}</span> <br> <br> <span
				class="table_word">海拔:</span> <span class="table_word" id="hb">${requestScope.hb}</span>
			<br> <br> <span class="table_word">震级:</span> <span
				class="table_word" id="zj">${requestScope.zj}</span> <br> <br>
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
				value="C:/Users/lemo/Workspaces/MyEclipse Professional 2014/kkss/WebRoot/image/cad(1).dwg">
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
	<div class="wave" id="first_wave">
		<img src="../image/head1.png">
		<div class="wave_in">
			<%-- <%@include file="auto.jsp"%> --%>
			<%-- <%@include file="index.jsp" %>  --%>	
			<div id="main01" style="width: 500px;height:300px;"></div>
			<script type="text/javascript">
					$(function(){
					var myChart01 = echarts.init(document
							.getElementById('main01'));
					$.get("../servlet/Sevlet01", {
						type : 1
					}, function(point, status) {
						/* alert(point); */
						var list = point;
						var data11 = point.split(",");

						var dataX02 = new Array();
						for (var i = 0; i < data11.length; i++) {
							//dataX02[i].push(data11[i]);
							dataX02[i] = data11[i];
						}

						//var base = +new Date(2024, 9, 3, 15, 12,10);

						var base = +new Date();
						var oneMin = 1000;
						var date = [];

						var data = [ Math.random() * 150 ];
						var now = new Date(base);

						//var drf=[10,10,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,9];
						var i = 0;

						function addData(shift) {
							now = [ now.getFullYear(), now.getMonth() + 1,
									now.getDate() ].join('/')
									+ "\n"
									+ [ now.getHours(), now.getMinutes(),
											now.getSeconds() ].join(':');
							date.push(now);
							/*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
							data.push(dataX02[i++]);
							if (shift) {
								date.shift();
								data.shift();
							}

							now = new Date(+new Date(now) + oneMin);

						}

						for (var i = 1; i < 300; i++) {
							addData();
						}

						option = {
							xAxis : {
								type : 'category',
								boundaryGap : false,
								data : date
							},
							yAxis : {
								boundaryGap : [ 0, '50%' ],
								type : 'value'
							},
							/*  series: [
							     {
							         name:'成交',
							         type:'line',
							         smooth:true,
							         symbol: 'none',
							         stack: 'a',
							         areaStyle: {
							             normal: {}
							         },
							         data: data
							     }
							 ] */

							series : [ {
								name : '模拟数据',
								type : 'line',
								showSymbol : true,
								hoverAnimation : false,
								data : data

							} ]
						};

						setInterval(function() {
							addData(true);
							myChart01.setOption({
								xAxis : {
									data : date
								},
								series : [ {
									name : '成交',
									data : data
								} ]
							});
						}, 1000);
						myChart01.setOption(option, true);

					});
				
					});
			</script>
		</div>
		<div class="wave_in">
			<div id="main02" style="width: 500px;height:300px;"></div>
			<script type="text/javascript">
					$(function(){
					var myChart02 = echarts.init(document
							.getElementById('main02'));
					$.get("../servlet/Sevlet01", {
						type : 2
					}, function(point, status) {
						/* alert(point); */
						var list = point;
						var data11 = point.split(",");

						var dataX02 = new Array();
						for (var i = 0; i < data11.length; i++) {
							//dataX02[i].push(data11[i]);
							dataX02[i] = data11[i];
						}

						//var base = +new Date(2024, 9, 3, 15, 12,10);

						var base = +new Date();
						var oneMin = 100;
						var date = [];

						var data = [ Math.random() * 150 ];
						var now = new Date(base);

						//var drf=[10,10,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,9];
						var i = 0;

						function addData(shift) {
							now = [ now.getFullYear(), now.getMonth() + 1,
									now.getDate() ].join('/')
									+ "\n"
									+ [ now.getHours(), now.getMinutes(),
											now.getSeconds() ].join(':');
							date.push(now);
							/*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
							data.push(dataX02[i++]);
							if (shift) {
								date.shift();
								data.shift();
							}

							now = new Date(+new Date(now) + oneMin);

						}

						for (var i = 1; i < 30; i++) {
							addData();
						}

						option = {
							xAxis : {
								type : 'category',
								boundaryGap : false,
								data : date
							},
							yAxis : {
								boundaryGap : [ 0, '50%' ],
								type : 'value'
							},
							/*  series: [
							     {
							         name:'成交',
							         type:'line',
							         smooth:true,
							         symbol: 'none',
							         stack: 'a',
							         areaStyle: {
							             normal: {}
							         },
							         data: data
							     }
							 ] */

							series : [ {
								name : '模拟数据',
								type : 'line',
								showSymbol : true,
								hoverAnimation : false,
								data : data

							} ]
						};

						setInterval(function() {
							addData(true);
							myChart02.setOption({
								xAxis : {
									data : date
								},
								series : [ {
									name : '成交',
									data : data
								} ]
							});
						}, 1000);
						myChart02.setOption(option, true);

					});
				
					});
			</script>
		</div>
		<div class="wave_in"><%-- <%@include file="auto2.jsp"%> --%>
		
		</div>
	</div>
	<div class="wave">
		<img src="../image/head2.png">
		<div class="wave_in">
			<div id="main11" style="width: 500px;height:300px;"></div>
		</div>
		<div class="wave_in"><%-- <%@include file="auto5.jsp"%> --%></div>
		<div class="wave_in"></div>
	</div>
	`

	<div class="wave">
		<img src="../image/head3.png">
		<div class="wave_in"><%-- <%@include file="sensor21.jsp"%> --%></div>
		<div class="wave_in"></div>
		<div class="wave_in"></div>
	</div>
	<div class="wave">
		<img src="../image/head4.png">
		<div class="wave_in"></div>
		<div class="wave_in"></div>
		<div class="wave_in"></div>
	</div>
	<div class="wave">
		<img src="../image/head5.png">
		<div class="wave_in"></div>
		<div class="wave_in"></div>
		<div class="wave_in"></div>
	</div>
	<script>
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
			var sImageFile = "C:/Users/lemo/Workspaces/MyEclipse Professional 2014/kkss/WebRoot/image/1234.png";
			o = mxOcx.DrawImageMark(x,y, 0.5, 0.0, sImageFile,"C:/Users/lemo/Workspaces/MyEclipse Professional 2014/kkss/WebRoot/image/1234.png,C:/Users/lemo/Workspaces/MyEclipse Professional 2014/kkss/WebRoot/image/456.png", false);
			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
            var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
            var res = mxOcx.NewResbuf();//新建图层
            res.AddLong(2147403647);//设置图层高度
            ent.SetProp("drawOrder",res);//将图片插入到新图层
		}
	</script>
</body>
</html>
