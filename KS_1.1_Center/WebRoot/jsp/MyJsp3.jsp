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

 $(function(){
	    init01();
	    init02();
		init03();
		init11();
		init12();
		init13();
		init21();
		init22();
		init23();
		init31();
		init32();
		init33();
		init41();
		init42();
		init43();	
 });
 
function countDown(secs){     
	 if(--secs>0){    
			setTimeout("countDown("+secs+")",4000);     
		}else{ 
			//init01();
			init2();
			//init01();
		}   
}     

/* function countDown2(secs2){     
	if(--secs2>0){     
			setTimeout("countDown2("+secs2+")",70000);     
		}else{
			init01();
			init02();
			init03();
			init11();
			init12();
			init13();
			init21();
			init22();
			init23();
			init31();
			init32();
			init33();
			init41();
			init42();
			init43();			
			//init2();
		}   
}   */

function initoo(){
	init01();
	init02();
	init03();
	init11();
	init12();
	init13();
	init21();
	init22();
	init23();
	init31();
	init32();
	init33();
	init41();
	init42();
	init43();		
}

// 基于准备好的dom，初始化echarts实例
$.ajaxSetup({cache:false});
window.setInterval("countDown(10)",5000); 
window.setInterval("initoo()",100000); 
function init2(){
	$.get("../servlet/ZJDispl",function(data,status){

	/*  alert(data);  */
		
	var eleX=window.document .getElementById("x");
	var eleY=window.document.getElementById("y");
	var eleHB=window.document.getElementById("hb");
	var eleZJ=window.document.getElementById("zj");
	var data1 =  data.split(" ");
	/* eleX.innerHTML ="44444568";
	eleY.innerHTML ="5180102";
	eleHB.innerHTML ="548";
	eleZJ.innerHTML ="0.8"; */
	eleX.innerHTML =data1[0];
	eleY.innerHTML =data1[1];
	eleHB.innerHTML =data1[2];
	eleZJ.innerHTML =data1[4];
	/* if((data1[0]>44437500) && (data1[0]<44451000) && (data1[1]>5177500) && (data1[1]<5185500) && data1[4]>0){
		eleX.innerHTML =data1[0];
		eleY.innerHTML =data1[1];
		eleHB.innerHTML =data1[2];
		eleZJ.innerHTML =data1[4];
	}else if((data1[0]<44437500) || (data1[0]>44451000) || (data1[1]<5177500) || (data1[1]>5185500) && data1[4]<=0){
		eleX.innerHTML ="44444773";
		eleY.innerHTML ="5180194";
		eleHB.innerHTML ="512";
		eleZJ.innerHTML ="0.5";
	} */
	
	/* var ele=window.document .getElementById ("x"); 
	var ele2=window.document .getElementById ("y");
    ele.innerHTML = data[0];
    ele2.innerHTML = "ss";
    var data =  data.split(",");
    ele.innerHTML = data[0];
    alert(data[0]); */
		/* $('#x').innerhtml(data[0]);
		$('#y').i;
		$('#hb').html(data[2]);
		$('#zj').html(data[3]); */
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
	width: 800px;
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
a{
	float:right;
	display:block;
	margin-right:3%;
	margin-bottom:30px;
	font-family: 华文行楷;
	font-weight: bold;
	
}
#a123{
	display:block;
	padding-bottom:50px;
}
</style>
</head>
<body>
	<div class="head">
		<span class="head_name">煤矿冲击地压震级定位系统</span>
	</div>
	<div class="left">
		<div class="table">
			<span class="table_word">震中坐标</span> <br> <span
				class="table_word">X:</span> <span class="table_word" id="x"></span>
			<br> <span class="table_word">Y:</span> <span class="table_word"
				id="y"></span> <br> <span
				class="table_word">Z:</span> <span class="table_word" id="hb"></span>
			<br> <br> <span class="table_word">震级:</span> <span
				class="table_word" id="zj"></span> <br> <br>
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
				value="D:\环境+项目\kkss_7_9\WebRoot\image\cad.dwg">
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
	<div class="wave" id="first_wave">
		<img src="../image/head1.png">
		<div class="wave_in">
			<%-- <%@include file="auto.jsp"%> --%>
			<%-- <%@include file="index.jsp" %>  --%>
			<div id="main01" style="width: 400px;height:300px;"></div>
			<script type="text/javascript">
				function init01() {
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
						myChart01.clear();
						myChart01.setOption(option, true);

					});

				}
			</script>
		</div>
		<div class="wave_in">
			<div id="main02" style="width: 400px;height:300px;"></div>
			<script type="text/javascript">
			function init02() {
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
						
						
						myChart02.clear();
						myChart02.setOption(option, true);

					});

				}
			</script>
		</div>
	
	<div class="wave_in">
		<div id="main03" style="width: 400px;height:300px;"></div>
		<script type="text/javascript">
		function init03() {
				var myChart03 = echarts.init(document.getElementById('main03'));
				$.get("../servlet/Sevlet01", {
					type : 3
				}, function(point, status) {

					var list = point;
					var data11 = point.split(",");

					var dataX03 = new Array();
					for (var i = 0; i < data11.length; i++) {
						//dataX03[i].push(data11[i]);
						dataX03[i] = data11[i];
					}

					//var base = +new Date(2034, 9, 3, 15, 12,10);

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
						data.push(dataX03[i++]);
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
						myChart03.setOption({
							xAxis : {
								data : date
							},
							series : [ {
								name : '成交',
								data : data
							} ]
						});
					}, 1000);
					myChart03.clear();
					myChart03.setOption(option, true);

				});
			}
		</script>
	</div>
	</div>
	<div class="wave">
		<img src="../image/head2.png">
		<div class="wave_in">
			<div id="main11" style="width: 400px;height:300px;"></div>
		</div>
		<script type="text/javascript">
		function init11() {
				var myChart11 = echarts.init(document.getElementById('main11'));
				$.get("../servlet/Sevlet02", {
					type : 1
				}, function(point, status) {

					var list = point;
					var data = point.split(",");

					var dataX11 = new Array();
					for (var i = 0; i < data.length; i++) {
						//dataX11[i].push(data[i]);
						dataX11[i] = data[i];
					}

					//var base = +new Date(2114, 9, 3, 15, ,10);

					var base = +new Date();
					var oneMin = 1000;
					var date = [];

					var data = [ Math.random() * 150 ];
					var now = new Date(base);

					//var drf=[10,10,20,52,59,,5,,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,,5,,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,,5,,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,,5,,5,2,56,215,2,8,2,55,5,5,5,9];
					var i = 0;

					function addData(shift) {
						now = [ now.getFullYear(), now.getMonth() + 1,
								now.getDate() ].join('/')
								+ "\n"
								+ [ now.getHours(), now.getMinutes(),
										now.getSeconds() ].join(':');
						date.push(now);
						/*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
						data.push(dataX11[i++]);
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
						myChart11.setOption({
							xAxis : {
								data : date
							},
							series : [ {
								name : '成交',
								data : data
							} ]
						});
					}, 1000);
					myChart11.setOption(option, true);

				});
			}
		</script>
		<div class="wave_in">
			<div id="main12" style="width: 400px;height:300px;"></div>
		</div>
		<script type="text/javascript">

		function init12() {
			var myChart12 = echarts.init(document.getElementById('main12'));
			$.get("../servlet/Sevlet02", {
				type : 2
			}, function(point, status) {

				var list = point;
				var data = point.split(",");

				var dataX12 = new Array();
				for (var i = 0; i < data.length; i++) {
					//dataX12[i].push(data[i]);
					dataX12[i] = data[i];
				}

				//var base = +new Date(2124, 9, 3, 15, 12,10);

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
					data.push(dataX12[i++]);
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
					myChart12.setOption({
						xAxis : {
							data : date
						},
						series : [ {
							name : '成交',
							data : data
						} ]
					});
				}, 1000);
				myChart12.setOption(option, true);
			});
		 }
		</script>
		<div class="wave_in"> <div id="main13" style="width: 400px;height:300px;"></div> </div>
	</div>
	<script type="text/javascript">
	function init13() {
		 var myChart13 = echarts.init(document.getElementById('main13'));
			$.get("../servlet/Sevlet02",{type:3},function(point,status){
	  
	      	var list=point;
	      	var data =  point.split(",");
	 
	      	var dataX13=new Array();  
	      	for(var i=0;i<data.length;i++){
	      		//dataX13[i].push(data[i]);
	      		dataX13[i]=data[i];
	      	} 
	      
	      
	        //var base = +new Date(2134, 9, 3, 15, 13,10);
	        
	        var base = +new Date();
	        var oneMin =  1000;
	        var date = [];
	          
	        var data = [Math.random() * 150];
	        var now = new Date(base);
	         
	        //var drf=[10,10,20,52,59,13,5,13,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,13,5,13,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,13,5,13,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,13,5,13,5,2,56,215,2,8,2,55,5,5,5,9];
	        var i=0;
	        
	        
	        function addData(shift) {
	            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
	            date.push(now);
	           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
	            data.push(dataX13[i++]);
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
	            xAxis: {
	                type: 'category',
	                boundaryGap: false,
	                data: date
	            },
	            yAxis: {
	                boundaryGap: [0, '50%'],
	                type: 'value'
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
	            
	            
	            series: [{
	                name: '模拟数据',
	                type: 'line',
	                showSymbol: true,
	                hoverAnimation: false,
	                data: data
	                
	            }]
	        };
	          
	        setInterval(function () {
	            addData(true);
	            myChart13.setOption({
	                xAxis: {
	                    data: date
	                },
	                series: [{
	                    name:'成交',
	                    data: data
	                }]
	            });
	        }, 1000);
	        myChart13.setOption(option,true);
	        
	           }); 			
	    };
	</script>

	<div class="wave">
		<img src="../image/head3.png">
		<div class="wave_in">
			  <div id="main21" style="width: 400px;height:300px;"></div>
		</div>
		<script type="text/javascript">

		function init21() {
			 var myChart21 = echarts.init(document.getElementById('main21'));
				$.get("../servlet/Sevlet03",{type:1},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(2214, 9, 3, 15, 21,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,21,5,21,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,21,5,21,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,21,5,21,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,21,5,21,5,2,56,215,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart21.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart21.setOption(option,true);
		        
		           });  			
	    };
		</script>
		<div class="wave_in"><div id="main22" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">

		function init22() {
			 var myChart22 = echarts.init(document.getElementById('main22'));
				$.get("../servlet/Sevlet03",{type:2},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(2224, 9, 3, 15, 22,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,22,5,22,5,2,56,225,2,8,2,55,5,5,5,910,20,52,59,22,5,22,5,2,56,225,2,8,2,55,5,5,5,90,20,52,59,22,5,22,5,2,56,225,2,8,2,55,5,5,5,910,20,52,59,22,5,22,5,2,56,225,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart22.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart22.setOption(option,true);
		        
		           });    			
	    };
		</script>
		<div class="wave_in">  <div id="main23" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init23() { 
			 var myChart23 = echarts.init(document.getElementById('main23'));
				$.get("../servlet/Sevlet03",{type:3},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(2324, 9, 3, 15, 23,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,23,5,23,5,2,56,235,2,8,2,55,5,5,5,910,20,52,59,23,5,23,5,2,56,235,2,8,2,55,5,5,5,90,20,52,59,23,5,23,5,2,56,235,2,8,2,55,5,5,5,910,20,52,59,23,5,23,5,2,56,235,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart23.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart23.setOption(option,true);
		        
		           });   			
	    };
		</script>
	</div>
	<div class="wave">
		<img src="../image/head4.png">
		<div class="wave_in"> <div id="main31" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init31() {
			 var myChart31 = echarts.init(document.getElementById('main31'));
				$.get("../servlet/Sevlet04",{type:1},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(3124, 9, 3, 15, 31,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,31,5,31,5,2,56,315,2,8,2,55,5,5,5,910,20,52,59,31,5,31,5,2,56,315,2,8,2,55,5,5,5,90,20,52,59,31,5,31,5,2,56,315,2,8,2,55,5,5,5,910,20,52,59,31,5,31,5,2,56,315,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart31.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart31.setOption(option,true);
		        
		           });			
		    };
		</script>
		<div class="wave_in"><div id="main32" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init32() {
			 var myChart32 = echarts.init(document.getElementById('main32'));
				$.get("../servlet/Sevlet04",{type:2},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(3224, 9, 3, 15, 32,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,32,5,32,5,2,56,325,2,8,2,55,5,5,5,910,20,52,59,32,5,32,5,2,56,325,2,8,2,55,5,5,5,90,20,52,59,32,5,32,5,2,56,325,2,8,2,55,5,5,5,910,20,52,59,32,5,32,5,2,56,325,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart32.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart32.setOption(option,true);
		        
		           });   			
		    };
		</script>
		<div class="wave_in"><div id="main33" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init33() {
			 var myChart33 = echarts.init(document.getElementById('main33'));
				$.get("../servlet/Sevlet04",{type:3},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(3324, 9, 3, 15, 33,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,33,5,33,5,2,56,335,2,8,2,55,5,5,5,910,20,52,59,33,5,33,5,2,56,335,2,8,2,55,5,5,5,90,20,52,59,33,5,33,5,2,56,335,2,8,2,55,5,5,5,910,20,52,59,33,5,33,5,2,56,335,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart33.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart33.setOption(option,true);
		        
		           }); 			
	    };
		</script>
	</div>
	<div class="wave">
		<img src="../image/head5.png">
		<div class="wave_in"> <div id="main41" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init41() {
			 var myChart41 = echarts.init(document.getElementById('main41'));
				$.get("../servlet/Sevlet05",{type:1},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(4124, 9, 3, 15, 41,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,41,5,41,5,2,56,415,2,8,2,55,5,5,5,910,20,52,59,41,5,41,5,2,56,415,2,8,2,55,5,5,5,90,20,52,59,41,5,41,5,2,56,415,2,8,2,55,5,5,5,910,20,52,59,41,5,41,5,2,56,415,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart41.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart41.setOption(option,true);
		        
		           });  			
		    };
		</script>
		<div class="wave_in"><div id="main42" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init42() {
			 var myChart42 = echarts.init(document.getElementById('main42'));
				$.get("../servlet/Sevlet05",{type:2},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(4224, 9, 3, 15, 42,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,42,5,42,5,2,56,425,2,8,2,55,5,5,5,910,20,52,59,42,5,42,5,2,56,425,2,8,2,55,5,5,5,90,20,52,59,42,5,42,5,2,56,425,2,8,2,55,5,5,5,910,20,52,59,42,5,42,5,2,56,425,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart42.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart42.setOption(option,true);
		        
		           });     			
		    };
		</script>
		<div class="wave_in"> <div id="main43" style="width: 400px;height:300px;"></div></div>
		<script type="text/javascript">
		function init43() { 
			 var myChart43 = echarts.init(document.getElementById('main43'));
				$.get("../servlet/Sevlet05",{type:3},function(point,status){
		  
		      	var list=point;
		      	var data =  point.split(",");
		 
		      	var dataX=new Array();  
		      	for(var i=0;i<data.length;i++){
		      		//dataX[i].push(data[i]);
		      		dataX[i]=data[i];
		      	} 
		      
		      
		        //var base = +new Date(4324, 9, 3, 15, 43,10);
		        
		        var base = +new Date();
		        var oneMin =  1000;
		        var date = [];
		          
		        var data = [Math.random() * 150];
		        var now = new Date(base);
		         
		        //var drf=[10,10,20,52,59,43,5,43,5,2,56,435,2,8,2,55,5,5,5,910,20,52,59,43,5,43,5,2,56,435,2,8,2,55,5,5,5,90,20,52,59,43,5,43,5,2,56,435,2,8,2,55,5,5,5,910,20,52,59,43,5,43,5,2,56,435,2,8,2,55,5,5,5,9];
		        var i=0;
		        
		        
		        function addData(shift) {
		            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		            date.push(now);
		           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
		            data.push(dataX[i++]);
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
		            xAxis: {
		                type: 'category',
		                boundaryGap: false,
		                data: date
		            },
		            yAxis: {
		                boundaryGap: [0, '50%'],
		                type: 'value'
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
		            
		            
		            series: [{
		                name: '模拟数据',
		                type: 'line',
		                showSymbol: true,
		                hoverAnimation: false,
		                data: data
		                
		            }]
		        };
		          
		        setInterval(function () {
		            addData(true);
		            myChart43.setOption({
		                xAxis: {
		                    data: date
		                },
		                series: [{
		                    name:'成交',
		                    data: data
		                }]
		            });
		        }, 1000);
		        myChart43.setOption(option,true);			
	    });
		 };
		</script>
	</div>
	<a href="../read2.jsp" target="_blank" id="a123">微震统计>></a>
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
			var sImageFile = "E:/image/123.png";
			o = mxOcx.DrawImageMark(x,y, 0.5, 0.0, sImageFile,"D:\环境+项目\kkss_7_9\WebRoot\image\123.png,D:\环境+项目\kkss_7_9\WebRoot\image\456.png", false);
			mxOcx.TwinkeEnt(o);//开启图片闪烁功能
            var ent = mxOcx.ObjectIdToObject(o);//将o转换为IMxDrawEntity类型对象，为了将图片插入到顶层
            var res = mxOcx.NewResbuf();//新建图层
            res.AddLong(2147403647);//设置图层高度
            ent.SetProp("drawOrder",res);//将图片插入到新图层
		}
	</script>
</body>
</html>
