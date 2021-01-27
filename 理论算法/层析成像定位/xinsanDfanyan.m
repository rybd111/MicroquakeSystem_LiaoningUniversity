%正演目的是求出每个网格中射线长度，即A矩阵，反演是求出网格的慢度矩阵
clear all
clc
%——————————1 构建系数矩阵和走时矩阵——————————————————
%——————————（1）加载数据 ———————————————————————
cell_length=load('E:\三维体单位射线长度.txt');%每条射线在网格单元中的长度矩阵
pp1=0;
[hh,ll]=size(cell_length);
for i=1:hh
    if cell_length(i,:)==0
        cell_length(i,:)=nan;
        pp1=pp1+1;
        qq1(pp1)=i;
    end
end
% cell_length(all(cell_length==0,2),:) = [];%删去矩阵中的全零行，即射线为0
L = cell_length(all(~isnan(cell_length),2),:);
rrr=load('E:\射线筛选.txt');
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
ray_Thetratime=rrr(1:427,8)/250;  %每条射线理论走时
%——————————（2）构建系数矩阵A—————————————————————
[r,c]=size(L);%r矩阵行-射线条数；c矩阵的列—单元网格数
for i=1:r   %射线条数
    for j=1:c  %单元网格数
        ray(i).A(j)=cell_length(i,j); %构建出系数矩阵A
    end
end
%——————————（3）走时矩阵（每条射线） —————————————————
%———————————（4）慢度初始赋值为零 ——————————————————
for i=1:c %单元网格数
    xc(i)=1/3900; %构建慢度矩阵，初始赋值为0
end
%———————————（5）系数矩阵中每列不为零值数的个数 ———————————
xc=xc';
    
%——————————2 迭代计算—————————————————————————
n=0;
fid = fopen('E:\三维空间慢度反演结果1.txt','w');
number=100; %迭代步数

for i=1:number
e=ray_Thetratime-L*xc;%误差分析
% if sum(sum((e-ray_Thetratime).*(e-ray_Thetratime)))<1e-140
%     fprintf('运行结束！')
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

          