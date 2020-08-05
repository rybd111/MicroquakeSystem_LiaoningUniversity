clc;
clear all;
load local.txt;
k=length(local(1,:));
% for i=1:k
%     X_0(i)=mean(local(:,i));
% end
coords=local;
%X_0=[400,400,400,96]';
x0=[ 400  400  400   96];
[a,b,c]=forminsearch();
