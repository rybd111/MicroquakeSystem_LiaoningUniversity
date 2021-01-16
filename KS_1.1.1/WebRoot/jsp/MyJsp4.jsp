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

<title>煤矿微震数据波形图</title>

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
window.setInterval("initoo()",100000); 
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
.wave {
	width: 100%;
	height: 400px;
	margin-bottom: 30px;
}
#first_wave {
	margin-top: 20px;
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
	margin-left: 2%;
	
}
.main{
	width:100%;
	height:100%;
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
		<span class="head_name">煤矿微震数据波形图</span>
	</div>
	<div class="wave" id="first_wave">
		<img src="../image/a.png">
		<div class="wave_in">
			<%-- <%@include file="auto.jsp"%> --%>
			<%-- <%@include file="index.jsp" %>  --%>
			<div id="main01" style="height:300px;"></div>
			<script type="text/javascript">
				function init01() {
				var myChart11 = echarts.init(document.getElementById('main01'));
				$.get("../servlet/Sevlet01", {
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
		</div>
		
		<div class="wave_in">
			<div id="main02" style="height:300px;"></div>
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
		<div id="main03" style="height:300px;"></div>
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
		<img src="../image/b.png">
		<div class="wave_in">
			<div id="main11" style="height:300px;"></div>
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
			<div id="main12" style="height:300px;"></div>
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
		<div class="wave_in"> <div id="main13" style="height:300px;"></div> </div>
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
		<img src="../image/c.png">
		<div class="wave_in">
			  <div id="main21" style="height:300px;"></div>
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
		<div class="wave_in"><div id="main22" style="height:300px;"></div></div>
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
		<div class="wave_in">  <div id="main23" style="height:300px;"></div></div>
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
		<img src="../image/d.png">
		<div class="wave_in"> <div id="main31" style="height:300px;"></div></div>
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
		<div class="wave_in"><div id="main32" style="height:300px;"></div></div>
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
		<div class="wave_in"><div id="main33" style="height:300px;"></div></div>
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
		<img src="../image/e.png">
		<div class="wave_in"> <div id="main41" style="height:300px;"></div></div>
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
		<div class="wave_in"><div id="main42" style="height:300px;"></div></div>
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
		<div class="wave_in"> <div id="main43" style="height:300px;"></div></div>
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
	<a href="../pic.jsp" id="a123">矿山网络构建示意图</a>
</body>
</html>
