#  矿山微震系统前端 #

## 开发环境与工具的使用 ##

开发工具：WebStorm                 起止时间：7月2日 ——7月12日

[Less自动转换css文件](https://mp.weixin.qq.com/s?__biz=Mzg3OTI1ODkzOQ==&tempkey=MTA2OF93MCtIM25JYkJJN3kyaWtjUG9iZ2libjBKVnN3b1R4ZE01VWtIcV9wSWJUdkhIMC1iSUxhM0JYVDZkR0pPNzQySkI5VVdGUlhHTFlXeERXaWpOLWV2emNtQ0JUTXE3R1hsYUJPWmNZX1BQZkZHSTgzTzVfRmFPRktVQUZIZFVOVkdJdFpMcDloM2t1REhDSHNzZG9CTjZnbE11RFZSbnlnSU5jd1B3fn4%3D&chksm=4f067f077871f61113d75155599526be56b5c1f31684f67c230fb9c5c54a374e22994ff6c6cb#rd)

[利用flexible.js页面实现自适应](https://mp.weixin.qq.com/s/H_CRJN5Q7Wd757rEqONQog)

[px2rwd插件的使用](https://blog.csdn.net/u010377383/article/details/101198104)

[WebStorm Git的使用](https://mp.weixin.qq.com/s/eh63n9Bw-UDh8elH7IzHDw)

[Echarts使用步骤与初始测试demo](https://mp.weixin.qq.com/s/RLL8VM4Hfk8EobUs_2bDeQ)

[Echarts基础配置及主要配置项的理解](https://mp.weixin.qq.com/s/R2jlhqnAK2T-6O7ioCYAAQ)

[Bootstrap栅格系统进行页面布局](https://v3.bootcss.com/css/#grid)

[梦想cad控件的使用](https://mp.weixin.qq.com/s/65yC0cQcWSDRLqvXXUR_XQ)





## 案例适配方案 ##
1.flexible.js 进行屏幕缩放的自适应
2.px2rwd插件的基准值设置为80px
3.设置基准值 setting的最下方 px to rwd中的FontSize里边


## 页面header头部的设计 ##
### 基础设置 ###
1.body设置背景图，缩放100%，行高1.15
2.背景图在整个容器内显示
### header ###
1.高度100px
2.背景图在容器内显示 缩放100% head_bg.jpg
3.h1标题部分 文字颜色：白色  大小38像素 居中显示 行高80像素
4.时间模块 定位右侧 right为30像素 行高75像素 文字颜色 rgba(255,255,255,0.7)
文字大小 20像素 实时时间的获取在 showTime.js中，在html进行引用，避免了代码的冗余

## 页面主体部分 ##
这一部分利用了bootstrap的栅格系统，引入bootstrap框架进行页面布局
那么该部分分为导航栏加主体部分
### navigation导航栏目部分 ###
布局：柵格系統分得 col-md-2  兩份

```html
<!--        导航栏按钮-->
        <button style="margin-top: 5px;" type="button" class="btn btn-default" href="1.html" target="iframe1" onclick="changeSrc1()">王陵煤矿</button>
        <button style="margin-top: 5px;" type="button" class="btn btn-default" href="2.html" target="iframe1" onclick="changeSrc2()">古城煤矿</button>
        <button style="margin-top: 5px;" type="button" class="btn btn-default" href="2.html" target="iframe1" onclick="changeSrc2()">唐口煤矿</button>
        <button style="margin-top: 5px;" type="button" class="btn btn-default" href="2.html" target="iframe1" onclick="changeSrc2()">架宝寺煤矿</button>
        <button style="margin-top: 5px;" type="button" class="btn btn-default" href="2.html" target="iframe1" onclick="changeSrc2()">鸡西煤矿</button>
```

按钮：使用 bootstrap的垂直排列的按钮组
切换：通过监听 onclick事件来触发更换页面函数
### mainbox主体部分 ###
运用H5 iframes进行内嵌页面的切换
内嵌页面 高 574px 宽1245px 向左偏移-10px ，可随底层进行自适应

#### 内嵌页面 ####
整体最大宽度限制在1230px，分为 up and bottom 两部分
##### up部分 #####
1.up部分 height:400px  display:flex 其中包含四个图表模块 一个CAD图纸模块
2.分成3列 布局3 5 3阵型

```css
  .column{
    flex: 3;
    &:nth-child(2){
      flex: 5;
      overflow: hidden;
      margin: 0 0.125rem 0.125rem;
    }
  }
```
3.每个图表高度200px 边框颜色rgba(25,186,139,17)
4.图表模块边角的实现

```css
&::before{
      content: '';
      position: absolute;
      top: 0px;
      left: 0px;
      width: 10px;
      height: 10px;
      border-left: 2px solid #02a5b6;
      border-top: 2px solid #02a5b6;
    }
    &::after{
      content: '';
      position: absolute;
      top: 0px;
      right: 0px;
      width: 10px;
      height: 10px;
      border-right: 2px solid #02a5b6;
      border-top: 2px solid #02a5b6;
    }
```

5.图表要给出具体的宽高，echarts的要求

```css
width: 18rem;
height: 9rem;
```

6.雷达图样例 的实现

```js
//雷达图模块1
(function () {
    //1.实例化对象
    var myChart=echarts.init(document.getElementById("ld1"));
    //2.指定配置项和数据
    var option = {
        tooltip: {},
        toolbox:{
            feature:{
                saveAsImage: {}
            }
        },
        radar: {
            // shape: 'circle',
            radius:57,
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5],
                }
            },
            indicator: [
                { name: '销售', max: 6500},
                { name: '管理', max: 16000},
                { name: '信息技术', max: 30000},
                { name: '客服', max: 38000},
                { name: '研发', max: 52000},
                { name: '市场', max: 25000}
            ]
        },
        series: [{
            name: '预算 vs 开销',
            type: 'radar',
            // areaStyle: {normal: {}},
            data: [
                {
                    value: [4300, 10000, 28000, 35000, 50000, 19000],
                    name: '预算分配'
                },
                {
                    value: [5000, 14000, 28000, 31000, 42000, 21000],
                    name: '实际开销'
                }
            ]
        }]
    };
    //3.把配置项给实例对象
    myChart.setOption(option);
    //4.图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    })
})();
```

7.折线图样例实现

```js
//折线图1模块立即执行函数
(function () {
    var myChart=echarts.init(document.getElementById("zx1"));
    var option = {
        color:['#2f89cf'],
        grid: {
            left: '0%',
            right: '0%',
            bottom: '4%',
            top:'10px',
            containLabel: true
        },
        tooltip:{
          trigger: 'axis',
          axisPointer: {
              type:'line'
          }
        },
        toolbox:{
          feature:{
              saveAsImage: {}
          }
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
                alignWithLabel: true
            },
            //修改刻度标签等相关样式
            axisLabel:{
              color:"rgba(255,255,255,0.6)",
              fontSize:"12"
            },
            //是否显示x 坐标轴的样式
            axisLine:{
                show:false
            }
        },
        yAxis: {
            type: 'value',
            axisTick: {
                alignWithLabel: true
            },
            //修改刻度标签等相关样式
            axisLabel:{
                color:"rgba(255,255,255,0.6)",
                fontSize:"12"
            },
            //是否显示y 坐标轴的样式
            axisLine:{
                lineStyle:{
                  color:"rgba(255,255,255,0.6)"
                },
            },
            //y轴分割线样式修改版
            splitLine:{
                lineStyle: {
                    color:"rgba(255,255,255,0.6)"
                }
            }
        },
        series: [{
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: 'line'
        }]
    };
    //3.把配置项给实例对象
    myChart.setOption(option);
    //4.图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    })
})();
```

##### bottom部分 #####
1.高度 173px 向下偏移 1px 边框 rgba(25,186,139,17)
2.图表宽高 height: 152px;width: 1230px;
3.柱形图样例

```js
//柱状图模块立即执行函数
(function () {
    var myChart=echarts.init(document.getElementById("bar"));
    var option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '0%',
            bottom: '4%',
            top:'10px',
            containLabel: true
        },
        toolbox:{
          feature:{
              saveAsImage:{}
          }
        },
        xAxis: [
            {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                },
                //修改刻度标签等相关样式
                axisLabel:{
                    color:"rgba(255,255,255,0.6)",
                    fontSize:"12"
                },
                //是否显示x 坐标轴的样式
                axisLine:{
                    show:false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisTick: {
                    alignWithLabel: true
                },
                //修改刻度标签等相关样式
                axisLabel:{
                    color:"rgba(255,255,255,0.6)",
                    fontSize:"12"
                },
                //是否显示y 坐标轴的样式
                axisLine:{
                    lineStyle:{
                        color:"rgba(255,255,255,0.6)"
                    },
                },
                //y轴分割线样式修改版
                splitLine:{
                    lineStyle: {
                        color:"rgba(255,255,255,0.6)"
                    }
                }
            }
        ],
        series: [
            {
                name: '直接访问',
                type: 'bar',
                barWidth: '35%',
                data: [22,10, 52, 200, 334, 390, 330, 220],
                //柱子的样式属性
                itemStyle:{
                    //柱子圆润些
                    barBorderRadius:5
                }
            }
        ]
    };
    //3.把配置项给实例对象
    myChart.setOption(option);
    //4.图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    })
})();
```




