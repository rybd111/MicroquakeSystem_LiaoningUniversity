# MicroquakeSystem_LiaoningUniversity

A system about coal mine monitor. Now we have accomplish the main procedure codes of this system, and we expect some problems in it will be solved fast and high quality with different views. No matter use what methods in software or in hardware ways, being the best is our destination.

**开发程序需要用到的jar包链接如下：**

*链接：https://pan.baidu.com/s/1tI7NmGY66iX0Gge9_UBbgg
 
提取码：w6pz
 
复制这段内容后打开百度网盘手机App，操作更方便哦

集中式程序为KS_1.1.2，包括前台jsp内容，主类为multiThread包下的
mainThread.java，测试后台程序可通过mainTest运行。

测点程序为KS_1.1_Remote，测点程序还需精简。

中心程序为KS_1.1_Center，中心机程序汇总时注意汇总时间对齐。


##20201208更新测点程序 KS_1.1_Remote
*添加了分布式计算所需的计算指标（盘符、绝对到时、初动极值、波形绝对路径，其中波形绝对路径需要将盘符更改为当前计算的盘符）

##20210111更新中心机程序内容 KS_1.0.8
*更新了数据库服务器常量与参数类。

*更新了数据库名，优化了CS端的数据库表显示。

*增加了环境测试类。

*常数类增加了区域设置，由于平顶山矿区需要坐标转换。

*简化了配置文件。

*数据库记录保存至服务器端数据库和本地txt各一份，以防断网时远程数据

*库无法获取数据。

*封装四个主要模块（对齐，读取、激发、计算、保存）

##20210224更新中心机程序 KS_1.1.2
*加入了新的自动配置路径机制与情景模式配置

*简化了常量类

*增加了拉取远程文件中间件

*进一步简化了配置文件

*数据库改为远程服务器数据库

*增加了台站工况的监测系统

*进一步降低了各模块耦合性

*优化了历史文件对齐逻辑

##20210402更新中心机程序 KS_1.1.2
*优化了文件拉取类，使之能够输出拷贝的进程。

*优化了波形文件保存，不再续接保存波形，而是先删除已有的波形文件，再重新写，这样不必每次删除波形文件

*加入新设备历史读取兼容机制
 
*确定新设备实时读取兼容机制

##20210410更新监测程序 GetDiskAndNetInformation3

*优化了远程挂载机制下的盘符状态，即工况

*优化了开机启动程序

*修复了由于服务器停机导致无法监控的问题。
