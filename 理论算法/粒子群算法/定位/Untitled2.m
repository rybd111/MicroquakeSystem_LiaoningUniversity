clc;
clear all;
% str='2020-07-0702:42:58'
% fen=strsplit(str,':')
% a=char(fen(1))
% b=char(fen(2))
% c=char(fen(3))
% c=str2double(c)+1.6
% if c>=60
%       b=str2double(b)+1
%       b=num2str(b)
%       c=c-60
% end
% d=[a,':',b,':',num2str(c)]


line1=[
        41516934,4598553;
        41517126,4598553;
        41516933,4597045;
        41517128,4597045;
     ];
% scatter(line1(:,1),line1(:,2))
% hold on;
text((line1(1,1)+line1(4,1))/2,(line1(1,2)+line1(4,2))/2,'西二采区1208工作面')
line([line1(1,1),line1(2,1)],[line1(1,2),line1(2,2)],'linewidth',2,'color','r')
hold on;
line([line1(1,1),line1(3,1)],[line1(1,2),line1(3,2)],'linewidth',2,'color','r')
hold on;
line([line1(3,1),line1(4,1)],[line1(3,2),line1(4,2)],'linewidth',2,'color','r')
hold on;
line([line1(4,1),line1(2,1)],[line1(4,2),line1(2,2)],'linewidth',2,'color','r')
hold on;
line2=[41518275,4597614;
       41519228,4597615;
       41518274,4597435;
       41519228,4597435;
    ];
% scatter(line2(:,1),line2(:,2))
% hold on;
text((line2(1,1)+line2(4,1))/2,(line2(1,2)+line2(4,2))/2,'北二采区1210工作面')
line([line2(1,1),line2(2,1)],[line2(1,2),line2(2,2)],'linewidth',2,'color','c')
hold on;
line([line2(1,1),line2(3,1)],[line2(1,2),line2(3,2)],'linewidth',2,'color','c')
hold on;
line([line2(3,1),line2(4,1)],[line2(3,2),line2(4,2)],'linewidth',2,'color','c')
hold on;
line([line2(4,1),line2(2,1)],[line2(4,2),line2(2,2)],'linewidth',2,'color','c')
hold on;
line3=[
    41516897,4596105;
    41518438,4596105;
    41516896,4595906;
    41518438,4595906; 
    ];
% scatter(line3(:,1),line3(:,2))
% hold on;

text((line3(1,1)+line3(4,1))/2,(line3(1,2)+line3(4,2))/2,'北二采区703工作面')
line([line3(1,1),line3(2,1)],[line3(1,2),line3(2,2)],'linewidth',2,'color','m')
hold on;
line([line3(1,1),line3(3,1)],[line3(1,2),line3(3,2)],'linewidth',2,'color','m')
hold on;
line([line3(3,1),line3(4,1)],[line3(3,2),line3(4,2)],'linewidth',2,'color','m')
hold on;
line([line3(4,1),line3(2,1)],[line3(4,2),line3(2,2)],'linewidth',2,'color','m')
hold on;

