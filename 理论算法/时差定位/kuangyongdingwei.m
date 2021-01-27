clear all
clc
%——————————%观测点坐标和观测到时 ———————————————————
Taizan=load('台站坐标.txt')';

DS=load('坐标.txt');
DS=sortrows(DS,5); %按第五列数据进行升序排列

[rows,clown]=size(Taizan);

[rr,cc]=size(DS);

for i=1:clown
daoshi(i)=NaN;
end

for i=1:rr
    for j=1:clown
     if DS(i,1)==j   
    daoshi(j)=DS(i,5);
     end
    end
end

BSN=numel(find(~isnan(daoshi)));

BS=DS(:,2:4)' ;     %观测点坐标

Q = eye(BSN-1) ;          %生成nxn的单位矩阵

for i=1:rr
  t(i)=DS(i,5); %震源到台站BS信号到达时间
end
 %————————————计算震源到各台站距离差（考虑噪声） ——————————
Noise=0;  % 给出背景噪音大小

c=3850;      % 给出地层波速取值

for j=1:rr-1
d(j)=(t(j+1)-t(1))*c+Noise*c*rand(1); %考虑噪音的各台站与第一台站的相对距离
end
d=d';% 计算得到各台站与台站1的距离差

%————————————大于4个台站用chan解法—————————————————
if BSN>4

%————————————一次WLS求解 ——————————————————————
for i = 1: BSN
 K(i) = (BS(1,i)^2 + BS(2,i)^2+ BS(3,i)^2);    %各台站到参考台站的距离
end
%系数矩阵的生成
for i = 1: BSN-1
    Ga(i,1) =-(BS(1,i+1)-BS(1,1));
    Ga(i,2) =-(BS(2,i+1)-BS(2,1));
    Ga(i,3) =-(BS(3,i+1)-BS(3,1));
    Ga(i,4) =-d(i);
 end  % 求出Ga
 
for i = 1: BSN-1,
    h(i) = 0.5*(d(i)^2 - K(i+1)+ K(1));   %观测参数矩阵的生成
end  % 求h

Za0 = inv(Ga'*inv(Q)*Ga)*Ga'*inv(Q)*h';% 第一次对未知向量Za进行粗略估计（根据残差加权最小二乘求解方程组）


B = eye(BSN-1);  % 利用以上的粗略估计值计算B

for i = 1: BSN-1,%B用来进行权值的确定（权值为测量值误差方差矩阵的逆矩阵时，估计误差的方差最下）
    B(i,i) = sqrt((BS(1,i+1) - Za0(1))^2 + (BS(2,i+1) - Za0(2))^2+(BS(3,i+1) - Za0(3))^2);
end

FI = B*Q*B;            % 求FI（下一次最小二乘时权值的确定）

Za1 = inv(Ga'*inv(FI)*Ga)*Ga'*inv(FI)*h'; % 第一次WLS结果

if Za1(4) < 0
    Za1(4) = abs(Za1(4));
end
% ————————————第二次WLS，在第一次定位的基础上继续进一步修正权值———
CovZa = inv(Ga'*inv(FI)*Ga);  % 第一次WLS结果的协方差

sB = eye(4);                  % 第二次B值

for i = 1:3
 sB(i,i) = Za1(i)-BS(i,1);
end

sB(4,4)=Za1(4);

sFI = 4*sB*CovZa*sB;          %第二次FI值，修正后的权值系数

sGa = [1, 0,0; 0, 1,0;0,0,1; 1,1,1];  % 第二次Ga值，系数矩阵

 sh  = [(Za1(1)-BS(1,1))^2; (Za1(2)-BS(2,1))^2; (Za1(3)-BS(3,1))^2;Za1(4)^2]; %第二次h值，观测值
 
Za2 =inv(sGa'*inv(sFI)*sGa)*sGa'*inv(sFI)*sh;% 第二次WLS结果，经过多次修正权值，得到较优位置估计

Za = sqrt(Za2);Za = sqrt(abs(Za2));% 输出，取正值

if BS(3,1)<-400
    
for i=1:3
POS1(i)= (BS(i,1)+Za(i));%现场核对
end
POS1=POS1
vpa(POS1,8)   %输出时保留8位有效数字
else
for i=1:2
POS2(i)= abs(Za(i))+BS(i,1); 
end
POS2(3)=-(abs(Za(3))+BS(3,1))
vpa(POS2,8)   %输出时保留8位有效数字
end
end


%————————————等于4个台站用解析解法—————————————————
if BSN==4
  BS=(DS(:,2:4))' ;           %观测点坐标
  Q = eye(BSN-1) ;          %生成3x3的单位矩阵
Noise=0; 
for i=1:rr
  t(i)=DS(i,5);     %震源到台站信号到达时间
end
 %————————————计算震源到各台站距离差 ————————————————
for j=1:rr-1
d(j)=(t(j+1)-t(1))*c+Noise*c*rand(1);
end
d=d';% 计算得到各台站与台站1的距离差
%——————————求解方程组—————————————————————————
BS=BS';
A=[BS(1,1)-BS(2,1),BS(1,2)-BS(2,2),BS(1,3)-BS(2,3);
   BS(1,1)-BS(3,1),BS(1,2)-BS(3,2),BS(1,3)-BS(3,3);
   BS(1,1)-BS(4,1),BS(1,2)-BS(4,2),BS(1,3)-BS(4,3)];

k=0.5*[d(1)^2+BS(1,1).^2+BS(1,2)^2+BS(1,3)^2-BS(2,1)^2-BS(2,2)^2-BS(2,3)^2;
       d(2)^2+BS(1,1).^2+BS(1,2)^2+BS(1,3)^2-BS(3,1)^2-BS(3,2)^2-BS(3,3)^2;
       d(3)^2+BS(1,1).^2+BS(1,2)^2+BS(1,3)^2-BS(4,1)^2-BS(4,2)^2-BS(4,3)^2];
   
A=inv(A);
   
m=[A(1,1)*k(1)+A(1,2)*k(2)+A(1,3)*k(3);
   A(2,1)*k(1)+A(2,2)*k(2)+A(2,3)*k(3);
   A(3,1)*k(1)+A(3,2)*k(2)+A(3,3)*k(3)];

n=[A(1,1)*d(1)+A(1,2)*d(2)+A(1,3)*d(3);
   A(2,1)*d(1)+A(2,2)*d(2)+A(2,3)*d(3);
   A(3,1)*d(1)+A(3,2)*d(2)+A(3,3)*d(3)];

a=n(1)^2+n(2)^2+n(3)^2-1;

b=(m(1)-BS(1,1))*n(1)+(m(2)-BS(1,2))*n(2)+(m(3)-BS(1,3))*n(3);

c=(m(1)-BS(1,1))^2+(m(2)-BS(1,2))^2+(m(3)-BS(1,3))^2;

if b^2-a*c < 0
    fprintf('请核对传感器到时数据');
    return
  else
    r0=[(-b+sqrt(b^2-a*c))/a,(-b-sqrt(b^2-a*c))/a];
    x=m(1)+n(1)*r0;
    y=m(2)+n(2)*r0;
    z=m(3)+n(3)*r0;
   end 
if z(2)>0
    z(2)=-z(2);
end
POS3=[x(2),y(2),z(2)]
vpa(POS3,8)     %输出时保留8位有效数字
end
