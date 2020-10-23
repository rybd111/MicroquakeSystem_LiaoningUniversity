function [Pdc] = tensor_pro(a,tongdao,tlineb,density,velocity)
%TENSOR_PRO this function will fine the tensor.
%�����������ĳ���¼��ľ����������Ϊdouble������Ϊ3�����飬�ֱ�Ϊ���п����ڲ��õĴ��������꣬������������ź�P��������ֵ���Լ���Դ���ꡣ
%a��tongdao�Ƕ�ά���飬tlineb��һά���顣

%tongdao denotes the info of each motivatied sensor.
%tongdao��ʾÿ����������������Ϣ����������������źʹ�����������������2�У������������
%tlineb denotes the quake source info, only contains the coordination.
%tlineb��ʾ��Դ��Ϣ�����ӳ���ֻʹ����Դ��x��y��z���ݣ�����ͬ�㷨����õ�����Դ���꣬��3�С�
%a denotes the coordination info of each sensor, which is a matrix.
%����a�ĵ�һ��Ϊ��������ţ��ڶ���Ϊ��������x��������Ϊ��������y��������Ϊ��������z��
%ֻʹ�����е�2��3��4�У������������������꣬������������򣬴�1-n��

for i=1:length(a(:,1))
    x(i,1) = a(i,2);
    x(i,2) = a(i,3);
    x(i,3) = a(i,4);
end

y1=tlineb(1,2);y2=tlineb(1,3);y3=tlineb(1,4);

for i=1:length(x)
    gama(i,1) = (x(i,1)-y1)/(((x(i,1)-y1)^2+(x(i,2)-y2)^2+(x(i,3)-y3)^2)^0.5);
    gama(i,2) = (x(i,2)-y2)/(((x(i,1)-y1)^2+(x(i,2)-y2)^2+(x(i,3)-y3)^2)^0.5);
    gama(i,3) = (x(i,3)-y3)/(((x(i,1)-y1)^2+(x(i,2)-y2)^2+(x(i,3)-y3)^2)^0.5);
end

tongdao=sortrows(tongdao,1);
[m,n]=size(tongdao);
displacement=ones(m,3)*nan;
tdh=tongdao(:,1);
fd=tongdao(:,2);
for r=1:3
    displacement(:,r)=fd;
end
gama1=gama(tdh,:);
displacement=displacement.*gama1;
displacement=reshape(displacement,3*m,1);
c=1;
for r=1:3:length(gama)*3
    g(r,1)=gama(c,1)*gama(c,1)*gama(c,1);g(r+1,1)=gama(c,2)*gama(c,1)*gama(c,1);g(r+2,1)=gama(c,3)*gama(c,1)*gama(c,1);
    g(r,2)=2*gama(c,1)*gama(c,1)*gama(c,2);g(r+1,2)=2*gama(c,2)*gama(c,1)*gama(c,2);g(r+2,2)=2*gama(c,3)*gama(c,1)*gama(c,2);
    g(r,3)=2*gama(c,1)*gama(c,1)*gama(c,3);g(r+1,3)=2*gama(c,2)*gama(c,1)*gama(c,3);g(r+2,3)=2*gama(c,3)*gama(c,1)*gama(c,3);
    g(r,4)=gama(c,1)*gama(c,2)*gama(c,2);g(r+1,4)=gama(c,2)*gama(c,2)*gama(c,2);g(r+2,4)=gama(c,3)*gama(c,2)*gama(c,2);
    g(r,5)=2*gama(c,1)*gama(c,2)*gama(c,3);g(r+1,5)=2*gama(c,2)*gama(c,2)*gama(c,3);g(r+2,5)=2*gama(c,3)*gama(c,2)*gama(c,3);
    g(r,6)=gama(c,1)*gama(c,3)*gama(c,3);g(r+1,6)=gama(c,2)*gama(c,3)*gama(c,3);g(r+2,6)=gama(c,3)*gama(c,3)*gama(c,3);
    
%     g(r,7)=gama(c,1)
    c=c+1;
end
c=1;
for r=1:3:length(g)
    g(r,:) = g(r,:)./(((x(c,1)-y1)^2+(x(c,2)-y2)^2+(x(c,3)-y3)^2)^0.5);
    g(r+1,:) = g(r+1,:)./(((x(c,1)-y1)^2+(x(c,2)-y2)^2+(x(c,3)-y3)^2)^0.5);
    g(r+2,:) = g(r+2,:)./(((x(c,1)-y1)^2+(x(c,2)-y2)^2+(x(c,3)-y3)^2)^0.5);
    c=c+1;
end

coordinate = g;

tdh1=[tdh*3-1;tdh*3-2;tdh*3];
tdh1=sortrows(tdh1);
coordinate=coordinate(tdh1,:);

Green=coordinate./(4*pi*density*(velocity^3));

M=Green\displacement;
m11=M(1,1);m12=M(2,1);m13=M(3,1);m22=M(4,1);m23=M(5,1);m33=M(6,1);
m21=m12;m31=m13;m32=m23;
M1=[m11 m12 m13;m21 m22 m23;m31 m32 m33];
[V,D]=eig(M1);
[D_sort,index] = sort(diag(D),'descend');
D_sort = D_sort(index);
V_sort = V(:,index);
m1=D_sort(1,1);
m2=D_sort(2,1);
m3=D_sort(3,1);
Miso=(m1+m2+m3)/3/m1;
Mdc=(m2-m3)/m1;
Mclvd=(2*m1+2*m3-4*m2)/3/m1;
Pdc=abs(Mdc)/(abs(Miso)+abs(Mdc)+abs(Mclvd));
end

