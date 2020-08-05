% %用lsqnonlin调用解决: 
% x=[3 4 5 7 9 15]; 
% y=[1 2 4 6 8 10]; 
clc
clear all
load mic.txt
Sample=mic;
[m,n]=size(Sample);
v=5;
for i=1:n
    if i==n
        x0(i)=min(Sample(:,i));
    else
        x0(i)=sum(Sample(:,i))/m;
    end
end
x0(n+1)=v;
%x0=[200,300,500,95,5.6]; 
options=optimset('lsqnonlin'); 
%options.iter='display'
options=optimset('Display','iter');
  options.MaxFunEvals=2500;
%  options.MaxIter=500;
[a,b,c]=lsqnonlin(@myfun,x0,[],[],options,Sample)
% clc



