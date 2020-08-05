%编写M文件：文件中的a(1)=a,a(2)=b,a(3)=c,a(4)=d 
% function E=fun(a,x,y) 
% x=x(:); 
% y=y(:); 
% Y=a(1)*(1-exp(-a(2)*x)) + a(3)*(exp(a(4)*x)-1); 
% E=y-Y; 
% %M文件结束 
% %用lsqnonlin调用解决: 
% x=[3 4 5 7 9 15]; 
% y=[1 2 4 6 8 10]; 
% a0=[1 1 1 1]; 
% options=optimset('lsqnonlin'); 
% a=lsqnonlin(@fun,a0,[],[],options,x,y) 

function F=myfun(X_0,X)
% load Mic.txt
% X=Mic;
m=length(X(:,1));
for i=1:m
    r(i)=(X(i,4)-X_0(4)-((((X(i,1)-X_0(1))^2+(X(i,2)-X_0(2))^2+(X(i,3)-X_0(3))^2)^0.5)/(X_0(5))))^2;
end
F=sum(r);
