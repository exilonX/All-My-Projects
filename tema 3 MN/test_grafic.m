function test_grafic(eps)
x=linspace(-pi,pi,1001);
for j=1:1001
			fx(j)=exp(3*cos(x(j)))/(2*pi*besseli(0,3));
end
    subplot(1,2,1) , plot(x,fx,'r');
    hold on;
    
    [N,fa]=eval_interpolator_c(1,eps);
    plot(x,fa,'b');
    hold on;
    
    [N,fa]=eval_interpolator_c(2,eps);
    if N~=inf
    plot(x,fa,'g');
    end
    hold on;
    [N,fa]=eval_interpolator_c(3,eps);
    plot(x,fa,'y');
    hold on;
    [N,fa]=eval_interpolator_c(4,eps);
    plot(x,fa,'m');
    hold on;
    [N,fa]=eval_interpolator_c(5,eps);
    plot(x,fa,'c');
    hold on;
    [N,fa]=eval_interpolator_c(6,eps);
    plot(x,fa,'k');
     
a=load('sunspot.dat');
subplot(1,2,2), plot(a(1,:),a(2,:),'r');
    
end
    
    
    
    