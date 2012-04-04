function [ N,fa] = eval_interpolator_c(tip, eps)
	
		% cele 1001 puncte 
		x=linspace(-pi,pi,1001);
		for j=1:1001
			fx(j)=exp(3*cos(x(j)))/(2*pi*besseli(0,3));
		end
	if tip==1
	% polinom de interpolare Lagrange 
			e1=0;
			e2=0; 
			i =0;
			x1=linspace(-pi,pi,5);
			h=2*pi/1001;
			
		 for j=1:5
			fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
		 end
		 fx1=fx1(:);
		 for j=1:5
			x1 = x1( : );
			v = ones( 5, 5 );
			for i = 2 : 5
				v( :, i ) = v( :, i - 1 ) .* x1;
			end
			a = v \ fx1;
		
		 end
		
		% a reprezinta coeficientii lagrange pt nr puncte =3 
		%calculam valoare pol lagrange in cele 1001 pcte pt gredul n=3
		for j=1:1001
			fxL=0;
			for k=1:5
				fxL=fxL+a(k)*(x(j)^(k-1));
            end
			% fxl valoarea punctului x1(j) pt coeficientii din a 
			e2=e2+(h*((fx(j)-fxL)^2));
		end
		e2=e2^0.5;
		
		% pt n=5
		
		
		
		
		x1=linspace(-pi,pi,9);
		fa=zeros(1,1001);
		 
		 for j=1:9
			fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
		 end
		 fx1=fx1(:);
		 for j=1:9
			x1 = x1( : );
			v = ones( 9, 9 );
			for i = 2 : 9
				v( :, i ) = v( :, i - 1 ) .* x1;
			end
			a = v \ fx1;
		
		 end
			
		% a reprezinta coeficientii lagrange pt nr puncte =3 
		%calculam valoare pol lagrange in cele 1001 pcte pt gredul n=3
		for j=1:1001
			fxL=0;
			for k=1:9
				fxL=fxL+a(k)*(x(j)^(k-1));
            end
            fa(j)=fxL;
			% fxl valoarea punctului x1(j) pt coeficientii din a 
			e1=e1+(((fx(j)-fxL)^2));
		end
		
		e1=(h*e1)^0.5;
		
		% pt gradul polinomului mai mare 
		i=3;
		N=8;
		nk=8;
		while e2-e1 > eps
			i=i+1;
			disp('aici');
			nk=2^i;
			h=(2*pi)/1001;
			x1=linspace(-pi,pi,nk+1);
			e2=e1;
			e1=0;
			for j=1:nk+1
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			for j=1:nk+1
			x1 = x1( : );
			v = ones( nk+1, nk+1 );
				for k = 2 : nk+1
					v( :, k ) = v( :, k - 1 ) .* x1;
				end
			a = v \ fx1;
			end
			
			for j=1:1001
				fxL=0;
				for k=1:nk+1
					fxL=fxL+a(k)*(x(j)^(k-1));
                end
                fa(j)=fxL;
			% fxl valoarea punctului x1(j) pt coeficientii din a 
			e1=e1+(h*((fx(j)-fxL)^2));
			end
			e1=e1^0.5;
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
	
	
	if tip==2
	% pt Newton
		e1=0;
		e2=0;
		i =0;
		x1=linspace(-pi,pi,5);
		h=2*pi/1001;
			
		for j=1:5
			fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
		end
		fx1=fx1(:);
		
		%pentru polinom de gradul 2  
		
		z=fx1;
		x1=x1(:);
		for i=1:5
			z(i+1:5)=(z(i+1:5)-z(i))./(x1(i+1:5)-x1(i));
		end
		%calculul coeficientilor newton
		A=zeros(5,5);
		A(1,5)=z(1);
		c=zeros(5,1);
		for i=2:5
			A(i,5-i+1:5)=poly(x1(1:i-1))*z(i);
		end
		for i=1:5
			c(i) = sum(A(:,i));
		end
		% c vector cu coeficientii newton 
		for j=1:1001
			fxN=0;
			for k=1:5
				fxN=fxN+(c(k)*(x(j)^(5-k+1)));
            end
            fa(j)=fxN;
			
			% fxl valoarea punctului x1(j) pt coeficientii din a 
			e2=e2+(h*((fx(j)-fxN)^2));
		end
		e2=e2^0.5;
		
		% pt polinom de grad 4 
			
		x1=linspace(-pi,pi,9);
		for j=1:9
			fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
		end
		fx1=fx1(:);
		z=fx1;
		x1=x1(:);
		for i=1:9
			z(i+1:9)=(z(i+1:9)-z(i))./(x1(i+1:9)-x1(i));
		end
		%calculul coeficientilor newton
		A=zeros(9,9);
		A(1,9)=z(1);
		c=zeros(9,1);
		for i=2:9
			A(i,9-i+1:9)=poly(x1(1:i-1))*z(i);
		end
		for i=1:9
			c(i) = sum(A(:,i));
		end
		% c vector cu coeficientii newton 
		for j=1:1001
			fxN=0;
			for k=1:9
				fxN=fxN+(c(k)*(x(j)^(9-k+1)));
            end
            fa(j)=fxN;
		
			% fxl valoarea punctului x1(j) pt coeficientii din a 
			e1=e1+(h*((fx(j)-fxN)^2));
		end
		e1=e1^0.5;
		disp(e1);
        disp(e2);
		i=3;
		N=8;
		nk=8;
        if e1>e2 
            N=inf;
        end
		while e2-e1 > eps
			i=i+1;
			nk=2^i;
			h=(2*pi)/1001;
			x1=linspace(-pi,pi,nk+1);
			e2=e1;
			e1=0;
			for j=1:nk+1
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			z=fx1;
			x1=x1(:);
			for i=1:nk+1
				z(i+1:nk+1)=(z(i+1:nk+1)-z(i))./(x1(i+1:nk+1)-x1(i));
			end
			%calculul coeficientilor newton
			A=zeros(nk+1,nk+1);
			A(1,nk+1)=z(1);
			c=zeros(nk+1,1);
			for i=2:nk+1
				A(i,nk+2-i:nk+1)=poly(x1(1:i-1))*z(i);
			end
			for i=1:nk+1
				c(i) = sum(A(:,i));
			end
			% c vector cu coeficientii newton 
			for j=1:1001
				fxN=0;
				for k=1:nk+1
					fxN=fxN+(c(k)*(x(j)^(nk+2-k)));
                end
                fa(j)=fxN;
				% fxl valoarea punctului x1(j) pt coeficientii din a 
				e1=e1+(h*((fx(j)-fxN)^2));
			end
			e1=e1^0.5;
			if e1>e2
				N=inf;
                fa=zeros(1,1001);
				break;
			end
			N=nk;
		end
		if N~=inf
			N=nk;
        
        else
           fa=zeros(1,1001);
			end
	end	
	
	if tip == 3
	% spline liniare
		e1=0;
		e2=0;
		i =0;
		E=zeros(1,2);
		h=2*pi/1001;
		for k=2:3
			n=2^k+1;
			x1=linspace(-pi,pi,n);
			fx1=zeros(1,n);
			for j=1:n
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			for j=1:n-1
				a(j)=(fx1(j+1)-fx1(j))/(x1(j+1)-x1(j));
				b(j)=(x1(j+1)*fx1(j)-x1(j)*fx1(j+1))/(x1(j+1)-x1(j));
			end
			
			for j=1:1001
				fxs=0;
				for i=1:n-1
					if (x1(i)<=x(j))&(x(j)<x1(i+1))
						fxs=a(i)*x(j)+b(i);
					end
                end
                fa(j)=fxs;
				E(k-1)=E(k-1)+(h*((fx(j)-fxs)^2));
			end
			E(k-1)=E(k-1)^0.5;
		end
		e2=E(1);
		e1=E(2);
		i=3; 
		N=8;
		nk=8;
		while e2-e1 > eps
			i=i+1;
			nk=2^i;
			h=(2*pi)/1001;
			x1=linspace(-pi,pi,nk+1);
			e2=e1;
			e1=0;
			for j=1:nk+1
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			
			for j=1:nk
				a(j)=(fx1(j+1)-fx1(j))/(x1(j+1)-x1(j));
				b(j)=(x1(j+1)*fx1(j)-x1(j)*fx1(j+1))/(x1(j+1)-x1(j));
			end
			j=1;
			s=0;
			for p=1:nk
				while (x(j)<x1(p+1) & j<=1001)
					s=s+((fx(j)-(a(p)*x(j)+b(p)))^2);
					fa(j)=a(p)*x(j)+b(p);
                    j=j+1;
				end	
			end	
			e1=(s*h)^0.5;
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
	if tip==4
		%spline natural
		e1=0;
		e2=0;
		E=zeros(1,2);
		q=2*pi/1001;
        fa=zeros(1,1001);
		for k=2:3
			n=2^k+1;
			A=eye(n,n);
			H=zeros(1,n-1);
			B=zeros(n,1);
			x1=linspace(-pi,pi,n);
			fx1=zeros(1,n);
			for j=1:n
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			H=x1(2)-x1(1);

			for i=1:n-2
				A(i+1,i+1)=4*H;
				A(i+1,i)=H;
				A(i+1,i+2)=H;
				B(i+1)=3*(fx1(i+2)-fx1(i+1))/H-3*(fx1(i+1)-fx1(i))/H;
			end
			c=zeros(1,n);
			c=A\B;
			a=fx1;
			b(1:n-1)=(a(2:n)-a(1:n-1))/H-H/3*(2*c(1:n-1)+c(2:n));
			d(1:n-1)=(c(2:n)-c(1:n-1))/(3*H);
			
			j=1;
			s=0;
			for p=1:n-1
				while (x(j) < x1(p+1)) & (j<=1001)
					s=s+((fx(j)-(a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3)))^2);
					fa(j)=a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3);
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
					 fa(j)=a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3);
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
	
    
    if tip==5 
        %spline tensionat 
		e1=0;
		e2=0;
        fa=zeros(1,1001);
		E=zeros(1,2);
		q=2*pi/1001;
		E=zeros(1,2);
		for k=2:3
			n=2^k+1;
			A=eye(n,n);
			H=zeros(1,n-1);
			B=zeros(n,1);
			x1=linspace(-pi,pi,n);
			fx1=zeros(1,n);
			for j=1:n
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
			H=x1(2)-x1(1);
            fpx0=(fx1(2)-fx1(1))/(x1(2)-x1(1));
            fpxn=(fx1(n)-fx1(n-1))/(x1(n)-x1(n-1));
			B(1)=3*(fx1(2)-fx1(1))/H-3*fpx0;
            B(n)=3*fpxn-3*(fx1(n)-fx1(n-1))/H;    
            A(1,1)=2*H;
            A(1,2)=H;
            A(n,n)=2*H;
            A(n,n-1)=H;
			for i=1:n-2
				A(i+1,i+1)=4*H;
				A(i+1,i)=H;
				A(i+1,i+2)=H;
				B(i+1)=3*(fx1(i+2)-fx1(i+1))/H-3*(fx1(i+1)-fx1(i))/H;
			end
			c=zeros(1,n);
			c=A\B;
			a=fx1;
			b(1:n-1)=(a(2:n)-a(1:n-1))/H-H/3*(2*c(1:n-1)+c(2:n));
			d(1:n-1)=(c(2:n)-c(1:n-1))/(3*H);
			
			j=1;
			s=0;
			for p=1:n-1
				while (x(j) < x1(p+1)) & (j<=1001)
					s=s+((fx(j)-(a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3)))^2);
					fa(j)=a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3);
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
            fpx0=(fx1(2)-fx1(1))/(x1(2)-x1(1));
            fpxn=(fx1(nk+1)-fx1(nk))/(x1(nk+1)-x1(nk));
			B(1)=3*(fx1(2)-fx1(1))/H-3*fpx0;
            B(nk+1)=3*fpxn-3*(fx1(nk+1)-fx1(nk))/H;    
            A(1,1)=2*H;
            A(1,2)=H;
            A(nk+1,nk+1)=2*H;
            A(nk+1,nk)=H;
            
            
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
					fa(j)=a(p)+b(p)*(x(j)-x1(p))+c(p)*((x(j)-x1(p))^2)+d(p)*((x(j)-x1(p))^3);
                    j=j+1;
				end	
			end	
			e1=(s*q)^0.5;
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
	
	    if tip==6
        % aproximare trigonometrica
		e1=0;
		e2=0;
        fa=zeros(1,1001);
		E=zeros(1,2);
		h=2*pi/1001;
        
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
			s1=0;
            % calcularea valorii 
            for j=1:1001
                s=a0/r2;
                for i=1:n1
                    s=s+b(i)*sin(i*x(j))+a(i)*cos(i*x(j));
                end
                fa(j)=s;
                
                s1=s1+(h*((fx(j)-s)^2));
            end
			E(k-1)=(s1)^0.5;
        end
		e2=E(1);
		e1=E(2);
        
		k=3; 
		N=8;
		nk=8;
		while e2-e1 > eps
			k=k+1;
			nk=2^k;
			x1=linspace(-pi,pi,nk+1);
			e2=e1;
			e1=0;
			for j=1:nk+1
				fx1(j)=exp(3*cos(x1(j)))/(2*pi*besseli(0,3));
			end
			fx1=fx1(:);
            
            
            
            r2=2^0.5;
            s=0;
            for j=0:nk
                teta=2*j*pi/(nk+1);
                ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                s=s+ft;
            end
            a0=(r2/(nk+1))*s;
            n1=2^(k-1);
            a=zeros(1,n1);
            b=zeros(1,n1);
            for j=1:n1
                s=0;
                for i=1:nk
                    teta=2*i*pi/(nk+1);
                    ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                    s=s+(ft*sin(j*teta));
                end
                b(j)=(2/(nk+1))*s;
                s=0;
                for i=0:nk
                    teta=2*i*pi/(nk+1);
                    ft=exp(3*cos(teta))/(2*pi*besseli(0,3));
                    s=s+(ft*cos(j*teta));
                end
                a(j)=(2/(nk+1))*s;
            end
			s1=0;
            % calcularea valorii 
            for j=1:1001
                s=a0/r2;
                for i=1:n1
                    s=s+b(i)*sin(i*x(j))+a(i)*cos(i*x(j));
                end
                fa(j)=s;
                s1=s1+(h*((fx(j)-s)^2));
            end
			e1=(s1)^0.5;
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
	
	
	
	
end