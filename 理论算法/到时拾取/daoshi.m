%%
%清除与本次计算无关的数据
clc
clear
close all
%%
%数据导入
a1 = importdata('C:\Users\QD-PC\Desktop\1.txt');
% a1 = a.data;
%%
%设置相关参数
path = 'D:\大理岩\';       %文件路径

cs = 250;        %长时窗
ds = 50;         %短时窗
km = 5;          %触发阈值

%%
%长短时窗法计算,提取所有的超过触发阈值的时间段
[m1,n1] = size(a1);
for i1 = 2:1:n1
    mm1 = 1;
    Az = a1(:,i1);                   %提取一列数据
    for i11 = 1:1:m1                 %按照曲线上所有的点进行循环  
        lta1 = 0;sta1 = 0;
        if i11 < cs
            continue
        end
        for i12 = cs:-1:1
            if i11 - i12 <= 0        %若超过所有数据的前范围
                lta1 = 0;            %将长时窗在此点计算结果定为0
                continue
            end
            if i11 + ds > m1         %若超过所有数据的后范围
                break                %终止长时窗的均值计算
            end
            lta1 = lta1 + Az(i11 - i12 + 1)^2 - Az(i11 - i12) * Az(i11 - i12 + 2);      %计算长时窗的总值
        end
        for i13 = ds:-1:1
            if i11 + i13 + 1 > m1    %若超过所有数据的后范围
                sta1 = 0;            %将短时窗在此点计算结果定为0
                break
            end
            sta1 = sta1 + Az(i11 + i13)^2 - Az(i11 + i13 - 1) * Az(i11 + i13 + 1);      %计算短时窗的总值
        end
        lta(i11,i1 - 1) = lta1/cs;              %计算长时窗的均值
        sta(i11,i1 - 1) = sta1/ds;              %计算短时窗的均值
        if lta(i11,i1 - 1) == 0                 %如果长时窗的计算结果为0
            kk(i11,i1 - 1) = 0;                 %此点触发阈值定为0
        else
            kk(i11,i1 - 1) = sta(i11,i1 - 1)/lta(i11,i1 - 1);
        end
        if kk(i11,i1 - 1) >= km                 %如果超过触发阈值
            ip1(mm1,i1 - 1) = i11;              %保存到时点序号
            mm1 = mm1 + 1;
        end
    end
end
%%
%将数据进行分段，为提取峰值做准备
[m2,n2] = size(ip1);
for i2 = 1:1:n2
    mm2 = 1;         %循环系数，为了设置矩阵的大小
    ik = 1;          %计数初始点
    for i21 = 1:1:m2 - 1
        io1 = ip1(i21,i2);                 %前一个数
        io2 = ip1(i21 + 1,i2);             %后一个数
        iu2 = io2 - io1;                   %前后数值差
        if iu2 > 10                        %如果前后数值差没有超过10
            io{mm2,i2} = ip1(ik:i21,i2);   %保留超过触发阈值的这一段的数据
            ik = i21 + 1;                  %保留完一段数据后更新下一段数据的起点
            mm2 = mm2 + 1;
        end
    end
    io{mm2,i2} = ip1(ik:end,i2);           %最后一组数据由于没有差值，单独对其进行保存
end
%提取超过触发阈值的比值曲线上的峰值点
[m21,n21] = size(io);
for i22 = 1:1:n21
    for i23 = 1:1:m21
        io3 = io{i23,i22};                 %提取出一个小分段数据
        ij = isempty(io3);                 %如果这一段数据为空白矩阵就跳过这一列的循环（空白矩阵是由于MATLAB自动补齐数据导致）
        if ij == 1
            break
        end
        [m22,n22] = size(io3);
        for i24 = 1:1:m22
            uh1 = 1;                       %提取这一段数据的第一个点
            if io3(i24) == 0               %如果数据为0时
                uh2 = i24 - 1;             %保留到0之前的非0数
                break                      %终止循环
            else                           %如果数据中没有出现0
                uh2 = m22;                 %保存这段数据的最后一个数
            end
        end 
        kk1 = kk(io3(uh1:uh2),i22);        %提取这段数据在比值曲线上的部分
        [pl,pk] = max(kk1);                %提取峰值点的位置
        io4 = io3(pk);                     
        ip2(i23,i22) = io4;                %将峰值点保存进新的矩阵
    end
end