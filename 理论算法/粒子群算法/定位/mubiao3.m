function f=mubiao3(fv,sample,v)
k=length(sample(:,1));
goal=zeros(1,4);
goal(1,1:3)=fv;
serr=ones(k,1);
    serr(:,1)=abs(sample(:,4)-(((sample(:,1)-fv(1)).^2+(sample(:,2)-fv(2)).^2+(sample(:,3)-fv(3)).^2).^0.5)/v);
goal(4)=var(serr(:,1))^0.5;
f=goal;%RWÄ¿±êº¯Êý
