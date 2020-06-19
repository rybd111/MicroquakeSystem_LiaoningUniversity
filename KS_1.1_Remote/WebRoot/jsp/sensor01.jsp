<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!-- 引入 ECharts 文件 -->
  <script src="../js/jquery-1.8.0.js"></script>
    <script src="../js/echarts.common.min.js"></script>
</head>

<body>

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main01" style="width: 500px;height:300px;"></div>
 	<div id="main02" style="width: 500px;height:300px;"></div>
 <script type="text/javascript">
       // 基于准备好的dom，初始化echarts实例
      /*   var ss="ssds"; */

		 $(function(){ 
			init01();     			
	    });

         function init01(){
		 var myChart01 = echarts.init01(document.getElementById('main01'));
		$.get("../servlet/Sevlet01",{type:1},function(point,status){
  		//alert(point);
      	var list=point;
      	var data11 =  point.split(",");
 
      	var dataX01=new Array();  
      	for(var i=0;i<data11.length;i++){
      		//dataX01[i].push(data11[i]);
      		dataX01[i]=data11[i];
      	} 
      
      
        //var base = +new Date(2014, 9, 3, 15, 12,10);
        
        var base = +new Date();
        var oneMin =  100;
        var date = [];
          
        var data = [Math.random() * 150];
        var now = new Date(base);
         
        //var drf=[10,10,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,90,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,910,20,52,59,12,5,12,5,2,56,215,2,8,2,55,5,5,5,9];
        var i=0;
        
        
        function addData(shift) {
            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + "\n" + [ now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
            date.push(now);
           /*  data.push((Math.random() - 0.4) * 10 + data[data.length - 1]); */
            data.push(dataX01[i++]);
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
            myChart01.setOption({
                xAxis: {
                    data: date
                },
                series: [{
                    name:'成交',
                    data: data
                }]
            });
        }, 1000);
        myChart01.setOption(option,true);
        
           });
    }
    
    function countDown(secs){     
			if(--secs>0){     
     			setTimeout("countDown("+secs+")",3000);     
     		}else{
     			
     			init01();
     		}   
		}     

		// 基于准备好的dom，初始化echarts实例
    
      window.setInterval("countDown(10)",3000); 
    
</script>

   
</body>

</html>