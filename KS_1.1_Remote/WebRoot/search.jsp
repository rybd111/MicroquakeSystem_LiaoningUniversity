<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>特征查询</title>
    
    <link rel="stylesheet" type="text/css" href="css/head.css">
	<link rel="stylesheet" type="text/css" href="css/search.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/echarts.common.min.js"></script>

  </head>
  
  <body>
    <div class="head">
        <a href="index.jsp"><img src="image/152569275640874.png"></a>
        <a href="jsp/MyJsp3.jsp">实时数据</a>
        <a href="read2.jsp">微震统计</a>
        <a href="search.jsp">特征查询</a>
        <a href="jsp/MyJsp4.jsp">煤矿微震波形</a>
    </div>
    <div class="body_left">
        <form class="form-horizontal">
            <div class="form-group">
               <label class="radio-inline" title="极值查询分为极大值查询和极小值查询两种，适用于分析在监测周期内，矿山地质运动的峰值信息，为后续分析矿山环境在哪个时刻最稳定或最不稳定提供支持。">
                    <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked="checked"> 最值查询
                </label>
                <label class="radio-inline" title="Top-K查询为极值查询的扩展查询，适用于分析在监测周期内，矿山地质运动的危险周期或安全周期的信息，为后续分析矿山最稳定或最不稳定的环境在哪些时间段内等问题提供支持。">
                    <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> top-k查询
                </label>
                <label class="radio-inline" title="利用范围查询，可在海量矿山数据中快速查找任意范围的数据集，适用于对矿山大数据的海量筛选处理，如搜索特定时间段内的矿山地址运动信息、某地区指定范围内岩层及应力信息等.">
                    <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> 范围查询
                </label>
            </div>

			<br> <br>
			 
            <div class="form-group" id="f1">
                <label class="col-sm-2 control-label">地点</label>
                <div class="col-sm-8">
                    <select class="form-control" name="place">
                        <option value="1">西风井</option>
                        <option value="2">火药库</option>
                        <option value="3">永华村</option>
                        <option value="4">水塔</option>
                        <option value="5">工业广场</option>
                    </select>
                </div>
            </div>

            <div class="form-group" id="f2">
                <label class="col-sm-2 control-label">范围</label>
                <div class="col-sm-8">
                    <select class="form-control" name="time" id="time">
                        <option value="10">前10秒</option>
                        <option value="20">前20秒</option>
                        <option value="30">前30秒</option>
                        <option value="40">前40秒</option>
                        <option value="50">前50秒</option>
                        <option value="60">前60秒</option>
                    </select>
                </div>
            </div>

            <div class="form-group" id="f3">
                <label class="col-sm-2 control-label">k值</label>
                <div class="col-sm-8">
                    <select class="form-control" name="k" id="k">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </div>
            </div>

            <div class="form-group" id="f4">
                <label for="point" class="col-sm-2 control-label">阈值</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="point" placeholder="条数" name="point">
                </div>
            </div>

            <div class="form-group" id="f5">
                <label for="range" class="col-sm-2 control-label">范围</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="range" placeholder="范围" name="range">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-2">
                    <input type="button" class="btn btn-default" id="submit" value="查询"/>
                </div>
            </div>

        </form>
    </div>
    <div class="body_right">
        <div class="echarts" id="main1"></div>
        <div class="echarts" id="main2"></div>
        <div class="echarts" id="main3"></div>
    </div>
    <div class="clear"></div>
    <div class="foot">
        <span>
            辽宁大学矿山微震大数据平台<br>
            Powered by辽宁大学信息学院  &nbsp;<br>
            地址:沈阳市皇姑区崇山中路66号 电话:024-62202344 邮编:110036
        </span>
    </div>
    
    <script>
        $(function () {
            $("#inlineRadio1").click(function () {
                $("#f2").css("display","block");
                $("#f3").css("display","none");
                $("#f4").css("display","none");
                $("#f5").css("display","none");
            });
            $("#inlineRadio2").click(function () {
                $("#f2").css("display","block");
                $("#f3").css("display","block");
                $("#f4").css("display","none");
                $("#f5").css("display","none");
            })
            $("#inlineRadio3").click(function () {
                $("#f2").css("display","none");
                $("#f3").css("display","none");
                $("#f4").css("display","block");
                $("#f5").css("display","block");
            });
            $("#submit").click(function () {
            	/* var x = document.getElementById('main1');
 */            	
            	if($("#inlineRadio1").is(':checked')){
            		var myChart1 = echarts.init(document.getElementById('main1'));
            		 var myChart2 = echarts.init(document.getElementById('main2'));
            		 var myChart3 = echarts.init(document.getElementById('main3'));
            		 var option = {
                    
                    tooltip: {}, 
                    grid:{
                    	x:80
                    },
                    xAxis: {
                        data: [],
                        axisLabel:{
                            interval:0,
                            rotate:20
                        }
                    },
                    yAxis: {},
                    series: [{
                        type: 'bar',
                        barWidth : 15,
                        barGap:'5%',
                        barCategoryGap:'40%',
                        data: []
                    }]
                };
            	// 使用刚指定的配置项和数据显示图表。
                myChart1.setOption(option);
                myChart2.setOption(option);
                myChart3.setOption(option);
                var time=$("#time").val();
                $.get("search/mSearch",{time:time},function(data,status){
                	myChart1.setOption({
						title: {
                        	text: '         第一数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.xdata
                           }]
                       });
                       myChart2.setOption({
                       title: {
                        	text: '         第二数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.ydata
                           }]
                       });
                       myChart3.setOption({
                       	title: {
                        	text: '         第三数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.zdata
                           }]
                       });
                })
            	}
            	if($("#inlineRadio2").is(':checked')){
            		 var myChart1 = echarts.init(document.getElementById('main1'));
            		 var myChart2 = echarts.init(document.getElementById('main2'));
            		 var myChart3 = echarts.init(document.getElementById('main3'));
            		 var option = {
                    
                    tooltip: {},
                    xAxis: {
                        data: [],
                        axisLabel:{
                            interval:0,
                            rotate:60
                        }
                    },
                    yAxis: {},
                    series: [{
                        type: 'line',
                        data: []
                    }]
                };
            	// 使用刚指定的配置项和数据显示图表。
                myChart1.setOption(option);
                myChart2.setOption(option);
                myChart3.setOption(option);
                var time=$("#time").val();
                var k=$("#k").val();
                $.get("search/topK",{time:time,k:k},function(data,status){
                	myChart1.setOption({
						title: {
                        	text: '第一数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.xdata
                           }]
                       });
                       myChart2.setOption({
                       title: {
                        	text: '第二数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.ydata
                           }]
                       });
                       myChart3.setOption({
                       	title: {
                        	text: '第三数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.zdata
                           }]
                       });
                })
            	}
            	if($("#inlineRadio3").is(':checked')){
            		 var myChart1 = echarts.init(document.getElementById('main1'));
            		 var myChart2 = echarts.init(document.getElementById('main2'));
            		 var myChart3 = echarts.init(document.getElementById('main3'));
            		 
            		 var option = {
                    
                    tooltip: {},
                    xAxis: {
                        data: [],
                        axisLabel:{
                            interval:0,
                            rotate:60
                        }
                    },
                    yAxis: {},
                    series: [{
                        type: 'line',
                        data: []
                    }]
                };
            	// 使用刚指定的配置项和数据显示图表。
                myChart1.setOption(option);
                myChart2.setOption(option);
                myChart3.setOption(option);	 
            	var point=$("#point").val();
                var range=$("#range").val();
                if(point==0){
                    alert("阈值不能为空");
                    return false;
                }
                if(range==0){
                    alert("范围不能为空");
                    return false;
                }
                if(point-range<0){
                    alert("范围过大");
                    return false;
                }	
            	$.get("search/rangeSearch",{point:point,range:range},function(data,status){
						myChart1.setOption({
						title: {
                        	text: '第一数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.xdata
                           }]
                       });
                       myChart2.setOption({
                       title: {
                        	text: '第二数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.ydata
                           }]
                       });
                       myChart3.setOption({
                       	title: {
                        	text: '第三数据通道'
                    	},
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '',
                               data: data.zdata
                           }]
                       });
					})	
            		 
            	}
            })
            
            
           /*  $("#submit").click(function () {
               if($("#inlineRadio3").is(':checked')){
               
               	   var myChart = echarts.init(document.getElementById('main1'));
               		
               		var option = {
                    title: {
                        text: '第一数据通道'
                    },
                    tooltip: {},
                    xAxis: {
                        data: [],
                        axisLabel:{
                            interval:0,
                            rotate:60
                        }
                    },
                    yAxis: {},
                    series: [{
                        type: 'line',
                        data: []
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
               		
                   var point=$("#point").val();
                   var range=$("#range").val();
                   if(point==0){
                       alert("阈值不能为空");
                       return false;
                   }
                   if(range==0){
                       alert("范围不能为空");
                       return false;
                   }
                   if(point-range<0){
                       alert("范围过大");
                       return false;
                   }
					$.get("/search/rangeSearch",{point:point,range:range},function(data,status){
						myChart.setOption({
                           xAxis: {
                               data: []
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '最值',
                               data: data.xdata
                           }]
                       });
					}
				
                   /* fetchData(function (data) {
                       myChart.setOption({
                           xAxis: {
                               data: data.categories
                           },
                           series: [{
                               // 根据名字对应到相应的系列
                               name: '最值',
                               data: data.data
                           }]
                       }); 
                   });*/



              /*  }
            }) */
        })
  </script>
  </body>
</html>
