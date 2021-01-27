%一条射线与网格交点坐标
clear all
clc
m1=180;n1=400;l1=30;
xqidian=69700;yqidian=73000;zqidian=-980;
lx=m1;%试件的长度
ly=n1;%试件的宽度
lz=l1;%试件的高度
xRes=9;%x方向的空间分辨率，即网格间隔
yRes=13; %y方向的空间分辨率，即网格间隔
zRes=1; %z方向的空间分辨率，即网格间隔
xUnit=lx/xRes;%每个体单元的长度
yUnit=ly/yRes;%每个体单元的宽度
zUnit=lz/zRes;%每个体单元的高度

shuju=load('E:\射线筛选.txt');
fid=fopen('E:\三维体单位射线长度.txt','w');
xx=0;
[ww,gg]=size(shuju);
 for i=1:ww-200 %射线条数
     xx=xx+1
    xSource=shuju(i,2)-xqidian-lx/2;
    ySource=shuju(i,3)-yqidian-ly/2;
    zSource=shuju(i,4)-zqidian-lz/2;
    xDetector=shuju(i,5)-xqidian-lx/2;  %调整坐标系到模型中间位置
    yDetector=shuju(i,6)-yqidian-ly/2;
    zDetector=shuju(i,7)-zqidian-lz/2; 
    
%————————yoz平面————————————————————————————
x_planeVector=1;y_planeVector=0;z_planeVector=0;%y0z平面
x_lineVector=xDetector-xSource; y_lineVector=yDetector-ySource; z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
y_planePoint=0;z_planePoint=0;
m=0;
for k=1:xRes+1
x_planePoint=-lx/2+xUnit*(k-1);
vpt =x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% 判断直线是否与平面平行条件
if vpt~= 0
m=m+1;
t = ((x_planePoint-x_linePoint) *x_planeVector+(y_planePoint-y_linePoint)*y_planeVector+(z_planePoint-z_linePoint)*z_planeVector)/vpt;
x(m)=x_linePoint+x_lineVector*t;
y(m)=y_linePoint+y_lineVector*t;
z(m)=z_linePoint+z_lineVector*t;
if y(m)>yRes/2*yUnit||y(m)<-yRes/2*yUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN; 
end
if z(m)>zRes/2*zUnit||z(m)<-zRes/2*zUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN;  
end    
end
end
%————————xoz平面————————————————————————————
x_planeVector=0; y_planeVector=1;z_planeVector=0; %y0z平面
x_lineVector=xDetector-xSource;y_lineVector=yDetector-ySource;z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
x_planePoint=0;z_planePoint=0;
% m=0;
for k=1:yRes+1
y_planePoint=-ly/2+yUnit*(k-1);
vpt=x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% 判断直线是否与平面平行条件
if vpt~= 0
m=m+1;
t=((x_planePoint-x_linePoint)*x_planeVector+(y_planePoint-y_linePoint)*y_planeVector+(z_planePoint-z_linePoint)*z_planeVector)/vpt;
x(m)=x_linePoint+x_lineVector*t;
y(m)=y_linePoint+y_lineVector*t;
z(m)=z_linePoint+z_lineVector*t;
if x(m)>xRes/2*xUnit||x(m)<-xRes/2*xUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN; 
end
if z(m)>zRes/2*zUnit||z(m)<-zRes/2*zUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN;  
end
end
end
%————————xoy平面————————————————————————————
x_planeVector=0;  y_planeVector=0; z_planeVector=1;%x0y平面
x_lineVector=xDetector-xSource;y_lineVector=yDetector-ySource;z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
x_planePoint=0;y_planePoint=0;
% m=0;
for k=1:zRes+1
z_planePoint=-lz/2+zUnit*(k-1);
vpt=x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% 判断直线是否与平面平行条件
if vpt~= 0
m=m+1;
t = ((x_planePoint-x_linePoint)*x_planeVector+(y_planePoint-y_linePoint)*y_planeVector+(z_planePoint-z_linePoint)*z_planeVector)/vpt;
x(m)=x_linePoint+x_lineVector*t;
y(m)=y_linePoint+y_lineVector*t;
z(m)=z_linePoint+z_lineVector*t;
if y(m)>yRes/2*yUnit||y(m)<-yRes/2*yUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN; 
end
if x(m)>xRes/2*xUnit||x(m)<-xRes/2*xUnit
x(m)=NaN;y(m)=NaN;z(m)=NaN;  
end
end
end
%————————剔除(TC)坐标中NaN数据—————————————————————
T=[x;y;z];
TC=T(1,:).*T(2,:).*T(3,:);
for i=length(T):-1:1
   if isnan(TC(1,i))==1
      T(:,i)=[]; 
      
   end
end
T=(unique(T','rows'))';%去掉相同坐标数据
%———————将坐标按xyz排序—————————————————————————
if x_lineVector~=0
   [sT,k]=sort(T(1,:),'ascend');
   T=T(:,k);
end
  if x_lineVector==0 && y_lineVector~=0
 [sT,k]=sort(T(2,:),'ascend');
   T=T(:,k);
  end
  if x_lineVector==0 && y_lineVector==0
   [sT,k]=sort(T(3,:),'ascend');
   T=T(:,k);
  end
  [A,B]=size(T);
%——————————计算直线与网格交线长度(未加震源点和探头点网格)———————————————————
for i=1:B-1
    raylength(i)=sqrt((T(1,i+1)-T(1,i))^2+(T(2,i+1)-T(2,i))^2+(T(3,i+1)-T(3,i))^2);
end
%——————————计算直线与网格交线的网格索引（编号）———————————————————   
if x_lineVector<0
    x_lineVector=-x_lineVector;
    y_lineVector=-y_lineVector;
    z_lineVector=-z_lineVector;
end
if x_lineVector==0 && y_lineVector<0
    x_lineVector=-x_lineVector;
    y_lineVector=-y_lineVector;
    z_lineVector=-z_lineVector;  
end
if x_lineVector==0 && y_lineVector==0 && z_lineVector<0
    x_lineVector=-x_lineVector;
    y_lineVector=-y_lineVector;
    z_lineVector=-z_lineVector;  
end

for i=1:B-1
Px=floor(T(1,i)/xUnit);Py=floor(T(2,i)/yUnit);Pz=floor(T(3,i)/zUnit);
if y_lineVector<0 && Py*yUnit==T(2,i)
    Py=Py-1;
end
if z_lineVector<0 && Pz*zUnit==T(3,i)
    Pz=Pz-1;
end
Px=Px+xRes/2;Py=Py+yRes/2;Pz=Pz+zRes/2;
   number(i)=abs((Px+1)+Py*xRes+Pz*xRes*yRes);
end
%————————计算射线在每个体单元上的大小并输出———————————————
for j=1:xRes*yRes*zRes %网格的个数
        transform(j)=0; %每条射线在每个体单元中长度初始值赋予0
   end
for q=1:length(number)
     transform(ceil(number(q)))=raylength(q);  
end
for j=1:xRes*yRes*zRes %网格的个数
       fprintf(fid,'%f ',transform(j));
    end
       fprintf(fid,'\n');
%        if xx==3
%        break
%        end
 end
fclose(fid);				

%——————————————缩减网格——————————————————————
% %分析区域坐标
% cell_length=load('E:\三维体单位射线长度.txt');%每条射线在网格单元中的长度矩阵
% xfanwei=[69100  69300];
% xquyu1=min(xfanwei) ;
% xquyu2=max(xfanwei) ;
% yfanwei=[72900 73200];
% yquyu1= min(yfanwei);
% yquyu2= max(yfanwei);
% zfanwei=[-890 -930];
% zquyu1=min(zfanwei);
% zquyu2=max(zfanwei);
% %————————按x消去单元数据————————
% %将小于x最小值的列赋值NaN
% if  xquyu1-69230-lx/2>0
% qqx=ceil((xquyu1-69230-lx/2)/xUnit)+xRes;
% else
% qqx=floor((xquyu1-69230-lx/2)/xUnit)+xRes;
% end
% for i=1:qqx
% cell_length(:,i)=nan;
% for n=1:zRes
% cell_length(:,i+n*xRes*yRes)=nan;
% end
% end
% 
% %将大于x最大值的列赋值NaN
% if  xquyu2-69230-lx/2>0
% ppx=ceil((xquyu2-69230-lx/2)/xUnit)+xRes;
% else
% ppx=floor((xquyu2-69230-lx/2)/xUnit)+xRes;
% end
% for i=ppx:xRes
% cell_length(:,i)=nan;
% for n=1:zRes
% cell_length(:,i+n*xRes*yRes)=nan;
% end
% end
% %————————按y消去单元数据————————
% %将小于y最小值的列赋值NaN
% if  yquyu1-73007-ly/2>0
% qqy=ceil((yquyu1-73007-ly/2)/yUnit)+yRes;
% else
% qqy=floor((yquyu1-73007-ly/2)/yUnit)+yRes;
% end
% for i=1:qqy*xRes
% cell_length(:,i)=nan;
% for n=1:zRes
% cell_length(:,i+n*xRes*yRes)=nan;
% end
% end
% %将大于y最大值的列赋值NaN
% if  yquyu2-73007-ly/2>0
% ppy=ceil((yquyu2-73007-ly/2)/yUnit)+yRes;
% else
% ppy=floor((yquyu2-73007-ly/2)/yUnit)+yRes;
% end
% for i=ppy*xRes:yRes*xRes
% cell_length(:,i)=nan;
% for n=1:zRes
% cell_length(:,i+n*xRes*yRes)=nan;
% end
% end
% 
% %————————按z消去单元数据————————
% %将小于z最小值的列赋值NaN
% if  zquyu1+876-lz/2>0
% qqz=ceil((zquyu1+876-lz/2)/zUnit)+zRes;
% else
% qqz=floor((zquyu1+876-lz/2)/zUnit)+zRes;
% end
% for i=1:qqz*xRes*yRes
% cell_length(:,i)=nan;
% end
% %将大于z最大值的列赋值NaN
% if  zquyu1+876-lz/2>0
% ppz=ceil((zquyu2+876-lz/2)/zUnit)+zRes;
% else
% ppz=floor((zquyu2+876-lz/2)/zUnit)+zRes;
% end
% for i=(ppz-1)*yRes*xRes+1:zRes*yRes*xRes
% cell_length(:,i)=nan;
% end
% fid=fopen('E:\缩减网格.txt','w');
% cell_length =cell_length (:,all(~isnan(cell_length),1));
% [tt,rr]=size(cell_length);
% for i=1:tt
%     for j=1:rr
%       fprintf(fid,'%f ',cell_length(i,j));
%     end
%        fprintf(fid,'\n');
% end
% fclose(fid);				
% 
% xRes=ppx-qqx  %在切片程序输入这个值
% yRes=ppy-qqy
% zRex=ppz-qqz
