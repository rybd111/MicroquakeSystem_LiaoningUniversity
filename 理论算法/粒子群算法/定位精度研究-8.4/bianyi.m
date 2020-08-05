function [result]=bianyi(fv,sample,step,len,v)
bian=zeros(6,4);
for i=1:6
    bian(i,1:3)=fv(:,1:3)+step(i,:)*len;
    bian(i,:)=mubiao3(bian(i,1:3),sample,v);
end
b=sortrows(bian,4);%将小组按照误差值排序
if b(1,4)<fv(:,4)
    result=b(1,:);
else
    result=fv;
end