clear all
clc
a=load('射线选择.txt');
[tt,rr]=size(a);
n=1;
for i=1:tt 
    
if a(i,2)<69700||a(i,2)>69880
    a(i,:)=nan;
end
if a(i,3)<73000||a(i,3)>73400
    a(i,:)=nan;
end
if a(i,4)<-980||a(i,4)>-950
   a(i,:)=nan;
end
if a(i,1)~=6&&a(i,1)~=7&&a(i,1)~=10&&a(i,1)~=19
    a(i,:)=nan;
end
end
fid=fopen('E:\射线筛选.txt','w');
a =a(all(~isnan(a),2),:);
[tt,rr]=size(a);
for i=1:tt
    for j=1:rr
      fprintf(fid,'%f ',a(i,j));
    end
       fprintf(fid,'\n');
end
fclose(fid);	