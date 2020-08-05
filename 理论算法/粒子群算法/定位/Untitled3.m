% N = 100;
% v = rand (N,1);
function [tmax,tmin,vmax,vmin,vTotal,tTotal]=Untitled3(v)
v=v';
t = 0:length(v)-1;

% Lmax = diff(sign(diff(v))) == -2; % logic vector for the local max value
% Lmin = diff(sign(diff(v))) == 2; % logic vector for the local min value
v1 = diff(v);
v2 = sign(v1);
%use the slide window method to find the level curve, and reduce the
%extreme max value which greater than zero.
for i=2:length(v1)-10
    if(v1(i:i+10)==0)
        if(v1(i-1)>0)
            Lmax(i,1)=true;
            L(i,1)=true;%total array.
        end
    end
    if(v1(i:i+10)==0)
        if(v1(i-1)<0)
            Lmin(i,1)=true;
            L(i,1)=true;%total array.
        end
    end
end
i=2;
while(i<length(v2)-5)
    if(v2(i)==1 && v2(i+5)==-1 && v(i+4)>0)
        Lmax(i+4,1)=true;
        L(i+4,1)=true;%total array.
        i=i+5;
    end
    if(i<=length(v2)-5)
        if(v2(i)==-1 && v2(i+5)==1 && v(i+4)<0)
            Lmin(i+4,1)=true;
            L(i+4,1)=true;%total array.
            i=i+5;
        end
    else%diagnose the last window's content.
        i=i-5;
        if(v2(i)==-1 &&  v2(i+5)==1 && v(i+4)<0)
            Lmin(i+4,1)=true;
            L(i+4,1)=true;%total array.
            i=i+5;
        end
    end
    i=i+1;
end

% match the logic vector to the original vecor to have the same length
Lmax = [false; Lmax; false];% supple the elements in this variable.
Lmin = [false; Lmin; false];% supple the elements in this variable.
tmax = t (Lmax); % locations of the local max elements
tmin = t (Lmin); % locations of the local min elements
vmax = v (Lmax); % values of the local max elements
vmin = v (Lmin); % values of the local min elements
L = [false; L; false];
tTotal = t(L);
vTotal = v(L);
%merge the vmax and vmin
%merge the tmax and tmin





% for i=1:length(vmax)-1
%     if(v(tmax(i))<0)%将小于0的极大值去掉，但位置不对，必须从数组中移除他们
%        for j=i:length(tmax)-1
%          if(v(tmax(j))>0)
%             tmax(i)=tmax(j);%后一个覆盖前一个，然后后面依次串一个位置
%             vmax(i)=vmax(j);
%             break;
%          end
%        end
%     end
% end
% for i=1:length(vmin)-1
%     if(v(tmin(i))>0)%将大于0的极小值去掉，但位置不对，必须从数组中移除他们
%         for j=i:length(vmin)-1
%             if(v(tmin(j))<0)
%                 tmin(i)=tmin(j);%后一个覆盖前一个，然后后面依次串一个位置
%                 vmin(i)=vmin(j);
%                 break;
%             end
%         end
%     end
% end
% plot them on a figure
% plot(t,v);
% xlabel('t'); ylabel('v');
% hold on;
% plot(tmax, vmax, '-r*');
% plot(tmin, vmin, '-g*');
% plot([0,length(v)],[0,0],'r');%画直线

% plot(vmin);
% plot(vmax);
% str=[repmat('  X:',length(v,1) num2str(x') repmat(', Y:',,1) num2str(y')];
% plot(x,y,'-o')
hold off;
