function [ff,one]=mysort3(fv,sample,v,diedai,lead)
    step=[1,0,0;
          -1,0,0;
          0,1,0;
          0,-1,0;
          0,0,0.2;
          0,0,-0.2];
one=ones(diedai,4);
for o=1:diedai
    len1=(1-(o-1)/diedai)*0.5;
    len2=(1-(o-1)/diedai)*600;
    len3=((o-1)/diedai)*5;
    for i=1:lead
        for j=1:3
            bian=bianyi(fv(i,:),sample,step,len2,v);
            fv(i,:)=bian;
        end
    end
    n=length(fv(:,1));%��ȡС�����
    k=n;%�Ӻ���ǰ����С��
   for j=1:n-lead%˳�������93��
       temp1=fv(k,:);
       temp2=fv(ceil(rand*lead),:);
       if(temp2(4)<temp1(4))
           temp=(temp2(:,1:3)-temp1(:,1:3))*len1+temp1(:,1:3);
           new_err=mubiao3(temp,sample,v);
           if new_err(4)<temp1(4)
            fv(k,:)=new_err;   
           end
       end
       bian=bianyi(fv(k,:),sample,step,len3,v);
       fv(k,:)=bian;
       k=k-1;
   end
    fv=sortrows(fv,4);%��С�鰴�����ֵ����
    one(o,:)=fv(1,:);
end
ff=fv;
