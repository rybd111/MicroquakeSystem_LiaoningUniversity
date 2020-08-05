function shift = shiftValue(z)%2D tensor.
for i=1:length(z(1,:))
    k(i) = sum(z(:,i));
    shift(i)=k(i)/length(z(:,i));
end




