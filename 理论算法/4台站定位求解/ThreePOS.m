clear
clc
format long g; %输出结果为小数
 
%设定已知量数值
% x(1)=-3431; y(1)=3040;  z(1)=9; t(1)=-0.3261 *3850;
% x(2)=-2078; y(2)=1702; z(2)=9; t(2)=-0.3982 *3850;
% x(3)=-1566; y(3)=779; z(3)=2; t(3)=-0.2657 *3850;

x(1)=-2061; y(1)=498;  z(1)=-75; t(1)=-0.153434*3850;
x(2)=-1044; y(2)=574; z(2)=-45; t(2)=-0.292645*3850;
x(3)=-3414; y(3)=1836; z(3)=-75; t(3)=-0.695784*3850;

syms xx yy zz tt %设定未知量
D=[x(1),y(1),z(1); x(2),y(2),z(2); x(3),y(3),z(3)];
dett=det(D); %求D的行列式；
if (dett==0)
    disp('行列式为0');
    return
end
for i=1:3
    bb=0.5*(x(i)^2 + y(i)^2 + z(i)^2 - t(i)^2) + t(i)*tt ;   %  求出b的代数式
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
jf = xx^2 + yy^2 + zz^2 - tt^2;
 jf=char(jf);%将jf的代数式转化为字符串的形式，以便下行程序写成等式形式进行方程求解
dengshi1=strcat(jf,'=0');   %连接字符串的函数，得到jf=0的形式
tt1=solve(dengshi1,'tt');   %求解参数tt
tt=tt1(1) ;  %得到的tt1有两个解取其中一个解
% tt=tt1(2);
xx1=subs(xx,'tt',tt) ; %将xx代数式中的tt参数值以tt数值代替，得到xx的数值xx1，下同
yy1=subs(yy,'tt',tt) ;
zz1=subs(zz,'tt',tt) ;
vpa(tt/3850,5) %保留5位有效小数
vpa(xx1,5)
vpa(yy1,5)
vpa(zz1,5)





