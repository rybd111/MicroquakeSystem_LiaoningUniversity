function F_value=Function_goal(X_0,X)
k=length(X(:,1));
for i=1:k
    ci(i)=X(i,4)-(((X(i,1)-X_0(1))^2+(X(i,2)-X_0(2))^2+(X(i,3)-X_0(3))^2)^0.5)/X_0(4);
end
cm=Median(ci);
for i=1:k
    r(i)=abs(ci(i)-cm);
end
F_value=sum(r);