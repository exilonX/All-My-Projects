if tip==4
		%spline natural
		e1=0;
		e2=0;
		E=zeros(1,2);
		q=2*pi/1001;
        
		for k=2:3
			n=2^k+1;
			x1=linspace(-pi,pi,n);
			fx1=zeros(1,n);
			for j=1:n
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
            r2=2^0.5;
            s=0;
            for j=0:n-1
                teta=2*j*pi/n;
                ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                s=s+ft;
            end
            a0=(r2/n)*s;
            n1=2^(k-1);
            a=zeros(1,n1);
            b=zeros(1,n1);
            for j=1:n1
                s=0;
                for i=1:n-1
                    teta=2*i*pi/n;
                    ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                    s=s+(ft*sin(j*teta));
                end
                b(j)=(2/n)*s;
                s=0;
                for i=0:n-1
                    teta=2*i*pi/n;
                    ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                    s=s+(ft*cos(j*teta));
                end
                a(j)=(2/n)*s;
            end
			j=1;
			s=0;
			for p=1:n-1
				while (x(j) < x1(p+1)) & (j<=1001)
					s=s+((fx(j)-(a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3)))^2);
					j=j+1;
				end	
			end	
			E(k-1)=(s*q)^0.5;
		end
		e2=E(1);
		e1=E(2);
		k=3; 
		N=8;
		nk=8;
		while e2-e1 > eps
			k=k+1;
			nk=2^k;
			A=eye(nk+1,nk+1);
			H=zeros(1,nk);
			B=zeros(nk+1,1);
			x1=linspace(-pi,pi,nk+1);
			e2=e1;
			e1=0;
			for j=1:nk+1
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			H=x1(2)-x1(1);
			for i=1:nk-1
				A(i+1,i+1)=4*H;
				A(i+1,i)=H;
				A(i+1,i+2)=H;
				B(i+1)=3*(fx1(i+2)-fx1(i+1))/H-3*(fx1(i+1)-fx1(i))/H;
			end
			c=zeros(1,nk+1);
			c=A\B;
			a=fx1;
			b(1:nk)=(a(2:nk+1)-a(1:nk))/H-H/3*(2*c(1:nk)+c(2:nk+1));
			d(1:nk)=(c(2:nk+1)-c(1:nk))/(3*H);
			j=1;
			s=0;
			for p=1:nk
				while (x(j) < x1(p+1)) & (j<=1001)
					s=s+((fx(j)-(a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3)))^2);
					j=j+1;
				end	
			end	
			e1=(s*q)^0.5;
			disp(e1);
			disp(e2);
			if e1>e2
				N=inf;
				break;
			end
			N=nk;
		end
		if N~=inf
			N=nk;
		end	
		
			
    end
	