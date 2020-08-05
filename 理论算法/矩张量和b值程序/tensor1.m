clc;
clear;
close all;
file = 'sample.txt';
a=load('I:\研究生阶段\矿山\理论算法\矩张量和b值程序\矩张量反演所需文件\fn_sta1.txt');
b=fopen('I:\研究生阶段\矿山\理论算法\矩张量和b值程序\矩张量反演所需文件\quake source info.txt');
c=fopen('I:\研究生阶段\矿山\理论算法\矩张量和b值程序\矩张量反演所需文件\channel info.txt');
fid=fopen('I:\研究生阶段\矿山\理论算法\矩张量和b值程序\矩张量反演所需文件\consequence.txt','wt');
tongdao=[];
i=1;

e1='Pdc';e2='dr';e3='l';e4='n';
fprintf(fid,'%s  %s  %s  %s  %s\n',e1,e2,e3,e4);
%浼犳劅鍣ㄥ潗鏍?
x011=a(1,2);x012=a(1,3);x013=a(1,4);
x021=a(2,2);x022=a(2,3);x023=a(2,4);
x031=a(3,2);x032=a(3,3);x033=a(3,4);
x041=a(4,2);x042=a(4,3);x043=a(4,4);
x051=a(5,2);x052=a(5,3);x053=a(5,4);
x061=a(6,2);x062=a(6,3);x063=a(6,4);
% x071=a(7,2);x072=a(7,3);x073=a(7,4);
% x081=a(8,2);x082=a(8,3);x083=a(8,4);
% x091=a(9,2);x092=a(9,3);x093=a(9,4);
% x101=a(10,2);x102=a(10,3);x103=a(10,4);
% x111=a(11,2);x112=a(11,3);x113=a(11,4);
% x121=a(12,2);x122=a(12,3);x123=a(12,4);
while ~feof(b)
    while ~feof(c)
        
         tlinec = fgetl(c);
    tlinec=str2num(tlinec);
     if tlinec(1,1)==i
         tongdao=[tongdao;tlinec]; 
     else
        tlineb = fgetl(b);
        tlineb=str2num(tlineb);
y1=tlineb(1,2);y2=tlineb(1,3);y3=tlineb(1,4);%闇囨簮瀹氫綅
% displacement=c(:,2);

% gamaji=(xi-x0i)/r;%xi涓哄彴绔欏悇鍧愭爣鍒嗛噺锛泋0i涓洪渿婧愬悇鍧愭爣鍒嗛噺锛況涓洪渿婧愬埌鍙扮珯鐨勮窛绂伙紱j琛ㄧず绗嚑閫氶亾锛沬=1锛?2锛?3琛ㄧず鍚勫潗鏍囪酱
gama011=(x011-y1)/(((x011-y1)^2+(x012-y2)^2+(x013-y3)^2)^0.5);gama012=(x012-y2)/(((x011-y1)^2+(x012-y2)^2+(x013-y3)^2)^0.5);gama013=(x013-y3)/(((x011-y1)^2+(x012-y2)^2+(x013-y3)^2)^0.5);
gama021=(x021-y1)/(((x021-y1)^2+(x022-y2)^2+(x023-y3)^2)^0.5);gama022=(x022-y2)/(((x021-y1)^2+(x022-y2)^2+(x023-y3)^2)^0.5);gama023=(x023-y3)/(((x021-y1)^2+(x022-y2)^2+(x023-y3)^2)^0.5);
gama031=(x031-y1)/(((x031-y1)^2+(x032-y2)^2+(x033-y3)^2)^0.5);gama032=(x032-y2)/(((x031-y1)^2+(x032-y2)^2+(x033-y3)^2)^0.5);gama033=(x033-y3)/(((x031-y1)^2+(x032-y2)^2+(x033-y3)^2)^0.5);
gama041=(x041-y1)/(((x041-y1)^2+(x042-y2)^2+(x043-y3)^2)^0.5);gama042=(x042-y2)/(((x041-y1)^2+(x042-y2)^2+(x043-y3)^2)^0.5);gama043=(x043-y3)/(((x041-y1)^2+(x042-y2)^2+(x043-y3)^2)^0.5);
gama051=(x051-y1)/(((x051-y1)^2+(x052-y2)^2+(x053-y3)^2)^0.5);gama052=(x052-y2)/(((x051-y1)^2+(x052-y2)^2+(x053-y3)^2)^0.5);gama053=(x053-y3)/(((x051-y1)^2+(x052-y2)^2+(x053-y3)^2)^0.5);
gama061=(x061-y1)/(((x061-y1)^2+(x062-y2)^2+(x063-y3)^2)^0.5);gama062=(x062-y2)/(((x061-y1)^2+(x062-y2)^2+(x063-y3)^2)^0.5);gama063=(x063-y3)/(((x061-y1)^2+(x062-y2)^2+(x063-y3)^2)^0.5);
% gama071=(x071-y1)/(((x071-y1)^2+(x072-y2)^2+(x073-y3)^2)^0.5);gama072=(x072-y2)/(((x071-y1)^2+(x072-y2)^2+(x073-y3)^2)^0.5);gama073=(x073-y3)/(((x071-y1)^2+(x072-y2)^2+(x073-y3)^2)^0.5);
% gama081=(x081-y1)/(((x081-y1)^2+(x082-y2)^2+(x083-y3)^2)^0.5);gama082=(x082-y2)/(((x081-y1)^2+(x082-y2)^2+(x083-y3)^2)^0.5);gama083=(x083-y3)/(((x081-y1)^2+(x082-y2)^2+(x083-y3)^2)^0.5);
% gama091=(x091-y1)/(((x091-y1)^2+(x092-y2)^2+(x093-y3)^2)^0.5);gama092=(x092-y2)/(((x091-y1)^2+(x092-y2)^2+(x093-y3)^2)^0.5);gama093=(x093-y3)/(((x091-y1)^2+(x092-y2)^2+(x093-y3)^2)^0.5);
% gama101=(x101-y1)/(((x101-y1)^2+(x102-y2)^2+(x103-y3)^2)^0.5);gama102=(x102-y2)/(((x101-y1)^2+(x102-y2)^2+(x103-y3)^2)^0.5);gama103=(x103-y3)/(((x101-y1)^2+(x102-y2)^2+(x103-y3)^2)^0.5);
% gama111=(x111-y1)/(((x111-y1)^2+(x112-y2)^2+(x113-y3)^2)^0.5);gama112=(x112-y2)/(((x111-y1)^2+(x112-y2)^2+(x113-y3)^2)^0.5);gama113=(x113-y3)/(((x111-y1)^2+(x112-y2)^2+(x113-y3)^2)^0.5);
% gama121=(x121-y1)/(((x121-y1)^2+(x122-y2)^2+(x123-y3)^2)^0.5);gama122=(x122-y2)/(((x121-y1)^2+(x122-y2)^2+(x123-y3)^2)^0.5);gama123=(x123-y3)/(((x121-y1)^2+(x122-y2)^2+(x123-y3)^2)^0.5);
gama=[gama011 gama012 gama013;gama021 gama022 gama023;gama031 gama032 gama033;gama041 gama042 gama043;gama051 gama052 gama053;gama061 gama062 gama063];
% displacement=c(:,2);
%闇囧姩浣嶇Щ鍦ㄥ悇涓柟鍚戜笂鐨勫垎閲?
% tlinec = fgetl(c);
% tlinec=str2num(tlinec);
% displacement1=tlinec(1,4)*gama011;displacement2=tlinec(1,4)*gama012;displacement3=tlinec(1,4)*gama013;
% tlinec = fgetl(c);
% tlinec=str2num(tlinec);
% displacement4=tlinec(1,4)*gama021;displacement5=tlinec(1,4)*gama022;displacement6=tlinec(1,4)*gama023;
% tlinec = fgetl(c);
% tlinec=str2num(tlinec);
% displacement7=tlinec(1,4)*gama031;displacement8=tlinec(1,4)*gama032;displacement9=tlinec(1,4)*gama033;
% % tlinec = fgetl(c);
% % tlinec=str2num(tlinec);
% % displacement10=tlinec(1,4)*gama041;displacement11=tlinec(1,4)*gama042;displacement12=tlinec(1,4)*gama043;
% tlinec = fgetl(c);
% tlinec=str2num(tlinec);
% displacement13=tlinec(1,4)*gama051;displacement14=tlinec(1,4)*gama052;displacement15=tlinec(1,4)*gama053;
% % tlinec = fgetl(c);
% % tlinec=str2num(tlinec);
% % displacement16=tlinec(1,4)*gama061;displacement17=tlinec(1,4)*gama062;displacement18=tlinec(1,4)*gama063;
% % tlinec = fgetl(c);
% % tlinec=str2num(tlinec);
% % displacement19=tlinec(1,4)*gama071;displacement20=tlinec(1,4)*gama072;displacement21=tlinec(1,4)*gama073;
% tlinec = fgetl(c);
% tlinec=str2num(tlinec);
% displacement22=tlinec(1,4)*gama081;displacement23=tlinec(1,4)*gama082;displacement24=tlinec(1,4)*gama083;
% 
% displacement=[displacement1;displacement2;displacement3
%               displacement4;displacement5;displacement6
%               displacement7;displacement8;displacement9
%               displacement13;displacement14;displacement15
%               displacement22;displacement23;displacement24];
%%閲嶆柊鍐欑殑displacement
 
         tongdao=sortrows(tongdao,2);%鎺掑簭
         [m,n]=size(tongdao);
         displacement=ones(m,3)*nan;
         tdh=tongdao(:,2);
         fd=tongdao(:,4);
         for r=1:3
             displacement(:,r)=fd;
         end
         gama1=gama(tdh,:);
         displacement=displacement.*gama1;
         displacement=reshape(displacement,3*m,1);


g11=gama011*gama011*gama011;g12=2*gama011*gama011*gama012;g13=2*gama011*gama011*gama013;g14=gama011*gama012*gama012;g15=2*gama011*gama012*gama013;g16=gama011*gama013*gama013;
g21=gama012*gama011*gama011;g22=2*gama012*gama011*gama012;g23=2*gama012*gama011*gama013;g24=gama012*gama012*gama012;g25=2*gama012*gama012*gama013;g26=gama012*gama013*gama013;
g31=gama013*gama011*gama011;g32=2*gama013*gama011*gama012;g33=2*gama013*gama011*gama013;g34=gama013*gama012*gama012;g35=2*gama013*gama012*gama013;g36=gama013*gama013*gama013;
g41=gama021*gama021*gama021;g42=2*gama021*gama021*gama022;g43=2*gama021*gama021*gama023;g44=gama021*gama022*gama022;g45=2*gama021*gama022*gama023;g46=gama021*gama023*gama023;
g51=gama022*gama021*gama021;g52=2*gama022*gama021*gama022;g53=2*gama022*gama021*gama023;g54=gama022*gama022*gama022;g55=2*gama022*gama022*gama023;g56=gama022*gama023*gama023;
g61=gama023*gama021*gama021;g62=2*gama023*gama021*gama022;g63=2*gama023*gama021*gama023;g64=gama023*gama022*gama022;g65=2*gama023*gama022*gama023;g66=gama023*gama023*gama023;
g71=gama031*gama031*gama031;g72=2*gama031*gama031*gama032;g73=2*gama031*gama031*gama033;g74=gama031*gama032*gama032;g75=2*gama031*gama032*gama033;g76=gama031*gama033*gama033;
g81=gama032*gama031*gama031;g82=2*gama032*gama031*gama032;g83=2*gama032*gama031*gama033;g84=gama032*gama032*gama032;g85=2*gama032*gama032*gama033;g86=gama032*gama033*gama033;
g91=gama033*gama031*gama031;g92=2*gama033*gama031*gama032;g93=2*gama033*gama031*gama033;g94=gama033*gama032*gama032;g95=2*gama033*gama032*gama033;g96=gama033*gama033*gama033;
g101=gama041*gama041*gama041;g102=2*gama041*gama041*gama042;g103=2*gama041*gama041*gama043;g104=gama041*gama042*gama042;g105=2*gama041*gama042*gama043;g106=gama041*gama043*gama043;
g111=gama042*gama041*gama041;g112=2*gama042*gama041*gama042;g113=2*gama042*gama041*gama043;g114=gama042*gama042*gama042;g115=2*gama042*gama042*gama043;g116=gama042*gama043*gama043;
g121=gama043*gama041*gama041;g122=2*gama043*gama041*gama042;g123=2*gama043*gama041*gama043;g124=gama043*gama042*gama042;g125=2*gama043*gama042*gama043;g126=gama043*gama043*gama043;
g131=gama051*gama051*gama051;g132=2*gama051*gama051*gama052;g133=2*gama051*gama051*gama053;g134=gama051*gama052*gama052;g135=2*gama051*gama052*gama053;g136=gama051*gama053*gama053;
g141=gama052*gama051*gama051;g142=2*gama052*gama051*gama052;g143=2*gama052*gama051*gama053;g144=gama052*gama052*gama052;g145=2*gama052*gama052*gama053;g146=gama052*gama053*gama053;
g151=gama053*gama051*gama051;g152=2*gama053*gama051*gama052;g153=2*gama053*gama051*gama053;g154=gama053*gama052*gama052;g155=2*gama053*gama052*gama053;g156=gama053*gama053*gama053;
g161=gama061*gama061*gama061;g162=2*gama061*gama061*gama062;g163=2*gama061*gama061*gama063;g164=gama061*gama062*gama062;g165=2*gama061*gama062*gama063;g166=gama061*gama063*gama063;
g171=gama062*gama061*gama061;g172=2*gama062*gama061*gama062;g173=2*gama062*gama061*gama063;g174=gama062*gama062*gama062;g175=2*gama062*gama062*gama063;g176=gama062*gama063*gama063;
g181=gama063*gama061*gama061;g182=2*gama063*gama061*gama062;g183=2*gama063*gama061*gama063;g184=gama063*gama062*gama062;g185=2*gama063*gama062*gama063;g186=gama063*gama063*gama063;
% g191=gama071*gama071*gama071;g192=2*gama071*gama071*gama072;g193=2*gama071*gama071*gama073;g194=gama071*gama072*gama072;g195=2*gama071*gama072*gama073;g196=gama071*gama073*gama073;
% g201=gama072*gama071*gama071;g202=2*gama072*gama071*gama072;g203=2*gama072*gama071*gama073;g204=gama072*gama072*gama072;g205=2*gama072*gama072*gama073;g206=gama072*gama073*gama073;
% g211=gama073*gama071*gama071;g212=2*gama073*gama071*gama072;g213=2*gama073*gama071*gama073;g214=gama073*gama072*gama012;g215=2*gama073*gama072*gama073;g216=gama073*gama073*gama073;
% g221=gama081*gama081*gama081;g222=2*gama081*gama081*gama082;g223=2*gama081*gama081*gama083;g224=gama081*gama082*gama082;g225=2*gama081*gama082*gama083;g226=gama081*gama083*gama083;
% g231=gama082*gama081*gama081;g232=2*gama082*gama081*gama082;g233=2*gama082*gama081*gama083;g234=gama082*gama082*gama082;g235=2*gama082*gama082*gama083;g236=gama082*gama083*gama083;
% g241=gama083*gama081*gama081;g242=2*gama083*gama081*gama082;g243=2*gama083*gama081*gama083;g244=gama083*gama082*gama082;g245=2*gama083*gama082*gama083;g246=gama083*gama083*gama083;
% g251=gama091*gama091*gama091;g252=2*gama091*gama091*gama092;g253=2*gama091*gama091*gama093;g254=gama091*gama092*gama092;g255=2*gama091*gama092*gama093;g256=gama091*gama093*gama093;
% g261=gama092*gama091*gama091;g262=2*gama092*gama091*gama092;g263=2*gama092*gama091*gama093;g264=gama092*gama092*gama092;g265=2*gama092*gama092*gama093;g266=gama092*gama093*gama093;
% g271=gama093*gama091*gama091;g272=2*gama093*gama091*gama092;g273=2*gama093*gama091*gama093;g274=gama093*gama092*gama092;g275=2*gama093*gama092*gama093;g276=gama093*gama093*gama093;
% g281=gama101*gama101*gama101;g282=2*gama101*gama101*gama102;g283=2*gama101*gama101*gama103;g284=gama101*gama102*gama102;g285=2*gama101*gama102*gama103;g286=gama101*gama103*gama103;
% g291=gama102*gama101*gama101;g292=2*gama102*gama101*gama102;g293=2*gama102*gama101*gama103;g294=gama102*gama102*gama102;g295=2*gama102*gama102*gama103;g296=gama102*gama103*gama103;
% g301=gama103*gama101*gama101;g302=2*gama103*gama101*gama102;g303=2*gama103*gama101*gama103;g304=gama103*gama102*gama102;g305=2*gama103*gama102*gama103;g306=gama103*gama103*gama103;
% g311=gama111*gama111*gama111;g312=2*gama111*gama111*gama112;g313=2*gama111*gama111*gama113;g314=gama111*gama112*gama112;g315=2*gama111*gama112*gama113;g316=gama111*gama113*gama113;
% g321=gama112*gama111*gama111;g322=2*gama112*gama111*gama112;g323=2*gama112*gama111*gama113;g324=gama112*gama112*gama112;g325=2*gama112*gama112*gama113;g326=gama112*gama113*gama113;
% g331=gama113*gama111*gama111;g332=2*gama113*gama111*gama112;g333=2*gama113*gama111*gama113;g334=gama113*gama112*gama112;g335=2*gama113*gama112*gama113;g336=gama113*gama113*gama113;
% g341=gama121*gama121*gama121;g342=2*gama121*gama121*gama122;g343=2*gama121*gama121*gama123;g344=gama121*gama122*gama122;g345=2*gama121*gama122*gama123;g346=gama121*gama123*gama123;
% g351=gama122*gama121*gama121;g352=2*gama122*gama121*gama122;g353=2*gama122*gama121*gama123;g354=gama122*gama122*gama122;g355=2*gama122*gama122*gama123;g356=gama122*gama123*gama123;
% g361=gama123*gama121*gama121;g362=2*gama123*gama121*gama122;g363=2*gama123*gama121*gama123;g364=gama123*gama122*gama122;g365=2*gama123*gama122*gama123;g366=gama123*gama123*gama123;


coordinate1=[g11 g12 g13 g14 g15 g16;g21 g22 g23 g24 g25 g26;g31 g32 g33 g34 g35 g36]./(((x011-y1)^2+(x012-y2)^2+(x013-y3)^2)^0.5);
coordinate2=[g41 g42 g43 g44 g45 g46;g51 g52 g53 g54 g55 g56;g61 g62 g63 g64 g65 g66]./(((x021-y1)^2+(x022-y2)^2+(x023-y3)^2)^0.5);
coordinate3=[g71 g72 g73 g74 g75 g76;g81 g82 g83 g84 g85 g86;g91 g92 g93 g94 g95 g96]./(((x031-y1)^2+(x032-y2)^2+(x033-y3)^2)^0.5);
coordinate4=[g101 g102 g103 g104 g105 g106;g111 g112 g113 g114 g115 g116;g121 g122 g123 g124 g125 g126]./(((x041-y1)^2+(x042-y2)^2+(x043-y3)^2)^0.5);
coordinate5=[g131 g132 g133 g134 g135 g136;g141 g142 g143 g144 g145 g146;g151 g152 g153 g154 g155 g156]./(((x051-y1)^2+(x052-y2)^2+(x053-y3)^2)^0.5);
coordinate6=[g161 g162 g163 g164 g165 g166;g171 g172 g173 g174 g175 g176;g181 g182 g183 g184 g185 g186]./(((x061-y1)^2+(x062-y2)^2+(x063-y3)^2)^0.5);
% coordinate7=[g191 g192 g193 g194 g195 g196;g201 g202 g203 g204 g205 g206;g211 g212 g213 g214 g215 g216]./(((x071-y1)^2+(x072-y2)^2+(x073-y3)^2)^0.5);
% coordinate8=[g221 g222 g223 g224 g225 g226;g231 g232 g233 g243 g235 g236;g241 g242 g243 g244 g245 g246]./(((x081-y1)^2+(x082-y2)^2+(x083-y3)^2)^0.5);
% coordinate9=[g251 g252 g253 g254 g255 g256;g261 g262 g263 g264 g265 g266;g271 g272 g273 g274 g275 g276]./(((x091-y1)^2+(x092-y2)^2+(x093-y3)^2)^0.5);
% coordinate10=[g281 g282 g283 g284 g285 g286;g291 g292 g293 g294 g295 g296;g301 g302 g303 g304 g305 g306]./(((x101-y1)^2+(x102-y2)^2+(x103-y3)^2)^0.5);
% coordinate11=[g311 g312 g313 g314 g315 g316;g321 g322 g323 g324 g325 g326;g331 g332 g333 g334 g335 g336]./(((x111-y1)^2+(x112-y2)^2+(x113-y3)^2)^0.5);
% coordinate12=[g341 g342 g343 g344 g345 g346;g351 g352 g353 g354 g355 g356;g361 g362 g363 g364 g365 g366]./(((x121-y1)^2+(x122-y2)^2+(x123-y3)^2)^0.5);
coordinate=[coordinate1;coordinate2;coordinate3;coordinate4;coordinate5;coordinate6];%;coordinate7;coordinate8;coordinate9;coordinate7;coordinate8;coordinate9;coordinate10;coordinate11;coordinate12];
tdh1=[tdh*3-1;tdh*3-2;tdh*3];
tdh1=sortrows(tdh1);
coordinate=coordinate(tdh1,:);


density=2650;
velocity=4100;
Green=coordinate./(4*pi*density*(velocity^3));%coordinate涓哄潗鏍囩煩闃碉紱density涓哄瘑搴︼紱r涓洪渿婧愬埌鍙扮珯鐨勮窛绂伙紱velocity涓篜娉㈡尝閫?;*r
% Green=Green.^-1;
%inv(A'*A)*A'*B'
M=Green\displacement;%M涓虹煩寮犻噺锛沝isplacement涓轰綅绉籾锛汫reen涓烘牸鏋楀嚱鏁?
m11=M(1,1);m12=M(2,1);m13=M(3,1);m22=M(4,1);m23=M(5,1);m33=M(6,1);
m21=m12;m31=m13;m32=m23;
M1=[m11 m12 m13;m21 m22 m23;m31 m32 m33]
%%鐭╁紶閲忓垎瑙?
[V,D]=eig(M1);%D涓虹壒寰佸?硷紝V涓虹壒寰佸悜閲?;鐭╅樀V鐨勬瘡涓?鍒楀搴斾竴涓壒寰佸悜閲?
[D_sort,index] = sort(diag(D),'descend');%缁欑壒寰佸?兼帓搴?
D_sort = D_sort(index);
V_sort = V(:,index);
m1=D_sort(1,1);%鏈?澶х壒寰佸??
m2=D_sort(2,1);
m3=D_sort(3,1);%鏈?灏忕壒寰佸??
Miso=(m1+m2+m3)/3/m1;
Mdc=(m2-m3)/m1;
Mclvd=(2*m1+2*m3-4*m2)/3/m1;
Pdc=abs(Mdc)/(abs(Miso)+abs(Mdc)+abs(Mclvd))

%%鐮磋鏂逛綅鍙婄牬瑁傞潰
v1=V_sort(:,1); 
v2=V_sort(:,2);
v3=V_sort(:,3);
l=v1.*sqrt((m1-m2)/(m1-m3))+v3.*sqrt((m2-m3)/(m1-m3))%杩愬姩鏂瑰悜
n=v1.*sqrt((m1-m2)/(m1-m3))-v3.*sqrt((m2-m3)/(m1-m3))%娉曞悜鏂瑰悜

%%璁＄畻鐩稿瑁傜紳浣撶Н
mkk=m1+m2+m3;
% l=l';n=n';
% lk=sqrt(sum(l.*l));
% nk=sqrt(sum(n.*n));
fx=dot(l,n);
dr=mkk/fx;
dr=dr/(10^20);

% fprintf(fid,'%8.4f  %8.4f  %8.4f\n',M');
fprintf(fid,'%f  ',Pdc);
fprintf(fid,'%f  ',dr);
fprintf(fid,'%f  ',mkk);
fprintf(fid,'%f  ',fx);
fprintf(fid,'%8.4f  %8.4f  %8.4f ',l');
fprintf(fid,'%8.4f  %8.4f  %8.4f\n ',n');

  i=i+1;
         tongdao=[];
         tongdao=[tongdao;tlinec];
     end
    end
end
fclose(b);
fclose(c);
fclose(fid);

