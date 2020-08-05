function [x,y,z,t] = PSO(sample,v)
% sample=[
% 4596627.472,41516836.655,21.545,2.13;%蒿子屯S1
% 4595913.485,41519304.125,23.921,2.28;%工业广场Z2
% 4595388.504,41518099.807,22.776,2.32;%杨甸子T4
% 4597983.404,41520207.356,22.661,2.36;%北青堆子W6
% 4594304.927,41518060.298,21.926,2.46;%树碑子U3
% 4593163.619,41516707.440,22.564,2.61;%南风井V7
%     ];
% sample=[
%      41517290.037,4599537.326,24.565,0; %大鹏R2
%      41519304.125,4595913.485,23.921,0.08;%工业广场Z1  7.11—8
%      41518060.298,4594304.927,21.926,0.23;%树碑子U3
%      41520207.356,4597983.404,22.661,0.099999;%北青堆子W4
% %      41520815.875,4597384.576,25.468,2.73;%矿上车队X5
%      41519926.476,4597275.978,20.705,0.08999 %火药库Y6
% %      41518099.807,4595388.504,22.776,0;%杨甸子T4
% %      41516836.655,4596627.472,21.545,0;%蒿子屯S??
% %      41516707.440,4593163.619,22.564,2.59;%南风井V??
%       ];
% del=sample(1,:);
% for i=1:length(sample(1,:))
%     sample(:,i)=sample(:,i)-sample(1,i);
% end
% v=3850;%系统速度
k=length(sample(:,1));
diedai=50;%迭代次数
lead=30;%开拓点数
xxyy=1000;%xy间隔
zz=200;%z间隔
xylon=8000/xxyy+1;
zlon=1200/zz+1;
long=xylon*xylon*zlon;%模拟点总数量
show_one=ones(diedai,6);
fv=zeros(long,4);
len=1;
for i1=1:zlon
    for i2=1:xylon
        for i3=1:xylon
            fv(len,1)=(i3-1)*xxyy;
            fv(len,2)=(i2-1)*xxyy;
            fv(len,3)=(i1-1)*zz;
            len=len+1;
        end
    end
end
%相对模拟点
fv(:,1)=fv(:,1)-4500;
fv(:,2)=fv(:,2)-3500;
fv(:,3)=fv(:,3)-1230;
for ii=1:long
    fv(ii,:)=mubiao3(fv(ii,1:3),sample,v);
end
fv=sortrows(fv,4);%将小组按照误差值排序
[ff,one]=mysort3(fv,sample,v,diedai,lead);%一次寻优
suan1(1,:)=ff(1,:);
times=ones(length(sample(:,1)),1);
times(:,1)=sample(:,4)-((sample(:,1)-ff(1,1)).^2+(sample(:,2)-ff(1,2)).^2+(sample(:,3)-ff(1,3)).^2).^0.5/3850;
time=mean(times);
result=ones(1,4);
result(1,1:3)=suan1(1,1:3);
result(1,4)=time;

x = result(1,1);
y = result(1,2);
z = result(1,3);
t = result(1,4);
x = sprintf('%8.3f',x);
y = sprintf('%8.3f',y);
z = sprintf('%8.3f',z);
t = sprintf('%f',t);



