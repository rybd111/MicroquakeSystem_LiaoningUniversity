%����Ŀ�������ÿ�����������߳��ȣ���A���󣬷����������������Ⱦ���
clear all
clc
%��������������������1 ����ϵ���������ʱ���󡪡���������������������������������
%����������������������1���������� ����������������������������������������������
cell_length=load('E:\��ά�嵥λ���߳���.txt');%ÿ������������Ԫ�еĳ��Ⱦ���
pp1=0;
[hh,ll]=size(cell_length);
for i=1:hh
    if cell_length(i,:)==0
        cell_length(i,:)=nan;
        pp1=pp1+1;
        qq1(pp1)=i;
    end
end
% cell_length(all(cell_length==0,2),:) = [];%ɾȥ�����е�ȫ���У�������Ϊ0
L = cell_length(all(~isnan(cell_length),2),:);
rrr=load('E:\����ɸѡ.txt');
pp2=0;
[hh2,ll2]=size(rrr);

for j=1:hh2
   if pp1==0
    continue
   else
       for m=1:length(qq1)
    if j==qq1(m)
        rrr(j,:)=nan;
    end
    end
end
end
rrr=rrr(all(~isnan(rrr),2),:);
ray_Thetratime=rrr(1:427,8)/250;  %ÿ������������ʱ
%����������������������2������ϵ������A������������������������������������������
[r,c]=size(L);%r������-����������c������С���Ԫ������
for i=1:r   %��������
    for j=1:c  %��Ԫ������
        ray(i).A(j)=cell_length(i,j); %������ϵ������A
    end
end
%����������������������3����ʱ����ÿ�����ߣ� ����������������������������������
%������������������������4�����ȳ�ʼ��ֵΪ�� ������������������������������������
for i=1:c %��Ԫ������
    xc(i)=1/3900; %�������Ⱦ��󣬳�ʼ��ֵΪ0
end
%������������������������5��ϵ��������ÿ�в�Ϊ��ֵ���ĸ��� ����������������������
xc=xc';
    
%��������������������2 �������㡪������������������������������������������������
n=0;
fid = fopen('E:\��ά�ռ����ȷ��ݽ��1.txt','w');
number=100; %��������

for i=1:number
e=ray_Thetratime-L*xc;%������
% if sum(sum((e-ray_Thetratime).*(e-ray_Thetratime)))<1e-140
%     fprintf('���н�����')
%     break
% end
fai1=zeros(c,c);
qq=0;
for i=1:c
for j=1:r
fai1(i,i)=fai1(i,i)+ray(j).A(i);
end
end
for i=1:c
    if fai1(i,i)==0
%         fai1(i,i)=3900*(1+0.1*rand(1,1));
                fai1(i,i)=0.000000001;

    end
end
% fai=inv(fai1);
kesai1=zeros(r,r);
for j=1:r
for i=1:c
kesai1(j,j)= kesai1(j,j)+ray(j).A(i)* ray(j).A(i);
end
end
% kesai=inv(kesai1);
xc=xc+(fai1\L')*(kesai1\e);
for i=1:c
    if xc(i)<0
        xc(i)=1/3900;
    end
    if xc(i)<1/6000
        xc(i)=1/3900;
    end
end
n=n+1
end

for i=1:c
    fprintf(fid,'%f ',1/xc(i));
    fprintf(fid,'\n');
end
    fclose(fid);

          