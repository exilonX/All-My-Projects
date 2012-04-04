function A=test(eps)
    A=zeros(2,6);
for i=1:6
    [A(1,i),f]=eval_interpolator_c(i,eps);
    [A(2,i),f]=eval_interpolator_d(i,eps);
end
end
