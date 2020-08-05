clear all
clc
format long g; %输出结果为小数

   
%设定已知量数值
x(1)=-2061; y(1)=498;  z(1)=-75; r(1)=-0.153434*3850;
x(2)=-1044; y(2)=574; z(2)=-45; r(2)=-0.292645*3850;
x(3)=-3414; y(3)=1836; z(3)=-75; r(3)=-0.695784*3850;
syms xx yy zz rr %设定未知量
D=[x(1),y(1),z(1); x(2),y(2),z(2); x(3),y(3),z(3)];
dett=det(D); %求D的行列式；
if (dett==0)
    disp('行列式为0');
    return
end
for i=1:3
    bb=0.5*(x(i)^2 + y(i)^2 + z(i)^2 - r(i)^2) + r(i)*rr ;   %  求出b的代数式
    if i==1
        b1=bb;
    elseif i==2
        b2=bb;
    else
        b3=bb;
    end  
end
D1=[b1,y(1),z(1);b2,y(2),z(2);b3,y(3),z(3)];
D2=[x(1),b1,z(1);x(2),b2,z(2);x(3),b3,z(3)];
D3=[x(1),y(1),b1;x(2),y(2),b2;x(3),y(3),b3];
xx=det(D1)/det(D);
yy=det(D2)/det(D);
zz=det(D3)/det(D);
jf = xx^2+ yy^2 + zz^2 - rr^2 ;
 jf=char(jf);%将jf的代数式转化为字符串的形式，以便下行程序写成等式形式进行方程求解
dengshi1=strcat(jf,'=0');   %连接字符串的函数，得到jf=0的形式
rr1=solve(dengshi1,'rr');   %求解参数rr
rr=rr1(1) ;  %得到的rr1有两个解取其中一个解
xx1=subs(xx,'rr',rr) ; %将xx代数式中的rr参数值以rr数值代替，得到xx的数值xx1，下同
yy1=subs(yy,'rr',rr) ;
zz1=subs(zz,'rr',rr) ;
vpa(rr/3850,5) %保留5位有效小数
vpa(xx1,5)
vpa(yy1,5)
vpa(zz1,5)





