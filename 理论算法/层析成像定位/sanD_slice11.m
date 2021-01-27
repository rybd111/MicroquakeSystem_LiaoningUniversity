clear all
clc
close all

%————————将慢度数组变成三维———————————————————————
a=load('E:\三维空间慢度反演结果.txt');
xRes=30; %按网格设置更改
yRes=40; 
zRes=4;
v=reshape(a,xRes,yRes,zRes);
% v=smooth3(v);%数据平滑
%————————对三维数据进行切片表示—————————————————————
[x,y,z]=meshgrid(1:xRes,1:yRes,1:zRes);
h=figure(1);
set(h,'name','切片')
subplot(121)
title('顶板')
%————————————顶板层，层2——————————————————————
x1=x(:,:,2)*6+69687;%9代表每个网格的x间隔尺寸
y1=y(:,:,2)*10+73040;%10代表每个网格的x间隔尺寸，需要计算一下
% z1=z(:,:,1);
v1 =smooth(v(:,:,2)',5);
v1=reshape(v1,40,30);%网格数，x为80个网格，y为40个网格
contourf(x1,y1,v1,15);
legend('顶板')
set(gca,'XDir','reverse'); 
annotation('arrow',[0.35 0.15],[0.8 0.8],'color','w');
text(69830,73358,'\fontsize{12}推进方向','color','w');%这个是“推进方向”文字位置，要按前面坐标‘69830,73358’改动,

%————————————煤层，层1——————————————————————
subplot(122)
x2=x(:,:,3)*6+69687;
y2=y(:,:,3)*10+73040;
% z2=z(:,:,2);
v2 =smooth(v(:,:,3)',10);
v2=reshape(v2,40,30);
contourf(x2,y2,v2,10);
legend('煤层')
set(gca,'XDir','reverse'); 
annotation('arrow',[0.8 0.6],[0.8 0.8],'color','w');
text(69830,73358,'\fontsize{12}推进方向','color','w');



