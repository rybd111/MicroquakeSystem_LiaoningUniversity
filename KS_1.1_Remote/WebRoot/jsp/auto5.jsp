<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 --%>
<!DOCTYPE html>

<html>

<head>
<%-- <base href="<%=basePath%>"> --%>
<meta charset="utf-8">
<!-- 引入 ECharts 文件 -->
<script type="text/javascript" src="../js/echarts.common.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.0.js"></script>
</head>

<body>
	 <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
   <!--  <div id="main1" style="width: 400px;height:300px;"></div> -->
	 <div id="main5" style="width: 400px;height:300px;"></div> 
 <script type="text/javascript">
       // 基于准备好的dom，初始化echarts实例
      /*   var ss="ssds"; */

        var myChart5 = echarts.init(document.getElementById('main5'));
      
      var drf=[10,30,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,910,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,90,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,910,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,9];
      
        var data = [];
        var now = +new Date(1997, 9, 3);
        var oneDay = 54 * 3600 * 1000;
        var value = Math.random() * 1000;
        for (var i = 0; i < 60; i++) {
            data.push(randomData());
        }

        var   i=0;
      
        function randomData() {
            now = new Date(+now + oneDay);
         
           
        	  
        	   value=drf[i++];
        	   
            
          //  value = value + Math.random() * 51 - 10;
            return {
                name: now.toString(),
                value: [
                    [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                    Math.round(value)
                ]
            }
        }

       
        option1 = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    params = params[0];
                    var date = new Date(params.name);
                    return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
                },
                axisPointer: {
                    animation: false
                }
            },
            
             xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            }, 
             yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                }
            }, 
            series: [{
                name: '模拟数据',
                type: 'line',
                showSymbol: true,
                hoverAnimation: false,
                data: data
                
            }]
        };

        setInterval(function () {

            for (var i = 0; i < 0.1; i=i+0.1) {
                data.shift();
                data.push(randomData());
            }
            myChart5.setOption({
                series: [{
                    data: data
                }]
            });
        }, 100);
        
        
        myChart5.setOption(option1,true);
        
        
       
</script>



<!-- <script type="text/javascript">
       // 基于准备好的dom，初始化echarts实例
      /*   var ss="ssds"; */

        var myChart5 = echarts.init(document.getElementById('main5'));
      
      var drf=[100,30,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,910,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,90,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,910,50,55,59,15,5,15,5,5,56,515,5,8,5,55,5,5,5,9];
      
        var data = [];
        var now = +new Date(1997, 9, 3);
        var oneDay = 54 * 3600 * 1000;
        var value = Math.random() * 1000;
        for (var i = 0; i < 60; i++) {
            data.push(randomData());
        }

        var   i=0;
      
        function randomData() {
            now = new Date(+now + oneDay);
         
           
        	  
        	   value=drf[i++];
        	   
            
          //  value = value + Math.random() * 51 - 10;
            return {
                name: now.toString(),
                value: [
                    [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                    Math.round(value)
                ]
            }
        }

       
        option5 = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    params = params[0];
                    var date = new Date(params.name);
                    return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
                },
                axisPointer: {
                    animation: false
                }
            },
            
             xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            }, 
             yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                }
            }, 
            series: [{
                name: '模拟数据',
                type: 'line',
                showSymbol: true,
                hoverAnimation: false,
                data: data
                
            }]
        };

        setInterval(function () {

            for (var i = 0; i < 0.1; i=i+0.1) {
                data.shift();
                data.push(randomData());
            }
            myChart.setOption({
                series: [{
                    data: data
                }]
            });
        }, 100);
        
        
        myChart5.setOption(option5,true); -->
        
        
       
</script>
</body>

</html>