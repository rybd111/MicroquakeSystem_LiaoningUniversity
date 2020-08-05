function varValue = varianceValue(z)
    for i=1:length(z(1,:))
        varValue(i) = var(z(:,i));
    end
end