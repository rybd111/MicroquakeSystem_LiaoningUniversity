%һ�����������񽻵�����
clear all
clc
m1=180;n1=400;l1=30;
xqidian=69700;yqidian=73000;zqidian=-980;
lx=m1;%�Լ��ĳ���
ly=n1;%�Լ��Ŀ��
lz=l1;%�Լ��ĸ߶�
xRes=9;%x����Ŀռ�ֱ��ʣ���������
yRes=13; %y����Ŀռ�ֱ��ʣ���������
zRes=1; %z����Ŀռ�ֱ��ʣ���������
xUnit=lx/xRes;%ÿ���嵥Ԫ�ĳ���
yUnit=ly/yRes;%ÿ���嵥Ԫ�Ŀ��
zUnit=lz/zRes;%ÿ���嵥Ԫ�ĸ߶�

shuju=load('E:\����ɸѡ.txt');
fid=fopen('E:\��ά�嵥λ���߳���.txt','w');
xx=0;
[ww,gg]=size(shuju);
 for i=1:ww-200 %��������
     xx=xx+1
    xSource=shuju(i,2)-xqidian-lx/2;
    ySource=shuju(i,3)-yqidian-ly/2;
    zSource=shuju(i,4)-zqidian-lz/2;
    xDetector=shuju(i,5)-xqidian-lx/2;  %��������ϵ��ģ���м�λ��
    yDetector=shuju(i,6)-yqidian-ly/2;
    zDetector=shuju(i,7)-zqidian-lz/2; 
    
%����������������yozƽ�桪������������������������������������������������������
x_planeVector=1;y_planeVector=0;z_planeVector=0;%y0zƽ��
x_lineVector=xDetector-xSource; y_lineVector=yDetector-ySource; z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
y_planePoint=0;z_planePoint=0;
m=0;
for k=1:xRes+1
x_planePoint=-lx/2+xUnit*(k-1);
vpt =x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% �ж�ֱ���Ƿ���ƽ��ƽ������
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
%����������������xozƽ�桪������������������������������������������������������
x_planeVector=0; y_planeVector=1;z_planeVector=0; %y0zƽ��
x_lineVector=xDetector-xSource;y_lineVector=yDetector-ySource;z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
x_planePoint=0;z_planePoint=0;
% m=0;
for k=1:yRes+1
y_planePoint=-ly/2+yUnit*(k-1);
vpt=x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% �ж�ֱ���Ƿ���ƽ��ƽ������
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
%����������������xoyƽ�桪������������������������������������������������������
x_planeVector=0;  y_planeVector=0; z_planeVector=1;%x0yƽ��
x_lineVector=xDetector-xSource;y_lineVector=yDetector-ySource;z_lineVector=zDetector-zSource;
x_linePoint=xSource;y_linePoint=ySource;z_linePoint=zSource;
x_planePoint=0;y_planePoint=0;
% m=0;
for k=1:zRes+1
z_planePoint=-lz/2+zUnit*(k-1);
vpt=x_lineVector*x_planeVector+y_lineVector*y_planeVector+z_lineVector*z_planeVector;% �ж�ֱ���Ƿ���ƽ��ƽ������
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
%�����������������޳�(TC)������NaN���ݡ�����������������������������������������
T=[x;y;z];
TC=T(1,:).*T(2,:).*T(3,:);
for i=length(T):-1:1
   if isnan(TC(1,i))==1
      T(:,i)=[]; 
      
   end
end
T=(unique(T','rows'))';%ȥ����ͬ��������
%�������������������갴xyz���򡪡�����������������������������������������������
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
%������������������������ֱ���������߳���(δ����Դ���̽ͷ������)��������������������������������������
for i=1:B-1
    raylength(i)=sqrt((T(1,i+1)-T(1,i))^2+(T(2,i+1)-T(2,i))^2+(T(3,i+1)-T(3,i))^2);
end
%������������������������ֱ���������ߵ�������������ţ���������������������������������������   
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
%��������������������������ÿ���嵥Ԫ�ϵĴ�С�����������������������������������
for j=1:xRes*yRes*zRes %����ĸ���
        transform(j)=0; %ÿ��������ÿ���嵥Ԫ�г��ȳ�ʼֵ����0
   end
for q=1:length(number)
     transform(ceil(number(q)))=raylength(q);  
end
for j=1:xRes*yRes*zRes %����ĸ���
       fprintf(fid,'%f ',transform(j));
    end
       fprintf(fid,'\n');
%        if xx==3
%        break
%        end
 end
fclose(fid);				

%�����������������������������������񡪡�����������������������������������������
% %������������
% cell_length=load('E:\��ά�嵥λ���߳���.txt');%ÿ������������Ԫ�еĳ��Ⱦ���
% xfanwei=[69100  69300];
% xquyu1=min(xfanwei) ;
% xquyu2=max(xfanwei) ;
% yfanwei=[72900 73200];
% yquyu1= min(yfanwei);
% yquyu2= max(yfanwei);
% zfanwei=[-890 -930];
% zquyu1=min(zfanwei);
% zquyu2=max(zfanwei);
% %������������������x��ȥ��Ԫ���ݡ���������������
% %��С��x��Сֵ���и�ֵNaN
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
% %������x���ֵ���и�ֵNaN
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
% %������������������y��ȥ��Ԫ���ݡ���������������
% %��С��y��Сֵ���и�ֵNaN
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
% %������y���ֵ���и�ֵNaN
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
% %������������������z��ȥ��Ԫ���ݡ���������������
% %��С��z��Сֵ���и�ֵNaN
% if  zquyu1+876-lz/2>0
% qqz=ceil((zquyu1+876-lz/2)/zUnit)+zRes;
% else
% qqz=floor((zquyu1+876-lz/2)/zUnit)+zRes;
% end
% for i=1:qqz*xRes*yRes
% cell_length(:,i)=nan;
% end
% %������z���ֵ���и�ֵNaN
% if  zquyu1+876-lz/2>0
% ppz=ceil((zquyu2+876-lz/2)/zUnit)+zRes;
% else
% ppz=floor((zquyu2+876-lz/2)/zUnit)+zRes;
% end
% for i=(ppz-1)*yRes*xRes+1:zRes*yRes*xRes
% cell_length(:,i)=nan;
% end
% fid=fopen('E:\��������.txt','w');
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
% xRes=ppx-qqx  %����Ƭ�����������ֵ
% yRes=ppy-qqy
% zRex=ppz-qqz
