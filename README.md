# MicroquakeSystem_LiaoningUniversity
A system about coal mine monitor.
    集中式程序为KS_1.0.8，包括前台jsp内容，主类为multiThread包下的mainThread.java，测试程序可通过mainTest运行。
测点程序为KS_1.1_Remote
中心程序为KS_1.1_Center

#20201208更新测点程序KS_1.1_Remote
1添加了分布式计算所需的计算指标（盘符、绝对到时、初动极值、波形绝对路径，其中波形绝对路径需要将盘符更改为当前计算的盘符）