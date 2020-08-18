function newch1=pre(ch11)
newch1(:)=0;%新建一个波形存储变量
k=60;
l = length(ch11(:,1))-k;%长度减k为了避免数组越界
sum1=0;%求和
newch1 = filter(ones(1,k)/k,1,ch11);
% for i=1:l
%    for j=i:i+k%在这六个数中循环，同时，这4个数除了最大值最小值之外求算术平均值
%        [minch,ind1]=min(ch11(i:i+k,1));%求ch11中每6个数的最大值和最小值，并将它们的序号记录在ind1和ind2中
%        [maxch,ind2]=max(ch11(i:i+k,1));
%        if(j~=ind1 && j~=ind2)%ind1和ind2就是实际的位置，若j不等于这两个序号，才将值求和再放入sum
%            sum1=sum1+ch11(j,1);
%        end
%    end
%    newch1(i) = sum1/(k-2);sum1=0;%对sum进行算术平均数的求取
%    
% end
% for i=length(newch1):length(ch11)
%     newch1(i) = 0;
% end
% fprintf('newch1%d',length(newch1));
% fprintf('ch11%d',length(ch11));

