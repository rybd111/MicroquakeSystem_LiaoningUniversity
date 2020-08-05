M = load("D:\data\ConstructionData\5moti\rytzwu 2020-07-30 12-16-09`02.csv");
M = M(:,7);
% M=0;
M=M';
T_sim_2 = ELM(M);