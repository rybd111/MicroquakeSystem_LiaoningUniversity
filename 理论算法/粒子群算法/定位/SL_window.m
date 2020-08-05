function [ratio,position] = SL_window(z)
short = 50;
long = 250;
sumshort=0;
sumlong=0;
sumrough=0;
sumrefine=0;
roughRange=500;
refineRange=5000;
timeInterval=1;
n=1;
flag=0;
short_line=ones(50,1);
ccc=1;
for i=1:timeInterval:(length(z)-short-long-refineRange)%the last dot must not exceed the scope.
   for k=i:i+long
       sumlong = sumlong + abs(z(k));
   end
   for j=k-short:k
       sumshort = sumshort + abs(z(j));
       short_line(ccc,1)=abs(z(j));
       ccc=ccc+1;
   end
   ccc=1;
   meanshort = sumshort/short;
   meanlong = sumlong/long;
   ratio(n) = meanshort/meanlong;
   short_max=sum(short_line)/50;
   if(ratio(n)>1.4 && short_max>500)
       for j=i+long:i+roughRange+long
           sumrough = sumrough + abs(z(j));
       end
       meanrough = sumrough/roughRange;
       if(meanrough>=1000)
           for j=i+long:i+refineRange+long
               sumrefine = sumrefine + abs(z(j));
           end
           meanrefine = sumrefine/refineRange;
           if(meanrefine>=2000 && flag==0)
%            if(meanrefine>=2000)
               position(i+long)=1;
               flag=1;
%                fprintf('精细阈值: %f\n',meanrefine);% %f显示原有数据格式
           end
       else
           position(i+long)=0;
       end
   else
       position(i+long)=0;
   end
   sumshort=0;
   sumlong=0;
   sumrough=0;
   sumrefine=0;

   n=n+1;
end
